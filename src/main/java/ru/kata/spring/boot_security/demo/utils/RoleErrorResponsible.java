package ru.kata.spring.boot_security.demo.utils;

public class RoleErrorResponsible {
    private String message;
    private Long timestamp;

    public RoleErrorResponsible(String message, Long timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}

