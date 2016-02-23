package network.processing.result;

import network.abs.Network;
import network.processing.result.boosting.DoubleBackBoosting;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;

/**
 * @author peaseloxes
 */
public class DoubleBackBoostingTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private DoubleBackBoosting doubleBackBoosting;
    private Network mockNetwork;

    private NetworkResultItem term;
    private NetworkResult response;
    private NetworkResult noBoostResponse;

    @Before
    public void setUp() {
        mockNetwork = Mockito.mock(Network.class);
        response = new NetworkResult();
        noBoostResponse = new NetworkResult();
        Mockito.when(mockNetwork.suggestionsFor(anyString())).thenReturn(response);
        Mockito.when(mockNetwork.suggestionsForNoBoost(anyString())).thenReturn(noBoostResponse);
        doubleBackBoosting = new DoubleBackBoosting(mockNetwork);
    }

    @Test
    public void testDetermineBoost() throws Exception {
        term = new NetworkResultItem("foo",0,1.1);
        response.add("bar",0,1.1);
        response.add("woop",0,1.1);

        noBoostResponse.add("foo",0,1.1);
        noBoostResponse.add("bar",0,1.1);

        double expectedBoostForFoo = 4.0;
        assertThat(doubleBackBoosting.determineBoost(term, new String[]{"bar","woop"}).getBoost(), is(expectedBoostForFoo));
    }

    @Test
    public void testDetermineBoostNetworkNull() throws Exception {
        NetworkResultItem result = new NetworkResultItem("", 0, 0);
        assertThat(new DoubleBackBoosting(null).determineBoost(result, new String[]{}).getBoost(), is(result.getBoost()));
    }
}