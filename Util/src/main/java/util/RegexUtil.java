package util;

/**
 * @author peaseloxes
 */
public enum RegexUtil {
    INSTANCE;
    /**
     * Should match characters like ' " ; ~ etc.
     * TODO but does it?
     */
    public static final String NOT_A_UNICODE_LETTER = "[\\p{P}\\p{C}\\p{S}]*";

    /**
     * Should match one or more whitespaces.
     */
    public static final String ONE_OR_MORE_WHITESPACES = " +";

    public static String regexifyString(final String string) {
        if (".".equals(string)) {
            return "\\.";
        }
        if ("?".equals(string)) {
            return "\\?";
        }
        return string;
    }


}
