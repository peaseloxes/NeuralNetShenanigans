package network.abs;

import java.util.Map;
import lombok.Data;
import network.processing.abs.LinePreProcessor;
import network.processing.abs.WordPreProcessor;
import network.processing.preparation.DataPreProcessor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author peaseloxes
 */
@Data
public abstract class Network {

    private DataPreProcessor dataPreProcessor;
    private LinePreProcessor linePreProcessor;
    private WordPreProcessor wordPreProcessor;

    protected static final Logger LOGGER = LogManager.getLogger(Network.class.getName());

    /**
     * Load a model file into this network.
     *
     * @param fileName the file to load.
     */
    public abstract void load(final String fileName);

    /**
     * Save a model file to disk.
     *
     * @param fileName the save file name.
     */
    public abstract void save(final String fileName);

    /**
     * Create a model based on training data.
     *
     * @param dataFileName the data file name.
     * @param saveFileName the save file name.
     */
    public abstract void create(final String dataFileName, final String saveFileName);

    /**
     * Prepare the data using the set data pre-processor.
     *
     * @param rawInputFileName     the file to clean and prep.
     * @param cleanedInputFileName the resulting clean file.
     */
    public abstract void prepareData(final String rawInputFileName, final String cleanedInputFileName);

    /**
     * Find words similar to the term provided.
     *
     * @param term the term to find mathes for.
     * @return a sorted result set with the found terms and their boost/similarity factor.
     */
    public abstract Map<String, Double> suggestionsFor(final String term);

}
