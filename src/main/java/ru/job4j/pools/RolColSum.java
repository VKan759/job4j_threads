package ru.job4j.pools;

import java.util.concurrent.CompletableFuture;

public class RolColSum {
    public static Sums[] sum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        int[] rowSums = new int[matrix.length];
        for (int row = 0; row < matrix.length; row++) {
            int sum = 0;
            for (int cell = 0; cell < matrix.length; cell++) {
                sum = sum + matrix[row][cell];
            }
            rowSums[row] = sum;
        }
        int[] cellSums = new int[matrix.length];
        for (int cell = 0; cell < matrix.length; cell++) {
            int sum = 0;
            for (int[] ints : matrix) {
                sum = sum + ints[cell];
            }
            cellSums[cell] = sum;
        }
        for (int i = 0; i < matrix.length; i++) {
            result[i] = new Sums(rowSums[i], cellSums[i]);
        }
        return result;
    }

    public static CompletableFuture<Integer> getRowSum(int[][] matrix, int row) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int i = 0; i < matrix.length; i++) {
                sum = sum + matrix[row][i];
            }
            return sum;
        });
    }

    public static CompletableFuture<Integer> getCellSum(int[][] matrix, int cell) {
        return CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int[] ints : matrix) {
                sum = sum + ints[cell];
            }
            return sum;
        });
    }

    public static Sums[] asyncSum(int[][] matrix) {
        Sums[] result = new Sums[matrix.length];
        CompletableFuture<Sums>[] futures = new CompletableFuture[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int index = i;
            futures[i] = getRowSum(matrix, i).thenCombine(getCellSum(matrix, i), (rowSum, cellSum) ->
                    result[index] = new Sums(rowSum, cellSum)
            );
        }
        CompletableFuture.allOf(futures).join();
        return result;
    }
}