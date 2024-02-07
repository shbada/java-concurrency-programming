package io.concurrency.chapter08.exam04;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class  LockFairnessPerformanceExample {
    private static final int THREAD_COUNT = 4;
    private static final int ITERATIONS = 1000_000;
    private static final Lock fairLock = new ReentrantLock(true);
    private static final Lock unfairLock = new ReentrantLock(false);

    public static void main(String[] args) {
        // 락이 비었을때 먼저 락을 획득 요청하는 스레드가 획득
        runTest("비공정한 락", unfairLock);
        // 대기 순서대로(가장 오래 대기한 스레드 먼저) 락 획득
        runTest("공정한 락", fairLock);
    }

    private static void runTest(String lockType, Lock lock) {
        long startTime = System.currentTimeMillis();

        Thread[] threads = new Thread[THREAD_COUNT];
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < ITERATIONS; j++) {
                    lock.lock();
                    try {
                        // 자원에 대한 작업 수행
                    } finally {
                        lock.unlock();
                    }
                }
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println(lockType + "의 실행 시간: " + elapsedTime + "밀리초");
    }
}
