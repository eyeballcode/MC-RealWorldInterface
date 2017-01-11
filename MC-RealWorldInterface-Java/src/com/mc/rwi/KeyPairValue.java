package com.mc.rwi.event;

public class KeyPairValue {

    private String name, value;

    public KeyPairValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getName() + "=" + getValue();
    }
}
