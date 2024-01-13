package io.concurrency.chapter03.exam03;

public class InterruptedExample3 {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            while (true) {
                System.out.println("스레드 작동 중");
                if (Thread.interrupted()) {
                    System.out.println("인터럽트 상태 초기화 되었습니다.");
                    break;
                }
            }

            System.out.println("인터럽트 상태: " + Thread.currentThread().isInterrupted());
            // 다시 인터럽트 걸기
            // 이 쓰레드를 참조하고있는 다른 쓰레드에서 이 쓰레드의 인터럽트 상태 초기화로 인한 영향을 받을시를 대비해서 다시 인터럽트를 걸어야하는 상황이 있을 수 있다.
            Thread.currentThread().interrupt();
            System.out.println("인터럽트 상태: " + Thread.currentThread().isInterrupted());
        });

        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }
}
