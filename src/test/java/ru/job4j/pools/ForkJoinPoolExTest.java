package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.assertj.core.api.Assertions.*;

class ForkJoinPoolExTest {
    @Test
    public void lineFindElement() {

        Integer[] array = {1, 2, 3, 4, 5, 6};
        ForkJoinPoolEx forkJoinPoolEx = new ForkJoinPoolEx(array, 0, array.length - 1, 2);
        ForkJoinPool pool = new ForkJoinPool();
        assertThat(pool.invoke(forkJoinPoolEx)).isEqualTo(1);
    }

    @Test
    public void findElement() {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        ForkJoinPoolEx forkJoinPoolEx = new ForkJoinPoolEx(array, 0, array.length - 1, 2);
        assertThat(forkJoinPool.invoke(forkJoinPoolEx)).isEqualTo(1);
    }

    @Test
    public void findUser() {
        ForkJoinPool pool = new ForkJoinPool();
        User user = new User("Vyacheslav", 1);
        Object[] elements = {1, 2, 3, 4, 5, user, 6, 7, 8, 9, 10, 11};
        ForkJoinPoolEx forkJoinPoolEx = new ForkJoinPoolEx(elements, 0, elements.length - 1, user);
        assertThat(pool.invoke(forkJoinPoolEx)).isEqualTo(5);
    }

    @Test
    public void elementNotFound() {
        ForkJoinPool pool = new ForkJoinPool();
        Object[] array = {1, 21, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        ForkJoinPoolEx example = new ForkJoinPoolEx(array, 0, array.length - 1, 14);
        assertThat(pool.invoke(example)).isEqualTo(-1);
    }
}