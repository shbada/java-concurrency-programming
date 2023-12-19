package io.concurrency.chapter01.exam01;

import java.util.ArrayList;
import java.util.List;

/**
 * core 12, task 12
 */
public class ParallelismExample {
    public static void main(String[] args) {
//        int cpuCores = 1;
        int cpuCores = Runtime.getRuntime().availableProcessors();

        // CPU 개수만큼 데이터를 생성
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < cpuCores; i++) {
            data.add(i);
        }

        // CPU 개수만큼 데이터를 병렬로 처리
        long startTime1 = System.currentTimeMillis();

        // parallelStream(), stream() 차이
        // stream() : 순차적, 0.5x12 task일때 : 6초
        // parallelStream() : 1개의 데이터를 1개의 쓰레드가 동시적으로 실행, 12개의 쓰레드가 각각의 task 수행 : 0.5초
        long sum1 = data.parallelStream()
                .mapToLong(i -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return i * i;
                })
                .sum();

        long endTime1 = System.currentTimeMillis();

        System.out.println("CPU 개수만큼 데이터를 병렬로 처리하는 데 걸린 시간: " + (endTime1 - startTime1) + "ms");
        System.out.println("결과1: " + sum1);

    }
}
