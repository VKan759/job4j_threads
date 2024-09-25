package ru.job4j.pool;

import ru.job4j.threads.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(2);

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            Thread thread = new Thread(() -> {
                while (tasks.isEmpty()) {
                    try {
                        Runnable task = tasks.poll();
                        if (task != null) {
                            task.run();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }
    }

    public void work(Runnable job) throws InterruptedException {
            tasks.offer(job);
    }

    public void shutdown() throws InterruptedException {
        for (Thread thread : threads) {
            thread.interrupt();
            thread.join();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool();
        Runnable task0 = () -> System.out.println("Выполнение задачи №0 - " + Thread.currentThread().getName());
        Runnable task1 = () -> System.out.println("Выполнение задачи №1 - " + Thread.currentThread().getName());
        Runnable task2 = () -> System.out.println("Выполнение задачи №2 - " + Thread.currentThread().getName());
        Runnable task3 = () -> System.out.println("Выполнение задачи №3 - " + Thread.currentThread().getName());
        Runnable task4 = () -> System.out.println("Выполнение задачи №4 - " + Thread.currentThread().getName());
        pool.work(task0);
        pool.work(task1);
        pool.work(task2);
        pool.work(task3);
        pool.work(task4);
        pool.shutdown();
    }
}