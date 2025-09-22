package ru.job4j.emailnotification;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class User {
    String name;
    String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

public class Emailnotification {
    ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        pool.submit(() -> {
            String subject = "Notification %s to Email %s";
            String body = String.format(subject, user.getName(), user.getEmail());
        send(subject, body, user.getEmail());
        });
    }

    public void send(String subject, String body, String email) {
        System.out.println("Sending email to " + email);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        System.out.println("Письмо успешно отправлено !");
    }

    public void close() {
        pool.shutdown();
    }
}