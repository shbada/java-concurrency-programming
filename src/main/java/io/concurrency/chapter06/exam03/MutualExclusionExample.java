package io.concurrency.chapter06.exam03;

public class MutualExclusionExample {
    private int counter = 0;

    // synchronized : 내부적으로 모니터 기능을 가지고있다.
    public synchronized void increment() {
        // 두 스레드가 동시에 호출할때 아래 공유변수를 동기화블록 안에서 처리해야한다. (상호배제의 기능이 들어가야한다.)
        counter++;
        System.out.println("스레드: " + Thread.currentThread().getName() + ", 카운터 값: " + counter);
    }

    public static void main(String[] args) {
        MutualExclusionExample example = new MutualExclusionExample();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                example.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                example.increment();
            }
        });

        thread1.start();
        thread2.start();
    }
}
