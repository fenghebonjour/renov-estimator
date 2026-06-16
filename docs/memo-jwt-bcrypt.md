# Technical Memo: Authentication Security — JWT & BCrypt

**Project:** Renov Estimator  
**Date:** 2026-06-16  
**Topic:** How user authentication works end-to-end

---

## 1. BCrypt Password Hashing

### What is BCrypt?

BCrypt is an **adaptive password-hashing function** specifically designed to secure user passwords in databases. Created by **Niels Provos and David Mazières in 1999**, it is based on the Blowfish cipher and remains an industry standard for password security.

#### Core features

**Adaptive cost factor**
BCrypt uses a configurable work factor (the `$12$` in the hash string). As hardware gets faster, developers can raise this number to make hashing slower, directly neutralising the speed advantage that modern GPUs give to brute-force attackers. The cost is stored inside the hash itself, so old hashes remain verifiable while new ones use the higher cost.

**Automatic salting**
BCrypt generates a unique random salt for every password automatically. Two users with the same password produce completely different hashes, making pre-computed rainbow table attacks useless.

**One-way hashing**
BCrypt is not an encryption algorithm — there is no decryption step. Verification works by re-hashing the candidate password with the salt extracted from the stored hash and comparing the results byte-for-byte.

#### Limitations to be aware of

**Resource intensive by design**
BCrypt is intentionally slow. It should never be used for general data encryption or high-frequency hashing (e.g. hashing files or search indexes). Use it only for passwords.

**72-byte input limit**
BCrypt silently truncates input at 72 bytes. Any characters beyond that limit are ignored. For applications that allow very long passwords, the recommended mitigation is to pre-hash the password with SHA-256 before passing it to BCrypt, converting it to a fixed 32-byte input well within the limit.

---

### What is stored in the database

Passwords are never stored as plain text. When a user is created, the plain-text password is passed through the BCrypt algorithm, and only the resulting hash string is saved in the `app_user` table.

**Example — username `client001`, password `client001`:**

```
Plain text :  client001
Stored hash:  $2b$12$AQJhajaC2mHREaC9e7tuyei7Gxs3h74bGMiZVW0fyJ6/aTF6UGS8C
```

### Anatomy of a BCrypt hash

```
$2b$12$AQJhajaC2mHREaC9e7tuyei7Gxs3h74bGMiZVW0fyJ6/aTF6UGS8C
 |   |  |______________________|______________________________|
 |   |         salt (22 chars)        digest (31 chars)
 |   cost factor: 2^12 = 4 096 rounds
 algorithm version (2b = standard BCrypt)
```

| Segment | Purpose |
|---|---|
| `$2b$` | Algorithm identifier |
| `$12$` | Cost: 4 096 hashing iterations per attempt |
| Next 22 chars | Random salt, generated fresh on every `encode()` call |
| Remaining chars | Hash digest — output of BCrypt(password + salt) |

### Origin of the terms "salt" and "digest"

The two words are metaphors borrowed from cooking and biology, coined decades apart to name abstract mathematical concepts.

---

#### Salt

The term was coined in **1979 by Robert Morris and Ken Thompson** (co-creator of Unix) as part of their redesign of the Unix password system.

**The cooking analogy**
In a kitchen, sprinkling salt onto a dish changes its flavour. Cook the exact same base meal twice, add a different pinch of salt each time, and the two results taste entirely different — even though the base ingredient is identical.

**The cryptographic meaning**
Before salting existed, if two users both chose `password123`, their stored hashes were byte-for-byte identical. An attacker could pre-compute a table of common passwords and their hashes (a *rainbow table*) and instantly look up any match.

Adding a unique random string — the salt — to each password before hashing makes every output unique, even for identical inputs. The same `client001` password hashed twice produces two completely different strings because a new salt is generated each time. A rainbow table becomes useless: the attacker would need a separate table for every possible salt value.

The salt is not secret; it is stored alongside the hash in the same DB column. Its purpose is randomness, not secrecy.

---

#### Digest

The term *message digest* was popularised in **1991 by Ronald Rivest** (the "R" in RSA) when he published the MD5 algorithm. His specification used it as the formal name for a hash output.

