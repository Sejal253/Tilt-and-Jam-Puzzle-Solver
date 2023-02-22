package puzzles.strings;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.List;

/**
 * Main class for the strings puzzle.
 *
 * @author Sejal Bhattad
 */
public class Strings {
    /**
     * Run an instance of the strings puzzle.
     *
     * @param args [0]: the starting string;
     *             [1]: the finish string.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Strings start finish"));
        } else {
            // TODO YOUR MAIN CODE HERE
            StringsConfiguration sc = new StringsConfiguration(args[0].strip());
            StringsConfiguration.resultString = args[1].strip();
            Solver solver = new Solver();
            List<Configuration> path = solver.bfs(sc);
            System.out.println("Start: " + args[0].strip() + ", End: " + args[1].strip());
            System.out.println("Total configs: " + solver.totalConfig);
            System.out.println("Unique configs: " + solver.uniqueConfig);
            if(path == null) {
                System.out.println("No solution");
            } else {
                for (int i = 0; i < path.size(); i++) {
                    System.out.println("Step " + i + ": " + path.get(i));
                }
            }
        }
    }
}
