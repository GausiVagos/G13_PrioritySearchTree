package G13.pst.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import G13.pst.models.Segment;
import G13.pst.utils.File;
import G13.pst.utils.SegmentParser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage menuStage = null;
    private Stage controlStage = null;
    private Stage previewStage = null;
    private Segment window = null;
    ArrayList<Line> segments = new ArrayList<Line>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        // save menu window
        this.menuStage = primaryStage;
        this.buildMenuStage();
        this.menuStage.show();
        // instantiate preview window and hide it
        this.buildPreviewStage();
        this.previewStage.hide();
    }

    private void buildPreviewStage() {
        this.previewStage = new Stage();
        this.previewStage.setTitle("Windowing preview");
        this.previewStage.setOnCloseRequest((value) -> {
            this.menuStage.show();
            this.controlStage.close();
        });
    }

    private void buildMenuStage() {
        this.menuStage.setTitle("Windowing menu");
        this.menuStage.setOnCloseRequest((value) -> {
            Platform.exit();
            System.out.println("Closing application");
            System.exit(0);
        });
        GridPane r = new GridPane();
        r.addColumn(0,new Label("Select a file:"));
        Button btn = new Button("Choose a resource file");
        btn.setOnAction((value) -> {
            this.openFileExplorer();
        });
        r.addColumn(0, btn);
        r.setAlignment(Pos.CENTER);
        r.setVgap(5);
        this.menuStage.setScene(new Scene(r, 200, 200));
        this.menuStage.setResizable(false);
    }

    private void openFileExplorer() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open a text resource file");
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
            this.buildControlStage();
            this.controlStage.show();
            this.menuStage.hide();
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    private void createSceneFromFile(String filename) throws IOException {
            String[] lines = File.getLines(filename);
            this.segments.clear();
            boolean first = true;
            for(String line : lines) {
                if(first) {
                    first = false;
                    this.window = SegmentParser.createFromString(line);
                } else {
                    Segment s = SegmentParser.createFromString(line);
                    Line l = new Line();
                    l.setStartX(s.getExt1().getX());
                    l.setStartY(s.getExt1().getY());
                    l.setEndX(s.getExt2().getX());
                    l.setEndY(s.getExt2().getY());
                    segments.add(l);
                }
            }
            Group root = new Group((Collection) segments);
            Scene scene = new Scene(root, Math.abs(this.window.getExt2().getX()), 800);
            scene.setOnKeyPressed( (event) -> {
                    switch (event.getCode()) {
                        case SPACE: this.controlStage.show(); break;
                        case ESCAPE: this.menuStage.show(); this.previewStage.close(); this.controlStage.close(); break;
                    }
            });
            this.previewStage.setScene(scene);
    }

    private void buildControlStage() {
        this.controlStage = new Stage();
        this.controlStage.setResizable(false);
        this.controlStage.setTitle("Windowing controls");
        GridPane r = new GridPane();
        r.addColumn(0,new Label("Total segments: " + this.segments.size()));
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
        Button btn = new Button("Switch resource file");
        btn.setPrefWidth(150);
        btn.setOnAction((value) -> {
            this.menuStage.show();
            this.controlStage.close();
            this.previewStage.close();
        });
        r.addColumn(0, btn);
        r.setAlignment(Pos.CENTER);
        r.setVgap(5);
        this.controlStage.setScene(new Scene(r, 300, 300));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
