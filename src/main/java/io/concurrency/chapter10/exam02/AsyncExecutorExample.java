package io.concurrency.chapter10.exam02;

import java.util.concurrent.Executor;

public class AsyncExecutorExample {
    public static void main(String[] args) {

        Executor asyncExecutor = new AsyncExecutor();

        asyncExecutor.execute(()->{
            System.out.println("비동기 작업 1 수행 중...");
            // 작업 수행
            System.out.println("비동기 작업 1 완료...");
        });

        asyncExecutor.execute(()->{
            System.out.println("비동기 작업 2 수행 중...");
            // 작업 수행
            System.out.println("비동기 작업 1 완료...");
        });
    }

    static class AsyncExecutor implements Executor{

        @Override
        public void execute(Runnable command) {
            // 별도 스레드 생성하여, 해당 스레드가 수행하도록 함
            Thread thread = new Thread(command);
            thread.start();
        }
    }
}
