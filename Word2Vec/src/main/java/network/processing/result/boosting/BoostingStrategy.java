package network.processing.result.boosting;

import network.processing.result.NetworkResultItem;

/**
 * Created by alex on 2/22/16.
 */
public interface BoostingStrategy {

    NetworkResultItem determineBoost(final NetworkResultItem term, final String[] result);
}
