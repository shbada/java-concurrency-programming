package io.concurrency.chapter10.exam08;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class InvokeAllExample {
    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(3);
        List<Callable<Integer>> tasks = new ArrayList<>();

        tasks.add(() -> {
            Thread.sleep(3000);
            return 1;
        });
        tasks.add(() -> {
            Thread.sleep(2000);
            return 1;
        });
        tasks.add(() -> {
            throw new RuntimeException("invokeAll");
        });

        long started = 0;
        try {
            started = System.currentTimeMillis();

            // 여러 작업 제출 후 결과를 반환받음
            List<Future<Integer>> results = executor.invokeAll(tasks);
            for (Future<Integer> future : results) {
                // 정상이든 예외든 모든 결과는 종료된 것이므로 true 반환
                boolean done = future.isDone();

                try {
                    Integer value = future.get();
                    System.out.println("result: " + value);
                } catch (ExecutionException e) { // 3번재 작업에서 오류 발생하여 수행
                    // 작업 중 예외가 발생한 경우 처리
                    Throwable cause = e.getCause();
                    if (cause instanceof RuntimeException) {
                        System.err.println("exception: " + cause.getMessage());
                    } else {
                        e.printStackTrace();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            executor.shutdown();
        }

        System.out.println("총 소요시간:"  + (System.currentTimeMillis() - started ));
    }
}
