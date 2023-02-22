package puzzles.jam.solver;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.jam.model.JamConfig;

import java.io.FileNotFoundException;
import java.util.List;

public class Jam {
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Usage: java Jam filename");
        } else {
            String filename = args[0].strip();
            JamConfig jc = new JamConfig(filename);
            System.out.println("File: " + filename);
            System.out.println(jc);
            jc.getNeighbors();

            Solver solver = new Solver();
            List<Configuration> path = solver.bfs(jc);
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