package io.concurrency.chapter02.exam03;

/**
 * WAITING
 */
public class WaitingStateThreadExample {

    public static void main(String[] args) throws InterruptedException {
        final Object lock = new Object();
        Thread thread = new Thread(() -> {
            synchronized (lock) {
                try {
                    lock.wait(); // 이 스레드는 wait 만난 순간 대기 상태로 변경
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        Thread.sleep(100);
        System.out.println("스레드 상태: " + thread.getState()); // WAITING
    }
}
