package ru.job4j;

import org.junit.jupiter.api.RepeatedTest;

import static org.assertj.core.api.Assertions.*;

class CASCountTest {

    @RepeatedTest(15)
    public void checkIncrease() throws InterruptedException {
        CASCount casCount = new CASCount();
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                casCount.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                casCount.increment();
            }
        });
        thread.start();
        thread2.start();
        thread.join();
        thread2.join();
        assertThat(casCount.get()).isEqualTo(20);
    }
}