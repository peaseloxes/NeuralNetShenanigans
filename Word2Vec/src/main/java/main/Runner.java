package main;

import network.abs.Network;
import network.wordvec.WordVecNetwork;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author peaseloxes
 */
public class Runner {

    // tiny, tiny source file, unsuited for proper training
    private static final String RAW_INPUT_FILE = "raw_data/pg1661.txt";
    private static final String INPUT_FILE = "data/input.txt";
    private static final String OUTPUT_FILE = "model/model.txt";

    private static final Logger LOGGER = LogManager.getLogger(Runner.class.getName());

    /**
     * Go go gadget neural net!
     *
     * @param args unused args param.
     */
    public static void main(String[] args) throws Exception {
        LOGGER.warn("Remember to set java heap space to an appropriate (i.e. high) level.");
        final Network network = new WordVecNetwork();
        network.prepareData(RAW_INPUT_FILE, INPUT_FILE);
        network.create(INPUT_FILE, OUTPUT_FILE);
        network.load(OUTPUT_FILE);
        System.out.println("hand -> " + network.suggestionsFor("hand"));
        System.out.println("arm -> " + network.suggestionsFor("arm"));
        System.out.println("shoulder -> " + network.suggestionsFor("shoulder"));
    }
}
