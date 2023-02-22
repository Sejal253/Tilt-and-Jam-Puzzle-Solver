package puzzles.water;

import puzzles.common.solver.Configuration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The configuration for the Water puzzle. This class defines the functions from the Configuration interface
 * that are specific to the Water puzzle
 * @author Sejal Bhattad
 */
public class WatersConfiguration implements Configuration {

    public ArrayList<Integer> buckets;
    static ArrayList<Integer> bucketCapacity;
    static Integer resultVol;

    /**
     * Constructor for the StringsConfiguration class. Sets all the values of the buckets to 0
     */
    public WatersConfiguration() {
        this.buckets = new ArrayList<>();
        for(int i=0; i<bucketCapacity.size(); i++) {
            buckets.add(0);
        }
    }

    /**
     * Constructor for the StringsConfiguration class
     * @param buckets: The bucket values to create the configuration with.
     */
    public WatersConfiguration(ArrayList<Integer> buckets) {
        this.buckets = buckets;
    }

    /**
     * This function is used to check if the given configuration is the final solution
     * @return: boolean value if the current configuration is the solution or not
     */
    @Override
    public boolean isSolution() {
        for(Integer vol : buckets) {
            if (vol.equals(resultVol)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This function is used to produce the children configuration of current config
     * @return Collection of the children configurations for the current config.
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> retVal = new ArrayList<>();

        // Completely fill a bucket
        for(int i=0; i<this.buckets.size(); i++) {
            ArrayList<Integer> newBuckets = new ArrayList<>();
            newBuckets.addAll(this.buckets);
            WatersConfiguration wc = new WatersConfiguration(newBuckets);
            wc.buckets.remove(i);
            wc.buckets.add(i, bucketCapacity.get(i));
            retVal.add(wc);
        }

        // Completely empty a bucket
        for(int i=0; i<this.buckets.size(); i++) {
            ArrayList<Integer> newBuckets = new ArrayList<>();
            newBuckets.addAll(this.buckets);
            WatersConfiguration wc = new WatersConfiguration(newBuckets);
            wc.buckets.remove(i);
            wc.buckets.add(i, 0);
            retVal.add(wc);
        }

        // Transferring from one bucket to another
        for(int i=0; i<this.buckets.size() - 1; i++) {
            for (int j=i+1; j<this.buckets.size(); j++) {
                ArrayList<Integer> newBuckets1 = new ArrayList<>();
                newBuckets1.addAll(this.buckets);
                WatersConfiguration wc1 = new WatersConfiguration(newBuckets1);

                //Bucket 1 to Bucket 2
                if(this.buckets.get(i) + this.buckets.get(j) <= bucketCapacity.get(j)) {
                    wc1.buckets.remove(j);
                    wc1.buckets.add(j, this.buckets.get(i) + this.buckets.get(j));
                    wc1.buckets.remove(i);
                    wc1.buckets.add(i, 0);
                } else {
                    int oldVal1 = wc1.buckets.remove(j);
                    wc1.buckets.add(j, bucketCapacity.get(j));
                    int oldVol2 = wc1.buckets.remove(i);
                    wc1.buckets.add(i, oldVol2 - (bucketCapacity.get(j) - oldVal1));
                }

                ArrayList<Integer> newBuckets2 = new ArrayList<>();
                newBuckets2.addAll(this.buckets);
                WatersConfiguration wc2 = new WatersConfiguration(newBuckets2);

                //Bucket 2 to Bucket 1
                if(this.buckets.get(i) + this.buckets.get(j) <= bucketCapacity.get(i)) {
                    wc2.buckets.remove(i);
                    wc2.buckets.add(i, this.buckets.get(i) + this.buckets.get(j));
                    wc2.buckets.remove(j);
                    wc2.buckets.add(j, 0);
                } else {
                    int oldVal1 = wc2.buckets.remove(i);
                    wc2.buckets.add(i, bucketCapacity.get(i));
                    int oldVol2 = wc2.buckets.remove(j);
                    wc2.buckets.add(j, oldVol2 - (bucketCapacity.get(i) - oldVal1));
                }

                retVal.add(wc1);
                retVal.add(wc2);
            }
        }

        return retVal;
    }

    /**
     * Equals function is used to check if the current configuration is equivalent to a given configuration
     * @param other: the other configuration to compare the current configuration to
     * @return: boolean value depending on if the current config is equivalent to the given other config.
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof WatersConfiguration) {
            WatersConfiguration otherWater = (WatersConfiguration) other;
            for(int i = 0; i<this.buckets.size(); i++) {
                if(!this.buckets.get(i).equals(otherWater.buckets.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Function used to calculate the hashvalue of the current configuration.
     * @return: The hashcode for the current config
     */
    @Override
    public int hashCode() {
        String s = "";
        for(Integer vol : this.buckets) {
            s += vol.toString();
        }
        return s.hashCode();
    }

    /**
     * Function to generate the string representation of the current configuration
     * @return: The string representation of the current configuration
     */
    @Override
    public String toString() {
        String retVal = "[";
        for(int i=0; i<this.buckets.size()-1; i++) {
            retVal += this.buckets.get(i).toString() + ", ";
        }
        retVal += this.buckets.get(this.buckets.size() - 1) + "]";

        return retVal;
    }
}
