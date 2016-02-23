package network.processing.result.comparators;

import java.util.Comparator;
import network.processing.result.NetworkResultItem;

/**
 * @author peaseloxes
 */
public class BoostDESCComparator implements Comparator<NetworkResultItem> {
    @Override
    public int compare(final NetworkResultItem o1, final NetworkResultItem o2) {
        if (o1.getBoost() > o2.getBoost()) {
            return -1;
        }
        return 1;
    }
}
