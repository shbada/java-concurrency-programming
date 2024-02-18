package io.concurrency.chapter10.exam08;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InvokeAnyExample {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Callable<String>> tasks = new ArrayList<>();

        tasks.add(() -> {
            Thread.sleep(2000);
            return "Task 1";
        });
        tasks.add(() -> {
            Thread.sleep(1000); // 가장 빠르게 작업이 완료되는 결과를 반환하고 나머지 작업은 모두 취소된다.
            throw new RuntimeException("error");
        });
        tasks.add(() -> {
            Thread.sleep(3000);
            return "Task 3";
        });
        long started = 0;
        try {
            started = System.currentTimeMillis();
            // Future 가 아닌 작업 결과를 가져온다.
            // "Task2"
            String result = executor.invokeAny(tasks);
            System.out.println("result: " + result);

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        System.out.println("총 소요시간:"  + (System.currentTimeMillis() - started ));
    }
}
