package io.concurrency.chapter07.exam01.method;

public class InstanceMethodSynchronizedExamples2 {

    private int count = 0;

    public synchronized void increment(){
        count++;
        System.out.println(Thread.currentThread().getName() + " 가 증가시켰습니다. 현재 값:" + count);
    }

    public synchronized void decrement(){
        count--;
        System.out.println(Thread.currentThread().getName() + " 가 감소시켰습니다. 현재 값:" + count);
    }

    public int getCount(){
        return count;
    }

    public static void main(String[] args) {
        // 2개의 객체로 수행
        // 여러개 모니터를 가지고 수행하더라도, 결국 같은 모니터끼리의 메서드는 스레드가 달라도 여전히 동기화가 된다.
        InstanceMethodSynchronizedExamples2 counter1 = new InstanceMethodSynchronizedExamples2();
        InstanceMethodSynchronizedExamples2 counter2 = new InstanceMethodSynchronizedExamples2();

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                counter1.increment();
                counter2.decrement();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                counter2.increment(); // t1이 수행중이여도 접근 가능
                counter1.decrement(); // 1이 수행중일경우 , t2가 실행하지 못하고
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("최종값:"  + counter1.getCount());
        System.out.println("최종값:"  + counter2.getCount());
    }
}
