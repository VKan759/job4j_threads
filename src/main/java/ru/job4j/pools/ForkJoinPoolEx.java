package ru.job4j.pools;

import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolEx extends RecursiveTask<Integer> {
    int from;
    int to;
    Object[] array;
    Object target;

    public ForkJoinPoolEx(Object[] array, int from, int to, Object target) {
        this.from = from;
        this.to = to;
        this.array = array;
        this.target = target;
    }


    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            return find(target);
        }
        int mid = (from + to) / 2;
        ForkJoinPoolEx left = new ForkJoinPoolEx(array, from, mid, target);
        ForkJoinPoolEx right = new ForkJoinPoolEx(array, mid + 1, to, target);
        left.fork();
        right.fork();
        int leftResult = left.join();
        int rightResult = right.join();
        return leftResult != -1 ? leftResult : rightResult;
    }

    public Integer find(Object target) {
        int result = -1;
        for (int i = 0; i < to; i++) {
            if (array[i] == target) {
                result = i;
                break;
            }
        }
        return result;
    }
}
