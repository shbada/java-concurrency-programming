package io.concurrency.chapter10.exam03;

import java.util.concurrent.*;

public class CallableExample {
    public static void main(String[] args)  {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // Callable
        Callable<Integer> callableTask = ()->{
            System.out.println("Callable 작업 수행중..");
            System.out.println("Callable 작업 완료");

            return 42;
        };

        // Callable 은 execute() 가 아닌 submit() 한다.
        // 리턴값 : Future
        Future<Integer> future = executorService.submit(callableTask);

        int result;
        try {
            // get()
            result = future.get();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Callable 작업 결과 : " + result);

        executorService.shutdown();
    }
}
