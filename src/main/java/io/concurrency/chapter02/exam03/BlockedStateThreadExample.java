package io.concurrency.chapter02.exam03;

/**
 * BLOCKED
 */
public class BlockedStateThreadExample {

    public static void main(String[] args) throws InterruptedException {
        final Object lock = new Object();
        Thread thread1 = new Thread(() -> {
            synchronized (lock) {
                while (true) {
                    // 무한 루프로 lock을 계속 점유
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            // thread1이 획득한 lock를 획득하려고 시도 -> blocked
            synchronized (lock) {
                System.out.println("Thread 2 실행 중");
            }
        });
        // thread의 start()는 두번 호출할 수 없다.
        thread1.start();
        Thread.sleep(100); // thread1이 lock을 점유하도록 잠시 대기
        thread2.start();
        Thread.sleep(100); // thread2가 lock을 기다리는 상태로 대기
        System.out.println("스레드 2 상태: " + thread2.getState()); // BLOCKED
    }

}
