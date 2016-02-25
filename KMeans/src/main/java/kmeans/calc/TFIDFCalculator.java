package kmeans.calc;

import java.util.Arrays;
import java.util.List;
import kmeans.Document;
import kmeans.Documents;

/**
 * Created by alex on 2/24/16.
 */
public enum TFIDFCalculator {
    INSTANCE;

    public static Double calc(final Documents documents, final Document document, final String term) {
        return (termFrequency(document, term) * inverseIndex(documents, term));
    }

    private static Double termFrequency(final Document document, final String term) {
        long termCounter = 0;
        List<String> words = Arrays.asList(document.getContent().split(" "));
        for (String word : words) {
            if (word.toLowerCase().equals(term.toLowerCase())) {
                termCounter++;
            }
        }
        return ((double) termCounter / words.size());
    }

    private static Double inverseIndex(final Documents documents, final String term) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (Document document : documents.getDocumentList()) {
            stringBuilder.append(document.getContent());
            stringBuilder.append(" ");
        }
        long termCounter = 0;
        List<String> words = Arrays.asList(stringBuilder.toString().split(" "));
        for (String word : words) {
            if (word.toLowerCase().equals(term.toLowerCase())) {
                termCounter++;
            }
        }
        return Math.log(words.size() / termCounter);
    }
}
