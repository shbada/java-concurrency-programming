package io.concurrency.chapter06.exam01;
public class SharedData {
    private int sharedValue = 0;
    private Mutex mutex;
    public SharedData(Mutex mutex) {
        this.mutex = mutex;
    }

    /**
     * mutex 사용 예제
     */
    public void sum() {
        try {
            mutex.acquired(); // Lock 을 획득

            // 임계영역
            for (int i = 0; i < 10_000_0000; i++) {
                sharedValue++;
            }

        } finally { // 반드시 finally
            // 오류가 나더라도 finally가 실행되도록 해야한다.
            mutex.release(); // Lock 해제
        }
    }

    public int getSum() {
        return sharedValue;
    }
}