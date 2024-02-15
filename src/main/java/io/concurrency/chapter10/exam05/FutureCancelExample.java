package io.concurrency.chapter10.exam05;

import java.util.concurrent.*;

public class FutureCancelExample {
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

            // future.cancel(true); 일때 실행 안됨
            // future.cancel(false); 실행됨
            // 위 2개는 CancellationException 발생은 동일
            System.out.println("비동기 작업 완료.");
            return 42;
        };

        // 작업을 제출 하고 Future 객체를 받음
        Future<Integer> future = executorService.submit(callableTask);

        /*while (!future.isDone()) { // future 의 동기 작업 (main 쓰레드에서 계속 확인하므로 동기적인 관계 생성)
            System.out.println("Waiting for the result...");
            Thread.sleep(500);
        }*/

        // 작업 취소 시도, 결과가 완료된 경우는 효과가 없다
        /*
        - FutureTask > cancel()
          - true : 실행중인 쓰레드 interrupt() 호출

        - false 일 경우에는 수행은 완료하지만 결과는 못받아옴 (set() 에서 if 문이 있고, CANCEL 되어있으면 결과를 outcome 에 넣지않음)
         */
        boolean cancel = future.cancel(true);

//        if (!future.isCancelled()) {
            try {
                Integer result = future.get();
                System.out.println("Result: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
//        } else {
//            System.out.println("Task was cancelled.");
//        }
        executorService.shutdown();
    }
}
