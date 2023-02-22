package puzzles.tilt.solver;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.tilt.model.TiltConfig;

import java.io.FileNotFoundException;
import java.util.List;

public class Tilt {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Usage: java Tilt filename");
        } else {
            String filename = args[0].strip();
            TiltConfig config = new TiltConfig(filename);
            System.out.println("File: " + filename);
            System.out.print(config);
            config.getNeighbors();

            Solver solver = new Solver();
            List<Configuration> path = solver.bfs(config);
            System.out.println("Total configs: " + solver.totalConfig);
            System.out.println("Unique configs: " + solver.uniqueConfig);
            if(path == null) {
                System.out.println("No solution");
            } else {
                for (int i = 0; i < path.size(); i++) {
                    System.out.println("Step " + i + ": \n" + path.get(i));
                }
            }
        }
    }
}