**The biological analogy**
The stomach takes a complex meal — a hamburger, say — and breaks it down, dissolves it, and converts it into a compact, unrecognisable byproduct. You cannot reconstruct the original hamburger from that byproduct.

**The cryptographic meaning**
A hashing algorithm takes an arbitrarily large input (a password, a file, a JWT payload) and "digests" it down into a fixed-size string of characters. Two key properties follow directly from this analogy:

- **One-way:** Just as digestion is irreversible, you cannot reverse-engineer the original data from the digest.
- **Avalanche effect:** Change even a single character in the input and the entire digest changes completely — the same way a single missing ingredient produces a completely different meal.

A digest therefore acts as a unique digital fingerprint: it lets you verify that data is authentic without ever revealing the data itself.

---

#### Quick mental cheat sheet

| Term | Mental image | What it does |
|---|---|---|
| **Salt** | A pinch of randomness sprinkled on the input | Ensures identical inputs produce different outputs, defeating pre-computed attack tables |
| **Digest** | The condensed, irreversible byproduct | A fixed-size one-way fingerprint that proves data authenticity without exposing the original |

---

### Key properties

**One-way:** There is no decryption. The original password cannot be recovered from the hash.

**Non-deterministic output:** Calling `encode("client001")` twice produces two different strings because a new random salt is generated each time. Both hashes are valid for the same password.

**Verification without decryption:** `BCryptPasswordEncoder.matches(candidate, storedHash)` works by:
1. Extracting the salt embedded in `storedHash`
2. Running BCrypt on `candidate` using that same salt
3. Comparing the resulting digest to the one stored — no decryption involved

**Cost factor makes brute-force expensive:** At `$12$` (4 096 rounds), one guess takes ~100ms on modern hardware. One billion guesses would take years.

**Deterministic given fixed inputs:** BCrypt is a deterministic algorithm. Given the same password, salt, and cost factor, it always produces the same digest — this reproducibility is the entire mechanism behind `matches()`.

```
BCrypt("client001", salt="AQJhajaC2mHREaC9e7tuye", rounds=4096)
  → always → "i7Gxs3h74bGMiZVW0fyJ6/aTF6UGS8C"
```

The randomness only happens once, at registration, when a fresh salt is generated. After that, the salt is frozen inside the stored hash string, and every future `matches()` call re-runs the same deterministic computation with that frozen salt.

| Stage | Random? | Why |
|---|---|---|
| `encode("client001")` — call 1 | Yes, new salt generated | Prevents identical hashes for identical passwords |
| `encode("client001")` — call 2 | Yes, different new salt | Same reason |
| `matches("client001", storedHash)` | No | Must reproduce the exact same digest to compare — uses the salt already embedded in `storedHash` |

### The story of the smoothie bartender

Imagine a bartender who runs a highly secure members-only bar. He never writes down anyone's secret word. Instead, he runs it through a machine.

---

**Registration — the first visit**

You walk in and whisper your secret word (`client001`) to the bartender.

He does three things:

**1. The salt — a random pinch of exotic spices**
Before doing anything with your word, the bartender reaches into a jar and grabs a random handful of exotic spices. The handful is different every time, even for the same customer. He notes exactly which spices he grabbed and clips that note to your membership card for later. This is the **salt**.

**2. The cost — 4 096 cycles in the industrial blender**
He throws your secret word and the spice handful together into an industrial blender, then runs it for exactly 4 096 cycles — not one more, not one less. The machine is deliberately slow; each cycle takes time. By the time it finishes, about 100ms have passed. This deliberate slowness is the **cost factor** (`$12$`). If someone wanted to guess one million words, they would need 100ms × 1 000 000 = 27 hours per attempt.

**3. The digest — the unrecognisable paste**
What comes out of the blender is a thick, completely unrecognisable purple paste. No one looking at the paste can tell it started as `client001`. This paste is the **digest**.

The bartender scoops the paste into a jar, clips the spice note (salt) and machine setting (cost) to the outside, and stores it on the shelf under your name. He never writes down `client001` anywhere. The plain text is gone.

```
Jar label  →  cost=$12$  salt=AQJhajaC2mHREaC9e7tuye
Jar inside →  digest=i7Gxs3h74bGMiZVW0fyJ6/aTF6UGS8C
```

---

**Login — every return visit**

You come back and whisper a word to the bartender.

