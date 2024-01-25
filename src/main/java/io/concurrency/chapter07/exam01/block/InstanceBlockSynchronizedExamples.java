package io.concurrency.chapter07.exam01.block;

public class InstanceBlockSynchronizedExamples {

    private int count = 0;

    // lock 객체 생성
    private Object lockObject = new Object();

    public void incrementBlockThis(){
        //
        synchronized (this) {
            count++;
            System.out.println(Thread.currentThread().getName() + " 가 This 에 의해 블록 동기화 함 : " + count);
        }
    }
    public void incrementBlockLockObject() {
        // 모니터 객체가 동일해야 thread1, thread2 수행 후 thread3, thread4가 수행되서 원하는 결과가 나온다.
        synchronized (lockObject){
            count++;
            System.out.println(Thread.currentThread().getName() + " 가 LockObject 에 의해 블록 동기화 함 : " + count);
        }
    }
    public static void main(String[] args){

        InstanceBlockSynchronizedExamples example = new InstanceBlockSynchronizedExamples();

        /*
        thread1, thread2 는 서로 상호배제
        thread3, thread4 는 서로 상호배제 (3 수행중 4 불가능)
         */
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example.incrementBlockThis();
            }
        },"스레드 1");

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example.incrementBlockThis();
            }
        },"스레드 2");

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example.incrementBlockLockObject();
            }
        },"스레드 3");

        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 100000; i++) {
                example.incrementBlockLockObject();
            }
        },"스레드 4");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("최종값:"  + example.count);

    }
}
