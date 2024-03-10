package io.concurrency.chapter11.exam10;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompleteExample {
    public static void main(String[] args) {

        MyService service = new MyService();
        CompletableFuture<Integer> cf = service.performTask();
        cf.thenApply(r -> r + 20);

        System.out.println("result: " + cf.join());
        System.out.println("메인 스레드 종료");

    }

    static class MyService{

        public CompletableFuture<Integer> performTask(){

            CompletableFuture<Integer> cf = new CompletableFuture<>();

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(()->{

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                // 명시적으로 값 설정
                cf.complete(40);
                // 또 complete()를 호출하면 null이 아니므로 기존값 유지
//                cf.complete(50);
            });
            return cf;
        }
    }
}