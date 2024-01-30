package io.concurrency.chapter08.exam02;

import java.util.concurrent.locks.ReentrantLock;

public class LockAPIExample {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(1000); // 스레드 1이 Lock을 보유한 상태에서 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        Thread thread2 = new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(1000); // 스레드 2가 Lock을 보유한 상태에서 대기
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        thread1.start();
        thread2.start();

        try {
            Thread.sleep(500); // 메인 스레드 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Lock에 대한 정보 출력

        // 현재 스레드가 이 락을 보유한 횟수를 반환하며이 락을 보유하지 않은 경우에는 0 을 반환한다
        System.out.println("Hold Count: " + lock.getHoldCount());

        // 현재 스레드가 이 락을 보유하고 있는지 확인한다. 이 메서드는 주로 디버깅 및 테스트에 사용되며 락이 보유될 때만 호출되어야 하는 메서드는 이러한 경우를 확인할 수 있다
        System.out.println("Is Held By Current Thread: " + lock.isHeldByCurrentThread());

        // 스레드가 이 락을 획득하기 위해 대기 중인지 여부를 조회한다.
        // 취소는 언제든지 발생할 수 있으므로 true 를 반환한다고 해서 다른 스레드가 이 락을 획득한다고 보장하지 않는다 (모니터링 용으로 사용)
        System.out.println("Has Queued Threads: " + lock.hasQueuedThreads());
        System.out.println("has Queued Thread1: " + lock.hasQueuedThread(thread1));
        System.out.println("has Queued Thread2: " + lock.hasQueuedThread(thread2));

        // 이 락을 획득하기 위해 대기 중인 스레드 수의 추정치를 반환한다
        // 이 값은 내부 데이터 구조를 탐색하는 동안 스레드 수가 동적으로 변경될 수 있기 때문에 실제값과 다를 수 있다 (모니터링 용으로 사용)
        System.out.println("Queue Length: " + lock.getQueueLength());

        System.out.println("isFair: " + lock.isFair());

    }
}
