package io.concurrency.chapter07.exam01.block;

public class InstanceBlockSynchronizedExamples2 {

    private int count = 0;

    private Object lockObject = new Object();

    public void incrementBlockThis(){
        synchronized (this){
            count++;
            System.out.println(Thread.currentThread().getName() + " 가 This 에 의해 블록 동기화 함 : " + count);
        }
    }
    public void incrementBlockLockObject() {
        synchronized (lockObject){ // this 여야 동기화되겠다
            count++;
            System.out.println(Thread.currentThread().getName() + " 가 LockObject 에 의해 블록 동기화 함 : " + count);
        }
    }
    public static void main(String[] args){

        // 여기서 모니터는 4개다
        // 각 개체가 2개씩 가지고 있으므로!
        InstanceBlockSynchronizedExamples2 example1 = new InstanceBlockSynchronizedExamples2();
        InstanceBlockSynchronizedExamples2 example2 = new InstanceBlockSynchronizedExamples2();

        /*
        thread1, thread2 , thread3, thread4 모두 모니터가 다르다.
        상호배제없이 모두 접근 가능해버린다.
         */
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                example1.incrementBlockThis();
            }
        },"스레드 1");

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                example2.incrementBlockThis();
            }
        },"스레드 2");

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                example1.incrementBlockLockObject();
            }
        },"스레드 3");

        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                example2.incrementBlockLockObject();
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

            System.out.println("최종값:"  + example1.count);
            System.out.println("최종값:"  + example2.count);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }



    }
}
