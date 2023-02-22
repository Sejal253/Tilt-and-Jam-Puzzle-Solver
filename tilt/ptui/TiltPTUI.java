package puzzles.tilt.ptui;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.tilt.model.TiltConfig;
import puzzles.tilt.model.TiltModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * This class is the program that drives the command line interaction for the puzzle
 */
public class TiltPTUI implements Observer<TiltModel, String> {
    private TiltModel model;
    private String msg;

    /**
     * This is a constructor for the PTUI
     */
    public TiltPTUI() {
        model = new TiltModel();
        model.addObserver(this);
        this.msg = "";
    }

    /**
     * This function updates the model and the message for the game
     * @param model the object that wishes to inform this object
     *                about something that has happened.
     * @param message optional data the server.model can send to the observer
     *
     */
    @Override
    public void update(TiltModel model, String message) {
        this.model = model;
        this.msg = message;
    }

    /**
     * This function prints the command option menu
     */
    public void printMenu() {
        System.out.println("h(int)\t\t\t\t-- hint next move");
        System.out.println("l(oad) filename\t\t-- load new puzzle file");
        System.out.println("t(ilt) {N|S|E|W}\t-- tilt the board in the given direction");
        System.out.println("q(uit)\t\t\t\t-- quit the game");
        System.out.println("r(eset)\t\t\t\t-- reset the current game");
    }

    /**
     * This function prints the smaller version of the command menu once the game is over
     */
    public void printShortMenu() {
        System.out.println("l(oad) filename\t\t-- load new puzzle file");
        System.out.println("q(uit)\t\t\t\t-- quit the game");
        System.out.println("r(eset)\t\t\t\t-- reset the current game");
    }

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Usage: java TiltPTUI filename");
        } else {
            String filename = args[0].strip();

            TiltPTUI ptui = new TiltPTUI();
            ptui.model.addConfig(filename);

            System.out.println("File: " + filename);

            Scanner sc = new Scanner(System.in);
            boolean shortMenu = false;
            while(true) {
                System.out.println("Current Board: ");
                System.out.println(ptui.model.getBoard());

                if(!ptui.msg.equals("")) {
                    System.out.println(ptui.msg + "\n");
                    ptui.update(ptui.model, "");
                }

                if(ptui.model.getBoard().isSolution()) {
                    System.out.println("You solved the puzzle!\n");
                    shortMenu = true;
                    ptui.printShortMenu();
                } else {
                    ptui.printMenu();
                }

                System.out.print("Enter Command: ");
                String usrInput = sc.nextLine();
                System.out.println();

                String command = usrInput.split(" ")[0];

                switch (command) {
                    case "h":
                        if(shortMenu) {
                            ptui.update(ptui.model, "Invalid Command! Try again");
                            break;
                        }
                        Solver solver = new Solver();
                        List<Configuration> path = solver.bfs(ptui.model.getBoard());
                        if(path == null) {
                            ptui.update(ptui.model, "No Solution Exists!");
                            break;
                        }
                        ptui.model.setConfig(path.get(1));
                        break;
                    case "l":
                        filename = usrInput.split(" ")[1].strip();
                        File file = new File(filename);
                        if(!file.exists()) {
                            ptui.update(ptui.model, "Invalid File! Please enter a valid File Path");
                            break;
                        }
                        ptui.model.addConfig(filename);
                        break;
                    case "t":
                        if(shortMenu) {
                            ptui.update(ptui.model, "Invalid Command! Try again");
                            break;
                        }
                        String tiltDir = usrInput.split(" ")[1];
                        if(tiltDir.equals("N")) {
                            boolean isValid = ptui.model.tiltNorth();
                            if(!isValid) {
                                ptui.update(ptui.model, "Invalid Move! Try Again!");
                            }
                        } else if(tiltDir.equals("S")) {
                            boolean isValid = ptui.model.tiltSouth();
                            if(!isValid) {
                                ptui.update(ptui.model, "Invalid Move! Try Again!");
                            }
                        } else if(tiltDir.equals("E")) {
                            boolean isValid = ptui.model.tiltEast();
                            if(!isValid) {
                                ptui.update(ptui.model, "Invalid Move! Try Again!");
                            }
                        } else if(tiltDir.equals("W")) {
                            boolean isValid = ptui.model.tiltWest();
                            if(!isValid) {
                                ptui.update(ptui.model, "Invalid Move! Try Again!");
                            }
                        } else {
                            ptui.update(ptui.model, "Invalid Command! Try again");
                        }
                        break;
                    case "q":
                        System.out.println("Quitting Game");
                        System.exit(0);
                    case "r":
                        ptui.update(ptui.model, "Board Reset!");
                        ptui.model.addConfig(filename);
                        break;
                    default:
                        System.out.println("Wrong Input! Try again!");
                }
            }
        }
    }
}
