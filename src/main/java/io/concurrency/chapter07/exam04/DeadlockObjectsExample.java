package io.concurrency.chapter07.exam04;

public class DeadlockObjectsExample {
    public static void main(String[] args) {

        ResourceA resourceA = new ResourceA();
        ResourceB resourceB = new ResourceB();

        Thread thread1 = new Thread(() -> {
            resourceA.methodA(resourceB);
        });

        Thread thread2 = new Thread(() -> {
            resourceB.methodB(resourceA);
        });

        thread1.start();
        thread2.start();
    }
}

class ResourceA {
    // 아래 두 메서드는 동일한 모니터 객체를 가짐
    // 따라서 methodA2() 를 호출했을때 methodA() 락이 걸려있으면 대기해야함

    public synchronized void methodA(ResourceB resourceB) {
        System.out.println(Thread.currentThread().getName() + ": methodA 실행");
        try {
            Thread.sleep(100);  // 각 메소드에 지연을 추가하여 데드락 가능성 높임
        } catch (InterruptedException e) {}

        // 락 보유한 상태에서 호출
        resourceB.methodB2();
    }

    public synchronized void methodA2() {
        System.out.println(Thread.currentThread().getName() + ": methodA2 실행");
    }
}

class ResourceB {
    // 아래 두 메서드는 동일한 모니터 객체를 가짐
    // 따라서 methodB2() 를 호출했을때 methodB() 락이 걸려있으면 대기해야함

    public synchronized void methodB(ResourceA resourceA) {
        System.out.println(Thread.currentThread().getName() + ": methodB 실행");
        try {
            Thread.sleep(100);  // 각 메소드에 지연을 추가하여 데드락 가능성 높임
        } catch (InterruptedException e) {}
        resourceA.methodA2();
    }

    public synchronized void methodB2() {
        System.out.println(Thread.currentThread().getName() + ": methodB2 실행");
    }
}
