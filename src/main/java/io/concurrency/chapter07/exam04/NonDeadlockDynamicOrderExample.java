package io.concurrency.chapter07.exam04;

public class NonDeadlockDynamicOrderExample {
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            methodWithLocks(lock1, lock2);
        });

        Thread thread2 = new Thread(() -> {
            methodWithLocks(lock2, lock1);
        });
        thread1.start();
        thread2.start();
    }

    private static void methodWithLocks(Object lockA, Object lockB) {
        // 아래 구문을 통해 lock 순서 조절
        Object firstLock = lockA;
        Object secondLock = lockB;

        // identityHashCode() : 각 객체의 해시코드를 가져와서 크기 비교
        // 각 객체의 순서를 동일하도록 조절하기 위함
        if (System.identityHashCode(lockA) > System.identityHashCode(lockB)) {
            firstLock = lockB;
            secondLock = lockA;
        }

        synchronized (firstLock) {
            System.out.println(Thread.currentThread().getName() + ": lockA 획득");

            synchronized (secondLock) {
                System.out.println(Thread.currentThread().getName() + ": lockB 획득");
            }
        }
    }
}
