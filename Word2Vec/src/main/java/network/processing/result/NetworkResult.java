package network.processing.result;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import network.processing.result.comparators.BoostASCComparator;
import network.processing.result.comparators.BoostDESCComparator;

/**
 * @author peaseloxes
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetworkResult {
    private SortBy sort = SortBy.BOOST_DESC;
    private String queryTerm;
    private List<NetworkResultItem> resultItems = new ArrayList<>();

    public int size() {
        return resultItems.size();
    }

    public void add(final NetworkResultItem itemToAdd) {
        resultItems.add(itemToAdd);
    }

    public void clear() {
        resultItems.clear();
    }

    public List<NetworkResultItem> sortByBoost() {
        return resultItems.parallelStream().sorted(sort.comparator).collect(Collectors.toList());
    }

    public void add(final String term, final double boost, final double similarity) {
        resultItems.add(new NetworkResultItem(term, boost, similarity));
    }

    public enum SortBy {
        BOOST_ASC(new BoostASCComparator()),
        BOOST_DESC(new BoostDESCComparator());

        private final Comparator<NetworkResultItem> comparator;

        SortBy(final Comparator<NetworkResultItem> comparator) {
            this.comparator = comparator;
        }
    }


}
