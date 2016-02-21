package network.processing.preparation.strategies;

import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author peaseloxes
 */
public class BasicCleaningTest {

    private List<String> testData;
    private BasicCleaning basicCleaning;

    @Before
    public void setUp() {
        testData = new ArrayList<>();
        basicCleaning = new BasicCleaning();
    }

    @Test
    public void testScrub() throws Exception {
        testData.add("I am a sentEnce!");
        testData.add("Do not mistake me for a fool Mr. T.");
        testData.add("Or ~~ I ' might-get");
        testData.add(" upset.");
        List<String> result = basicCleaning.scrub(testData);
        assertThat(result.get(0), is("i am a sentence"));
        assertThat(result.get(1), is("do not mistake me for a fool mr t"));
        assertThat(result.get(2), is("or i might get upset"));
    }

    @Test
    public void testReformatLines() throws Exception {
        testData.add("I am a sentence, ");
        testData.add("and I continue on the next line.");
        testData.add("I'm just a single sentence!");
        testData.add("I am the next line, and I contain the word Mr. ");
        testData.add("which is not a line ending and therefore I should be ");
        testData.add("merged as well.");
        testData.add("All this should result in 5 lines. Because this one is actually 2 in 1.");
        List<String> result = basicCleaning.reformatLines(testData);
        assertThat(result.size(), is(5));

        testData.clear();
        testData.add("I am a sentence.");
        assertThat(basicCleaning.reformatLines(testData).size(), is(1));
    }
}