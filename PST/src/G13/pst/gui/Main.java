package G13.pst.gui;

import java.util.ArrayList;
import java.util.Collection;

import G13.pst.models.Segment;
import G13.pst.utils.File;
import G13.pst.utils.SegmentParser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage = null;
    private Stage secondaryStage = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Windowing preview");
        this.createSceneFromFile(".\\samples\\1000.txt");
        this.primaryStage.show();
    }

    private void createSceneFromFile(String filename) {
        try {
            String[] lines = File.getLines(filename);
            System.out.println("Total lines in file: " + lines.length);
            ArrayList<Line> linesList = new ArrayList<Line>();
            boolean first = true;
            Segment window = null;
            for(String line : lines) {
                if(first) {
                    first = false;
                    window = SegmentParser.createFromString(line);
                    System.out.println("Window : " + window.toString());
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
            this.createLineScene(linesList, window);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void createLineScene(Collection lines, Segment window) {
        Group root = new Group(lines);
        this.primaryStage.setScene(new Scene(root, Math.abs(window.getExt2().getX()), Math.abs(window.getExt2().getY())));
    }

    private void createCommandStage(Parent root) {
        this.secondaryStage = new Stage();
        this.secondaryStage.setScene(new Scene(root, 300, 300));
        this.secondaryStage.setTitle("Windowing controls");
        this.secondaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
