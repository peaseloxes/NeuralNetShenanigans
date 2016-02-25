package kmeans.calc;

import java.util.*;
import kmeans.entities.Document;
import kmeans.entities.Term;

/**
 * Created by alex on 2/25/16.
 */
public class TermFrequencyCalculator {

    private final int dimensions;
    private List<Document> allDocuments;


    public TermFrequencyCalculator(final List<Document> documents, final int dimensions) {
        this.allDocuments = documents;
        this.dimensions = dimensions;
    }

    public Document calc(final Document document) {
        final List<String> words = document.words();
        Queue<Term> termFrequency = new PriorityQueue<>((Comparator<Term>) (o1, o2) -> {
            if (o1.getTermFrequency() > o2.getTermFrequency()) {
                return -1;
            }
            return 1;
        });
        for (String word : words) {
            termFrequency.add(new Term(word, tfidf(document, word)));
        }
        double[] vector = new double[dimensions];
        for (int i = 0; i < dimensions; i++) {
            vector[i] = termFrequency.poll().getTermFrequency();
        }
        document.setVector(vector);
        return document;
    }

//    Consider a document containing 100 words wherein the word cat appears 3 times.
// The term frequency (i.e., tf) for cat is then (3 / 100) = 0.03. Now, assume we have 10 million documents and the word cat appears in one thousand of these.
// Then, the inverse document frequency (i.e., idf) is calculated as log(10,000,000 / 1,000) = 4.
// Thus, the Tf-idf weight is the product of these quantities: 0.03 * 4 = 0.12.

    private double tfidf(final Document document, final String term) {
        double tf = termFrequency(document, term);
        double idf = inverseDocumentFrequency(term);
        return termFrequency(document, term) * inverseDocumentFrequency(term);
    }

    private double termFrequency(final Document document, final String term) {
        long termFrequency = 0;
        for (String word : document.words()) {
            if (word.equals(term)) {
                termFrequency++;
            }
        }
        return ((double) termFrequency / (double) document.words().size());
    }

    private double inverseDocumentFrequency(final String term) {
        long documentsContainingTerm = 0;
        for (Document document : allDocuments) {
            if (document.containsWord(term)) {
                documentsContainingTerm++;
            }
        }
        return (Math.log((double) allDocuments.size() / (double) documentsContainingTerm));
    }

}
