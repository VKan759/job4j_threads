package ru.job4j.email;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        String subject = String.format("Notification {%s} to email {%s}", user.getUserName(), user.getEmail());
        String body = String.format("Add a new event to %s", user.getUserName());
        executor.submit(() -> send(subject, body, user.getEmail()));
    }

    public void send(String subject, String body, String email) {
    }

    public void close() {
        executor.shutdown();
        while (!executor.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        User user = new User("Vyacheslav", "vkan745@gmail.com");
        EmailNotification notification = new EmailNotification();
        notification.emailTo(user);
        notification.close();
    }
}

