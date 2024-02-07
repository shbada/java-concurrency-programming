package io.concurrency.chapter08.exam06;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {

    private final Lock lock = new ReentrantLock();
    /* condition 객체 */
    private final Condition condition = lock.newCondition();
    private boolean flag = false;

    /**
     * 대기
     * @throws InterruptedException
     */
    public void awaiting() throws InterruptedException {
        lock.lock();
        try {
            while (!flag) {
                System.out.println("조건이 만족 하지 못해 대기함");
                condition.await();
            }
            System.out.println("임계영역 수행");
        } finally {
            lock.unlock();
        }
    }

    /**
     * 깨우기
     */
    public void signaling() {
        lock.lock();
        try {
            flag = true;
            System.out.println("조건을 만족 시키고 깨움");
            condition.signalAll();
        } finally {
            // 락을 해제해야 깨어난 쓰레드가 락을 획득할 수 있다.
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {

        ConditionExample conditionExample = new ConditionExample();

        /* t1 */
        Thread thread1 = new Thread(() -> {
            try {
                conditionExample.awaiting();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        /* t2 */
        Thread thread2 = new Thread(conditionExample::signaling);

        /*
        t1 대기
        t2 가 t1을 깨움
        t1 수행
         */
        thread1.start();
        Thread.sleep(2000); // 스레드 thread1이 플래그를 기다리게 하기 위한 잠깐의 지연
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
