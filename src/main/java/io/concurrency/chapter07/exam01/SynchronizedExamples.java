package io.concurrency.chapter07.exam01;

/**
 * Synchronized 예제
 */
public class SynchronizedExamples {
    // 공유변수
    private int instanceCount = 0;
    // 공유변수
    private static int staticCount = 0;

    /*
     1-3 : 같은 모니터 객체
     2-4 : 같은 모니터 객체

     1-2 : 서로 다른 모니터 객체이므로 서로 다른 스레드 진입 가능
     3-4 : 서로 다른 모니터 객체이므로 서로 다른 스레드 진입 가능

     스레드가 진입 이후 수행 완료 후 lock 을 해제하여 다른 스레드가 진입 가능
     */

    /**
     * 1
     */
    public synchronized void instanceMethod() {
        instanceCount++;
        // 아래 로직을 넣어보고 staticMethod() 와 다른 모니터이기 때문에 각 스레드 진입이 가능하다.
        // 이 경우에는 동시접근이 가능하므로 스레드 안전하지 못하다.
//        staticCount++;
        System.out.println("인스턴스 메서드 동기화: " + instanceCount);
    }

    /**
     * 2
     */
    public static synchronized void staticMethod() {
        staticCount++;
        System.out.println("정적 메서드 동기화: " + staticCount);
    }

    /**
     * 3
     */
    public void instanceBlock() {
        synchronized (this) {
            instanceCount++;
            // 아래 로직을 넣어보고 staticMethod() 와 다른 모니터이기 때문에 각 스레드 진입이 가능하다.
//            staticCount++;
            System.out.println("인스턴스 블록 동기화: " + instanceCount);
        }
    }

    /**
     * 4
     */
    public static void staticBlock() {
        synchronized (SynchronizedExamples.class) {
            staticCount++;
            System.out.println("정적 블록 동기화: " + staticCount);
        }
    }

    public static void main(String[] args) {
        SynchronizedExamples example = new SynchronizedExamples();

        // 동시 수행
        // 결과를 보면 먼저 수행된 스레드 이후 공유변수 사용
        new Thread(example::instanceMethod).start();
        new Thread(example::instanceBlock).start();
        new Thread(SynchronizedExamples::staticMethod).start();
        new Thread(SynchronizedExamples::staticBlock).start();
    }
}
