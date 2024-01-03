package io.concurrency.chapter02.exam02;

public class ThreadStartRunExample {
    public static void main(String[] args) {

        MyRunnable myRunnable = new MyRunnable();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " :스레드 실행중..");
            }
        });

        thread.start();
        // Thread.java > run () : JVM이 실행해줌
        // Thread.java > start() > start0() (native 메서드) 호출
        // 그 다음 우리가 정의한 run() 메서드가 호출된다.

//        thread.run();
        // run() 즉시 호출하면, 바로 쓰레드의 run()을 호출하게된다. (thread.java > run())
        // 위 start()였을때의 차이점은 쓰레드 자체가 생기지 않은 것

//        myRunnable.run(); // main 쓰레드에서 실행됨

        // 출력 이후 쓰레드 run 로직의 출력 수행
        // 쓰레드 추가할때마다 쓰레드 수행 순서는 다름 (독립적)
        //
        System.out.println("main 쓰레드 종료");
    }

    static class MyRunnable implements Runnable{

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": 스레드 실행 중...");
        }
    }
}
