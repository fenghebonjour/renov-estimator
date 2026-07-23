import { Component } from '@angular/core';

interface Scenario {
  id: string;
  label: string;
  severity: 'cpu' | 'lock' | 'deadlock';
  tagline: string;
  description: string;
  dump: string;
  symptoms: string[];
  diagnosis: string[];
  fix: string[];
  commands: string[];
}

const HIGH_CPU_DUMP = `2024-11-14 09:12:34
Full thread dump OpenJDK 64-Bit Server VM (11.0.21+9 mixed mode):

"pool-2-thread-1" #42 prio=5 os_prio=0 tid=0x00007f8a3c00b800 nid=0x3f18 runnable [0x00007f8a1c0fe000]
   java.lang.Thread.State: RUNNABLE
\tat com.renov.service.EstimateCalculator.recalcPrices(EstimateCalculator.java:187)
\tat com.renov.service.EstimateCalculator.processAllBids(EstimateCalculator.java:143)
\tat com.renov.service.ProjectBidService.triggerRecalculation(ProjectBidService.java:89)
\tat java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:290)
\tat java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1020)
\tat java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1594)

"pool-2-thread-2" #43 prio=5 os_prio=0 tid=0x00007f8a3c00c800 nid=0x3f19 runnable [0x00007f8a1bffe000]
   java.lang.Thread.State: RUNNABLE
\tat com.renov.service.EstimateCalculator.recalcPrices(EstimateCalculator.java:187)
\tat com.renov.service.EstimateCalculator.processAllBids(EstimateCalculator.java:143)
\tat com.renov.service.ProjectBidService.triggerRecalculation(ProjectBidService.java:89)
\tat java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:290)
\tat java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1020)
\tat java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1594)

"pool-2-thread-3" #44 prio=5 os_prio=0 tid=0x00007f8a3c00d800 nid=0x3f1a runnable [0x00007f8a1befe000]
   java.lang.Thread.State: RUNNABLE
\tat com.renov.service.EstimateCalculator.recalcPrices(EstimateCalculator.java:187)
\tat com.renov.service.EstimateCalculator.processAllBids(EstimateCalculator.java:143)
\tat com.renov.service.ProjectBidService.triggerRecalculation(ProjectBidService.java:89)
\tat java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:290)
\tat java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1020)
\tat java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1594)

"http-nio-8080-exec-1" #10 prio=5 os_prio=0 tid=0x00007f8a3800a000 nid=0x3e01 waiting on condition [0x00007f8a18ffe000]
   java.lang.Thread.State: WAITING (parking)
\tat sun.misc.Unsafe.park(Native Method)
\tat java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
\tat org.apache.tomcat.util.threads.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1083)

"GC task thread#0 (ParallelGC)" os_prio=0 tid=0x00007f8a3000c800 nid=0x3d01 runnable`;

const BLOCKED_DUMP = `2024-11-14 09:22:07
Full thread dump OpenJDK 64-Bit Server VM (11.0.21+9 mixed mode):

"http-nio-8080-exec-1" #28 prio=5 os_prio=0 tid=0x00007f8a4c001000 nid=0x4a21 runnable [0x00007f8a2c0ff000]
   java.lang.Thread.State: RUNNABLE
\tat com.renov.service.ContractorCache.rebuild(ContractorCache.java:81)
\t- locked <0x000000076b3a1234> (a com.renov.service.ContractorCache)
\tat com.renov.service.ContractorCache.lookup(ContractorCache.java:52)
\tat com.renov.controller.ContractorController.getAll(ContractorController.java:28)
\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:897)

"http-nio-8080-exec-3" #30 prio=5 os_prio=0 tid=0x00007f8a4c003000 nid=0x4a23 waiting for monitor entry [0x00007f8a2befd000]
   java.lang.Thread.State: BLOCKED (on object monitor)
\tat com.renov.service.ContractorCache.lookup(ContractorCache.java:52)
\t- waiting to lock <0x000000076b3a1234> (a com.renov.service.ContractorCache)
\tat com.renov.controller.ContractorController.getAll(ContractorController.java:28)
\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:897)

"http-nio-8080-exec-4" #31 prio=5 os_prio=0 tid=0x00007f8a4c004000 nid=0x4a24 waiting for monitor entry [0x00007f8a2bdfc000]
   java.lang.Thread.State: BLOCKED (on object monitor)
\tat com.renov.service.ContractorCache.lookup(ContractorCache.java:52)
\t- waiting to lock <0x000000076b3a1234> (a com.renov.service.ContractorCache)
\tat com.renov.controller.ContractorController.getAll(ContractorController.java:28)
\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:897)

"http-nio-8080-exec-5" #32 prio=5 os_prio=0 tid=0x00007f8a4c005000 nid=0x4a25 waiting for monitor entry [0x00007f8a2bcfb000]
   java.lang.Thread.State: BLOCKED (on object monitor)
\tat com.renov.service.ContractorCache.lookup(ContractorCache.java:52)
\t- waiting to lock <0x000000076b3a1234> (a com.renov.service.ContractorCache)
\tat com.renov.controller.ContractorController.getAll(ContractorController.java:28)
\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:897)`;

