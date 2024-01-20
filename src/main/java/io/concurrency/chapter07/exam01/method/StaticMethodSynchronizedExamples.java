package io.concurrency.chapter07.exam01.method;

public class StaticMethodSynchronizedExamples {

    private static int count = 0;

    /*
        클래스 타입 자체가 메모리에 1개밖에 없으므로 모니터를 2개줄 수 없음
        아래 두 메서드의 모니터는 동일
        여러개의 모니터를 활용해야 한다면 인스턴스 방식으로 해야할것
     */

    public static synchronized void increment(){
        count++;
        System.out.println(Thread.currentThread().getName() + " 가 증가시켰습니다. 현재 값:" + count);
    }
    public static synchronized void decrement(){
        count--;
        System.out.println(Thread.currentThread().getName() + " 가 감소시켰습니다. 현재 값:" + count);
    }

    public static int getCount(){
        return count;
    }

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                StaticMethodSynchronizedExamples.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                StaticMethodSynchronizedExamples.decrement();
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

        System.out.println("최종값:"  + StaticMethodSynchronizedExamples.getCount());
    }
}
