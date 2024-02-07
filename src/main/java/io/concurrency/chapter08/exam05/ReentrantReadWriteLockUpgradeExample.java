package io.concurrency.chapter08.exam05;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockUpgradeExample {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public void failedUpgradeAttempt() {
        System.out.println("읽기 잠금 획득 시도...");
        // 읽기락 획득
        lock.readLock().lock();
        System.out.println("읽기 잠금 획득!");

        try {
            System.out.println("쓰기 잠금 획득 시도...");
            // 쓰기락 획득 불가
            lock.writeLock().lock(); // 데드락 발생
            System.out.println("이 메시지는 출력되지 않습니다.");
        } finally {
            lock.readLock().unlock();
        }
    }

    public static void main(String[] args) {
        ReentrantReadWriteLockUpgradeExample example = new ReentrantReadWriteLockUpgradeExample();
        example.failedUpgradeAttempt();
    }
}
