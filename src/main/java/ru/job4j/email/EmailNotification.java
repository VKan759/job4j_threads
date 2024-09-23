package ru.job4j.email;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public List<String> emailTo(User user) {
        String subject = String.format("Notification {%s} to email {%s}", user.getUserName(), user.getEmail());
        String body = String.format("Add a new event to %s", user.getUserName());
        return List.of(subject, body);
    }

    public void send(String subject, String body, String email) {
    }

    public void close() {
        executor.shutdown();
    }

    public static void main(String[] args) {
        User user = new User("Vyacheslav", "vkan745@gmail.com");
        EmailNotification notification = new EmailNotification();
        notification.executor.submit(new Runnable() {
            @Override
            public void run() {
                List<String> email = notification.emailTo(user);
                notification.send(email.get(0), email.get(1), user.getEmail());
            }
        });
        notification.close();
    }
}

