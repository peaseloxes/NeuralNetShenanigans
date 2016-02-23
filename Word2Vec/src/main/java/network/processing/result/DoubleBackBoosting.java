package network.processing.result;

import java.util.Map;
import network.abs.Network;
import network.wordvec.NetworkResult;

/**
 * Boosts terms based on their occurrence position in the result queries.
 *
 * @author alex
 */
public class DoubleBackBoosting implements BoostingStrategy {

    private final Network network;

    // no touchy
    private DoubleBackBoosting(){
        network = null;
    }

    public DoubleBackBoosting(final Network network) {
        this.network = network;
    }

    @Override
    public NetworkResult determineBoost(final NetworkResult term, final String[] result) {
        for (String subResult : result) {
            assert network != null;
            Map<String, Double> subSuggestion = network.suggestionsForNoBoost(subResult);
            int boost = subSuggestion.size();
            for (Map.Entry<String, Double> stringDoubleEntry : subSuggestion.entrySet()) {
                if(stringDoubleEntry.getKey().equals(term.getTerm())) {
                    term.addBoost(boost);
                }
                boost--;
            }
        }
        return term;
    }
}
