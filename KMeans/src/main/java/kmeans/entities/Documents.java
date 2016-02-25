package kmeans.entities;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 * @author peaseloxes
 */
@Data
public class Documents {
    List<Document> documentList = new ArrayList<>();

    public void addDocument(final Document document) {
        documentList.add(document);
    }
}
