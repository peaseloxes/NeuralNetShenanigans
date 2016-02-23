package network.processing.result;

import network.wordvec.NetworkResult;

/**
 * Created by alex on 2/22/16.
 */
public interface BoostingStrategy {

    NetworkResult determineBoost(final NetworkResult term, final String[] result);
}
