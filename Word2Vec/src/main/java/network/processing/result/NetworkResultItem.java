package network.processing.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author peaseloxes
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetworkResultItem {
    private String term;
    private double boost;
    private double similarity;

    public void addBoost(final int boost) {
        this.boost += boost;
    }

    @Override
    public String toString() {
        return "[" + term + ", b: " + boost + ", s: " + similarity + "]";
    }
}
