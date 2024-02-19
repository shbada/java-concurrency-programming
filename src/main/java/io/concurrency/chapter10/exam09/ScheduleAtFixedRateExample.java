package io.concurrency.chapter10.exam09;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduleAtFixedRateExample {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
        Runnable task = () -> {
            try {
                Thread.sleep(2000);
                System.out.println("thread: " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                // 예외 처리
            }
        };

        // 처음 1 초가 지난 후 실행 되고 지정된 주기(1초) 마다 계속 실행 된다
        // 스레드 풀이 3개의 스레드를 가지고 있으므로, 각 스레드가 1초 간격으로 번갈아가면서 작업을 실행한다. 이렇게 하면 스레드 간에 작업이 겹치지 않고 균등하게 분산된다
        // 이전 작업이 끝난 후 작업을 수행하려면 작업시간과 맞춰서 주기를 설정하는게 좋다.
        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);

        // 여러개를 둔다면?
        // thread 3개를 각각이 가져가서 재사용한다.
        ScheduledFuture<?> future2 = scheduler.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);
        ScheduledFuture<?> future3 = scheduler.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);
        ScheduledFuture<?> future4 = scheduler.scheduleAtFixedRate(task, 1, 1, TimeUnit.SECONDS);


        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        future.cancel(true); // 작업을 취소하면 인터럽트 되어 스케줄링이 중지된다
        scheduler.shutdown();
    }
}
