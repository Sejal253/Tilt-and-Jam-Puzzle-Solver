package puzzles.tilt.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.stage.FileChooser;
import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.tilt.model.TiltConfig;
import puzzles.tilt.model.TiltModel;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import puzzles.tilt.solver.Tilt;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * GUI Class for the Tilt Puzzle
 */
public class TiltGUI extends Application implements Observer<TiltModel, String> {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    private Label msg;
    private GridPane gridPane;
    private TiltModel model;
    private String filename;
    private int boardSize;
    private Button tiltN;
    private Button tiltS;
    private Button tiltE;
    private Button tiltW;

    // for demonstration purposes
    private Image greenDisk = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"green.png"));
    private Image blueDisk = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"blue.png"));
    private Image hole = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"hole.png"));
    private Image block = new Image(getClass().getResourceAsStream(RESOURCES_DIR+"block.png"));

    /**
     * This function initializes the game board
     * @throws FileNotFoundException
     */
    public void init() throws FileNotFoundException {
        String filename = getParameters().getRaw().get(0);
        this.filename = filename;
        this.model = new TiltModel();
        this.msg = new Label("");
        this.msg.setAlignment(Pos.CENTER);
        this.model.addObserver(this);
        this.model.addConfig(filename);
        this.boardSize = this.model.getBoard().getBoardSize();
        this.msg.setMinSize(124*boardSize+80, 50);
        this.msg.setMaxSize(124*boardSize+80, 50);
        tiltN = new Button("^");
        tiltS = new Button("v");
        tiltE = new Button(">");
        tiltW = new Button("<");
    }

    /**
     * This function is used to set up the game board
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        BorderPane pane = new BorderPane();


        pane.setTop(this.msg);

        BorderPane board = new BorderPane();

        tiltN.setMaxSize(124*boardSize+80, 40);
        tiltN.setMinSize(124*boardSize+80, 40);
        tiltN.setOnAction((event) -> {
            if(this.model.getBoard().isSolution()) {
                this.msg.setText("Board Already Solved!");
            } else {
                this.model.tiltNorth();
                this.updateBoard();
            }
        });

        tiltS.setMaxSize(124*boardSize+80, 40);
        tiltS.setMinSize(124*boardSize+80, 40);
        tiltS.setOnAction((event) -> {
            if(this.model.getBoard().isSolution()) {
                this.msg.setText("Board Already Solved!");
            } else {
                this.model.tiltSouth();
                this.updateBoard();
            }
        });

        tiltE.setMaxSize(40, 124*boardSize);
        tiltE.setMinSize(40, 124*boardSize);
        tiltE.setOnAction((event) -> {
            if(this.model.getBoard().isSolution()) {
                this.msg.setText("Board Already Solved!");
            } else {
                this.model.tiltEast();
                this.updateBoard();
            }
        });

        tiltW.setMaxSize(40, 124*boardSize);
        tiltW.setMinSize(40, 124*boardSize);
        tiltW.setOnAction((event) -> {
            if(this.model.getBoard().isSolution()) {
                this.msg.setText("Board Already Solved!");
            } else {
                this.model.tiltWest();
                this.updateBoard();
            }
        });

        board.setTop(tiltN);
        board.setBottom(tiltS);
        board.setLeft(tiltW);
        board.setRight(tiltE);

        this.gridPane = new GridPane();
        this.gridPane.setAlignment(Pos.CENTER);
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                String tile = this.model.getBoard().getCurrentBoard()[i][j];
                Button button = new Button();
                if(tile.equals("B")) {
                    button.setGraphic(new ImageView(blueDisk));
                } else if (tile.equals("G")){
                    button.setGraphic((new ImageView(greenDisk)));
                } else if (tile.equals("*")) {
                    button.setGraphic(new ImageView(block));
                } else if (tile.equals("O")) {
                    button.setGraphic(new ImageView(hole));
                }
                button.setMinHeight(124);
                button.setMinWidth(124);
                button.setMaxSize(124, 124);
                this.gridPane.add(button, j, i);
            }
        }

        board.setCenter(this.gridPane);
        pane.setCenter(board);

        Button hint = new Button("Hint");
        hint.setMinSize(50, 25);
        hint.setMaxSize(50, 25);
        hint.setOnAction((event) -> {
            if(this.model.getBoard().isSolution()) {
                this.msg.setText("Board Already Solved!");
            } else {
                Solver solver = new Solver();
                List<Configuration> path = solver.bfs(this.model.getBoard());
                if(path == null) {
                    this.msg.setText("No Solution Exists!");
                }
                this.model.setConfig(path.get(1));
                this.msg.setText("Hint Used!");
                this.updateBoard();
            }
        });
        Button load = new Button("Load");
        load.setMinSize(50, 25);
        load.setMaxSize(50, 25);
        load.setOnAction((event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text File", "*.txt"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            try {
                model.addConfig(selectedFile.getPath());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            this.boardSize = model.getBoard().getBoardSize();
            updateBoard();
        });
        Button reset = new Button("Reset");
        reset.setMinSize(50, 25);
        reset.setMaxSize(50, 25);
        reset.setOnAction((event) -> {
            try {
                this.model.resetBoard(this.filename);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            this.updateBoard();
        });

        VBox buttons = new VBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10, 20, 10, 20));
        buttons.setSpacing(20);
        buttons.getChildren().add(load);
        buttons.getChildren().add(reset);
        buttons.getChildren().add(hint);

        pane.setRight(buttons);
        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The update function updates the message on the GUI
     * @param tiltModel the object that wishes to inform this object
     *                about something that has happened.
     * @param message optional data the server.model can send to the observer
     *
     */
    @Override
    public void update(TiltModel tiltModel, String message) {
        this.msg.setText(message);
    }

    /**
     * This function is used to update the GUI of the board. This updates the configuration of the
     * GUI board.
     */
    public void updateBoard() {
        this.gridPane.getChildren().clear();

        if(this.model.getBoard().isSolution()) {
            this.msg.setText("Game Board Solved!");
        }

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                String tile = this.model.getBoard().getCurrentBoard()[i][j];
                Button button = new Button();
                if(tile.equals("B")) {
                    button.setGraphic(new ImageView(blueDisk));
                } else if (tile.equals("G")){
                    button.setGraphic((new ImageView(greenDisk)));
                } else if (tile.equals("*")) {
                    button.setGraphic(new ImageView(block));
                } else if (tile.equals("O")) {
                    button.setGraphic(new ImageView(hole));
                }
                button.setMinHeight(124);
                button.setMinWidth(124);
                button.setMaxSize(124, 124);
                this.gridPane.add(button, j, i);
            }
        }
    }

    /**
     * This is the main function that drives the GUI program
     * @param args
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java TiltGUI filename");
        } else {
            Application.launch(args);
        }
    }
}
