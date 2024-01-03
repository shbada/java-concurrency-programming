package io.concurrency.chapter02.exam02;

/**
 * 싱글 스레드
 * 모든 작업이 끝나면 main 스레드가 종료됨
 */
public class SingleThreadAppTerminatedExample {
    public static void main(String[] args) {

        int sum = 0;
        for (int i = 0; i <1000; i++) {
            sum += i;
        }

        System.out.println("sum : " + sum);

        System.out.println("메인 스레드 종료");

    }
}
