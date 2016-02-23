package network.processing.preparation.strategies;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import util.StringUtil;

/**
 * @author peaseloxes
 */
public class BasicCleaning implements CleaningStrategy {

    // TODO incomplete, replace with OpenNLP?
    private static final String[] LINE_ENDING_EXCEPTIONS = new String[]{"Mr.", "Mrs.", "Ms."};
    private static final String[] LINE_ENDINGS = new String[]{".", "?", "!", ".\"", ".\'", "?\"", "?\'", "!\"", "!\'"};

    private static final Logger LOGGER = LogManager.getLogger(BasicCleaning.class.getName());

    private static final int UPDATE_INTERVAL = 10000;

    @Override
    public List<String> scrub(final List<String> input) {
        LOGGER.info("Rearranging lines...");
        List<String> result = reformatLines(input);
        LOGGER.info("Performing text cleanup...");
        result = cleanUpText(result);
        return result;
    }

    /**
     * Rearranges lines so that each sentence is on a single line.
     *
     * @param input the lines to rearrange.
     * @return the rearranged lines.
     */
    protected List<String> reformatLines(final List<String> input) {
        final List<String> cleanedLines = new ArrayList<>(input.size());
        StrBuilder buffer = new StrBuilder();
        int intervalCounter = 0;
        int processedCounter = 0;
        // for each line
        for (final String s : input) {
            // add a whitespace in case the lines are improperly stored
            // excess will be removed at a later stage anyway
            buffer.append(s + " ");
            // check whether it contains an end of a sentence in the middle
            if (isMergedLine(buffer.toString())) {
                // this is a merged line, so split it
                // and append as separate sentences to the buffer
                List<String> splitLines = StringUtil.removeEmptyLines(splitLine(buffer.toString()));
                cleanedLines.add(splitLines.remove(0));
                buffer.clear();
                splitLines.forEach(buffer::append);
            }
            if (intervalCounter == UPDATE_INTERVAL) {
                processedCounter += intervalCounter;
                intervalCounter = 0;
                System.out.println(LocalDateTime.now() + " > " + processedCounter + "/" + input.size()  + " lines rearranged.");
            }
            intervalCounter++;
        }
        // append remaining buffer
        if (!"".equals(buffer.toString())) {
            cleanedLines.add(buffer.toString());
        }
        return cleanedLines;
    }

    private List<String> cleanUpText(final List<String> lines) {
        List<String> result = new ArrayList<>(lines.size());
        int intervalCounter = 0;
        int processedCounter = 0;
        for (final String line : lines) {

            // remove all non-letters
            String cleaned = StringUtil.removeNonLetters(line);

            // remove all redundant whitespace
            cleaned = StringUtil.convertToSingleWhitespace(cleaned);

            // convert all to lowercase
            cleaned = cleaned.toLowerCase();

            result.add(cleaned);
            if (intervalCounter == UPDATE_INTERVAL) {
                processedCounter += intervalCounter;
                intervalCounter = 0;
                System.out.println(LocalDateTime.now() + " > " + processedCounter + "/" + lines.size() +" lines cleaned.");
            }
            intervalCounter++;
        }
        return result;
    }

    private boolean isMergedLine(final String line) {
        // checks whether the line contains an element from LINE_ENDINGS
        // but only if it is not also an element in LINE_ENDING_EXCEPTIONS
        return StringUtil.containsRule(
                line,
                LINE_ENDINGS,
                LINE_ENDING_EXCEPTIONS,
                " "
        );
    }

    private List<String> splitLine(final String line) {
        // split the line on all proper line endings
        // but keep in mind the exceptions to line endings

        // the line does contain line ending exceptions, split with account for exceptions
        return StringUtil.splitOnAllExcept(line, LINE_ENDINGS, LINE_ENDING_EXCEPTIONS);
    }
}
