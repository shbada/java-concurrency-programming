package io.concurrency.chapter05.exam01;

public class MultiThreadExample {
    private static int sum = 0;
    private static final Object lock = new Object();

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        // SingleThreadExample.java 에서 단일스레드의 행위를 2개의 쓰레드로 나눠서 병렬 수행

        // 별도 스레드 수행
        Thread thread1 = new Thread(() -> {
            for (int i = 1; i <= 500; i++) {
                synchronized (lock) {
                    sum += i;
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // 별도 스레드 수행
        Thread thread2 = new Thread(() -> {
            for (int i = 501; i <= 1000; i++) {
                // 1번 쓰레드가 작업중이면 해당 쓰레드에서 작업 못함
                // 해당 쓰레드에서 작업중이면 1번 쓰레드는 작업 못함
                synchronized (lock) {
                    sum += i;
                }
                try {
                    Thread.sleep(1);
                    // main 쓰레드 오류는 아니므로 main 쓰레드의 이후 로직은 계속 수행됨
                    throw new RuntimeException("error"); // 오류
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("합계: " + sum);
        System.out.println("멀티 스레드 처리 시간: " + (System.currentTimeMillis() - start) + "ms");
    }
}
