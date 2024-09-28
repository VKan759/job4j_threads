package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class RolColSumTest {
    @Test
    void whenSum() {
        int[][] array = {
                {1, 2},
                {3, 4}
        };
        Sums[] result = {new Sums(3, 4), new Sums(7, 6)};
        assertThat(RolColSum.sum(array)).isEqualTo(result);
    }

    @Test
    void whenAsyncSum() {
        int[][] array = {
                {1, 2},
                {3, 4}
        };
        Sums[] result = {new Sums(3, 4), new Sums(7, 6)};
        assertThat(RolColSum.asyncSum(array)).isEqualTo(result);
    }
}
