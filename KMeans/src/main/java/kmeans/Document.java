package kmeans;

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
    private double[] vectorSpace;

    public Document(final String content){
        this.content = content;
    }
}
