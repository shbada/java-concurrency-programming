package io.concurrency.chapter04.exam03;

public class DaemonThreadLifeCycleExample {
    public static void main(String[] args) throws InterruptedException {
        Thread userThread = new Thread(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("사용자 스레드 실행 중..");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // 끝나지 않는 데몬 스레드
        Thread daemonThread = new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(500);
                    System.out.println("데몬 스레드 실행 중..");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        daemonThread.setDaemon(true); // 데몬스레드 설정
//        daemonThread.setDaemon(false); // 데몬스레드 해제

        userThread.start();
        daemonThread.start();

        // 사용자 스레드가 완료되면 데몬 스레드는 진행중이여도 메인 쓰레드가 종료된다.
        // 모든 사용자 스레드가 사라지면 데몬 스레드도 사라진다.
        userThread.join();

        System.out.println("메인 스레드 종료");
    }
}
