package ru.job4j.pools;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolEx<T> extends RecursiveTask<Integer> {
    int from;
    int to;
    T[] array;
    T target;

    public ForkJoinPoolEx(T[] array, int from, int to, T target) {
        this.from = from;
        this.to = to;
        this.array = array;
        this.target = target;
    }

    public static <T> int findElementIndex(T[] array, T element) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ForkJoinPoolEx<>(array, 0, array.length - 1, element));
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return find(target);
        }
        int mid = (from + to) / 2;
        ForkJoinPoolEx<T> left = new ForkJoinPoolEx<>(array, from, mid, target);
        ForkJoinPoolEx<T> right = new ForkJoinPoolEx<>(array, mid + 1, to, target);
        left.fork();
        right.fork();
        int leftResult = left.join();
        int rightResult = right.join();
        return Math.max(leftResult, rightResult);
    }

    public Integer find(T target) {
        int result = -1;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(target)) {
                result = i;
                break;
            }
        }
        return result;
    }
}
