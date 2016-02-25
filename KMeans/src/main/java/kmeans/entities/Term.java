package kmeans.entities;

import lombok.Data;

/**
 * Created by alex on 2/25/16.
 */
@Data
public class Term {

    private final String word;
    private final double termFrequency;

    public Term(final String word, final double termFrequency) {
        this.word = word;
        this.termFrequency = termFrequency;
    }
}
