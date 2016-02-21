package network.processing.preparation;

import java.util.List;
import network.processing.preparation.strategies.BasicCleaning;
import network.processing.preparation.strategies.CleaningStrategy;
import util.IOUtil;

/**
 * Pre processes data input files before being fed to a neural network.
 *
 * @author peaseloxes
 */
public class DataPreProcessor {

    private CleaningStrategy cleaningStrategy;

    public DataPreProcessor() {
        this.cleaningStrategy = new BasicCleaning();
    }

    public DataPreProcessor(final CleaningStrategy strategy) {
        this.cleaningStrategy = strategy;
    }

    /**
     * Process the file using the set rules.
     *
     * @param inputFileName  input file name.
     * @param outputFileName output file name.
     */
    public final void process(final String inputFileName, final String outputFileName) {
        // load
        List<String> lines = IOUtil.readLines(inputFileName);
        // clean
        lines = cleaningStrategy.scrub(lines);
        // save
        IOUtil.writeLines(outputFileName, lines);
    }
}