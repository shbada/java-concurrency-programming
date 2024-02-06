package io.concurrency.chapter08.exam03;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    /*
    읽기락은 동시에 가능
    읽기락 수행 중에 쓰기락 획득 못함
    쓰기락 수행 중에 읽기락 획득 못함
     */
    public static void main(String[] args) {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        SharedData sharedData = new SharedData();

        Thread reader1 = new Thread(() -> {
            // 읽기락
            readWriteLock.readLock().lock();
            try {
                System.out.println("읽기 스레드 1이 데이터를 읽고 있습니다. 데이터: " + sharedData.getData());
                try {
                    Thread.sleep(1000); // 1초
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } finally {
                readWriteLock.readLock().unlock();
            }
        });

        Thread reader2 = new Thread(() -> {
            // 읽기락
            readWriteLock.readLock().lock();
            try {
                System.out.println("읽기 스레드 2가 데이터를 읽고 있습니다. 데이터: " + sharedData.getData());
                try {
                    Thread.sleep(1000); // 1초
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } finally {
                readWriteLock.readLock().unlock();
            }
        });

        Thread writer = new Thread(() -> {
            // 쓰기락
            readWriteLock.writeLock().lock();
            try {
                System.out.println("쓰기 스레드가 데이터를 쓰고 있습니다");
                sharedData.setData(40);
                try {
                    Thread.sleep(2000); // 2초
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("쓰기 스레드가 데이터를 변경 했습니다. 데이터: " + sharedData.getData());
            } finally {
                readWriteLock.writeLock().unlock();
            }
        });

        writer.start();
        reader1.start();
        reader2.start();
    }
    static class SharedData{
        private int data = 0;

        public int getData() {
            return data;
        }

        public void setData(int data) {
            this.data = data;
        }
    }

}
