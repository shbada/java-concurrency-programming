package io.concurrency.chapter06.exam01;
public class Mutex {
    private boolean lock = false;

    public synchronized void acquired() { // synchronized 로 상호배제 등 추가적인 기능 포함
        // 동시 접근 불가능
        while (lock) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 이렇게 해야 lock을 잡은 쓰레드가 release()를 호출할때까지 lock이 잡힌다.
        this.lock = true;
    }

    public synchronized void release() {
        this.lock = false; // lock 해제
        this.notify();
    }
}
