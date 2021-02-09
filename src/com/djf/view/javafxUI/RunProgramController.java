package com.djf.view.javafxUI;

import com.djf.controller.Controller;
import com.djf.model.ProgramState;
import com.djf.model.value.StringValue;
import com.djf.model.value.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RunProgramController {

    private Controller controller;

    public void setController(Controller controller) {
        this.controller = controller;
    }

    @FXML
    TextField noProgramStates_TextField;
    @FXML
    TableView<Map.Entry<Integer, String>> heapTable_TableView;
    @FXML
    TableColumn<Map.Entry<Integer, String>, Integer> heapTableAddress_TableColumn;
    @FXML
    TableColumn<Map.Entry<Integer, String>, String> heapTableValue_TableColumn;
    @FXML
    ListView<String> outList_ListView;
    @FXML
    ListView<String> fileTable_ListView;
    @FXML
    ListView<String> programStateList_ListView;
    @FXML
    TableView<Map.Entry<String, String>> symbolTable_TableView;
    @FXML
    TableColumn<Map.Entry<String, String>, String> symbolTableVariableName_TableColumn;
    @FXML
    TableColumn<Map.Entry<String, String>, String> symbolTableValue_TableColumn;
    @FXML
    ListView<String> exeStack_ListView;

    // new Table
    @FXML
    TableView<Triplet<Integer, String, String>> someTable_TableView;
    @FXML
    TableColumn<Triplet<Integer, String, String>, Integer> someTableC1_TableColumn;
    @FXML
    TableColumn<Triplet<Integer, String, String>, String> someTableC2_TableColumn;
    @FXML
    TableColumn<Triplet<Integer, String, String>, String> someTableC3_TableColumn;



    public void init() {
        programStateList_ListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        heapTableAddress_TableColumn.setCellValueFactory(entry -> new SimpleIntegerProperty(entry.getValue().getKey()).asObject());
        heapTableValue_TableColumn.setCellValueFactory(entry -> new SimpleStringProperty(entry.getValue().getValue()));

        symbolTableVariableName_TableColumn.setCellValueFactory(entry -> new SimpleStringProperty(entry.getValue().getKey()));
        symbolTableValue_TableColumn.setCellValueFactory(entry -> new SimpleStringProperty(entry.getValue().getValue()));

        someTableC1_TableColumn.setCellValueFactory(entry -> new SimpleIntegerProperty(entry.getValue().getFirst()).asObject());
        someTableC2_TableColumn.setCellValueFactory(entry -> new SimpleStringProperty(entry.getValue().getSecond()));
        someTableC3_TableColumn.setCellValueFactory(entry -> new SimpleStringProperty(entry.getValue().getThird()));

        try {
            updateAllViews(controller.getAllProgramStates().get(0));
        } catch (Exception ex) {
            displayMessage("ERROR", ex.getMessage());
//            ex.printStackTrace();
        }

    }

    public void runOneStepForAllPrograms_Handler() {
        if (controller.getAllProgramStates().isEmpty()) {
            displayMessage("DONE", "Nothing more to execute");
            return;
        }
        ProgramState prg = controller.getAllProgramStates().get(0);
        controller.oneStepForAllConcurrent();
        updateAllViews(prg);
    }

    public void runAllSteps() {
        ProgramState last = null;
        while (!controller.getAllProgramStates().isEmpty()) {
            last = controller.getAllProgramStates().get(0);
            controller.oneStepForAllConcurrent();
        }
        if (last == null) {
            displayMessage("DONE", "Nothing more to execute");
            return;
        }
        updateAllViews(last);
    }

    private void updateOutputListView(ProgramState prg) {
        var outList = prg.getOutList().getContent();
        var outObservable = outList.stream()
                .map(Value::toString)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        outList_ListView.setItems(outObservable);
    }

    private void updateFileTableListView(ProgramState prg) {
        var filesTable = prg.getFileTable().getContent();
        var filesTableObservable = filesTable.keySet().stream()
                .map(StringValue::toString)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        fileTable_ListView.setItems(filesTableObservable);
    }

    private void updateHeapTableView(ProgramState prg) {
        var heapTable = prg.getHeapTable().getContent();
        // transform from Entry<Int, Value> -> Entry<Int, String>
        var heapTableObservable = heapTable.entrySet().stream()
                .map((Map.Entry<Integer, Value> el) -> (Map.Entry<Integer, String>) new AbstractMap.SimpleEntry<>(el.getKey(), el.getValue().toString()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        heapTable_TableView.setItems(heapTableObservable);
    }

    private void updateSymbolTable(ProgramState programState) {
        var symbolTable = programState.getSymbolTable().getContent();
        var symbolTableObservable = symbolTable.entrySet().stream()
                .map((Map.Entry<String, Value> el) -> (Map.Entry<String, String>) new AbstractMap.SimpleEntry<>(el.getKey(), el.getValue().toString()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        symbolTable_TableView.setItems(symbolTableObservable);
    }

    private void updateExeStackListView(ProgramState programState) {
        var exeStack = programState.getExecutionStack().getContent();
        var exeStackObservable = exeStack.stream()
                .map(Object::toString)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        exeStack_ListView.setItems(exeStackObservable);
    }

    private void updateProgramStates() {
        var programStates = controller.getProgramStates();
        var programStatesObservable = programStates.stream()
                .map(el -> "[id:" + el.getId() + "]   " + el.getOriginalProgram())
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        programStateList_ListView.setItems(programStatesObservable);
    }

    private void updateNoProgramStatesTextField() {
        noProgramStates_TextField.setText(Integer.toString(controller.getProgramStates().size()));
    }

    private void updateSomeTable(ProgramState prg) {
        var barrierTable = prg.getBarrierTable().getContent();
//         transform from Entry<Int, Pair<Integer, ArrayList>> -> Triplet<Int, String, String>
        var barrierTableObservable = barrierTable.entrySet().stream()
                .map((Map.Entry<Integer, Pair<Integer, List<Integer>>> el) -> new Triplet<>(el.getKey(), el.getValue().getKey().toString(), el.getValue().getValue().toString()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        someTable_TableView.setItems(barrierTableObservable);

    }


    private void updateAllViews(ProgramState prg) {
        programStateList_ListView.getSelectionModel().selectIndices(0);

        try {
            updateExeStackListView(prg);
            updateSymbolTable(prg);
            updateFileTableListView(prg);
            updateHeapTableView(prg);
            updateOutputListView(prg);

            updateSomeTable(prg);

            updateProgramStates();
            updateNoProgramStatesTextField();

        } catch (Exception ex) {
            displayMessage("Error", ex.getMessage());
//            ex.printStackTrace();
        }
    }

    public void changeProgramStateHandler() {
        List<ProgramState> states = controller.getProgramStates();
        int index = programStateList_ListView.getSelectionModel().getSelectedIndex();
        ProgramState program = states.get(index);
        try {
            updateExeStackListView(program);
            updateSymbolTable(program);
        } catch (Exception e) {
            displayMessage("Error Switching", "Something went wrong while switching programState");
//            e.printStackTrace();
        }
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
