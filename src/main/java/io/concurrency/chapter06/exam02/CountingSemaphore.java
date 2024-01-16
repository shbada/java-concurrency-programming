package io.concurrency.chapter06.exam02;

public class CountingSemaphore implements CommonSemaphore {
    // permits 갯수를 넘으면 안되며, permits와 최초에 동일하게 셋팅해준다.
    // -, + 되는 변수
    private int signal; // 우리가 정할 수 없다. 생성자로 받을것
    private int permits; // 얼마만큼의 쓰레드를 동시 허용할것인가?

    public CountingSemaphore(int permits) {
        this.permits = permits;
        this.signal = permits;
    }

    public void acquired() {
        synchronized (this) {
            while (this.signal == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.signal--;
        }
        System.out.println(Thread.currentThread().getName() + " 락 획득, 현재 세마포어 값: " + signal);
    }

    public synchronized void release() {
        if (this.signal < permits) { // signal 값이 permits 보다 작을 때만 증가
            this.signal++;
            System.out.println(Thread.currentThread().getName() + " 락 해제, 현재 세마포어 값: " + signal);
            notifyAll();
        }
    }
}
