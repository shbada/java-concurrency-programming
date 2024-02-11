package io.concurrency.chapter09.exam01;

public class NonAtomicExample {
    private static int value = 0;
    private static final int NUM_THREADS = 3;

    public static void main(String[] args) {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(() -> {
                // 여러번 반복하여 확률적으로 경쟁 조건을 발생시킴
                for (int j = 0; j < 100000; j++) {
                    int expectedValue = value; // 캐시에서 읽어온 값
                    int newValue = expectedValue + 1; // 기댓값
                    value = newValue;
                    System.out.println(Thread.currentThread().getName() + ":" + expectedValue + " , " + newValue);
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Final value: " + value);
    }
}
