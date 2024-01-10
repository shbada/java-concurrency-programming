package io.concurrency.chapter11.exam12;

import java.util.concurrent.RecursiveTask;

public class CustomRecursiveTask extends RecursiveTask<Long> {
    private static final int THRESHOLD = 2;
    private final int[] array;
    private final int start;
    private final int end;

    public CustomRecursiveTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() { // 자체가 queue에 들어가는 작업
        if (end - start < THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }

            return sum;
        } else {
            int mid = start + (end - start) / 2; // 중간값
            // task 분할
            CustomRecursiveTask left = new CustomRecursiveTask(array, start, mid);
            CustomRecursiveTask right = new CustomRecursiveTask(array, mid, end);

            // fork() 수행하면, 별도의 쓰레드가 이 task의 compute()를 호출하게된다.
            left.fork(); // 해당 task가 forkJoinPool의 queue에 들어감 (쓰레드가 이 queue에서 꺼내서 수행한다는 의미)
            // 직접 compute() 한다는건 현재 쓰레드가 작업을 수행한다는 것이다.
            long rightResult = right.compute(); // 재귀
            long leftResult = left.join(); // 작업을 마치고 올때까지 기다린다.

            // right는 현재 쓰레드가 수행하므로 바로 가능 
            return leftResult + rightResult;
        }
    }
}
