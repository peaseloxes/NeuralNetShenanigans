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
public class WordVecResult {
    private String term;
    private double boost;
    private double similarity;
}
