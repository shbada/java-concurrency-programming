package io.concurrency.chapter08.exam03;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockReadLockExample {

    public static void main(String[] args) {

        ReadWriteLock lock = new ReentrantReadWriteLock();

        /* 계좌 */
        BankAccount account = new BankAccount(lock, 0);

        /*
        아래 쓰레드들이 동시에 실행
        쓰기 쓰레드 수행 중에 읽기쓰레드 동시 수행 안됨
        읽기 쓰레드끼리는 동시 접근이 가능
        쓰기 쓰레드가 락을 가지고있으면 읽기 쓰레드 수행 불가능
        읽기 쓰레드 수행 중에도 쓰기 쓰레드 동시 수행 안됨
         */
        // 읽기 스레드가 잔액 조회
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                int balance = account.getBalance();
                System.out.println(Thread.currentThread().getName() + " - 현재 잔액: " + balance);
            }).start();
        }

        // 쓰기 스레드가 입금
        for (int i = 0; i < 2; i++) {
            new Thread(()->{
                int depositAmount = (int)(Math.random() * 1000);
                account.deposit(depositAmount);
                System.out.println(Thread.currentThread().getName() + " - 입금: " + depositAmount);
            }).start();
        }

        // 읽기 스레드가 잔액 조회
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                int balance = account.getBalance();
                System.out.println(Thread.currentThread().getName() + " - 현재 잔액: " + balance);
            }).start();
        }
    }
}
