package com.renovSolution.renov.security;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtUtilTest {

    @Test
    void rejectsTheLeakedSecret() {
        JwtUtil jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", "RenovEstimatorSuperSecretKeyForJWTTokenGeneration2024!!LongEnough");

        assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(jwtUtil, "validateSecret"))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void rejectsBlankSecret() {
        JwtUtil jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", " ");

        assertThatThrownBy(() -> ReflectionTestUtils.invokeMethod(jwtUtil, "validateSecret"))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void acceptsANewSecret() {
        JwtUtil jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", "a-sufficiently-long-and-unique-test-secret-value");

        assertThatCode(() -> ReflectionTestUtils.invokeMethod(jwtUtil, "validateSecret"))
                .doesNotThrowAnyException();
    }
}