He picks your jar off the shelf, reads the spice note and machine setting from the label, throws your whispered word together with those exact same spices into the blender, and runs it for exactly 4 096 cycles again.

- If the resulting paste **matches** the paste inside the jar → you are in.
- If it **does not match** → rejected.

The bartender answered yes or no. He still does not know what your original word was. He only knows the pastes matched.

---

**What if two members both whisper `client001`?**

Because the bartender grabs a fresh random handful of spices each time, two members with the same secret word get completely different pastes. Their jars look nothing alike on the shelf.

**What if you forget your word?**

The bartender cannot help. There is no way to reverse a blender. He can only make you a brand new jar with a new word — which is exactly why every legitimate site says *reset your password*, not *here is your password*.

---

**The jar stored in the database**

```
$2b$      $12$      AQJhajaC2mHREaC9e7tuye      i7Gxs3h74bGMiZVW0fyJ6/aTF6UGS8C
 |         |                |                               |
algorithm  cost           salt                           digest
(bartender (4096 cycles)  (spice note clipped            (the paste
 version)                  to the jar)                    inside the jar)
```

The jar carries everything needed to repeat the process. The only thing missing — the only secret — is the word you whispered.

---

### Putting it all together

To protect privacy and sensitive information, plain-text passwords like `client001` are never stored in the database. Instead, the password is passed through BCrypt and converted into a **digest** that cannot be reversed back to the original text.

Even if two users register with the exact same password, a different random salt is generated for each, so their stored hashes look completely different. However, for any one user, given the exact same cost + password + salt, BCrypt always produces the exact same digest.

This means the value stored in the database is not a password — it is a self-contained record for future verification:

```
stored value = algorithm + cost + salt + digest
             = $2b$      + $12$ + AQJhajaC2mHREaC9e7tuye + i7Gxs3h74bGMiZVW0fyJ6/aTF6UGS8C
```

At login, BCrypt extracts the cost and salt from that stored value, re-runs the computation on the candidate password, and checks whether the resulting digest matches — all without ever knowing or storing the original plain text.

### Real-world implications

**Why "forgot password" resets instead of recovers**
Any site that can email you your existing password in plain text is storing it unprotected — a well-known security red flag. A site using BCrypt has no way to recover the original; the only option is to generate a new one. This is why every legitimate password flow says "reset your password", never "here is your password".

**The user is the only keeper of the plain text**
The database holds the hash. The algorithm holds no memory of the input. The server never logs or stores the plain text at any point in the process. Once the password leaves the user's keyboard, the plain text effectively ceases to exist on the server side.

**Authentication only answers yes or no**
`BCryptPasswordEncoder.matches()` returns a boolean. The server learns nothing about *what* the password is — only that whatever was typed produces the same digest as the one on file. The verification process knows your password is right or wrong, but does not know and has no need to know what it actually is.

**cost + password + salt is sufficient for verification**
Those three inputs fully determine the digest, deterministically. The stored value `$2b$12$<salt><digest>` carries two of the three (cost and salt) openly inside the hash string, so the only thing verification ever needs from the outside world is the password candidate itself.

> BCrypt transfers the burden of knowing the password entirely to the user. The system only ever needs to ask "does this candidate produce the same digest?" — never "what is the password?"

### What about Google Passwords and browser autofill?

The statement "the plain text ceases to exist on the server side" remains true — the web app server never stores it. But the **browser** operates on a completely separate layer, intercepting what you type before it ever reaches the server:

```
You type "client001"
       ↓
[Browser / Google Password Manager]  ← saves a copy HERE, client-side
       ↓
POST /auth/login { password: "client001" }
       ↓
[Web App Server]  ← receives it, BCrypt-hashes it, discards the plain text
```

Google Password Manager, Apple Keychain, 1Password, Bitwarden — these are all **client-side vaults**. They intercept the plain text at the browser level, encrypt it with their own key, and store it either locally on your device or in their cloud. The web application has no knowledge that this happened.

| Vault | Operated by | Where stored |
|---|---|---|
| Google Password Manager | Google | Google's cloud, synced across devices |
| Apple Keychain | Apple | iCloud / local device |
| 1Password / Bitwarden | Third-party companies | Their cloud (Bitwarden is open source) |
| Firefox password manager | Mozilla | Local + Firefox account sync |
| Windows Credential Manager | Microsoft | Local device |

