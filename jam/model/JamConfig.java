package puzzles.jam.model;

// TODO: implement your JamConfig for the common solver

import puzzles.common.solver.Configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class is the config for the BFS solver.
 */
public class JamConfig implements Configuration {

    private String[][] currBoard;
    private int numRows;
    private int numCols;
    private int numCars;
    HashMap<String, Integer[][]> carPos;

    /**
     * This is the constructor that takes in the file name and creates the board using the file content
     * @param filename
     * @throws FileNotFoundException
     */
    public JamConfig(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner reader = new Scanner(file);
        String[] rowCol = reader.nextLine().split(" ");
        this.numRows = Integer.parseInt(rowCol[0]);
        this.numCols = Integer.parseInt(rowCol[1]);
        this.currBoard = new String[this.numRows][this.numCols];
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numCols; j++) {
                this.currBoard[i][j] = ".";
            }
        }
        this.carPos = new HashMap<>();
        this.numCars = Integer.parseInt(reader.nextLine().strip());
        while(reader.hasNext()) {
            String[] line = reader.nextLine().split(" ");
            String carLetter = line[0];
            int startRow = Integer.parseInt(line[1]);
            int startCol = Integer.parseInt(line[2]);
            int endRow = Integer.parseInt(line[3]);
            int endCol = Integer.parseInt(line[4]);

            Integer[][] val = {{startRow, startCol}, {endRow, endCol}};
            this.carPos.put(carLetter, val);

            for (int i = startRow; i <= endRow; i++) {
                for (int j = startCol; j <= endCol; j++) {
                    this.currBoard[i][j] = carLetter;
                }
            }
        }
    }

    /**
     * This is an overloaded constructor that is used to copy a differeent config.
     * @param board: the board ot be copied
     * @param numCars: the number of cars on the board
     * @param carPos: the positions of each car
     * @param car: the current car
     * @param startRow: the start row of the car
     * @param startCol: the start col of the car
     * @param endRow: the end row of the car
     * @param endCol: the end of the col
     */
    public JamConfig(String[][] board, int numCars, HashMap<String, Integer[][]> carPos, String car, int startRow, int startCol, int endRow, int endCol) {
        this.numRows = board.length;
        this.numCols = board[0].length;
        this.numCars = numCars;
        this.carPos = new HashMap<>();
        for(String s : carPos.keySet()) {
            if(s.equals(car)) {
                Integer[][] val = {{startRow, startCol}, {endRow, endCol}};
                this.carPos.put(car, val);
            } else {
                Integer[][] val = carPos.get(s);
                Integer[][] newVal = {{val[0][0], val[0][1]}, {val[1][0], val[1][1]}};
                this.carPos.put(s, newVal);
            }
        }

        this.currBoard = new String[this.numRows][this.numCols];
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numCols; j++) {
                this.currBoard[i][j] = board[i][j];
            }
        }
    }

    /**
     * This function is used to check if the current config is the solution
     * @return if the current config is the solution or not
     */
    @Override
    public boolean isSolution() {
        for (int i = 0; i < this.numRows; i++) {
            if(this.currBoard[i][this.numCols-1].equals("X")){
                return true;
            }
        }
        return false;
    }

    /**
     * This function is used ot get the current config
     * @return
     */
    public String[][] getCurrBoard() {
        return this.currBoard;
    }

    /**
     * This function is used to get the car postitions on the board
     * @return
     */
    public HashMap<String, Integer[][]> getCarPos() {
        return this.carPos;
    }

    /**
     * This is used ot get the number of col
     * @return the total num of col
     */
    public int getNumCols() {
        return this.numCols;
    }

    /**
     * this is used to get the number of rows
     * @return the total num of rows
     */
    public int getNumRows() {
        return this.numRows;
    }

    /**
     * This function is used to get the child configs for the current configs
     * @return an arraylist of all the possible child configs.
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> neighbors = new ArrayList<>();

        for(String s : this.carPos.keySet()) {
            Integer[][] val = this.carPos.get(s);
            int startRow = val[0][0];
            int startCol = val[0][1];
            int endRow = val[1][0];
            int endCol = val[1][1];

            if(startRow != endRow) {
                if(endRow < this.numRows - 1 && this.currBoard[endRow+1][endCol].equals(".")) {
                    String[][] copy = new String[this.numRows][this.numCols];
                    for (int i = 0; i < this.numRows; i++) {
                        for (int j = 0; j < this.numCols; j++) {
                            copy[i][j] = this.currBoard[i][j];
                        }
                    }

                    copy[endRow+1][endCol] = s;
                    copy[startRow][endCol] = ".";

                    Configuration config = new JamConfig(copy, this.numCars, this.carPos, s, startRow+1, startCol, endRow+1, endCol);
                    neighbors.add(config);
                }

                if(startRow > 0 && this.currBoard[startRow-1][endCol].equals(".")) {
                    String[][] copy = new String[this.numRows][this.numCols];
                    for (int i = 0; i < this.numRows; i++) {
                        for (int j = 0; j < this.numCols; j++) {
                            copy[i][j] = this.currBoard[i][j];
                        }
                    }

                    copy[startRow-1][endCol] = s;
                    copy[endRow][endCol] = ".";

                    Configuration config = new JamConfig(copy, this.numCars, this.carPos, s, startRow-1, startCol, endRow-1, endCol);
                    neighbors.add(config);
                }
            }

            if(startCol != endCol) {
                if(endCol < this.numCols - 1 && this.currBoard[endRow][endCol+1].equals(".")) {
                    String[][] copy = new String[this.numRows][this.numCols];
                    for (int i = 0; i < this.numRows; i++) {
                        for (int j = 0; j < this.numCols; j++) {
                            copy[i][j] = this.currBoard[i][j];
                        }
                    }

                    copy[endRow][endCol+1] = s;
                    copy[startRow][startCol] = ".";

                    Configuration config = new JamConfig(copy, this.numCars, this.carPos, s, startRow, startCol+1, endRow, endCol+1);
                    neighbors.add(config);
                }

                if(startCol > 0 && this.currBoard[startRow][startCol-1].equals(".")) {
                    String[][] copy = new String[this.numRows][this.numCols];
                    for (int i = 0; i < this.numRows; i++) {
                        for (int j = 0; j < this.numCols; j++) {
                            copy[i][j] = this.currBoard[i][j];
                        }
                    }

                    copy[startRow][startCol-1] = s;
                    copy[startRow][endCol] = ".";

                    Configuration config = new JamConfig(copy, this.numCars, this.carPos, s, startRow, startCol-1, endRow, endCol-1);
                    neighbors.add(config);
                }
            }
        }
        return neighbors;
    }

    /**
     * this function is used to check if the other config is equal to the current config
     * @param other the other config to compare to this current config
     * @return whether the current config is equal to the other config
     */
    @Override
    public boolean equals(Object other) {
        if(other instanceof JamConfig) {
            JamConfig otherConfig = (JamConfig) other;
            if(otherConfig.numRows != this.numRows && otherConfig.numCols != this.numCols && otherConfig.numCars != this.numCars) {
                return false;
            }
            for (int i = 0; i < this.numRows; i++) {
                for (int j = 0; j < this.numCols; j++) {
                    if(!otherConfig.currBoard[i][j].equals(this.currBoard[i][j])) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * this functio is used to calculate the hashvalue of the current object
     * @return the hash value of the current object
     */
    @Override
    public int hashCode() {
        String s = "";
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numCols; j++) {
                s += this.currBoard[i][j] + " ";
            }
            s += "\n";
        }
        return s.hashCode();
    }

    /**
     * this function is used to get the string representation of the objeect
     * @return the string representation of the current object
     */
    @Override
    public String toString() {
        String s = "  ";
        for (int i = 0; i < this.numCols; i++) {
            s += i + " ";
        }
        s += "\n";
        for (int i = 0; i < this.numRows; i++) {
            s += i + " ";
            for (int j = 0; j < this.numCols; j++) {
                s += this.currBoard[i][j] + " ";
            }
            s += "\n";
        }
        return s;
    }
}
