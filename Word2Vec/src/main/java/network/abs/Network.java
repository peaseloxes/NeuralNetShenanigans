package network.abs;

import lombok.Data;
import network.processing.abs.LinePreProcessor;
import network.processing.abs.WordPreProcessor;
import network.processing.preparation.DataPreProcessor;
import network.processing.result.NetworkResult;
import network.processing.result.boosting.BoostingStrategy;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author peaseloxes
 */
@Data
public abstract class Network {

    private int batchSize = 500;
    private int iterations = 1;
    private int epochs = 1;
    private int minWordOccurrence = 10;
    private int numVectors = 300;
    private double learningRate = 0.025;
    private double minLearningRate = 1e-3;
    private int negativeSample = 10;
    private String stopwordsFileName = "model/stopwords.txt";


    private BoostingStrategy boostingStrategy;
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

    // TODO make multifile
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
     * @param term the term to find matches for.
     * @return a sorted result set with the found terms and their boost/similarity factor.
     */
    public abstract NetworkResult suggestionsFor(final String term);

    /**
     * Find words similar to the term provided.
     * <p>
     * Guarantees that no boost will be applied to the result.
     *
     * @param term the term to find matches for.
     * @return a sorted result set with the found terms and their boost/similarity factor.
     */
    public abstract NetworkResult suggestionsForNoBoost(final String term);

    /**
     * Load a binary model into this network.
     *
     * @param outputFileName the file to load.
     */
    public abstract void loadBinary(final String outputFileName);
}
