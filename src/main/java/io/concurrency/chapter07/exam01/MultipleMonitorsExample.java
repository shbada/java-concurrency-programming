package io.concurrency.chapter07.exam01;

class BankAccount {
    private double balance;
    private final Object lock = new Object();

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    /**
     * 입금
     * @param amount
     */
    public void deposit(double amount) {
        synchronized (lock) { // deposit, withdraw 는 모니터가 동일
            balance += amount;
        }
    }

    /**
     * 출금
     * @param amount
     * @return
     */
    public boolean withdraw(double amount) {
        synchronized (lock) {
            if (balance < amount) {
                return false;
            }
            balance -= amount;
            return true;
        }
    }

    /**
     * 이체
     * @param to
     * @param amount
     * @return
     */
    public boolean transfer(BankAccount to, double amount) {
        // 만약에 1번 쓰레드가 이 작업을 수행할때 여기에 다른 쓰레드는 못들어온다.
        synchronized (this.lock) { // 위 lock 객체 (deposit, withdraw 랑 동일한 객체)
            if (this.withdraw(amount)) {
                // 1번 쓰레드는 계좌 A, B 의 lock(모니터)를 가지고있다
                // -> 1번 쓰레드의 작업이 끝날때까지 계좌 A, B의 lock 을 어떤 스레드도 획득하지 못한다.
                synchronized (to.lock) { // 이체받을 대상 계좌의 lock
                    to.deposit(amount);
                    return true;
                }
            }
            return false;
        }
    }

    public double getBalance() {
        synchronized (lock) {
            return balance;
        }
    }

}

public class MultipleMonitorsExample {

    public static void main(String[] args) {
        // 계좌 A, B 생성
        BankAccount accountA = new BankAccount(1000);
        BankAccount accountB = new BankAccount(1000);

        // accountA에서 accountB로 송금하는 스레드
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                // 계좌이체
                boolean result = accountA.transfer(accountB, 10);
                if (result) {
                    System.out.println("accountA에서 accountB로 10 송금 성공");
                } else {
                    System.out.println("accountA에서 accountB로 10 송금 실패");
                }
            }
        });

        // accountB에서 accountA로 송금하는 스레드
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                // 계좌이체
                boolean result = accountB.transfer(accountA, 10);
                if (result) {
                    System.out.println("accountB에서 accountA로 10 송금 성공");
                } else {
                    System.out.println("accountB에서 accountA로 10 송금 실패");
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("accountA의 최종 잔액: " + accountA.getBalance());
        System.out.println("accountB의 최종 잔액: " + accountB.getBalance());
    }
}