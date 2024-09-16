package ru.job4j.threads;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SimpleBlockingQueueTest {

    @Test
    public void checkProducer() throws InterruptedException {
        SimpleBlockingQueue<String> simpleBlockingQueue = new SimpleBlockingQueue<>(3);
        simpleBlockingQueue.offer("first");
        simpleBlockingQueue.offer("second");
        simpleBlockingQueue.offer("third");
        Thread thread = new Thread(() -> {
            try {
                simpleBlockingQueue.offer("fourth");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        Thread.sleep(100);
        assertThat(thread.getState()).isEqualTo(Thread.State.WAITING);
    }

    @Test
    public void checkConsumer() throws InterruptedException {
        SimpleBlockingQueue<String> simpleBlockingQueue = new SimpleBlockingQueue<>(3);
        Thread thread = new Thread(() -> {
            try {
                String result = simpleBlockingQueue.poll();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
        Thread.sleep(100);
        assertThat(thread.getState()).isEqualTo(Thread.State.WAITING);
    }

}