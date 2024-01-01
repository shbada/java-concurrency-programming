package io.concurrency.chapter01.exam02;

public class ContextSwitchExample {
    public static void main(String[] args) {
        // thread1, thread2, thread3을 동시에 실행

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 1: " + i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 2: " + i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 3: " + i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }

    /*
    실행결과
    Thread 1: 0
    Thread 2: 0
    Thread 3: 0
    Thread 1: 1
    Thread 3: 1
    Thread 2: 1
    Thread 3: 2
    Thread 2: 2
    Thread 1: 2
    Thread 1: 3
    Thread 3: 3
    Thread 2: 3
    Thread 2: 4
    Thread 1: 4
    Thread 3: 4

    thread1, thread2, thread3 순서가 바뀌는 것은 컨텍스트 스위칭이 발생하기 때문
    thread1을 할당받아서 처리하다가, thread2을 할당받아서 처리하다가...
    각각의 쓰레드마다 할당 받아서 진행하고 있는 것임
    쓰레드 개수가 많으면 thread1, thread1, thread3 등 어떤 쓰레드는 더 많이 실행하고, 더 적게 실행하는게 보일 수 있음
    이게 컨텍스트 스위칭을 하기 때문에 발생
     */
}
