package io.concurrency.chapter11.exam10;

import java.util.concurrent.CompletableFuture;

public class CompletedFutureExample {
    public static void main(String[] args) {
        // 값을 가지고있는 cf 생성
        CompletableFuture<String> cf = CompletableFuture.completedFuture("Hello World");

//        CompletableFuture<String> cf2 = new CompletableFuture<>();
//        cf2.complete("Hello World");

        CompletableFuture<Void> finalCf = cf.thenAccept(r -> {
            System.out.println("result: " + r);
        });
    }
}
