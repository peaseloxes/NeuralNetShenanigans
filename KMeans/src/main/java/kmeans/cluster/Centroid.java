package kmeans.cluster;

import java.util.ArrayList;
import java.util.List;
import kmeans.Document;
import lombok.Data;

/**
 * Created by alex on 2/24/16.
 */
@Data
public class Centroid {
    private List<Document> documentList = new ArrayList<>();
}
