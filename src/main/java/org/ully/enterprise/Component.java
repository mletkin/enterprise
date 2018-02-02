package org.ully.enterprise;

public class Component {

    private String name;
    private boolean online = true;

    Component(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void toggle() {
        online = !online;
    }

    public boolean isOnline() {
        return online;
    }
}
