package com.mc.rwi.event;

public class ButtonPushEvent {

    int mode = 0;

    public ButtonPushEvent(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }
}
