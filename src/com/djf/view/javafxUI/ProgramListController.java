package com.djf.view.javafxUI;


import com.djf.controller.Controller;
import com.djf.model.ProgramState;
import com.djf.model.adt.MyDictionary;
import com.djf.model.statement.*;

import com.djf.repository.IRepository;
import com.djf.repository.Repository;
import com.djf.view.UtilProgramStatements;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ProgramListController {

    private final ObservableList<IStatement> programStatements;

    // constructor is called before FXML members load
    public ProgramListController() {
        this.programStatements = FXCollections.observableArrayList(UtilProgramStatements.getProgramStatementsList());
    }

    @FXML
    ListView<IStatement> programsListView;

    // called after FXML members load
    @FXML
    public void initialize() {
        programsListView.setItems(programStatements);
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    public void runButtonClickedHandler() {
        int selectedIndex = programsListView.getSelectionModel().getSelectedIndex();
        if(selectedIndex < 0 || selectedIndex >= programStatements.size()) {
            displayMessage("ERROR", "Please select a Program to run");
            return;
        }

        IStatement selectedStatement = programStatements.get(selectedIndex);

        try {
            selectedStatement.typeCheck(new MyDictionary<>());
        } catch (Exception exception) {
            displayMessage("TypeChecking ERROR", exception.getMessage());
            exception.printStackTrace();
            return;
        }

        ProgramState prg = new ProgramState(selectedStatement);
        IRepository repo = new Repository(prg, "" + selectedIndex + ".log");
        Controller ctr = new Controller(repo);

        try{
            changeSceneToRun(ctr);
        }
        catch (Exception ex) {
            displayMessage("Something Went Wrong", ex.getMessage());
            ex.printStackTrace();
        }

    }
    private void changeSceneToRun(Controller ctr) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("runProgram.fxml"));
        Parent root = loader.load();
//        RunProgramController runProgramController = new RunProgramController(ctr);
//        loader.setController(runProgramController);
        RunProgramController runProgramController = loader.getController();
        runProgramController.setController(ctr);
        runProgramController.init();


        Scene scene = new Scene(root);
        Stage window = new Stage();
        window.setTitle("Running a program");
        window.setScene(scene);
//        window.setOnCloseRequest( e -> {updateTableView();} );

        window.show();
    }

    public static void displayMessage(String title, String message) {
        Stage window = new Stage();

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(150);

        Label label = new Label();
        label.setText(message);

        VBox layout = new VBox(40);
        layout.getChildren().add(label);
        layout.setAlignment(Pos.CENTER);

        //Display window and wait for it to be closed before returning
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}