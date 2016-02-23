package network.wordvec;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author peaseloxes
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetworkResult {
    private String term;
    private double boost;
    private double similarity;

    public void addBoost(final int boost) {
        this.boost += boost;
    }
}
