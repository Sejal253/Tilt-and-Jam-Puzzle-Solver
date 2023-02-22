package puzzles.tilt.model;

// TODO: implement your TiltConfig for the common solver

import puzzles.common.solver.Configuration;
import puzzles.tilt.solver.Tilt;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * This is the configuration class used by the solver class and the model to solve the puzzle and
 * provide helper functions for the TUI and GUI
 */
public class TiltConfig implements Configuration {

    private String[][] currentBoard;
    private int boardSize;

    /**
     * The main constructor for the class that takes in the file name and creates the board using it.
     * @param filename: Name of the file with the board config
     * @throws FileNotFoundException
     */
    public TiltConfig(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner reader = new Scanner(file);
        this.boardSize = Integer.parseInt(reader.nextLine().strip());
        this.currentBoard = new String[this.boardSize][this.boardSize];
        int i = 0;
        while(reader.hasNextLine()) {
            String[] line = reader.nextLine().split(" ");
            this.currentBoard[i] = line;
            i++;
        }
    }

    /**
     * This is overloaded constructor which takes in the game board and the board size to create a new
     * config
     * @param board: the board to make the config with
     * @param boardSize: the size of the board.
     */
    public TiltConfig(String[][] board, int boardSize) {
        this.boardSize = boardSize;
        this.currentBoard = new String[this.boardSize][this.boardSize];
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                this.currentBoard[i][j] = board[i][j];
            }
        }
    }

    /**
     * This function return the size of the board
     * @return: the size of the game board.
     */
    public int getBoardSize() {
        return this.boardSize;
    }

    /**
     * Returns the current board 2d array
     * @return: the current board
     */
    public String[][] getCurrentBoard() {
        return this.currentBoard;
    }

    /**
     * Returns whether the current config is the solution to the puzzle
     * @return boolean whether the config is the solution or not
     */
    @Override
    public boolean isSolution() {
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                if(this.currentBoard[i][j].equals("G")){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Tilts the board up
     * @return whether the tilt up move was valid or not
     */
    public boolean tiltUp() {
        String[][] copyUp = new String[this.boardSize][this.boardSize];
        boolean isValid = true;
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                copyUp[i][j] = this.currentBoard[i][j];
            }
        }
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                if(copyUp[i][j].equals("G") || copyUp[i][j].equals("B")) {
                    String tile = copyUp[i][j];
                    int upCount = i;
                    while(upCount>0 && copyUp[upCount-1][j].equals(".")) {
                        copyUp[upCount][j] = ".";
                        copyUp[upCount-1][j] = tile;
                        upCount--;
                    }
                    if(upCount-1 >= 0 && copyUp[upCount-1][j].equals("O")) {
                        if(tile.equals("B")) {
                            isValid = false;
                        }
                        copyUp[upCount][j] = ".";
                    }
                }
            }
        }

        if(isValid) {
            this.currentBoard = copyUp;
        }

        return isValid;
    }

    /**
     * Tilts the board down
     * @return whether the tilt down move was valid or not
     */
    public boolean tiltDown() {
        String[][] copyDown = new String[this.boardSize][this.boardSize];
        boolean isValid = true;
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                copyDown[i][j] = this.currentBoard[i][j];
            }
        }

        for (int i = this.boardSize - 1; i >= 0; i--) {
            for (int j = this.boardSize - 1; j >= 0; j--) {
                if(copyDown[i][j].equals("G") || copyDown[i][j].equals("B")) {
                    String tile = copyDown[i][j];
                    int downCount = i;
                    while(downCount<this.boardSize-1 && copyDown[downCount+1][j].equals(".")) {
                        copyDown[downCount][j] = ".";
                        copyDown[downCount+1][j] = tile;
                        downCount++;
                    }
                    if(downCount+1 < this.boardSize && copyDown[downCount+1][j].equals("O")) {
                        if(tile.equals("B")) {
                            isValid = false;
                        }
                        copyDown[downCount][j] = ".";
                    }
                }
            }
        }

        if(isValid) {
            this.currentBoard = copyDown;
        }

        return isValid;
    }

    /**
     * Tilts the board right
     * @return whether the tilt right move was valid or not
     */
    public boolean tiltRight() {
        String[][] copyRight = new String[this.boardSize][this.boardSize];
        boolean isValid = true;
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                copyRight[i][j] = this.currentBoard[i][j];
            }
        }

        for (int j = this.boardSize - 1; j >= 0; j--) {
            for (int i = 0; i < this.boardSize; i++) {
                if(copyRight[i][j].equals("G") || copyRight[i][j].equals("B")) {
                    String tile = copyRight[i][j];
                    int rightCount = j;
                    while(rightCount<this.boardSize-1 && copyRight[i][rightCount+1].equals(".")) {
                        copyRight[i][rightCount] = ".";
                        copyRight[i][rightCount+1] = tile;
                        rightCount++;
                    }
                    if(rightCount+1 < this.boardSize && copyRight[i][rightCount+1].equals("O")) {
                        if(tile.equals("B")) {
                            isValid = false;
                        }
                        copyRight[i][rightCount] = ".";
                    }
                }
            }
        }

        if(isValid) {
            this.currentBoard = copyRight;
        }

        return isValid;
    }

    /**
     * Tilts the board left
     * @return whether the tilt left move was valid or not
     */
    public boolean tiltLeft() {
        String[][] copyLeft = new String[this.boardSize][this.boardSize];
        boolean isValid = true;
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                copyLeft[i][j] = this.currentBoard[i][j];
            }
        }

        for (int j = 0; j < this.boardSize; j++) {
            for (int i = this.boardSize - 1; i >= 0; i--) {
                if(copyLeft[i][j].equals("G") || copyLeft[i][j].equals("B")) {
                    String tile = copyLeft[i][j];
                    int leftCount = j;
                    while(leftCount>0 && copyLeft[i][leftCount-1].equals(".")) {
                        copyLeft[i][leftCount] = ".";
                        copyLeft[i][leftCount-1] = tile;
                        leftCount--;
                    }
                    if(leftCount-1 >= 0 && copyLeft[i][leftCount-1].equals("O")) {
                        if(tile.equals("B")) {
                            isValid = false;
                        }
                        copyLeft[i][leftCount] = ".";
                    }
                }
            }
        }

        if(isValid) {
            this.currentBoard = copyLeft;
        }

        return isValid;
    }

    /**
     * This function is used to generate the child configs for the given config
     * @return Arraylist of configurations which are child configs of the current
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> children = new ArrayList<>();

        //Tilt Right
        String[][] copyRight = new String[this.boardSize][this.boardSize];
        boolean isRightValid = true;
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                copyRight[i][j] = this.currentBoard[i][j];
            }
        }

        for (int j = this.boardSize - 1; j >= 0; j--) {
            for (int i = 0; i < this.boardSize; i++) {
                if(copyRight[i][j].equals("G") || copyRight[i][j].equals("B")) {
                    String tile = copyRight[i][j];
                    int rightCount = j;
                    while(rightCount<this.boardSize-1 && copyRight[i][rightCount+1].equals(".")) {
                        copyRight[i][rightCount] = ".";
                        copyRight[i][rightCount+1] = tile;
                        rightCount++;
                    }
                    if(rightCount+1 < this.boardSize && copyRight[i][rightCount+1].equals("O")) {
                        if(tile.equals("B")) {
                            isRightValid = false;
                        }
                        copyRight[i][rightCount] = ".";
                    }
                }
            }
        }

        if(isRightValid) {
            Configuration configRight = new TiltConfig(copyRight, this.boardSize);
            children.add(configRight);
        }

        //Tilt Down
        String[][] copyDown = new String[this.boardSize][this.boardSize];
        boolean isDownValid = true;
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                copyDown[i][j] = this.currentBoard[i][j];
            }
        }

        for (int i = this.boardSize - 1; i >= 0; i--) {
            for (int j = this.boardSize - 1; j >= 0; j--) {
                if(copyDown[i][j].equals("G") || copyDown[i][j].equals("B")) {
                    String tile = copyDown[i][j];
                    int downCount = i;
                    while(downCount<this.boardSize-1 && copyDown[downCount+1][j].equals(".")) {
                        copyDown[downCount][j] = ".";
                        copyDown[downCount+1][j] = tile;
                        downCount++;
                    }
                    if(downCount+1 < this.boardSize && copyDown[downCount+1][j].equals("O")) {
                        if(tile.equals("B")) {
                            isDownValid = false;
                        }
                        copyDown[downCount][j] = ".";
                    }
                }
            }
        }

        if (isDownValid) {
            Configuration configDown = new TiltConfig(copyDown, this.boardSize);
            children.add(configDown);
        }

        //Tilt Left
        String[][] copyLeft = new String[this.boardSize][this.boardSize];
        boolean isLeftValid = true;
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                copyLeft[i][j] = this.currentBoard[i][j];
            }
        }

        for (int j = 0; j < this.boardSize; j++) {
            for (int i = this.boardSize - 1; i >= 0; i--) {
                if(copyLeft[i][j].equals("G") || copyLeft[i][j].equals("B")) {
                    String tile = copyLeft[i][j];
                    int leftCount = j;
                    while(leftCount>0 && copyLeft[i][leftCount-1].equals(".")) {
                        copyLeft[i][leftCount] = ".";
                        copyLeft[i][leftCount-1] = tile;
                        leftCount--;
                    }
                    if(leftCount-1 >= 0 && copyLeft[i][leftCount-1].equals("O")) {
                        if(tile.equals("B")) {
                            isLeftValid = false;
                        }
                        copyLeft[i][leftCount] = ".";
                    }
                }
            }
        }

        if(isLeftValid) {
            Configuration configLeft = new TiltConfig(copyLeft, this.boardSize);
            children.add(configLeft);
        }


        //Tilt Up
        String[][] copyUp = new String[this.boardSize][this.boardSize];
        boolean isUpValid = true;
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                copyUp[i][j] = this.currentBoard[i][j];
            }
        }
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                if(copyUp[i][j].equals("G") || copyUp[i][j].equals("B")) {
                    String tile = copyUp[i][j];
                    int upCount = i;
                    while(upCount>0 && copyUp[upCount-1][j].equals(".")) {
                        copyUp[upCount][j] = ".";
                        copyUp[upCount-1][j] = tile;
                        upCount--;
                    }
                    if(upCount-1 >= 0 && copyUp[upCount-1][j].equals("O")) {
                        if(tile.equals("B")) {
                            isUpValid = false;
                        }
                        copyUp[upCount][j] = ".";
                    }
                }
            }
        }

        if(isUpValid) {
            Configuration configUp = new TiltConfig(copyUp, this.boardSize);
            children.add(configUp);
        }

        return children;
    }

    /**
     * This function is used to check of the current object is equal to the other object
     * @param other a second config to compare with
     * @return whether the second object is equal to the current object
     */
    @Override
    public boolean equals(Object other) {
        if(other instanceof TiltConfig) {
            TiltConfig otherConfig = (TiltConfig) other;
            if(otherConfig.boardSize != this.boardSize) {
                return false;
            }
            for (int i = 0; i < this.boardSize; i++) {
                for (int j = 0; j < this.boardSize; j++) {
                    if(!otherConfig.currentBoard[i][j].equals(this.currentBoard[i][j])) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * To get the hashcode of the current object
     * @return
     */
    @Override
    public int hashCode() {
        String s = "";
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                s += this.currentBoard[i][j] + " ";
            }
        }
        return s.hashCode();
    }

    /**
     * This function returns the string representation of the current object.
     * @return the string representation of the current object
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < this.boardSize; i++) {
            for (int j = 0; j < this.boardSize; j++) {
                s += this.currentBoard[i][j] + " ";
            }
            s += "\n";
        }
        return s;
    }
}
