package network.processing.result.boosting;

import network.abs.Network;
import network.processing.result.NetworkResult;
import network.processing.result.NetworkResultItem;

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
    public NetworkResultItem determineBoost(final NetworkResultItem term, final String[] result) {
        for (String subResult : result) {
            assert network != null;
            NetworkResult subSuggestion = network.suggestionsForNoBoost(subResult);
            int boost = subSuggestion.size();
            for (NetworkResultItem item : subSuggestion.getResultItems()) {
                if(item.getTerm().equals(term.getTerm())) {
                    term.addBoost(boost);
                }
                boost--;
            }
        }
        return term;
    }
}
