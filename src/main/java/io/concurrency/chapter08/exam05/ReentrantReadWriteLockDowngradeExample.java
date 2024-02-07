package io.concurrency.chapter08.exam05;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockDowngradeExample {
    public static void main(String[] args) {

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Lock readLock = lock.readLock();
        Lock writeLock = lock.writeLock();
        SharedData sharedData = new SharedData();

        // 여러 개의 스레드가 쓰기 락을 사용하는 경우
        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                // 쓰기락 획득 (상호배제)
                writeLock.lock();

                try {
                    System.out.println("쓰기 락 획득: " + Thread.currentThread().getName());

                    // 임계 영역 수행 (상호배제)
                    sharedData.setData((int) (Math.random() * 100));
                    System.out.println("데이터 업데이트");

                    // 쓰기 락을 읽기 락으로 다운그레이드 (락의 재진입)
                    // write lock -> read lock
                    // 재진입 가능
                    readLock.lock();
                    System.out.println("다운그레이드: 쓰기 락 -> 읽기 락");

                    // 쓰기 락 해제
                    // 이 전까지의 영역만 상호배제
                    // 이 이후부터는 read lock만 가지고 있는 상태가 됨 -> 여러 스레드 접근 가능
                    writeLock.unlock();
                    System.out.println("쓰기 락 해제: " + Thread.currentThread().getName());

                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } finally {
                    readLock.unlock();
                    System.out.println("다운그레이드: 읽기 락 해제: " + Thread.currentThread().getName());
                }
            }).start();
        }

        // 여러 개의 스레드가 읽기 락을 사용하는 경우
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                readLock.lock();
                try {
                    System.out.println("읽기 락 획득: " + Thread.currentThread().getName());
                    int data = sharedData.getData();
                    System.out.println("데이터 읽기: " + data);
                } finally {
                    readLock.unlock();
                    System.out.println("읽기 락 해제: " + Thread.currentThread().getName());
                }
            }).start();
        }
    }
    static class SharedData {
        private int data = 0; // 공유변수

        public int getData() {
            return data;
        }
        public void setData(int value) {
            data = value;
        }
    }
}
