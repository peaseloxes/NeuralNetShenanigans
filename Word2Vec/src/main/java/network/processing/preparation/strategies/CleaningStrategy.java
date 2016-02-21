package network.processing.preparation.strategies;

import java.util.List;

/**
 * @author peaseloxes
 */
public interface CleaningStrategy {
    /**
     * Clean the lines.
     *
     * @param input the input lines.
     * @return cleaned lines.
     */
    List<String> scrub(List<String> input);
}
