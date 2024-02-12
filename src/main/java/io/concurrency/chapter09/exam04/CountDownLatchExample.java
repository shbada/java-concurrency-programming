package io.concurrency.chapter09.exam04;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {

        int numThreads = 5;
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(5);

        for (int i = 0; i <numThreads; i++) {
            new Thread(new Worker(startSignal, doneSignal)).start();
        }

        Thread.sleep(3000);
        startSignal.countDown();

        System.out.println("시작신호를 알렸습니다.");

        // 5개의 쓰레드가 모두 완료될 때까지 기다리게된다. (main 쓰레드 블로킹)
        doneSignal.await();

        System.out.println("모든 스레드의 작업이 완료되었습니다.");
    }

    static class Worker implements Runnable{

        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        public Worker(CountDownLatch startSignal, CountDownLatch doneSignal){

            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        @Override
        public void run() {
            try {
                // 쓰레드 시작
                // 모든 쓰레드가 여기서 멈춰있음 - startSignal.countDown(); 가 호출되면 이제 수행
                startSignal.await();

                System.out.println(Thread.currentThread().getName() + " 가 작업을 수행하고 있습니다.");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " 가 작업을 완료했습니다.");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                // 쓰레드 종료
                doneSignal.countDown();
            }
        }
    }
}