const DEADLOCK_DUMP = `2024-11-14 09:38:55
Full thread dump OpenJDK 64-Bit Server VM (11.0.21+9 mixed mode):

Found one Java-level deadlock:
=============================
"http-nio-8080-exec-7":
  waiting to lock monitor 0x00007f8a4c007000 (object 0x000000076b4c5678, a com.renov.service.ServiceOfferService),
  which is held by "http-nio-8080-exec-8"
"http-nio-8080-exec-8":
  waiting to lock monitor 0x00007f8a4c008000 (object 0x000000076b3a1234, a com.renov.service.ProjectBidService),
  which is held by "http-nio-8080-exec-7"

Java stack information for the threads listed above:
===================================================
"http-nio-8080-exec-7":
\tat com.renov.service.ServiceOfferService.updateBidStatus(ServiceOfferService.java:112)
\t- waiting to lock <0x000000076b4c5678> (a com.renov.service.ServiceOfferService)
\tat com.renov.service.ProjectBidService.acceptOffer(ProjectBidService.java:134)
\t- locked <0x000000076b3a1234> (a com.renov.service.ProjectBidService)
\tat com.renov.controller.ProjectBidController.acceptOffer(ProjectBidController.java:67)
\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)

"http-nio-8080-exec-8":
\tat com.renov.service.ProjectBidService.notifyClient(ProjectBidService.java:201)
\t- waiting to lock <0x000000076b3a1234> (a com.renov.service.ProjectBidService)
\tat com.renov.service.ServiceOfferService.accept(ServiceOfferService.java:78)
\t- locked <0x000000076b4c5678> (a com.renov.service.ServiceOfferService)
\tat com.renov.controller.ServiceOfferController.accept(ServiceOfferController.java:45)
\tat sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)

Found 1 deadlock.`;

@Component({
  selector: 'app-jvm-metrics',
  templateUrl: './jvm-metrics.component.html',
  styleUrls: ['./jvm-metrics.component.css']
})
export class JvmMetricsComponent {
  activeId: string | null = null;

