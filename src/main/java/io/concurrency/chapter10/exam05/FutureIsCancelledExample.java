package io.concurrency.chapter10.exam05;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureIsCancelledExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Callable 작업 생성
        Callable<Integer> callableTask = () -> {
            System.out.println("비동기 작업 시작...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("비동기 작업 완료.");
            return 42;
        };

        // 작업을 제출 하고 Future 객체를 받음
        Future<Integer> future = executorService.submit(callableTask);

        // 작업 취소 시도, 결과가 완료된 경우는 효과가 없다
        // 해당 결과값은 정확한 취소 여부가 아님 (isCanceled() 로 확인)
        boolean cancel = future.cancel(true);

        // 취소 여부를 정확히 확인할 수 있다
        if (!future.isCancelled()) {
            try {
                Integer result = future.get();
                System.out.println("Result: " + result);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            System.out.println("Task was cancelled.");
        }
        executorService.shutdown();
    }
}
