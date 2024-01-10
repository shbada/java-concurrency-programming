package io.concurrency.chapter11.exam12;

import java.util.concurrent.ForkJoinPool;

public class CustomForkJoinPoolExample {

    public static void main(String[] args) {

        int[] array = new int[10];
        for (int i = 0; i < array.length; i++) {
            // [0,1,2,3,4,5,6,7,8,9]
            // [0,1] = 0
            // [1,2] = 1 = 0(위에 [0,1]) + 1 = 1
            // [2,3] = 2
            // [3,4] = 3 = 2(위에 [2,3]) + 3 = 5 => 5 + 1(위에 [0,1][1,2]) = 6
            // [4,5] = 4
            // [5,6] = 5 = 4 + 5 = 9
            // [6,7] = 6
            // [7,8] = 7
            // [8,9] = 8

            array[i] = i;
        }

        // ForkJoinPool 생성 (쓰레드수 = CPU 코어 갯수만큼)
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        // RecursiveTask 상속받는 커스텀 클래스 생성
        // compute() 오버라이딩
        /*
           RecursiveTask
           n 이 1보다 크면 Fibonacci 클래스 f1, f2 생성해서 두개의 클래스를 가지고 compute 작업을 수행
           (1개는 fork(), 다른 하나는 직접 compute() 수행하고 f1을 join()해서 결과를 기다림)
           그러고 합산해서 결과 다시 compute() 수행
         */
        CustomRecursiveTask task = new CustomRecursiveTask(array, 0, array.length);
        long result = pool.invoke(task);

        System.out.println("result = " + result);
        System.out.println("pool = " + pool);
        System.out.println("stealing = " + pool.getStealCount());

        pool.shutdown();
    }
}
