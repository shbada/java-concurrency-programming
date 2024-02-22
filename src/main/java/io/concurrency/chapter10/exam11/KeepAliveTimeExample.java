package io.concurrency.chapter10.exam11;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class KeepAliveTimeExample {
    public static void main(String[] args) throws InterruptedException {

        int corePoolSize = 2;
        int maxPoolSize = 4;
        long keepAliveTime = 1L; // 유휴 스레드의 최대 대기시간(초)
        BlockingQueue<Runnable> workQueue =  new LinkedBlockingQueue<>(2);
        int taskNum = 6;


        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue);

        for (int i = 0; i < taskNum; i++) {
            final int taskId = i;
            executor.execute(()->{
                try {
                    // 스레드를 유휴 상태로 만든다.
                    // 2초가 지난 후 쓰레드 4개 -> 2개로 되겠다
                    Thread.sleep(2000); // keepAliveTime 1초 경과 했으므로 쓰레드 2개가 제거됨 (corePoolSize 초과 갯수만큼)
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " 가 태스크" + taskId + " 를 실행하고 있습니다.");
            });
        }

        // corePoolSize 만큼도 모두 제거할 경우
//        executor.allowCoreThreadTimeOut(true);


        Thread.sleep(4000);
        executor.shutdown();
    }
}
