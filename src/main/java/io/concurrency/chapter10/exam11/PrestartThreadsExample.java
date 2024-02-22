package io.concurrency.chapter10.exam11;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PrestartThreadsExample {
    public static void main(String[] args) {

        int corePoolSize = 2;
        int maxPoolSize = 4;
        long keepAliveTime = 0L;
        BlockingQueue<Runnable> workQueue =  new LinkedBlockingQueue<>();
        int taskNum = 9;

        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

        // task 가 들어오기 전에 쓰레드를 미리 생성해놓는 메서드
        // 원래는 작업이 들어와야 2개를 생성하는데, 아래 메서드 수행시 작업 전 1개 또는 corePoolSize 만큼 쓰레드 생성
        // 1개만 생성
//        executor.prestartCoreThread();
//        executor.prestartCoreThread();

        // corePoolSize만큼 생성
        executor.prestartAllCoreThreads();

        for (int i = 0; i < taskNum; i++) {
            final int taskId = i;
            executor.execute(()->{
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " 가 태스크" + taskId + " 를 실행하고 있습니다.");
            });
        }

        executor.shutdown();

    }
}
