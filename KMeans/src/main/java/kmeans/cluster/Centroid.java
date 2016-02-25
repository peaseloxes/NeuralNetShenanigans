package kmeans.cluster;

import java.util.ArrayList;
import java.util.List;
import kmeans.entities.Document;
import lombok.Data;

/**
 * Created by alex on 2/24/16.
 */
@Data
public class Centroid {
    private List<Document> documentList = new ArrayList<>();
    private double[] vector;

    public Centroid(final double[] vector){
        this.vector = vector;
    }

    public void clearDocuments() {
        documentList = new ArrayList<>();
    }

    public void addDocument(final Document document) {
        documentList.add(document);
    }

    public boolean hasDocuments() {
        return !documentList.isEmpty();
    }

    public int getDocAmount() {
        return documentList.size();
    }
}
