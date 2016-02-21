package util;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author peaseloxes
 */
public class StringUtilTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testContainsRule() throws Exception {
        String[] rules = new String[]{"a"};
        String[] exceptions = new String[]{"ab"};
        assertThat(StringUtil.containsRule("xasdas a asdasdas", rules, exceptions, " "), is(true));
        assertThat(StringUtil.containsRule("xasdas", rules, exceptions, " "), is(true));
        assertThat(StringUtil.containsRule("im ab", rules, exceptions, " "), is(false));
        assertThat(StringUtil.containsRule("im a ab", rules, exceptions, " "), is(true));

        rules = new String[]{"."};
        exceptions = new String[]{"Mr."};
        assertThat(StringUtil.containsRule("Mr. Rogers", rules, exceptions, " "), is(false));
        assertThat(StringUtil.containsRule("Mr Rogers.", rules, exceptions, " "), is(true));

        assertThat(StringUtil.containsRule("Me myself and I", new String[]{"myself"}), is(true));
    }

    @Test
    public void testSplitOnAll() throws Exception {
        String line = "I am, a sentence and should be. Split in 3.";
        assertThat(StringUtil.splitOnAll(line, new String[]{",", "."}).size(), is(3));
    }

    @Test
    public void testSplitOnAllExcept() throws Exception {
        String line = "I am, a sentence and should be. Split in 2.";
        assertThat(StringUtil.splitOnAllExcept(line, new String[]{",", "."}, new String[]{"am,"}).size(), is(2));

        line = "I am. A sentence split in two Mr. Rogers.";
        assertThat(StringUtil.splitOnAllExcept(line, new String[]{"."}, new String[]{"Mr."}).size(), is(2));
    }

    @Test
    public void testEndsWithExceptions() throws Exception {
        String validEnding = "I am a string.";
        String invalidEnding = "I a string am.";
        String[] rules = new String[]{"."};
        String[] exceptions = new String[]{"am."};
        assertThat(StringUtil.endsWith(validEnding, rules, exceptions), is(true));
        assertThat(StringUtil.endsWith(invalidEnding, rules, exceptions), is(false));
    }

    @Test
    public void testEndsWith() throws Exception {
        String validEnding = "I am a string.";
        String invalidEnding = "I am a string";
        String[] rules = new String[]{"."};
        assertThat(StringUtil.endsWith(validEnding, rules), is(true));
        assertThat(StringUtil.endsWith(invalidEnding, rules), is(false));
    }

    @Test
    public void testContains() throws Exception {
        assertThat(StringUtil.contains("woop", new String[]{"weep", "woop"}), is(true));
    }

    @Test
    public void testRemoveNonLetters() throws Exception {
        // note that `-` is a special case and will end up with extra whitespace
        assertThat(StringUtil.removeNonLetters("I am' a sentence."), is("I am a sentence"));
    }

    @Test
    public void testConvertToSingleWhitespace() throws Exception {
        assertThat(StringUtil.convertToSingleWhitespace("I am      a single  sentence."), is("I am a single sentence."));
    }
}