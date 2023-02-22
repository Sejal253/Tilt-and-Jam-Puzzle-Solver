package puzzles.jam.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.jam.model.JamModel;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * This class drives the entire functionality of the GUI part of the puzzle.
 */

public class JamGUI extends Application  implements Observer<JamModel, String>  {
    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    // for demonstration purposes
    private final static String X_CAR_COLOR = "#DF0101";
    private final static String A_CAR_COLOR = "#398984";
    private final static String B_CAR_COLOR = "#DA6121";
    private final static String C_CAR_COLOR = "#AA7201";
    private final static String D_CAR_COLOR = "#F23359";
    private final static String E_CAR_COLOR = "#BE6093";
    private final static String F_CAR_COLOR = "#67A90E";
    private final static String G_CAR_COLOR = "#934023";
    private final static String H_CAR_COLOR = "#BEAC29";
    private final static String I_CAR_COLOR = "#389748";
    private final static String J_CAR_COLOR = "#203894";
    private final static String K_CAR_COLOR = "#ABCD83";
    private final static String L_CAR_COLOR = "#D3AF39";
    private final static String O_CAR_COLOR = "#937942";
    private final static String P_CAR_COLOR = "#203084";
    private final static String Q_CAR_COLOR = "#ABDCCC";
    private final static String R_CAR_COLOR = "#AAAAAA";
    private final static String S_CAR_COLOR = "#298938";

    private final static int BUTTON_FONT_SIZE = 20;
    private final static int ICON_SIZE = 75;

    private String filename;
    private JamModel model;

    private Label msg;
    private int numRows;
    private int numCols;
    private GridPane board;

    private int currentRow;
    private int currentCol;
    private int destRows;
    private int destCols;

    /**
     * This function is used to initialize the GUI for the puzzle
     * @throws FileNotFoundException
     */
    public void init() throws FileNotFoundException {
        String filename = getParameters().getRaw().get(0);
        this.filename = filename;
        this.model = new JamModel();
        this.msg = new Label("");
        this.msg.setAlignment(Pos.CENTER);
        this.model.addObserver(this);
        this.model.addConfig(filename);
        this.numRows = this.model.getBoard().getNumRows();
        this.numCols = this.model.getBoard().getNumCols();

        this.msg.setMinSize(75*this.numCols, 50);
        this.msg.setMaxSize(75*this.numCols, 50);

        this.currentRow = -1;
        this.currentCol = -1;
    }

    /**
     * This function sets up the board and the entire layout of the GUI
     * @param stage the main stage which is shown on the window
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        VBox mainArea = new VBox();

        mainArea.getChildren().add(this.msg);

        board = new GridPane();
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numCols; j++) {
                Button button = new Button(model.getBoard().getCurrBoard()[i][j].equals(".") ? " " : model.getBoard().getCurrBoard()[i][j]);
                button.setMaxSize(75, 75);
                button.setMinSize(75, 75);
                board.add(button, j, i);
                String letter = model.getBoard().getCurrBoard()[i][j];
                if(!letter.equals(".")){
                    int buttonRow = i;
                    int buttonCol = j;
                    button.setOnAction((event) -> {
                        this.currentRow = buttonRow;
                        this.currentCol = buttonCol;
                    });
                    if(letter.equals("A")){
                        button.setStyle(
                            "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                            "-fx-background-color: " + A_CAR_COLOR + ";" +
                            "-fx-font-weight: bold;");
                    }
                    if(letter.equals("B")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + B_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("C")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + C_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("D")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + D_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("E")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + E_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("F")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + F_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("G")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + G_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("H")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + H_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("I")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + I_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("J")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + J_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("K")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + K_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("L")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + L_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("O")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + O_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("P")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + P_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("Q")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + Q_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("R")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + R_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("S")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + S_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("X")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + X_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                } else {
                    int destRow = i;
                    int destCol = j;
                    button.setOnAction((event) -> {
                        if(this.currentRow != -1 && this.currentCol != -1) {
                            this.model.moveCar(model.getBoard().getCurrBoard()[this.currentRow][this.currentCol], destRow, destCol);
                            this.msg.setText("Car " + this.model.getBoard().getCurrBoard()[this.currentRow][this.currentCol] + " moved!");
                            this.currentRow = -1;
                            this.currentCol = -1;
                        }
                        this.updateBoard();
                    });
                }
            }
        }

        mainArea.getChildren().add(board);

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        Button load = new Button("Load");
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
            this.numRows = model.getBoard().getNumRows();
            this.numCols = model.getBoard().getNumCols();
            updateBoard();
        });
        buttons.getChildren().add(load);
        Button reset = new Button("Reset");
        reset.setOnAction((event) -> {
            try {
                this.model.resetBoard(this.filename);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            this.updateBoard();
        });
        buttons.getChildren().add(reset);
        Button hint = new Button("Hint");
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
        buttons.getChildren().add(hint);
        mainArea.getChildren().add(buttons);
        Scene scene = new Scene(mainArea);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This function is used to update the model and the messgae on the GUI
     * @param jamModel the object that wishes to inform this object
     *                about something that has happened.
     * @param message optional data the server.model can send to the observer
     *
     */
    @Override
    public void update(JamModel jamModel, String message) {
        this.msg.setText(message);
    }

    /**
     * This function is used to update the board layout based on the backend board.
     */
    public void updateBoard() {
        this.board.getChildren().clear();

        if(this.model.getBoard().isSolution()) {
            this.msg.setText("Game Board Solved!");
        }

        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numCols; j++) {
                Button button = new Button(model.getBoard().getCurrBoard()[i][j].equals(".") ? " " : model.getBoard().getCurrBoard()[i][j]);
                button.setMaxSize(75, 75);
                button.setMinSize(75, 75);
                board.add(button, j, i);
                String letter = model.getBoard().getCurrBoard()[i][j];
                if(!letter.equals(".")){
                    int buttonRow = i;
                    int buttonCol = j;
                    button.setOnAction((event) -> {
                        this.currentRow = buttonRow;
                        this.currentCol = buttonCol;
                    });
                    if(letter.equals("A")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + A_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("B")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + B_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("C")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + C_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("D")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + D_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("E")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + E_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("F")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + F_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("G")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + G_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("H")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + H_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("I")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + I_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("J")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + J_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("K")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + K_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("L")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + L_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("O")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + O_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("P")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + P_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("Q")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + Q_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("R")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + R_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("S")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + S_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                    if(letter.equals("X")){
                        button.setStyle(
                                "-fx-font-size: " + BUTTON_FONT_SIZE + ";" +
                                        "-fx-background-color: " + X_CAR_COLOR + ";" +
                                        "-fx-font-weight: bold;");
                    }
                } else {
                    int destRow = i;
                    int destCol = j;
                    button.setOnAction((event) -> {
                        if(this.currentRow != -1 && this.currentCol != -1) {
                            this.model.moveCar(model.getBoard().getCurrBoard()[this.currentRow][this.currentCol], destRow, destCol);
                            this.msg.setText("Car " + this.model.getBoard().getCurrBoard()[this.currentRow][this.currentCol] + " moved!");
                            this.currentRow = -1;
                            this.currentCol = -1;
                        }
                        this.updateBoard();
                    });
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java JamGUI filename");
        } else {
            Application.launch(args);
        }
    }
}
