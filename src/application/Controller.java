package application;

import application.Models.ChapinField;
import application.Models.SpenField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Controller {
    private String filePath = null;

    private ObservableList<SpenField> spenFields = FXCollections.observableArrayList();
    private ObservableList<ChapinField> chapinFields = FXCollections.observableArrayList();
    private ObservableList<ChapinField> chapinFieldsIO =  FXCollections.observableArrayList();
    private String text;
    @FXML
    Text filePathText;

    @FXML
    Text chapinValue;

    @FXML
    Text chapinValueIO;

    @FXML
    TableView<ChapinField> chapin;

    @FXML
    TableColumn<ChapinField, String> groupName;

    @FXML
    TableColumn<ChapinField, List<String>> groupVar;

    @FXML
    TableColumn<ChapinField, Integer> grouCount;

    @FXML
    TableView<ChapinField> chapinIO;

    @FXML
    TableColumn<ChapinField, String> groupNameIO;

    @FXML
    TableColumn<ChapinField, List<String>> groupVarIO;

    @FXML
    TableColumn<ChapinField, Integer> grouCountIO;

    @FXML
    TableView<SpenField> spen;

    @FXML
    TableColumn<SpenField, String> spenId;

    @FXML
    TableColumn<SpenField, Integer> spenValue;



    @FXML
    public void onParseClick() {
        if (filePath == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("File error");
            alert.setHeaderText(
                    "Chose file to analysis.");
            alert.showAndWait();
        }else{

        }
    }

    @FXML
    public void onOpenFileClick() throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(filePathText.getScene().getWindow());
        if (file != null){
            filePath = file.getPath();
            filePathText.setText(file.getName());
            text = new String(Files.readAllBytes(Paths.get(filePath)));
            setSpenTable();
            setChapinTable();
            setChapinTableIO();
        }
    }

    @FXML
    private void initialize(){
        groupName.setCellValueFactory(new PropertyValueFactory<>("name"));
        groupVar.setCellValueFactory(new PropertyValueFactory<>("variables"));
        grouCount.setCellValueFactory(new PropertyValueFactory<>("count"));
        chapin.setItems(chapinFields);

        groupNameIO.setCellValueFactory(new PropertyValueFactory<>("name"));
        groupVarIO.setCellValueFactory(new PropertyValueFactory<>("variables"));
        grouCountIO.setCellValueFactory(new PropertyValueFactory<>("count"));
        chapinIO.setItems(chapinFieldsIO);

        spenId.setCellValueFactory(new PropertyValueFactory<>("name"));
        spenValue.setCellValueFactory(new PropertyValueFactory<>("spen"));
        spen.setItems(spenFields);
    }

    private void setChapinTable(){
        ChapinAnalayzer analayzer = new ChapinAnalayzer(text);
        chapinFields.clear();
        chapinFields.setAll(analayzer.getChapinFields());
        chapinValue.setText(Double.toString(analayzer.getChapin()));
    }

    private void setChapinTableIO(){
        ChapinAnalayzer analayzer = new ChapinAnalayzer(text);
        chapinFieldsIO.clear();
        chapinFieldsIO.setAll(analayzer.getChapinFieldsIO());
        chapinValueIO.setText(Double.toString(analayzer.getChapinIO()));
    }

    private void setSpenTable(){
        SpenAnalyzer analyzer = new SpenAnalyzer(text);
        spenFields.clear();
        spenFields.addAll(analyzer.getSpenList());
    }

}