**The security implication:** if your Google account is compromised, an attacker can access every password Google saved for you. The web application's BCrypt protection is irrelevant at that point — the attacker already has the plain text from the vault.

The web app protects the password **on the server side** using BCrypt. The browser/Google protects the password **on the client side** using their own vault. These are two independent layers, operated by completely different parties.

### Code reference

| Step | Location |
|---|---|
| Hash created at startup | `DataLoader.java:47` — `passwordEncoder.encode("client001")` |
| Verification during login | `SecurityConfig.java:44-45` — wires `BCryptPasswordEncoder` into `AuthenticationManager` |

---

## 2. BCrypt vs AES — Choosing the Right Tool

BCrypt and AES are both used to protect sensitive data, but they solve different problems. Using the wrong one leaves data either unreadable or unprotected.

### The story of two rooms

Imagine a high-security building with two rooms: the Blender Room and the Safe Room.

---

**The Blender Room — BCrypt**

When you create a password, a security guard throws it into an industrial blender together with a handful of random spices (the salt), then runs it for 4 096 cycles. What comes out is an unrecognisable paste. The guard writes the recipe — cost and salt — on the outside of the jar, stores it on a shelf, and throws away the original ingredients. The plain-text password is gone forever.

When you return to log in, the guard does not need to know what you originally put in. They take your new input, blend it with the same spices recorded on the jar, and compare the result to the paste inside. Match → you're in. No match → rejected. The guard answers yes or no, and learns nothing else.

If you forget what you threw in the blender — too bad. There is no way to reverse a blender. The guard can only hand you a new jar (reset your password).

---

**The Safe Room — AES**

When you store sensitive data such as your age (30), a security guard places it inside a steel safe and locks it with a master key. The safe sits in the vault. Whenever the system needs your age — for a birthday check, an eligibility filter, a display — the guard opens the safe with the master key and reads the value. The original data is always retrievable, as long as the key remains secret.

If someone steals the master key, every safe in the building is compromised. This is precisely why AES is wrong for passwords: one stolen key exposes every user's password in plain text, all at once.

---

**Why you cannot use the Blender Room for age**

If an attacker gets hold of your blended jar, they do not need to reverse the blender. They simply try every possible input: blend 0 and compare, blend 1 and compare, blend 2... By the time they reach 30, they have found the answer. That is 30 attempts — done in milliseconds. The blender provides no protection when the number of possible inputs is tiny.

**Why you cannot use the Safe Room for passwords**

If the master key is ever stolen, the attacker opens every safe and reads every password in plain text. The safe never forgets — which is exactly the problem.

---

| | Blender Room (BCrypt) | Safe Room (AES) |
|---|---|---|
| Original value recoverable? | Never | Yes, with the master key |
| What happens if compromised? | Digest is useless without the input | All stored values exposed |
| Right for passwords? | Yes | No |
| Right for age, SIN, etc.? | No — too few inputs, trivially brute-forced | Yes |

### BCrypt — one-way hashing (verification only)

BCrypt converts a value into a digest that **cannot be reversed**. It is the right choice when the application never needs to see the original value again — only to confirm whether a future candidate matches it.

- Use for: **passwords**
- Cannot be used for: anything that needs to be read, displayed, calculated, or queried

### AES — two-way encryption (store and retrieve)

AES (Advanced Encryption Standard) encrypts a value with a secret key and can decrypt it back to the original using the same key. It is the right choice when the application needs to store a sensitive value and retrieve it later.

- Use for: **age, SIN, credit card number, medical data, any sensitive field that must be read back**
- Cannot be used for: passwords (if the key is ever compromised, all passwords are exposed)

### Why BCrypt cannot protect low-entropy data like age

Even with a salt, BCrypt gives no meaningful protection to a value with a tiny input space. Age has at most ~120 possible values. An attacker with the stored hash can simply try all of them:

```
matches(0,   storedHash) → false
matches(1,   storedHash) → false
...
matches(30,  storedHash) → true  ← found in under a second
```

BCrypt is designed for **high-entropy** inputs like passwords where the search space is astronomically large. A 120-value space makes it trivially brute-forceable regardless of cost factor or salt.

