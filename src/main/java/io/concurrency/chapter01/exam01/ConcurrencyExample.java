package io.concurrency.chapter01.exam01;

import java.util.ArrayList;
import java.util.List;

/**
 * ConcurrencyExample vs ParallelismExample
 * core 12, task 24 : 1초
 * core 12, task 13 : 0.6초 정도 걸려야 맞는데? -> 여전히 1초가 걸린다.
 *
 * 어떤 의미일까?
 * 병렬성은 CPU 1개당, 쓰레드가 1개씩 붙어서 동시적으로 처리하는 행위다.
 * 하지만 CPU 개수보다 작업 갯수가 더 많아지면 동시성이 되어버려서, 쓰레드 1개당 1개의 task 할당이 불가능하다.
 * 따라서 task 갯수는 줄었어도 동일하게 1초가 걸린다.
 *
 * 병렬성 : 성능을 위한 CPU 사용 극대화
 * 동시성 : 많은 쓰레드들을 CPU가 최대한 많은 작업을 하기 위함
 */
public class ConcurrencyExample {
    public static void main(String[] args) {

        // cpu 코어 * 2 (코어갯수 < 작업갯수를 만든다.)
        int cpuCores = Runtime.getRuntime().availableProcessors() * 2;
//        int cpuCores = 13; (코어갯수 + 1)

        // CPU 개수를 초과하는 데이터를 생성
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < cpuCores; i++) {
            data.add(i);
        }

        // CPU 개수를 초과하는 데이터를 병렬로 처리
        long startTime2 = System.currentTimeMillis();

        // 작업 갯수 > 코어갯수일때는 병렬성이 안되고 동시성이 된다.
        long sum2 = data.parallelStream()
                .mapToLong(i -> {
                    try {
                        Thread.sleep(500); // 0.5초
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return i * i;
                })
                .sum();

        long endTime2 = System.currentTimeMillis();

        System.out.println("CPU 개수를 초과하는 데이터를 병렬로 처리하는 데 걸린 시간: " + (endTime2 - startTime2) + "ms");
        System.out.println("결과2: " + sum2);
    }
}
