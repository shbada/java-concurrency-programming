package io.concurrency.chapter11.exam09;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class WhenCompleteExample {
    public static void main(String[] args) throws InterruptedException {

    CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(500);
                    throw new RuntimeException("error");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                return 10;
            }).whenComplete((r,e) ->{ // 리턴값이 없으므로 대체값을 줄 수 없음, 그래서 예외객체를 cf 가 그대로 가지고있음
                if(e != null){
                    System.out.println("Exception: " + e.getMessage());
                }else{
                    System.out.println("result: " + r);
                }
             });

        try {
            Thread.sleep(2000);
            cf.join(); // 오류를 가지고있으므로 get(), join() 호출 시 오류 발생!
        }catch(CompletionException e){
            System.out.println("예외 처리를 합니다");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