### Side-by-side comparison

| | BCrypt | AES-256 |
|---|---|---|
| Direction | One-way (irreversible) | Two-way (encrypt / decrypt) |
| Key required | No | Yes — must be stored securely |
| Original value recoverable | Never | Yes, with the key |
| Suitable for passwords | Yes | No — key compromise exposes all |
| Suitable for age, SIN, etc. | No — tiny input space, brute-forceable | Yes |
| Suitable for range queries | No | Only with additional techniques |

### One-sentence rule

> Use **BCrypt** when you only ever need to *verify* a value — passwords.
> Use **AES encryption** when you need to *retrieve* a value — age, SIN, credit card number, medical data.

---

## 3. JWT Authentication Flow

JWT (JSON Web Token) is a secure, compact standard used to transmit information between two parties as a digitally signed object. It is most commonly used for authentication, API authorization, and secure information exchange in modern web and mobile applications. The server is **stateless** — it holds no session. Each request carries its own proof of identity in the token.

### Authentication vs JWT — what is the relationship?

They are not the same thing. **Authentication is the process; JWT is the result.**

```
Authentication  →  answers "who are you?" (once, at login)
JWT             →  carries proof of that answer (on every request after)
```

Without authentication first, there is no JWT. Without JWT, you would have to authenticate (re-enter your password) on every single API call.

```
Step 1 — Authentication (happens once)
  User submits username + password
  Server runs BCrypt.matches() → identity confirmed
  Server issues a JWT → "here is your proof"

Step 2 — JWT in use (every request after)
  User sends the JWT in the Authorization header
  Server verifies the signature → no password needed again
  Request is allowed
```

JWT exists specifically to avoid repeating authentication. Authentication is expensive — BCrypt takes ~100ms plus a DB query. JWT verification is cheap — one HMAC check, no DB call. JWT lets the server be **stateless**: it remembers nothing between requests, trusting the token to carry all necessary proof.

---

### The story of the airport boarding pass

The best way to understand JWT is to think of an airport.

---

**Check-in — Login (authentication)**

You arrive at the airport and walk up to the check-in counter. The agent asks for your passport (your username and password). They verify your identity against their records (BCrypt `matches()`). Once confirmed, they do not follow you around the airport for the rest of the day. Instead, they hand you a **boarding pass** — a small, self-contained document that proves who you are and what you are allowed to do.

That boarding pass is the **JWT**.

```
Boarding pass (JWT):
  Passenger : client001            ← payload: { sub: "client001" }
  Issued at : 09:00                ← payload: { iat: ... }
  Valid until: 09:00 next day      ← payload: { exp: ... }
  Barcode   : ████████████         ← signature (HMAC-SHA256)
```

The check-in agent never sees you again after this. The counter does not track where you go. This is what **stateless** means — the server issues the pass and forgets about you. Every checkpoint from here on trusts the pass itself, not a database lookup.

---

**Security, gate, lounge — API authorization**

Now you walk through the airport. Every checkpoint reads your boarding pass:

- **Security checkpoint** → scans the barcode, confirms it is genuine, lets you into the terminal.
- **Boarding gate** → reads your flight and seat, confirms you are on this flight.
- **Business lounge** → checks your class; economy passengers are turned away.

None of these checkpoints called the check-in counter. They each read the boarding pass independently and made their own decision. This is exactly how `JwtRequestFilter` works on every API request — it reads the token, verifies the signature, and decides whether to allow the request, all without touching a session store.

---

**The barcode — the signature**

Here is the key security property: the barcode on the boarding pass is printed by the airline's secure machine (the server's secret key). If you try to scratch out "Economy" and write "Business" by hand, the barcode no longer matches the text — every scanner will reject you instantly.

This is the JWT signature. The payload (`sub`, `iat`, `exp`) is readable by anyone — it is just base64, not encrypted. But if even a single character of the payload is changed after the token was issued, the HMAC-SHA256 signature no longer matches. The server detects the tampering and rejects the request.

The boarding pass is not secret — other passengers can see it. What matters is that **no one except the airline can print a valid barcode**.

---

**Expiry — the flight departs**

