package io.concurrency.chapter06.exam02;

public class BinarySemaphore implements CommonSemaphore {
    private int signal = 1; // 신호 전달 변수

    public synchronized void acquired() {
        while (this.signal == 0) {
            try {
                wait(); // 대기
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 현재 스레드의 인터럽트 상태를 설정
            }
        }
        this.signal = 0; // S--
    }

    public synchronized void release() {
        this.signal = 1; // S++
        this.notify();
    }
}
