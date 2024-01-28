package io.concurrency.chapter07.exam03;

/**
 * 가시성 보장
 */
public class VolatileExample {
    // volatile 키워드 추가
//   volatile boolean running = true; // main memory에 쓰고, main memory에서 읽어와야 가시성 보장
   boolean running = true; // 각 쓰레드마다 해당 변수를 cache memory에서 가져옴

    public void volatileTest() {
        new Thread(() -> {
            int count = 0;
            while (running) { // 반복 기준
                /*try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }*/
                count++;
            }
            System.out.println("Thread 1 종료. Count: " + count);
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
            System.out.println("Thread 2 종료 중..");
            running = false; // false로 변경
        }).start();
    }

    public static void main(String[] args) {
        new VolatileExample().volatileTest();
    }
}
