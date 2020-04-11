package G13.pst.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import G13.pst.models.Segment;
import G13.pst.utils.File;
import G13.pst.utils.SegmentParser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage menuStage = null;
    private Stage controlStage = null;
    private Stage previewStage = null;
    private Segment window = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        // save menu window
        this.menuStage = primaryStage;
        this.menuStage.setTitle("Windowing menu");
        this.showMenu();
        // instantiate preview window and hide it
        this.previewStage = new Stage();
        this.previewStage.setTitle("Windowing preview");
        this.previewStage.hide();
    }

    private void showMenu() {
        GridPane r = new GridPane();
        r.addColumn(0,new Label("Select a file:"));
        Button btn = new Button("Select a valid txt file");
        btn.setOnAction((value) -> {
            this.openFileExplorer();
        });
        r.addColumn(0, btn);
        r.setAlignment(Pos.CENTER);
        r.setVgap(5);
        this.menuStage.setScene(new Scene(r, 200, 200));
        this.menuStage.setResizable(false);
        this.menuStage.show();
    }

    private void openFileExplorer() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        java.io.File selectedFile = fileChooser.showOpenDialog(this.menuStage);
        if (selectedFile != null) {
            System.out.println(selectedFile.getAbsolutePath());
            this.showPreview(selectedFile.getAbsolutePath());
        }
    }

    private void showPreview(String filename) {
        try {
            this.createSceneFromFile(filename);
            this.previewStage.show();
            this.createCommandStage();
            this.menuStage.hide();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void createSceneFromFile(String filename) throws IOException {
            String[] lines = File.getLines(filename);
            System.out.println("Total lines in file: " + lines.length);
            ArrayList<Line> linesList = new ArrayList<Line>();
            boolean first = true;
            for(String line : lines) {
                if(first) {
                    first = false;
                    this.window = SegmentParser.createFromString(line);
                    System.out.println("Window : " + this.window.toString());
                } else {
                    Segment s = SegmentParser.createFromString(line);
                    Line l = new Line();
                    l.setStartX(s.getExt1().getX());
                    l.setStartY(s.getExt1().getY());
                    l.setEndX(s.getExt2().getX());
                    l.setEndY(s.getExt2().getY());
                    linesList.add(l);
                }
            }
            System.out.println("Total segments: " + linesList.size());
            Group root = new Group((Collection) linesList);
            this.previewStage.setScene(new Scene(root, Math.abs(this.window.getExt2().getX()), 800));
    }

    private void createCommandStage() {
        this.controlStage = new Stage();
        GridPane r = new GridPane();
        r.addColumn(0,new Label("Window x1:"));
        Spinner extX1 = new Spinner(-Math.abs(this.window.getExt1().getX()), Math.abs(this.window.getExt2().getX()),this.window.getExt1().getX());
        r.addColumn(0, extX1);
        r.addColumn(0,new Label("Window y1:"));
        Spinner extY1 = new Spinner(-Math.abs(this.window.getExt1().getY()), Math.abs(this.window.getExt2().getY()),this.window.getExt1().getY());
        r.addColumn(0, extY1);
        r.addColumn(0,new Label("Window x2:"));
        Spinner extX2 = new Spinner(-Math.abs(this.window.getExt1().getX()), Math.abs(this.window.getExt2().getX()), this.window.getExt2().getX());
        r.addColumn(0, extX2);
        r.addColumn(0,new Label("Window y2:"));
        Spinner extY2 = new Spinner(-Math.abs(this.window.getExt1().getY()), Math.abs(this.window.getExt2().getY()), this.window.getExt2().getY());
        r.addColumn(0, extY2);
        r.setAlignment(Pos.CENTER);
        r.setVgap(5);
        this.controlStage.setScene(new Scene(r, 300, 250));
        this.controlStage.setResizable(false);
        this.controlStage.setTitle("Windowing controls");
        this.controlStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
