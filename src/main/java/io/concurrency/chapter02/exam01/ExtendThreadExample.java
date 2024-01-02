package io.concurrency.chapter02.exam01;

/**
 * Thread를 상속한 클래스
 */
public class ExtendThreadExample {
    public static void main(String[] args) {

        MyThread myThread = new MyThread();
        myThread.start();
    }
 }

 class MyThread extends Thread{
     @Override
     public void run() {
         System.out.println(Thread.currentThread().getName() + " :스레드 실행 중.. ");
     }
 }