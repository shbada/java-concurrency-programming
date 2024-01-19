package io.concurrency.chapter06.exam05;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 참조용 예시
 */
public class SpinLockVsSynchronized {
    private AtomicBoolean spinLock = new AtomicBoolean(false);
    private final Object syncLock = new Object();
    private int count = 0;

    // 쓰레드를 늘릴수록 컨텍스트 스위칭이 증가한다.
    // 스핀락 시간 > synchronized 시간
    // 쓰레드 5만개정도로 늘리니까 스핀락 시간 < synchronized 시간
    final static int THREAD_COUNT = 5;
    final int ITERATIONS = 10_000_000;

    /**
     * spin lock
     */
    public void useSpinLock() {
        while (!spinLock.compareAndSet(false, true)) ;
        for (int j = 0; j < ITERATIONS; j++) {
            count++;
        }
        spinLock.set(false);
    }

    /**
     * synchronized block
     */
    public void useSynchronized(){
        synchronized (syncLock) {
            for (int j = 0; j < ITERATIONS; j++) {
                count++;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        SpinLockVsSynchronized tester = new SpinLockVsSynchronized();

        // synchronized 성능 테스트
        Thread[] syncThreads = new Thread[THREAD_COUNT];
        long start2 = System.currentTimeMillis();

        for (int i = 0; i < THREAD_COUNT; i++) {
            syncThreads[i] = new Thread(() -> {
                tester.useSynchronized();
            });
            syncThreads[i].start();
        }
        for (int i = 0; i < THREAD_COUNT; i++) {
            syncThreads[i].join();
        }
        long end2 = System.currentTimeMillis();
        System.out.println("synchronized 시간: " + (end2 - start2));

        // 스핀락 성능 테스트
        Thread[] spinThreads = new Thread[THREAD_COUNT];
        long start = System.currentTimeMillis();

        for (int i = 0; i < THREAD_COUNT; i++) {
            spinThreads[i] = new Thread(() -> {
                tester.useSpinLock();

            });
            spinThreads[i].start();
        }
        for (int i = 0; i < THREAD_COUNT; i++) {
            spinThreads[i].join();
        }
        long end = System.currentTimeMillis();
        System.out.println("스핀락 시간: " + (end - start));


    }
}
