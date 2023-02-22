package puzzles.strings;

import puzzles.common.solver.Configuration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * The configuration for the String puzzle. This class defines the functions from the Configuration interface
 * that are specific to the String puzzle
 * @author Sejal Bhattad
 */
public class StringsConfiguration implements Configuration {

    private String currentString;
    static String resultString;

    /**
     * Constructor for the StringsConfiguration class
     * @param currentString: The string used to create the configuration
     */
    public StringsConfiguration(String currentString) {
        this.currentString = currentString;
    }

    /**
     * This function is used to check if the given configuration is the final solution
     * @return: boolean value if the current configuration is the solution or not
     */
    @Override
    public boolean isSolution() {
        return currentString.equals(resultString);
    }

    /**
     * This function is used to produce the children configuration of current config
     * @return Collection of the children configurations for the current config.
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        ArrayList<Configuration> retVal = new ArrayList<>();
        for(int i = 0; i<this.currentString.length(); i++) {
            char c = this.currentString.charAt(i);
            int asciiVal = c;
            String str1, str2;
            switch (asciiVal) {
                case 65:
                    str1 = this.currentString.substring(0, i) + (char) (90) + this.currentString.substring(i+1);
                    str2 = this.currentString.substring(0, i) + (char) (66) + this.currentString.substring(i+1);
                    break;
                case 90:
                    str1 = this.currentString.substring(0, i) + (char) (89) + this.currentString.substring(i+1);
                    str2 = this.currentString.substring(0, i) + (char) (65) + this.currentString.substring(i+1);
                    break;
                default:
                    str1 = this.currentString.substring(0, i) + (char) (asciiVal - 1) + this.currentString.substring(i+1);
                    str2 = this.currentString.substring(0, i) + (char) (asciiVal + 1) + this.currentString.substring(i+1);
                    break;
            }
            Configuration config1 = new StringsConfiguration(str1);
            Configuration config2 = new StringsConfiguration(str2);
            retVal.add(config1);
            retVal.add(config2);
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
        if(other instanceof StringsConfiguration) {
            StringsConfiguration otherString = (StringsConfiguration)other;
            if(this.currentString.equals(otherString.currentString)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function used to calculate the hashvalue of the current configuration.
     * @return: The hashcode for the current config
     */
    @Override
    public int hashCode() {
        return currentString.hashCode();
    }

    /**
     * Function to generate the string representation of the current configuration
     * @return: The string representation of the current configuration
     */
    @Override
    public String toString() {
        return currentString;
    }
}
