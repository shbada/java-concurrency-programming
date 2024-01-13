package io.concurrency.chapter03.exam03;

public class IsInterruptedExample {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            // 인터럽트 걸면 해당 반복문에서 빠져나온다
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("스레드가 작동 중입니다.");
            }
            System.out.println("스레드가 인터럽트 되었습니다.");
            System.out.println("인터럽트 상태: " + Thread.currentThread().isInterrupted());
        });
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 인터럽트
        thread.interrupt();
    }
}
