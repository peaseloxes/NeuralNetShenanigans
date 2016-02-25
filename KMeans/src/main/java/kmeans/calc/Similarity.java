package kmeans.calc;

import kmeans.Document;

/**
 * Created by alex on 2/24/16.
 */
public enum Similarity {
    INSTANCE;

    public static Double calc(final Document one, final Document other) {
        try {
            return one.dot(other) / (one.magnitude() * other.magnitude());
        } catch (ArithmeticException e) {
            return 0d;
        }
    }
}
