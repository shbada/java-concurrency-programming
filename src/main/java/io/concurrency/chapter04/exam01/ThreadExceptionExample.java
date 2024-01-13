package io.concurrency.chapter04.exam01;

public class ThreadExceptionExample {

    public static void main(String[] args) {

        try {
            // 해당 쓰레드의 에러를 외부에서 받을수가 없다.
            // 그래서 쓰레드의 예외 처리가 힘들다. -> 별도의 방법이 필요하다. (DefaultExceptionHandlerExample.java)
            new Thread(() -> {
                throw new RuntimeException("스레드 1 예외!");
            }).start();
        } catch(Exception e) {
            // 위 쓰레드에서 오류가 난다해도, 해당 catch문을 실행하지 못한다.
            //
            sendNotificationToAdmin(e);
        }

    }

    private static void sendNotificationToAdmin(Throwable e) {
        System.out.println("관리자에게 알림: " + e.getMessage());
    }
}
