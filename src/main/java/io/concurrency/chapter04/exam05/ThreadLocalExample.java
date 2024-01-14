package io.concurrency.chapter04.exam05;

/**
 * debugging
 * 1) Thread.java > ThreadLocal.ThreadLocalMap threadLocals = null;
 *
 * ThreadLocal > get()
 * -> ThreadLocalMap이 null인 경우 setInitialValue() 호출 > createMap() (Entry 객체 사용)
 *
 * ThreadLocal > set()
 * -> ThreadLocalMap 객체를 가져와서 set()
 * ThreadLocalMap > set()
 * -> 최종적으로 값 저장
 *
 * ThreadLocal > get()
 * -> getEntry()
 * ->
 */
public class ThreadLocalExample {
    // 초기값 설정
    private static ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "Hello World");
    // ThreadLocal 변수 생성. 초기값은 null
//    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

        // 첫 번째 스레드: ThreadLocal 값을 설정하고 출력
        Thread thread1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
            threadLocal.set("스레드 1의 값");
            System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
        }, "Thread-1");

        // 두 번째 스레드: ThreadLocal 값을 설정하고 출력
        Thread thread2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
            threadLocal.set("스레드 2의 값");
            System.out.println(Thread.currentThread().getName() + ": " + threadLocal.get());
        }, "Thread-2");

        thread1.start();
        thread2.start();
    }
}
