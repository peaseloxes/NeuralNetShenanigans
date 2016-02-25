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

    public Document(final String content) {
        this.content = content;
    }

    // return the inner product of this Vector a and b
    public double dot(Document that) {
        if (this.vectorSpace.length != that.vectorSpace.length) {
            throw new RuntimeException("Dimensions don't agree");
        }
        double sum = 0.0;
        for (int i = 0; i < getVectorSpace().length; i++) {
            sum = sum + (this.vectorSpace[i] * that.vectorSpace[i]);
        }
        return sum;
    }

    // return the Euclidean norm of this Vector
    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }
}
