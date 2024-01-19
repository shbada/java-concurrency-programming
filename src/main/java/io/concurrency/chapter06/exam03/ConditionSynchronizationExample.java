package io.concurrency.chapter06.exam03;

public class ConditionSynchronizationExample {
    private boolean isAvailable = false; // 조건변수

    public synchronized void produce() {
        while (isAvailable) {
            try {
                // 다른 스레드가 들어오더라도 무조건 대기
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("생산됨.");
        // 깨워지면 수행
        isAvailable = true;
        notify(); // consume() 호출 쪽에 스레드가 있다면 깨워준다.
    }

    public synchronized void consume() {
        while (!isAvailable) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("소비됨.");
        isAvailable = false;
        notify(); // produce() 호출쪽에 스레드가 있다면 깨워준다.
    }

    public static void main(String[] args) {
        ConditionSynchronizationExample example = new ConditionSynchronizationExample();

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                example.produce();
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                example.consume();
            }
        });

        producer.start();
        consumer.start();
    }
}