Your boarding pass is valid only until your flight departs. After that it is worthless; a new one must be issued. JWT works the same way: `exp: <issued-at + 24h>` means the token expires after 24 hours and the user must log in again. This limits the damage if a token is ever stolen.

---

**Single Sign-On (SSO) — one pass, multiple terminals**

Imagine your boarding pass is accepted not just at one terminal but at every terminal and partner airline across the entire airport network — without checking in again at each one. This is SSO: one JWT issued by a central authority, validated by many independent services. Because the token is self-contained and each service only needs the shared secret to verify the barcode, no central session server is required.

---

**What the JWT is and is not**

| | JWT (boarding pass) |
|---|---|
| Proves identity? | Yes — barcode (signature) was printed by the server |
| Contains sensitive secrets? | No — payload is readable, but unforgeable |
| Server tracks it? | No — stateless; the pass carries all needed info |
| Can it be tampered with? | No — any change breaks the signature |
| Does it last forever? | No — expires (`exp` claim); re-login issues a new one |
| Works across multiple services? | Yes — any service with the secret can verify it (SSO) |

### Phase 1 — Login (obtaining a token)

```
Browser                          Backend
  |                                 |
  |  POST /auth/login               |
  |  { username, password }  -----> |
  |                                 |
  |                         [1] AuthController receives request
  |                         [2] AuthenticationManager.authenticate()
  |                               → UserDetailsServiceImpl loads user from DB
  |                               → BCryptPasswordEncoder.matches() verifies password
  |                         [3] JwtUtil.generateToken() builds token
  |                         [4] UserRepo fetches userId + userType
  |                                 |
  |  { token, userId, userType } <--|
  |                                 |
  [5] localStorage.setItem(token, userId, userType)
```

**Step 3 in detail — what goes into the token:**

```
Header  (base64): { "alg": "HS256" }
Payload (base64): { "sub": "client001", "iat": <issued-at>, "exp": <issued-at + 24h> }
Signature       : HMAC-SHA256(header + "." + payload, secret)
```

The three parts are joined with `.` to form the compact JWT string. The secret key is defined in `application.properties:8`. Expiry is 24 hours (`jwt.expiration=86400000` ms).

> **Note:** `userId` and `userType` are **not** embedded in the token. They are returned alongside it in the login response and stored separately in `localStorage`.

### How the JWT secret relates to salt and digest

BCrypt and JWT both use a one-way function to produce an unrecognisable output, but they use it for different purposes and in different ways. Comparing the two reveals exactly what the JWT secret is — and what it is not.

---

**BCrypt recap**

```
random salt  +  password  →  BCrypt (4096 rounds)  →  digest
```

- The **salt** is random and unique per user. It is generated once and stored inside the hash.
- The **digest** proves "this candidate password matches the one that was originally registered."
- Purpose: verify that a user knows their password.

---

**JWT signature**

```
fixed secret  +  header.payload  →  HMAC-SHA256  →  signature (the digest)
```

- The **secret** (`RenovEstimatorSuperSecretKey...` in `application.properties:8`) is fixed and identical for every token ever issued. It is never stored inside the token.
- The **signature** is the HMAC-SHA256 output — it plays the role of a digest.
- Purpose: prove that this token was created by this server and has not been tampered with.

---

**Side-by-side**

| | BCrypt | JWT |
|---|---|---|
| One-way function | BCrypt (Blowfish-based) | HMAC-SHA256 |
| Random per record? | Yes — new salt every `encode()` | No — same secret for every token |
| The "salt" equivalent | Random salt embedded in the hash | No equivalent — the secret is fixed |
| The "digest" equivalent | Digest stored in the hash | Signature appended to the token |
| Secret stored with output? | Salt yes, password never | Secret never — only the signature |
| If the secret/key is stolen | Attacker can crack passwords offline | Attacker can forge any token |
| Verifier needs | The stored hash + candidate password | The secret + the token |

---

**The critical difference: salt is random, JWT secret is not**

BCrypt's salt is random so that two identical passwords never produce the same digest. The salt defeats pre-computed attack tables.

JWT's secret is intentionally fixed — every token must be verifiable by the same key. There is no "per-token salt." What protects JWT is the **secrecy of the key itself**: anyone who holds the secret can both create and verify tokens, so if the secret leaks, all tokens can be forged. This is why the secret must be long, random, and stored only in a secure config (environment variable, secret manager) — never in source code.

