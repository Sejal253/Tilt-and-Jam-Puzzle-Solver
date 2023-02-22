package puzzles.water;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.strings.StringsConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Main class for the water buckets puzzle.
 *
 * @author Sejal Bhattad
 */
public class Water {

    /**
     * Run an instance of the water buckets puzzle.
     *
     * @param args [0]: desired amount of water to be collected;
     *             [1..N]: the capacities of the N available buckets.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(
                    ("Usage: java Water amount bucket1 bucket2 ...")
            );
        } else {
            WatersConfiguration.resultVol = Integer.parseInt(args[0].strip());

            ArrayList<Integer> bucketCapacity = new ArrayList<>();
            for (int i = 1; i < args.length; i++) {
                bucketCapacity.add(Integer.parseInt(args[i].strip()));
            }

            WatersConfiguration.bucketCapacity = bucketCapacity;

            WatersConfiguration wcBuckets = new WatersConfiguration(bucketCapacity);

            WatersConfiguration wc = new WatersConfiguration();
            Solver solver = new Solver();
            List<Configuration> path = solver.bfs(wc);
            System.out.println("Amount: " + args[0].strip() + ", Buckets: " + wcBuckets);
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
