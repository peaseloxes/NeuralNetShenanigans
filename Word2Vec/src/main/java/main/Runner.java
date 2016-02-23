package main;

import network.abs.Network;
import network.processing.preparation.strategies.OpenNLPCleaning;
import network.wordvec.WordVecNetwork;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.joda.time.LocalDateTime;

/**
 * @author peaseloxes
 */
public class Runner {

    // tiny, tiny source file, unsuited for proper training
    // private static final String RAW_INPUT_FILE = "raw_data/pg1661.txt";

    // following file not included on git, too big
    // uncomment the above file for testing
    private static final String RAW_INPUT_FILE = "raw_data/massiveness.txt";
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
        network.getDataPreProcessor().setCleaningStrategy(new OpenNLPCleaning());
        network.prepareData(RAW_INPUT_FILE, INPUT_FILE);
        network.create(INPUT_FILE, OUTPUT_FILE);
        network.load(OUTPUT_FILE);
        System.out.println(LocalDateTime.now() + " hand -> " + network.suggestionsFor("hand"));
        System.out.println(LocalDateTime.now() + " arm -> " + network.suggestionsFor("arm"));
        System.out.println(LocalDateTime.now() + " shoulder -> " + network.suggestionsFor("shoulder"));

        System.out.println("horse -> " + network.suggestionsFor("horse"));
        System.out.println("dog -> " + network.suggestionsFor("dog"));
        System.out.println("wolf -> " + network.suggestionsFor("wolf"));
        System.out.println("cat -> " + network.suggestionsFor("cat"));
        System.out.println("house -> " + network.suggestionsFor("house"));
        System.out.println("sky -> " + network.suggestionsFor("sky"));
        System.out.println("water -> " + network.suggestionsFor("water"));
        System.out.println("mother -> " + network.suggestionsFor("mother"));
        System.out.println("child -> " + network.suggestionsFor("child"));
        System.out.println("sea -> " + network.suggestionsFor("sea"));
        System.out.println("tree -> " + network.suggestionsFor("tree"));
        System.out.println("flower -> " + network.suggestionsFor("flower"));
        System.out.println("wide-spreading -> " + network.suggestionsFor("wide-spreading"));
        System.out.println("knife -> " + network.suggestionsFor("knife"));
        System.out.println("rock -> " + network.suggestionsFor("rock"));
        System.out.println("radio -> " + network.suggestionsFor("radio"));
        System.out.println("devil -> " + network.suggestionsFor("devil"));
        System.out.println("heaven -> " + network.suggestionsFor("heaven"));
        System.out.println("love -> " + network.suggestionsFor("love"));
        System.out.println("hate -> " + network.suggestionsFor("hate"));
        System.out.println("money -> " + network.suggestionsFor("money"));
        System.out.println("people -> " + network.suggestionsFor("people"));
        System.out.println("animal -> " + network.suggestionsFor("animal"));
        System.out.println("food -> " + network.suggestionsFor("food"));
        System.out.println("eat -> " + network.suggestionsFor("eat"));
        System.out.println("sleep -> " + network.suggestionsFor("sleep"));
        System.out.println("sleeping -> " + network.suggestionsFor("sleeping"));
        System.out.println("walk -> " + network.suggestionsFor("walk"));
        System.out.println("walking -> " + network.suggestionsFor("walking"));
        System.out.println("running -> " + network.suggestionsFor("running"));
    }
}
