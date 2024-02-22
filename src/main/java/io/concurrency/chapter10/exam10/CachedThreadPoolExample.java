package io.concurrency.chapter10.exam10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolExample {
    public static void main(String[] args) {

        // task마다 즉시 쓰레드 생성하여 수행
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 1; i <= 20; i++) {
            executorService.submit(() -> {
                System.out.println("Thread : " + Thread.currentThread().getName());
            });
        }

        executorService.shutdown();
    }
}
