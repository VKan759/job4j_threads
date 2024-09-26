package ru.job4j.pools;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ForkJoinPoolExTest {
    @Test
    public void lineFindElement() {
        int target = 2;
        Integer[] array = {1, 2, 3, 4, 5, 6};
        assertThat(ForkJoinPoolEx.findElementIndex(array, target)).isEqualTo(1);
    }

    @Test
    public void findElement() {
        Integer[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        int target = 2;
        assertThat(ForkJoinPoolEx.findElementIndex(array, target)).isEqualTo(1);
    }

    @Test
    public void findUser() {
        User target = new User("Vyacheslav", 1);
        Object[] elements = {6, 7, 8, 9, 10, target, 11};
        assertThat(ForkJoinPoolEx.findElementIndex(elements, target)).isEqualTo(5);
    }

    @Test
    public void elementNotFound() {
        Object[] array = {1, 21, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        assertThat(ForkJoinPoolEx.findElementIndex(array, 14)).isEqualTo(-1);
    }
}