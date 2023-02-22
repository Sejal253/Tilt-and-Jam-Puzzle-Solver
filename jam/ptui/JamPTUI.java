package puzzles.jam.ptui;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.jam.model.JamModel;
import puzzles.tilt.ptui.TiltPTUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class JamPTUI implements Observer<JamModel, String> {
    private JamModel model;
    private String msg;

    /**
     * This constructor is used to add the current object to the observer list
     */
    public JamPTUI() {
        this.model = new JamModel();
        model.addObserver(this);
        this.msg = "";
    }

    /**
     * This function is used ot update the model and the message
     * @param jamModel the object that wishes to inform this object
     *                about something that has happened.
     * @param message optional data the server.model can send to the observer
     *
     */
    @Override
    public void update(JamModel jamModel, String message) {
        this.model = jamModel;
        this.msg = message;
    }

    /**
     * This function prints the command option menu
     */
    public void printMenu() {
        System.out.println("h(int)\t\t\t\t-- hint next move");
        System.out.println("l(oad) filename\t\t-- load new puzzle file");
        System.out.println("s(elect) r c\t\t-- select cell at r, c");
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
            System.out.println("Usage: java JamPTUI filename");
        } else {
            String filename = args[0].strip();

            JamPTUI ptui = new JamPTUI();
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
                            ptui.update(ptui.model, "Puzzle already solved!");
                            break;
                        }
                        Solver solver = new Solver();
                        List<Configuration> path = solver.bfs(ptui.model.getBoard());
                        if(path==null){
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
                    case "s":
                        if(shortMenu) {
                            break;
                        }

                        int row = Integer.parseInt(usrInput.split(" ")[1].strip());
                        int col = Integer.parseInt(usrInput.split(" ")[2].strip());

                        if(!ptui.model.getBoard().getCurrBoard()[row][col].equals(".")) {
                            String car = ptui.model.getBoard().getCurrBoard()[row][col];
                            System.out.println("s(elect) r c\t-- select the destination r, c");
                            System.out.print("Enter Command: ");
                            String nextInput = sc.nextLine();
                            System.out.println();
                            int destRow = Integer.parseInt(nextInput.split(" ")[1]);
                            int destCol = Integer.parseInt(nextInput.split(" ")[2]);

                            if(ptui.model.getBoard().getCurrBoard()[destRow][destCol].equals(".")) {
                                ptui.model.moveCar(car, destRow, destCol);
                            } else {
                                ptui.update(ptui.model, "Invalid Move! Try Again!");
                                break;
                            }
                        } else {
                            ptui.update(ptui.model, "Invalid Move! Try Again!");
                            break;
                        }
                        break;
                    case "q":
                        System.out.println("Quitting Game!");
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
