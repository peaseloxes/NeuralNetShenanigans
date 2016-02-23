package main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import network.abs.Network;
import network.processing.preparation.strategies.OpenNLPCleaning;
import network.wordvec.WordVecNetwork;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import util.IOUtil;

/**
 * @author peaseloxes
 */
public class Runner {

    // tiny, tiny source file, unsuited for proper training
    private static final String RAW_INPUT_FILE = "raw_data/sherlocks.txt";

    // following file not included on git, too big
    // uncomment the above file for testing
//    private static final String RAW_INPUT_FILE = "raw_data/massiveness.txt";
    private static final String INPUT_FILE = "data/input.txt";
    //    private static final String OUTPUT_FILE = "model/model.txt";
    private static final String OUTPUT_FILE = "model/model.txt";

    private static final String QUERY_INPUT_FILE = "query/query_input.txt";
    private static final String QUERY_OUTPUT_FILE = "query/query_output.txt";


    private static final Logger LOGGER = LogManager.getLogger(Runner.class.getName());

    /**
     * Go go gadget neural net!
     *
     * @param args unused args param.
     */
    public static void main(String[] args) throws Exception {
        LOGGER.warn("Remember to set java heap space to an appropriate (i.e. high) level.");
        final Network network = new WordVecNetwork();
        network.getDataPreProcessor().setCleaningStrategy(new OpenNLPCleaning());
        network.prepareData(RAW_INPUT_FILE, INPUT_FILE);
        network.create(INPUT_FILE, OUTPUT_FILE);
        network.load(OUTPUT_FILE);
        final List<String> terms = util.IOUtil.readLinesFromClasspath(QUERY_INPUT_FILE);
        final List<String> results = new ArrayList<>(terms.size());
        results.addAll(terms.stream().map(term -> term + " -> " + network.suggestionsFor(term).sortByBoost()).collect(Collectors.toList()));
        System.out.println("Wrote result to: " + IOUtil.writeLinesToClasspath(QUERY_OUTPUT_FILE, results));
    }
}
