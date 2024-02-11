package io.concurrency.chapter09.exam01;

import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadCASExample {
    // 캐시가 아닌 main memory 에서 값을 가져오는 변수
    private static AtomicInteger value = new AtomicInteger(0);
    private static final int NUM_THREADS = 3;

    /*
    1. 3개의 스레드가 각각 5번씩 CAS 연산을 시도한다
    2. compareAndSet 메서드를 사용해서 현재 값과 변경하려는 값을 비교하여 일치할 때까지 시도한다
    3. CAS 연산이 성공할 때마다 각 스레드는 결과를 출력하고, 모든 스레드가 작업을 마친 후 최종 값을 출력한다
    4. 실행 결과에서는 CAS 연산이 동시에 여러 스레드에서 수행되더라도 적절히 값을 증가시키는 것을 확인할 수 있다
     */
    public static void main(String[] args) {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < NUM_THREADS; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    // 기댓값, 새로 설정하려는 값
                    int expectedValue, newValue;
                    do { // main memory의 값과 신규 값이 동일할 때만 수행
                        // main memory 에서 가져온 값
                        expectedValue = value.get();
                        // 신규 값
                        newValue = expectedValue + 1;
                        // compareAndSet 수행하는 시점에 expectedValue 가 달라져있을 수 있음 (이때 새로운 값으로 변경하지 않게됨)
                    } while (!value.compareAndSet(expectedValue, newValue)); // 반환 값이 false 이면 true 가 반환 될 때 까지 재시도
                    System.out.println(Thread.currentThread().getName() + ":" + expectedValue + " , " + newValue);
                }
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Final value: " + value.get());
    }
}
