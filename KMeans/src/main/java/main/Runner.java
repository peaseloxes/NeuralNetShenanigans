package main;

import java.io.IOException;
import java.util.List;
import kmeans.calc.TermFrequencyCalculator;
import kmeans.cluster.Centroid;
import kmeans.cluster.KMeansClustering;
import kmeans.entities.Document;
import kmeans.entities.Documents;

/**
 * @author peaseloxes
 */
public class Runner {

    public static void main(String[] args) throws IOException {
        Documents documents = new Documents();
        documents.addDocument(new Document("Sherlock is a detective who finds people"));
        documents.addDocument(new Document("Watson is a doctor who heals people"));
        documents.addDocument(new Document("Lestrade is a policemen"));

        TermFrequencyCalculator calculator = new TermFrequencyCalculator(documents.getDocumentList(), 3);
        for (Document document : documents.getDocumentList()) {
            calculator.calc(document);
        }

        List<Centroid> result = new KMeansClustering().run(documents.getDocumentList(), 3);
        for (Centroid centroid : result) {
            System.out.println(centroid.getDocumentList());
        }

    }
}