  readonly scenarios: Scenario[] = [
    {
      id: 'highCpu',
      label: 'High CPU — Hot Loop',
      severity: 'cpu',
      tagline: 'Multiple RUNNABLE threads pinning CPU cores',
      description:
        'A runaway computation keeps one or more threads RUNNABLE with no blocking or I/O. ' +
        'In this example, EstimateCalculator.recalcPrices() iterates over every bid without pagination. ' +
        'CPU usage approaches N×100% where N is the number of affected threads. ' +
        'The process stays alive and responsive at the OS level, but latency for all other requests degrades because fewer cores remain free.',
      dump: HIGH_CPU_DUMP,
      symptoms: [
        'top / htop shows java process at 200–400% CPU',
        'Application responds slowly but does not crash',
        'Multiple consecutive jstack snapshots show the same threads at the same frame',
        'GC logs show minor collections are frequent (allocation pressure from the hot loop)',
      ],
      diagnosis: [
        'Run jstack <pid> twice, 5 seconds apart — same stack in both snapshots confirms the thread is stuck',
        'Look for RUNNABLE threads deep in application code (not I/O or lock lines)',
        'Correlate thread names with CPU-heavy PIDs: top -H -p <pid> shows per-thread CPU, match nid (hex) to thread name in jstack',
        'Identify the common frame at the top of all hot stacks — that is the hot method',
      ],
      fix: [
        'Add pagination or a size limit to the offending query or loop',
        'Move heavy batch work to an off-hours scheduled job',
        'Add a circuit-breaker or work-queue bounded by available cores',
        'Use async processing + back-pressure rather than blocking the request thread',
      ],
      commands: [
        'jstack <pid>',
        'jstack <pid> | grep -A 15 "RUNNABLE"',
        'top -H -p <pid>   # Linux: per-thread CPU',
        'printf "%x\\n" <decimal-pid>   # convert top TID to hex for jstack match',
      ],
    },
    {
      id: 'blocked',
      label: 'BLOCKED Threads — Lock Contention',
      severity: 'lock',
      tagline: 'One thread holds a lock; all others queue behind it',
      description:
        'ContractorCache.rebuild() holds an intrinsic lock while performing a slow DB reload. ' +
        'Every incoming HTTP request calls ContractorCache.lookup(), which also needs that lock. ' +
        'All request threads enter BLOCKED state and pile up. ' +
        'Throughput collapses to one-request-at-a-time for that endpoint. ' +
        'This is the classic "synchronized bottleneck" — the lock address <0x000000076b3a1234> appears once as "locked" and many times as "waiting to lock".',
      dump: BLOCKED_DUMP,
      symptoms: [
        'Response times for one endpoint spike from ms to seconds under load',
        'Thread pool exhaustion — server returns 503 or queues requests',
        'jstack shows many threads BLOCKED on the same object monitor address',
        'CPU is low (threads are waiting, not computing)',
      ],
      diagnosis: [
        'In jstack output, search for "waiting to lock" — the repeated lock address is the contended monitor',
        'Find the one thread with "- locked <same-address>" — that is the lock holder',
        'Check what the lock holder is doing: a slow DB call or I/O inside a synchronized block is the root cause',
        'Count BLOCKED threads vs. your thread pool size to estimate impact',
      ],
      fix: [
        'Replace the synchronized cache rebuild with a copy-on-write pattern (volatile reference swap)',
        'Use ReadWriteLock — many readers can proceed concurrently; only the writer blocks',
        'Use ConcurrentHashMap or Caffeine cache instead of a hand-rolled synchronized map',
        'Move expensive I/O completely outside the synchronized section',
      ],
      commands: [
        'jstack <pid> | grep -c "BLOCKED"   # count blocked threads',
        'jstack <pid> | grep -B 5 "waiting to lock"',
        'jcmd <pid> Thread.print',
        'jstack <pid> > dump1.txt && sleep 5 && jstack <pid> > dump2.txt && diff dump1.txt dump2.txt',
      ],
    },
    {
      id: 'deadlock',
      label: 'Deadlock — Circular Wait',
      severity: 'deadlock',
      tagline: 'Two threads each hold a lock the other needs — permanent freeze',
      description:
        'Thread exec-7 holds the ProjectBidService lock and waits for ServiceOfferService. ' +
        'Thread exec-8 holds the ServiceOfferService lock and waits for ProjectBidService. ' +
        'Neither can proceed. Unlike BLOCKED contention, this never resolves on its own. ' +
        'jstack detects it automatically and prints a "Found N deadlock" summary at the top of the dump. ' +
        'The affected requests time out; if those threads are not replaced, the thread pool drains and the server freezes.',
      dump: DEADLOCK_DUMP,
      symptoms: [
        'Two or more specific endpoints hang indefinitely with no CPU activity',
        'jstack output contains "Found one Java-level deadlock:" section',
        'The hung requests never complete — not even with an error — until the JVM is restarted',
        'Thread pool slowly exhausts as more requests pile on the deadlocked paths',
      ],
      diagnosis: [
        'Run jstack <pid> — if there is a deadlock, the JVM prints it explicitly at the end',
        'Read the "Java stack information" section: identify which locks are held vs. waited on by each thread',
        'Draw the wait-for graph: A→B and B→A forms a cycle — that is the deadlock',
        'Identify the two entry points (controller methods) that trigger the conflicting lock acquisition order',
      ],
      fix: [
        'Enforce a global lock ordering: always acquire locks in the same sequence (e.g., alphabetically by class name)',
        'Replace two fine-grained locks with a single coarser lock covering both resources',
        'Use tryLock() with a timeout and back off on failure instead of blocking indefinitely',
        'Redesign to avoid holding one lock while acquiring another (pass data instead of sharing mutable state)',
      ],
      commands: [
        'jstack <pid> | grep -A 30 "deadlock"',
        'jcmd <pid> Thread.print   # alternative to jstack',
        'jstack <pid> | tail -30   # deadlock summary is at the end of the dump',
        'kill -3 <pid>   # send SIGQUIT — JVM prints thread dump to stdout/log',
      ],
    },
  ];

  get active(): Scenario | null {
    return this.scenarios.find(s => s.id === this.activeId) ?? null;
  }

  activate(id: string): void {
    this.activeId = this.activeId === id ? null : id;
  }

  lineClass(line: string): string {
    if (/java\.lang\.Thread\.State:\s+RUNNABLE/.test(line)) return 'hl-runnable';
    if (/java\.lang\.Thread\.State:\s+BLOCKED/.test(line)) return 'hl-blocked';
    if (/java\.lang\.Thread\.State:\s+WAITING/.test(line)) return 'hl-waiting';
    if (/^\s+-\s+locked\s+/.test(line)) return 'hl-lock-held';
    if (/^\s+-\s+waiting to lock\s+/.test(line)) return 'hl-lock-wait';
    if (/Found\s+\d+\s+deadlock|Found one Java-level deadlock/.test(line)) return 'hl-deadlock-alert';
    if (/which is held by|waiting to lock monitor/.test(line)) return 'hl-deadlock-body';
    if (/^"[^"]+"/.test(line)) return 'hl-thread-name';
    return 'hl-normal';
  }

  dumpLines(dump: string): { text: string; cls: string }[] {
    return dump.split('\n').map(text => ({ text, cls: this.lineClass(text) }));
  }
}
