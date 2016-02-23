package network.processing.result;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author peaseloxes
 */
public class NetworkResultTest {

    private NetworkResult result = new NetworkResult();

    @Before
    public void setUp() {
        result.add(new NetworkResultItem("three",10.0,0));
        result.add(new NetworkResultItem("one",100.0,0));
        result.add(new NetworkResultItem("two",50.0,0));
    }

    @Test
    public void testSortByBoost() throws Exception {
        result.setSort(NetworkResult.SortBy.BOOST_ASC);
        List<NetworkResultItem> items = result.sortByBoost();
        assertThat(items.get(0).getTerm(), is("three"));
        assertThat(items.get(1).getTerm(), is("two"));
        assertThat(items.get(2).getTerm(), is("one"));

        result.setSort(NetworkResult.SortBy.BOOST_DESC);
        items = result.sortByBoost();
        assertThat(items.get(0).getTerm(), is("one"));
        assertThat(items.get(1).getTerm(), is("two"));
        assertThat(items.get(2).getTerm(), is("three"));
    }
}