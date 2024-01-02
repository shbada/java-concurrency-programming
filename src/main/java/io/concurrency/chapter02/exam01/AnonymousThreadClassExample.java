package io.concurrency.chapter02.exam01;

/**
 * Thread 익명 클래스
 */
public class AnonymousThreadClassExample {
    public static void main(String[] args) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " :스레드 실행 중..");
            }
        };

        thread.start();
    }
}
