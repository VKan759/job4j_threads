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
        while (first.getState() != Thread.State.TERMINATED && second.getState() != Thread.State.TERMINATED) {
            if (first.getState() == Thread.State.RUNNABLE) {
                System.out.println(first.getName());
            }
            if (second.getState() == Thread.State.RUNNABLE) {
                System.out.println(second.getName());
            }
        }
        System.out.println("Работа завершена");
    }
}