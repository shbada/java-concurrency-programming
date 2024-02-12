package io.concurrency.chapter09.exam02;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerGetAndUpdateExample {
    private static AtomicInteger accountBalance = new AtomicInteger(1000); // 초기 계좌 잔고

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> { // 2개 스레드 수행
                int withdrawalAmount = 500; // 출금액
                // 스레드 안전하게 수행
                int updatedBalance = accountBalance.getAndUpdate(balance -> {

                    if (balance >= withdrawalAmount) {
                        return balance - withdrawalAmount; // 출금 성공
                    } else {
                        return balance; // 출금 실패
                    }
                });

                // 3개 스레드 수행
                if (updatedBalance < 0) {
                    System.out.println(Thread.currentThread().getName() + " : 잔고 부족으로 출금 실패");
                } else {
                    System.out.println(Thread.currentThread().getName() + " : 출금 후 잔고: " + updatedBalance);
                }
            }).start();
        }
    }
}
