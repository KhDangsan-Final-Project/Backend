package com.ms2.event;

import org.springframework.context.ApplicationEvent;

public class roomUserEvent extends ApplicationEvent {
    private int roomId;

    public roomUserEvent(Object source, int roomId) {
        super(source);
        this.roomId = roomId;
    }

    public int getRoomId() {
        return roomId;
    }
}
