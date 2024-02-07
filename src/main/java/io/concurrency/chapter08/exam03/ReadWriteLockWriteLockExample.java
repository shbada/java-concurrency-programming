package io.concurrency.chapter08.exam03;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockWriteLockExample {
    public static void main(String[] args) throws InterruptedException {
        ReadWriteLock lock = new ReentrantReadWriteLock();

        /* 계좌 */
        BankAccount account = new BankAccount(lock, 10000);

        /*
        쓰기 작업끼리는 상호배제 발생
        쓰기 락을 가지고있지 않을때 읽기 락 가능
         */
        // 읽기 스레드가 잔액 조회
        new Thread(() -> {
            int balance = account.getBalance();
            System.out.println("현재 잔액: " + balance);
        }).start();


        // 여러 스레드가 출금
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                int withdrawAmount = (int) (Math.random() * 1000);
                account.withdraw(withdrawAmount);
                System.out.println("출금: " + withdrawAmount);
            }).start();
        }

        // 읽기 스레드가 잔액 조회
        new Thread(() -> {
            int balance = account.getBalance();
            System.out.println("현재 잔액: " + balance);
        }).start();

    }
}


