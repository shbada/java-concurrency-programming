package io.concurrency.chapter06.exam02;

public class CountingSemaphoreExample {
    public static void main(String[] args) {
        // 10개까지 세마포어 허용
        int permits = 10;

        CountingSemaphore semaphore = new CountingSemaphore(permits);
        SharedResource resource = new SharedResource(semaphore);

        // permits < threadCount 이면 permits가 0이 될때까지 스레드 수행되고, permits가 ++ 될때마다 세마포어 획득
        int threadCount = 5; // 전체 스레드 개수

        // thread 배열
        Thread[] threads = new Thread[threadCount];

        // 10개의 permits 락 획득할때마다 세마포어 갯수 --
        for (int i = 0; i < threadCount; i++) {
            // 정확한 값을 나오게 하려면, 아래 로직
//            threads[i] = new Thread(() -> {
//                synchronized (CountingSemaphoreExample.class) {
//                    resource.sum();
//                }
//            });

            // 결과가 계속 바뀐다.
            threads[i] = new Thread(resource::sum);
            threads[i].start();
        }

        // 락이 해제될때마다 세마포어 갯수 ++
        for (int i = 0; i < threadCount; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("최종 값: " + resource.getSum());
    }
}

