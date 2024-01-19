package io.concurrency.chapter06.exam05;

import java.util.concurrent.atomic.AtomicBoolean;

public class SpinLockExample {
    private AtomicBoolean lock = new AtomicBoolean(false);

    public void lock() {
        // lock 을 점유할 수 있을때까지 반복 수행 (대기)
        while (!lock.compareAndSet(false, true)) ;
    }

    public void unlock() {
        // 수행되면 다른 쓰레드가 lock 을 점유할 수 있음
        lock.set(false);
    }

    public static void main(String[] args) {
        SpinLockExample spinLock = new SpinLockExample();

        Runnable task = () -> {
            spinLock.lock();
            System.out.println(Thread.currentThread().getName() + " 락을 획득했습니다!");

            // critical section
            try {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                System.out.println(Thread.currentThread().getName() + " 락을 해제합니다!");
                spinLock.unlock();
                System.out.println(Thread.currentThread().getName() + " 락을 해제했습니다!");
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();
    }
}