---

**The story: stamp vs blender**

Think of the JWT secret as a **wax seal stamp** owned exclusively by the server.

When a token is issued, the server stamps the payload with its seal (HMAC). The sealed token goes to the browser. On every future request, the server checks: "does this seal match my stamp?" If yes, the token is genuine. If someone altered even one character of the payload, the seal no longer matches — rejected.

The stamp never leaves the server. The browser holds the sealed letter (token) but cannot forge the stamp, because it does not have the stamp. The seal is the signature; the stamp is the secret.

BCrypt's blender, by contrast, takes a different random handful of spices each time. The JWT stamp uses the same ink every single time — its protection comes from nobody else having that stamp, not from randomness.

---

### Phase 2 — Authenticated requests (using the token)

```
Browser                          Backend
  |                                 |
  [1] AuthInterceptor clones every outgoing request
      → adds header: Authorization: Bearer <jwt>
  |                                 |
  |  GET /client/all                |
  |  Authorization: Bearer <jwt> -> |
  |                                 |
  |                         [2] JwtRequestFilter.doFilterInternal()
  |                               → strips "Bearer " prefix
  |                               → JwtUtil.extractUsername() parses token,
  |                                 verifies HMAC signature, returns "client001"
  |                               → loads UserDetails from DB
  |                               → JwtUtil.validateToken():
  |                                   username matches AND expiry > now
  |                               → sets SecurityContextHolder authentication
  |                         [3] Request proceeds to controller
  |                                 |
  |  200 OK + data <--------------- |
```

### Route protection

**Backend (`SecurityConfig.java:53-54`):**
- `/auth/**` — public, no token required (the login endpoint itself)
- All other routes — require a valid token; `JwtRequestFilter` must have set the security context

**Frontend (`AuthGuard.ts:10-11`):**
- Checks `localStorage` for a token before allowing Angular route navigation
- Redirects to `/login` if no token is present

### Client-side token decoding

`AuthService.getCurrentUsername()` reads the username from the token by base64-decoding the payload segment (`token.split('.')[1]`). This does **not** verify the signature — it is only a convenience read. Signature verification happens exclusively server-side in `JwtUtil.validateToken()`.

### Inspecting the JWT in the browser (F12)

**Network tab — catch it at login**
1. Open F12 → **Network** tab
2. Log in
3. Click the `login` request → **Response** — the raw JSON:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjbGllbnQwMDEiLCJpYXQiOjE3....",
  "userId": 1,
  "userType": "Client"
}
```

**Application tab — read it from localStorage**
1. F12 → **Application** tab
2. Left sidebar → **Local Storage** → `http://localhost:4200`
3. Key `token` holds the full JWT string

**Console tab — decode the payload instantly**

Paste this in the **Console**:
```js
JSON.parse(atob(localStorage.getItem('token').split('.')[1]))
```
Output:
```json
{ "sub": "client001", "iat": 1750000000, "exp": 1750086400 }
```

This is exactly what `AuthService.getCurrentUsername()` does at `auth.service.ts:49`.

**Decode all three segments**
```js
const [h, p, s] = localStorage.getItem('token').split('.');
console.log('Header: ',  JSON.parse(atob(h)));  // { alg: "HS256" }
console.log('Payload: ', JSON.parse(atob(p)));  // { sub, iat, exp }
console.log('Signature:', s);                   // raw base64 — not decodable
```

The signature (`s`) cannot be decoded into readable JSON — it is the raw HMAC-SHA256 bytes in base64. Only the server, holding the secret key (`application.properties:8`), can verify it.

---

## 4. Summary

| Concern | Mechanism | Where |
|---|---|---|
| Password storage | BCrypt hash (never plain text) | `app_user.password` column |
| Login credential check | `BCryptPasswordEncoder.matches()` | `AuthenticationManager` → `SecurityConfig` |
| Token generation | HMAC-SHA256 JWT, 24h expiry | `JwtUtil.generateToken()` |
| Token transmission | `Authorization: Bearer` header | `AuthInterceptor.ts` |
| Token validation per request | Signature + expiry check | `JwtRequestFilter.java` |
| Frontend route protection | Token presence check | `AuthGuard.ts` |