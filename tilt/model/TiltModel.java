package puzzles.tilt.model;

import javafx.scene.layout.TilePane;
import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class TiltModel {
    /** the collection of observers of this model */
    private final List<Observer<TiltModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private TiltConfig currentConfig;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<TiltModel, String> observer) {
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
        this.currentConfig = new TiltConfig(filename);
        this.alertObservers("File: " + filename + " Loaded!");
    }

    /**
     * this function resets the game board
     * @param filename: the filename to reset the board to
     * @throws FileNotFoundException
     */
    public void resetBoard(String filename) throws FileNotFoundException {
        this.currentConfig = new TiltConfig(filename);
        this.alertObservers("Game Board Reset");
    }

    /**
     * This function is used to copy a config to the current config
     * @param config
     */
    public void setConfig(Configuration config) {
        if(config instanceof TiltConfig) {
            TiltConfig tc = (TiltConfig) config;
            this.currentConfig = tc;
        }
    }

    /**
     * this function is used to get the conig
     * @return the current config
     */
    public TiltConfig getBoard() {
        return this.currentConfig;
    }

    /**
     * this function is used to tilt the board north
     * @return whether the tilt was valid or not
     */
    public boolean tiltNorth() {
        boolean isValid = this.currentConfig.tiltUp();
        if(!isValid){
            this.alertObservers("Invalid Move! Try a different Move!");
        } else {
            this.alertObservers("Tilted North");
        }
        return isValid;
    }

    /**
     * this function is used to tilt the board south
     * @return whether the tilt was valid or not
     */
    public boolean tiltSouth() {
        boolean isValid = this.currentConfig.tiltDown();
        if(!isValid){
            this.alertObservers("Invalid Move! Try a different Move!");
        } else {
            this.alertObservers("Tilted South");
        }
        return isValid;    }

    /**
     * this function is used to tilt the board east
     * @return whether the tilt was valid or not
     */
    public boolean tiltEast() {
        boolean isValid = this.currentConfig.tiltRight();
        if(!isValid){
            this.alertObservers("Invalid Move! Try a different Move!");
        } else {
            this.alertObservers("Tilted East");
        }
        return isValid;    }

    /**
     * this function is used to tilt the board west
     * @return whether the tilt was valid or not
     */
    public boolean tiltWest() {
        boolean isValid = this.currentConfig.tiltLeft();
        if(!isValid){
            this.alertObservers("Invalid Move! Try a different Move!");
        } else {
            this.alertObservers("Tilted West");
        }
        return isValid;    }
}
