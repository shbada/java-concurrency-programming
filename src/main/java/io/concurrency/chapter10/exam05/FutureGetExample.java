package io.concurrency.chapter10.exam05;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureGetExample {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Callable 작업 생성
        Callable<Integer> callableTask = () -> {
            System.out.println("비동기 작업 시작...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("비동기 작업 완료.");
            return 42;
        };

        /*
        - AbstractExecutorService > newTaskFor() : FutureTask 사용
        - AbstractExecutorService > submit() : Future 리턴
        - AbstractExecutorService > submit() > execute() : Worker 객체 생성, Worker 클래스의 run() 호출 : Worker 클래스가 FutureTask 를 가지고있다
        - Worker > runWorker() > task.run() 호출 > FutureTask 의 run() 을 호출하는것
        - FutureTask > run()
          - runner 가 null 이면 현재 쓰레드로 할당하라
          - callable 의 call() 호출
          - 완료 후 set(결과) 해서 결과를 셋팅
          - FutureTask > set() > 최종 결과를 outcome 에 저장
          - state, callable, outcome, runner 모두 셋팅 완료됨 -> state 가 2로 업데이트되면서 완료
        - 위 과정을 통해 future 에 결과가 담기는 것
        */
        Future<Integer> future = executorService.submit(callableTask);

        try {
            /* blocking */
            /*
            - FutureTask > get() : state(0), awaitDone() 호출 (메인쓰레드 대기)
            - FutureTask > awaitDone()
              - for 문으로 무한 반복 (여러 조건들이 존재)
              - 어떤 조건이 만족되야 반복문을 빠져나온다
              - 1) 작업이 완료된 경우
              - 2) timed 가 true일 경우 (타임아웃이 지정되어있을 경우)
              - 작업이 완료되면 status 가 2로 변경되어서 위 for문을 탈출
              - 메인쓰레드의 interrupt() 여부도 체크하는 로직이 있음
              - 위 반복문 돌면서 WAITERS.weakCompareAndSet(this, q.next = waiters, q);
                - q.next() 가 널이면 새로 생성된 q 객체는 WAITERS 에 저장하라
             - LockSupport.park(this);
               - main 쓰레드가 해당 로직 수행부터 차단됨
             - 모든 작업이 완료되면 최종적으로 LockSupport.unpark() 호출 : 대기하고있는 main 쓰레드 깨움
             */
            Integer result = future.get();
            System.out.println("Result: " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        executorService.shutdown();
    }
}
