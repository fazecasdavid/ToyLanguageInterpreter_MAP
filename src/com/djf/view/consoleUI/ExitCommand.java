package com.djf.view.consoleUI;

import com.djf.view.consoleUI.Command;

public class ExitCommand extends Command {

    public ExitCommand(String key, String description) {
        super(key, description);
    }

    @Override
    public void execute() {
        System.exit(0);
    }
}
