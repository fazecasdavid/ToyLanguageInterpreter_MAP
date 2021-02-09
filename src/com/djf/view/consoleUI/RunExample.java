package com.djf.view.consoleUI;

import com.djf.controller.Controller;
import com.djf.view.consoleUI.Command;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RunExample extends Command {
    private final Controller controller;
    public RunExample(String key, String description, Controller controller) {
        super(key, description);
        this.controller = controller;
    }

    @Override
    public void execute() {
        try{
            controller.allSteps();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            try{
                var writer = new PrintWriter(new BufferedWriter(new FileWriter("errors.txt", true)));
                ex.printStackTrace(writer);
                writer.close();
            }
            catch (IOException e){
                System.out.println(e.getMessage());
            }

        }

    }
}
