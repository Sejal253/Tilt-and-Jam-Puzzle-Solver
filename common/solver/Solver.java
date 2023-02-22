package puzzles.common.solver;

import java.util.*;

/**
 * Solver class that uses the BFS algorithm to solve the given puzzle
 * @author Sejal Bhattad
 */
public class Solver {
    public int totalConfig = 0;
    public int uniqueConfig = 0;

    /**
     * This function is used to conduct breath first search to find the shortest path to the solution of the
     * given puzzle
     * @param startConfig: The starting configuration of the puzzle
     * @return: The shortest path from startConfig to the result needed
     */
    public List<Configuration> bfs(Configuration startConfig) {
        Map<Configuration, Configuration> predecessor = new HashMap<>();
        HashSet<Configuration> unique = new HashSet<>();

        unique.add(startConfig);
        predecessor.put(startConfig, null);

        ArrayList<Configuration> toVisit = new ArrayList<>();
        toVisit.add(startConfig);
        this.totalConfig++;

        while( !toVisit.isEmpty() && !toVisit.get(0).isSolution()) {
            Configuration thisConfig = toVisit.remove(0);
            thisConfig.getNeighbors().forEach( nbr -> {
                this.totalConfig++;
                if (!predecessor.containsKey(nbr)) {
                    predecessor.put(nbr, thisConfig);
                    toVisit.add(nbr);
                    unique.add(nbr);
                }
            });
        }

        this.uniqueConfig = unique.size();

        if (toVisit.isEmpty()) {
            return null;
        } else {
            List<Configuration> path = new ArrayList<>();
            path.add(0, toVisit.get(0));
            Configuration config = predecessor.get(toVisit.get(0));
            while (config != null) {
                path.add(0, config);
                config = predecessor.get(config);
            }
            return path;
        }
    }
}
