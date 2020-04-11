package G13.pst.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import G13.pst.models.Point;
import G13.pst.models.Segment;
import G13.pst.models.Window;
import G13.pst.utils.File;
import G13.pst.utils.Parser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage menuStage = null;
    private Stage controlStage = null;
    private Stage previewStage = null;
    private Group previewGroup = null;
    private Window window = null;

    private Point queryStart = null;
    private Window queryWindow = null;
    private Rectangle queryRectangle = null;

    private Spinner spinnerQueryXS;
    private Spinner spinnerQueryYS;
    private Spinner spinnerQueryXE;
    private Spinner spinnerQueryYE;

    private ArrayList<Line> segments = new ArrayList<Line>();

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
        this.previewStage.setResizable(false);
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
                    this.window = Parser.createWindowFromString(line);
                    this.queryWindow = this.window;
                } else {
                    Segment s = Parser.createSegmentFromString(line);
                    Line l = new Line();
                    l.setStartX(s.getExt1().getX());
                    l.setStartY(s.getExt1().getY());
                    l.setEndX(s.getExt2().getX());
                    l.setEndY(s.getExt2().getY());
                    segments.add(l);
                }
            }
            this.previewGroup = new Group((Collection) segments);
            Rectangle r = new Rectangle();
            System.out.println(this.window);
            r.setX(this.window.getStart().getX());
            r.setY(this.window.getStart().getY());
            r.setWidth(this.window.getEnd().getX() - this.window.getStart().getX());
            r.setHeight(this.window.getEnd().getY() - this.window.getStart().getY());
            r.setFill(Color.TRANSPARENT);
            r.setStroke(Color.BLUE);
            this.previewGroup.getChildren().add(r);
            this.queryRectangle = new Rectangle();
            this.queryRectangle.setX(this.window.getStart().getX());
            this.queryRectangle.setY(this.window.getStart().getY());
            this.queryRectangle.setWidth(this.window.getEnd().getX() - this.window.getStart().getX());
            this.queryRectangle.setHeight(this.window.getEnd().getY() - this.window.getStart().getY());
            this.queryRectangle.setFill(null);
            this.queryRectangle.setStroke(Color.RED);
            this.previewGroup.getChildren().add(this.queryRectangle);
            ScrollPane sp = new ScrollPane();
            sp.setFitToHeight(true);
            sp.setFitToWidth(true);
            sp.setContent(this.previewGroup);
            Scene scene = new Scene(sp, 800, 800);
            scene.setOnKeyPressed( (event) -> {
                    switch (event.getCode()) {
                        case TAB: this.controlStage.show(); break;
                        case ESCAPE: this.menuStage.show(); this.previewStage.close(); this.controlStage.close(); break;
                    }
            });
            this.previewGroup.setOnMousePressed((value) -> {
                this.queryRectangle.setStroke(Color.TRANSPARENT);
                if(value.getX() >= this.previewGroup.getLayoutBounds().getMinX() &&
                        value.getY() >= this.previewGroup.getLayoutBounds().getMinY() &&
                        value.getX() <= this.previewGroup.getLayoutBounds().getMaxX() &&
                        value.getY() <= this.previewGroup.getLayoutBounds().getMaxY()) {
                    this.queryStart = new Point(value.getX(), value.getY());
                    System.out.println("START: " + this.queryStart);
                }

            });
            this.previewGroup.setOnMouseReleased((value) -> {
                if(this.queryStart != null) {
                    if(value.getX() >= this.previewGroup.getLayoutBounds().getMinX() &&
                            value.getY() >= this.previewGroup.getLayoutBounds().getMinY() &&
                            value.getX() <= this.previewGroup.getLayoutBounds().getMaxX() &&
                            value.getY() <= this.previewGroup.getLayoutBounds().getMaxY()) {
                        this.queryWindow = new Window(this.queryStart, new Point(value.getX(), value.getY()));
                        System.out.println("QUERY: " + this.queryWindow);
                        this.spinnerQueryXS.getValueFactory().setValue(this.queryWindow.getStart().getX());
                        this.spinnerQueryXE.getValueFactory().setValue(this.queryWindow.getEnd().getX());
                        this.spinnerQueryYS.getValueFactory().setValue(this.queryWindow.getStart().getY());
                        this.spinnerQueryYE.getValueFactory().setValue(this.queryWindow.getEnd().getY());
                    }
                    this.queryStart = null;
                }
            });
            this.previewGroup.setOnMouseDragged((value) -> {
                if(this.queryStart != null) {
                    if(this.queryStart.getY() < value.getY()) {
                        this.spinnerQueryYS.getValueFactory().setValue(this.queryStart.getY());
                        this.spinnerQueryYE.getValueFactory().setValue(value.getY());
                    } else {
                        this.spinnerQueryYS.getValueFactory().setValue(value.getY());
                        this.spinnerQueryYE.getValueFactory().setValue(this.queryStart.getY());
                    }
                    if(this.queryStart.getX() < value.getX()) {
                        this.spinnerQueryXS.getValueFactory().setValue(this.queryStart.getX());
                        this.spinnerQueryXE.getValueFactory().setValue(value.getX());
                    } else {
                        this.spinnerQueryXS.getValueFactory().setValue(value.getX());
                        this.spinnerQueryXE.getValueFactory().setValue(this.queryStart.getX());
                    }
                }
            });
            this.previewStage.setScene(scene);
            this.previewStage.setResizable(true);
    }

    private void buildControlStage() {
        this.controlStage = new Stage();
        this.controlStage.setResizable(false);
        this.controlStage.setTitle("Windowing controls");
        GridPane r = new GridPane();
        Button btn2 = new Button("Exit");
        btn2.setPrefWidth(150);
        btn2.setOnAction((value) -> {
            Platform.exit();
            System.out.println("Closing application");
            System.exit(0);
        });
        r.addColumn(0, btn2);
        Button btn = new Button("Switch resource file");
        btn.setPrefWidth(150);
        btn.setOnAction((value) -> {
            this.menuStage.show();
            this.controlStage.close();
            this.previewStage.close();
        });
        r.addColumn(0, btn);
        r.addColumn(0,new Label("Total segments: " + this.segments.size()));
        r.addColumn(0,new Label("Window x0:"));
        this.spinnerQueryXS = new Spinner(this.window.getStart().getX(), this.window.getEnd().getX(),this.window.getStart().getX());
        this.spinnerQueryXS.valueProperty().addListener((value) -> {
            this.drawWindowQuery(new Point((double) this.spinnerQueryXS.valueProperty().getValue(), (double) this.spinnerQueryYS.valueProperty().getValue()),
                    new Point((double) this.spinnerQueryXE.valueProperty().getValue(),(double) this.spinnerQueryYE.valueProperty().getValue()));
        });
        r.addColumn(0, this.spinnerQueryXS);
        r.addColumn(0,new Label("Window y0:"));
        this.spinnerQueryYS = new Spinner(this.window.getStart().getY(), this.window.getEnd().getY(),this.window.getStart().getY());
        this.spinnerQueryYS.valueProperty().addListener((value) -> {
            this.drawWindowQuery(new Point((double) this.spinnerQueryXS.valueProperty().getValue(), (double) this.spinnerQueryYS.valueProperty().getValue()),
                    new Point((double) this.spinnerQueryXE.valueProperty().getValue(),(double) this.spinnerQueryYE.valueProperty().getValue()));
        });
        r.addColumn(0, this.spinnerQueryYS);
        r.addColumn(0,new Label("Window x'0:"));
        this.spinnerQueryXE = new Spinner(this.window.getStart().getX(), this.window.getEnd().getX(), this.window.getEnd().getX());
        this.spinnerQueryXE.valueProperty().addListener((value) -> {
            this.drawWindowQuery(new Point((double) this.spinnerQueryXS.valueProperty().getValue(), (double) this.spinnerQueryYS.valueProperty().getValue()),
                    new Point((double) this.spinnerQueryXE.valueProperty().getValue(),(double) this.spinnerQueryYE.valueProperty().getValue()));
        });
        r.addColumn(0, this.spinnerQueryXE);
        r.addColumn(0,new Label("Window y'0:"));
        this.spinnerQueryYE = new Spinner(this.window.getStart().getY(), this.window.getEnd().getY(), this.window.getEnd().getY());
        this.spinnerQueryYE.valueProperty().addListener((value) -> {
            this.drawWindowQuery(new Point((double) this.spinnerQueryXS.valueProperty().getValue(), (double) this.spinnerQueryYS.valueProperty().getValue()),
                    new Point((double) this.spinnerQueryXE.valueProperty().getValue(),(double) this.spinnerQueryYE.valueProperty().getValue()));
        });
        r.addColumn(0, this.spinnerQueryYE);
        r.setAlignment(Pos.CENTER);
        r.setVgap(5);
        this.controlStage.setScene(new Scene(r, 300, 300));
    }

    private void drawWindowQuery(Point p1, Point p2) {
        if(p2.getY() < p1.getY()) {
            this.queryRectangle.setY(p2.getY());
            this.queryRectangle.setHeight(p1.getY() - p2.getY());
        } else {
            this.queryRectangle.setY(p1.getY());
            this.queryRectangle.setHeight(p2.getY() - p1.getY());
        }
        if(p2.getX() < p1.getX()) {
            this.queryRectangle.setX(p2.getX());
            this.queryRectangle.setWidth(p1.getX() - p2.getX());
        } else {
            this.queryRectangle.setX(p1.getX());
            this.queryRectangle.setWidth(p2.getX() - p1.getX());
        }
        this.queryRectangle.setFill(null);
        this.queryRectangle.setStroke(Color.RED);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
