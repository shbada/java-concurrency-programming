package io.concurrency.chapter10.exam07;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class AwaitTerminationExample {
    public static void main(String[] args) throws InterruptedException {

        // ThreadFactory 사용해서 쓰레드 생성
        ExecutorService executorService = Executors.newFixedThreadPool(2, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true); // 데몬쓰레드
                return thread;
            }
        });

        executorService.submit(()->{
            while(true){
                System.out.println(Thread.currentThread().getName() + " : 데몬 스레드 실행 중...");
                Thread.sleep(1000);
            }
        });

        // 정상적인 종료 없이 쓰레드가 종료된다면 데몬 쓰레드인지를 의심해봐야한다.
        executorService.shutdown();

//        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        Thread.sleep(3000);

        // 데몬 쓰레드는 사용자 쓰레드 모두 종료시 자동 종료
        System.out.println("메인 스레드 종료");

    }
}
