package io.concurrency.chapter02.exam02;

/**
 * 멀티 스레드
 * main 스레드가 종료된다해서 종료되는 것이 아님
 * main 스레드는 가장 먼저 종료됨
 * 그 이후, 각 쓰레드가 모두 수행되고, 모두 종료가 되어야 애플리케이션이 종료된다.
 * 멀티 스레드의 경우 모든 쓰레드가 모두 종료되어야한다.
 * 쓰레드 중 단 하나라도 종료가 안되면 애플리케이션이 종료가 안된다.
 */
public class MultiThreadAppTerminatedExample {
    public static void main(String[] args) {

        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(new ThreadStackExample.MyRunnable(i));
            thread.start();
        }

        System.out.println("메인 스레드 종료");

    }
    static class MyRunnable implements Runnable{

        private final int threadId;

        public MyRunnable(int threadId) {

            this.threadId = threadId;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + ": 스레드 실행 중...");
            firstMethod(threadId);
        }

        private void firstMethod(int threadId) {

            int localValue = threadId + 100;
            secondMethod(localValue);

        }

        private void secondMethod(int localValue) {
            String objectReference = threadId + ": Hello World";
            System.out.println(Thread.currentThread().getName() + " : 스레드 ID : " + threadId + ", Value:" + localValue);
        }
    }
}
