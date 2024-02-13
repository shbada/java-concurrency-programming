package io.concurrency.chapter10.exam02;

import java.util.concurrent.Executor;

public class SyncExecutorExample {
    public static void main(String[] args) {
        Executor syncExecutor = new SyncExecutor();

        syncExecutor.execute(()->{
            System.out.println("동기 작업 1 수행 중...");
            // 작업 수행
            System.out.println("동기 작업 1 완료...");
        });

        syncExecutor.execute(()->{
            System.out.println("동기 작업 2 수행 중...");
            // 작업 수행
            System.out.println("동기 작업 1 완료...");
        });
    }

    static class SyncExecutor implements Executor{

        @Override
        public void execute(Runnable command) {
            // 실행 주체 : 이 Executor 을 사용하는 쓰레드 (그러므로 동기)
            // 동기 실행은 Executor 을 사용할 필요는 없지만 쟉업의 실행/제출을 분리하는것에 의미
            command.run();
        }
    }
}
