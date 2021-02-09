package com.djf.view.consoleUI;

public abstract class Command {
    private final String key;
    private final String description;


    protected Command(String key, String description) {
        this.key = key;
        this.description = description;
    }
    public abstract void execute();

    public String getDescription() {
        return description;
    }

    public String getKey() {
        return key;
    }

}
