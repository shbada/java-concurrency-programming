package io.concurrency.chapter04.exam02;

public class FlagThreadStopExample {
    // volatile 키워드 추가
   volatile boolean running = true;
//    boolean running = true; // main memeory가 아닌 cache memory를 보기 때문에 원하는 결과가 나오지 않는다. (즉시 반영이 아니므로)

    public void volatileTest() {
        new Thread(() -> {
            int count = 0;
            while (running) {
                // volatile 아니여도 아래처럼 sleep 넣으면 됨
                // 컨텍스트 스위칭 발생하면 문맥전환함 (CPU가 가진 공유 메모리를 문맥정보에 담았다가 thread2로 갈때 캐시에 있는 값들을 비워줘야한다.
                // 그리고 대기 이후 thread2가 수행 이후 다시 여기 왔을때 본인의 메모리가 없다.
                // 그래서 이때 메모리껄 가져오기 때문에 running이 false가 되어있어서 읽어올 수 있다.
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
            } catch (InterruptedException e) {
            }
            System.out.println("Thread 2 종료 중..");
            running = false; // 바꾸는 순간 위 Thread의 while문이 종료된다.
        }).start();
    }

    public static void main(String[] args) {
        new FlagThreadStopExample().volatileTest();
    }
}
