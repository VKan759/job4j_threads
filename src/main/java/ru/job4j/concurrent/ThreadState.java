package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        second.start();
        Thread.sleep(1000);
        if (second.getState() == Thread.State.TERMINATED
                && first.getState() == Thread.State.TERMINATED) {
            System.out.println("Работа завершена");
        }
    }
}