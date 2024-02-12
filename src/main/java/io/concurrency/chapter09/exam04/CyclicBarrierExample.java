package io.concurrency.chapter09.exam04;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    static int[] parallelSum = new int[2];

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int numThreads = 2;
        // 장벽 액션 : new BarrierAction(parallelSum)
        CyclicBarrier barrier = new CyclicBarrier(numThreads, new BarrierAction(parallelSum));

        // 쓰레드 2개
        for (int i = 0; i < numThreads; i++) {
            new Thread(new Worker(i, numbers, barrier, parallelSum)).start();
        }
    }
}

class BarrierAction implements Runnable {
    private final int[] parallelSum;

    public BarrierAction(int[] parallelSum) {
        this.parallelSum = parallelSum;
    }

    /**
     * 모든 스레드가 장벽에 모인 시점에 실행된다.
     */
    @Override
    public void run() {
        int finalSum = 0;

        // 최종 합산
        for (int sum : parallelSum) {
            finalSum += sum;
        }
        System.out.println("Final Sum: " + finalSum);
    }
}

class Worker implements Runnable {
    private final int id;
    private final int[] numbers;
    private final CyclicBarrier barrier;
    private final int[] parallelSum;

    public Worker(int id, int[] numbers, CyclicBarrier barrier, int[] parallelSum) {
        this.id = id;
        this.numbers = numbers;
        this.barrier = barrier;
        this.parallelSum = parallelSum;
    }

    public void run() {
        // 첫번째 쓰레드 : 0 ~ 4
        // 두번째 쓰레드 : 5 ~ 9
        int start = id * (numbers.length / 2);
        int end = (id + 1) * (numbers.length / 2);
        int sum = 0;

        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }

        // id = 0, 1
        parallelSum[id] = sum; // 각 스레드는 병렬로 데이터를 나누어 연산한다.

        try {
            // 총 쓰레드 2개가 모두 await()을 할때까지 대기
            barrier.await(); // 모든 스레드가 합산을 완료할 때까지 대기한다.

            // 여기서 다시 barrier.await() 할 수 있음 (재사용 가능)
            // 다시 2개의 쓰레드가 모두 await()을 호출 할때까지 기다린다.
//            barrier.await(); // 모든 스레드가 합산을 완료할 때까지 대기한다.
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
