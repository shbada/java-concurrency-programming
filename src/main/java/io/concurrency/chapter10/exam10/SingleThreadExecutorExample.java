package io.concurrency.chapter10.exam10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutorExample {
    public static void main(String[] args) {

        // 1개의 쓰레드가 작업을 수행
        // 동시성 문제 발생X -> 동기화할 필요 없음
        // 병렬적 수행이 어려움
        ExecutorService executor = Executors.newSingleThreadExecutor();

        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("Task " + taskId + " is executing on " + Thread.currentThread().getName());
            });
        }

        executor.shutdown();
    }
}
