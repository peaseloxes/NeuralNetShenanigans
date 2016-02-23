package util;

import java.util.*;
import org.apache.commons.lang.text.StrBuilder;

/**
 * @author peaseloxes
 */
public enum StringUtil {
    INSTANCE;

    /**
     * Shorthand for checking whether a sentence contains any of the rules.
     * <p>
     * Sentence will be split on space and there are no exceptions to the rules.
     *
     * @param sentence the sentence to check.
     * @param rules    the rules to check for.
     * @return true if a rule applies, false otherwise.
     */
    public static boolean containsRule(final String sentence, final String[] rules) {
        return containsRule(sentence, rules, new String[]{}, " ");
    }

    /**
     * Check if a sentence contains a specific rule, but the match may not be one of exception.
     *
     * @param sentence   the sentence to check.
     * @param rules      the rules to search for.
     * @param exceptions the exceptions to ignore.
     * @param splitOn    the character(s) to split the line on, may be regex.
     * @return true if rule is present, false otherwise.
     */
    public static boolean containsRule(final String sentence, final String[] rules, final String[] exceptions, final String splitOn) {
        String[] words = sentence.split(splitOn);
        for (final String word : words) {
            // if the word is not an exception
            if (!contains(word, exceptions)) {
                // and a rule applies
                if (contains(word, rules)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks whether a string array contains a certain string.
     *
     * @param string the string to find.
     * @param array  the array to look in.
     * @return true if present, false otherwise.
     */
    public static boolean contains(final String string, final String[] array) {
        for (final String s : array) {
            if (string.contains(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a string ends on any of the elements in the array.
     *
     * @param string the string to check.
     * @param array  the elements to check for.
     * @return true if ends on one of array, false otherwise.
     */
    public static boolean endsWith(final String string, final String[] array) {
        for (final String s : array) {
            if (string.endsWith(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a string ends on an element in the array disregarding any element in exceptions.
     *
     * @param string     the string to check.
     * @param array      the elements to check for.
     * @param exceptions the exceptions to disregard if a match with array.
     * @return true if ends on one of array, false otherwise.
     */
    public static boolean endsWith(final String string, final String[] array, final String[] exceptions) {
        for (final String s : array) {
            if (string.endsWith(s)) {
                for (final String exception : exceptions) {
                    if (!string.endsWith(exception)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Splits a line on all occurrences.
     *
     * @param line       the line to split.
     * @param delimiters elements to split on.
     * @return a list of split lines.
     */
    public static List<String> splitOnAll(final String line, final String[] delimiters) {
        List<String> splitLines = new ArrayList<>();
        splitLines.add(line);
        for (final String delimiter : delimiters) {
            splitLines = splitOnOne(splitLines, delimiter);
        }
        return splitLines;
    }

    /**
     * Splits a line on one occurrence.
     *
     * @param lines     the lines to split.
     * @param delimiter the delimiter to split on, may be regex.
     * @return the split line.
     */
    public static List<String> splitOnOne(final List<String> lines, final String delimiter) {
        List<String> splitLines = new ArrayList<>();
        for (final String line : lines) {
            String[] parts = line.split(RegexUtil.regexifyString(delimiter));
            splitLines.addAll(Arrays.asList(parts));
        }
        return splitLines;
    }

    /**
     * Splits a line on all delimiters except if it's part of the exceptions.
     *
     * @param line                the line to split.
     * @param delimiters          the delimiters to split on.
     * @param delimiterExceptions the exceptions to the above rule.
     * @return the split line.
     */
    public static List<String> splitOnAllExcept(final String line, final String[] delimiters, final String[] delimiterExceptions) {
        String[] words = line.split(" ");
        StrBuilder buffer = new StrBuilder();
        for (final String delimiter : delimiters) {
            buffer.clear();
            for (final String word : words) {
                buffer.append(word);
                buffer.append(" ");
                // if the word is delimitable and not an exception
                if (word.endsWith(delimiter) && !contains(word, delimiterExceptions)) {
                    buffer.append("\\n");
                }
            }
        }
        // all your backslash are belong to us
        return Arrays.asList(buffer.toString().split("\\\\n"));
    }

    /**
     * Removes all non letters from a line.
     *
     * @param line the line to clean.
     * @return the lines without non-letter characters.
     */
    public static String removeNonLetters(final String line) {
        String response = line;

        // TODO make generic solution for characters that should be wrapped in whitespace
        // if it contains a dash, add spaces around it
        // this way we won't end up with contracted words
        response = response.replaceAll("-", " - ");

        // remove all non letters
        response = response.replaceAll(RegexUtil.NOT_A_UNICODE_LETTER, "");

        return response;
    }

    /**
     * Replaces all multiple spaces with a single whitespace.
     *
     * @param string the string to convert.
     * @return the string stripped of excessive whitespace.
     */
    public static String convertToSingleWhitespace(final String string) {
        return string.trim().replaceAll(RegexUtil.ONE_OR_MORE_WHITESPACES, " ");
    }

    /**
     * Removes all empty lines from a list of strings.
     *
     * @param strings the list of strings.
     * @return a list with empty lines removed.
     */
    public static List<String> removeEmptyLines(final List<String> strings) {
        List<String> input = new LinkedList<>(strings);
        Iterator<String> it = input.iterator();
        while (it.hasNext()) {
            String line = it.next();
            if ("".equals(line) || line.matches(RegexUtil.ONE_OR_MORE_WHITESPACES)) {
                it.remove();
            }
        }
        return input;
    }
}
