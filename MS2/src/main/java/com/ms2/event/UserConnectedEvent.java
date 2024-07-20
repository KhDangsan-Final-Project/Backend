package com.ms2.event;

import org.springframework.context.ApplicationEvent;

public class UserConnectedEvent extends ApplicationEvent {
    private String id;

    public UserConnectedEvent(Object source, String id) {
        super(source);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
