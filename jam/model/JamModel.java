package puzzles.jam.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.jam.ptui.JamPTUI;
import puzzles.tilt.model.TiltConfig;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class JamModel {
    /** the collection of observers of this model */
    private final List<Observer<JamModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private JamConfig currentConfig;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<JamModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }

    /**
     * This function is used to create a new config using a filename
     * @param filename the name of the file
     * @throws FileNotFoundException
     */
    public void addConfig(String filename) throws FileNotFoundException {
        this.currentConfig = new JamConfig(filename);
        this.alertObservers("File: " + filename + " loaded!");
    }

    /**
     * this function is used to get the conig
     * @return the current config
     */
    public JamConfig getBoard() {
        return this.currentConfig;
    }

    /**
     * This function is used to copy a config to the current config
     * @param config
     */
    public void setConfig(Configuration config) {
        if(config instanceof JamConfig) {
            JamConfig jc = (JamConfig) config;
            this.currentConfig = jc;
        }
    }

    /**
     * this function resets the game board
     * @param filename: the filename to reset the board to
     * @throws FileNotFoundException
     */
    public void resetBoard(String filename) throws FileNotFoundException {
        this.currentConfig = new JamConfig(filename);
        this.alertObservers("Game Board Reset");
    }

    /**
     * This function is used to take the user input for moving a car by one block
     * @param car the car to be moved
     * @param destRow the destination row to move the car to
     * @param destCol the destination col to move the car to
     */
    public void moveCar(String car, int destRow, int destCol) {
        HashMap<String, Integer[][]> carPos = this.currentConfig.getCarPos();
        String[][] currBoard = this.currentConfig.getCurrBoard();

        Integer[][] val = carPos.get(car);
        int startRow = val[0][0];
        int startCol = val[0][1];
        int endRow = val[1][0];
        int endCol = val[1][1];

        if(startRow == endRow && startRow == destRow) {
            if(destCol == endCol + 1) {
                Integer[][] newVal = {{startRow, startCol + 1}, {endRow, endCol + 1}};
                carPos.put(car, newVal);
                this.currentConfig.getCurrBoard()[endRow][endCol+1] = car;
                this.currentConfig.getCurrBoard()[startRow][startCol] = ".";
            } else if(destCol == startCol - 1) {
                Integer[][] newVal = {{startRow, startCol - 1}, {endRow, endCol - 1}};
                carPos.put(car, newVal);
                this.currentConfig.getCurrBoard()[startRow][startCol-1] = car;
                this.currentConfig.getCurrBoard()[endRow][endCol] = ".";
            }
        } else if (startCol == endCol && startCol == destCol) {
            if(destRow == endRow + 1) {
                Integer[][] newVal = {{startRow + 1, startCol}, {endRow + 1, endCol}};
                carPos.put(car, newVal);
                this.currentConfig.getCurrBoard()[endRow + 1][endCol] = car;
                this.currentConfig.getCurrBoard()[startRow][startCol] = ".";
            } else if (destRow == startRow - 1) {
                Integer[][] newVal = {{startRow - 1, startCol}, {endRow - 1, endCol}};
                carPos.put(car, newVal);
                this.currentConfig.getCurrBoard()[startRow - 1][startCol] = car;
                this.currentConfig.getCurrBoard()[endRow][endCol] = ".";
            }
        }
    }
}
