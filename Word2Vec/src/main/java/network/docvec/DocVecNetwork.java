package network.docvec;

import network.abs.Network;
import network.processing.result.NetworkResult;

/**
 * @author peaseloxes
 */
public class DocVecNetwork extends Network {

    @Override
    public void load(final String fileName) {

    }

    @Override
    public void save(final String fileName) {

    }

    @Override
    public void create(final String dataFileName, final String saveFileName) {

    }

    @Override
    public void prepareData(final String rawInputFileName, final String cleanedInputFileName) {

    }

    @Override
    public NetworkResult suggestionsFor(final String term) {
        return null;
    }

    @Override
    public NetworkResult suggestionsForNoBoost(final String term) {
        return null;
    }

    @Override
    public void loadBinary(final String outputFileName) {

    }
}
