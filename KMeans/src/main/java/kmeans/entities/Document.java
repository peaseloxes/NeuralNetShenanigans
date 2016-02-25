package kmeans.entities;

import java.util.Arrays;
import java.util.List;
import lombok.Data;

/**
 * @author peaseloxes
 */
@Data
public class Document {

    /**
     * The content of this document.
     */
    private String content;

    /**
     * The Inverted Term Frequency index.
     */
    private double[] vector;

    public Document(final String content) {
        this.content = content;
    }

    public List<String> words() {
        return Arrays.asList(content.split(" "));
    }

    public boolean containsWord(final String term) {
        return content.contains(term);
    }
}
