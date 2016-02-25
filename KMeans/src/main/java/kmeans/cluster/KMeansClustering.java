package kmeans.cluster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kmeans.entities.Document;

/**
 * @author peaseloxes
 */
public class KMeansClustering {
    private static final int MAX_RUNS = 11000;
    private int totalCounter = 0;
    private List<Centroid> oldCentroids = new ArrayList<>();

    //    1. Randomly choose k items and make them as initial centroids.
    //    2. For each point, find the nearest centroid and assign the point to the cluster associated with the nearest centroid.
    //    3. Update the centroid of each cluster based on the items in that cluster. Typically, the new centroid will be the average of all points in the cluster.
    //    4. Repeats steps 2 and 3, till no point switches clusters.
    public List<Centroid> run(final List<Document> documents, final int numClusters) {
        totalCounter = 0;
        assert numClusters < documents.size();

        // 1. Randomly choose k items and make them as initial centroids.
        List<Centroid> centroids = initializeCentroids(documents, numClusters);

        boolean stop = false;
        while (!stop) {

            // 2. For each point,
            for (Centroid centroid : centroids) {
                centroid.clearDocuments();
                for (Document document : documents) {
                    // find the nearest centroid and assign the point to the cluster associated with the nearest centroid.
                    if (centroid == nearestCentroid(document, centroids)) {
                        centroid.addDocument(document);
                    }
                }

            }

            // 3. Update the centroid of each cluster based on the items in that cluster. Typically, the new centroid will be the average of all points in the cluster.
            for (Centroid centroid : centroids) {
                Centroid updatedCentroid = updateCentroid(centroid);
                assert updatedCentroid != null;
                centroid.setVector(updatedCentroid.getVector());
            }
            // update globals
            totalCounter++;
            oldCentroids = centroids;

            // 4. Repeats steps 2 and 3, till no point switches clusters.
            stop = areWeThereYet(centroids);
        }

        return centroids;
    }

    private Centroid updateCentroid(final Centroid centroid) {
        if (!centroid.hasDocuments()) {
            return centroid;
        }
        int dimensionSize = centroid.getDocumentList().get(0).getVector().length;
        double[] newVector = new double[dimensionSize];

        for (Document document : centroid.getDocumentList()) {
            double[] docVector = document.getVector();
            for (int i = 0; i < dimensionSize; i++) {
                newVector[i] += docVector[i];
            }
        }
        for (int i = 0; i < newVector.length; i++) {
            newVector[i] = newVector[i] / centroid.getDocAmount();
        }
        centroid.setVector(newVector);
        return centroid;
    }

    private Centroid nearestCentroid(final Document document, final List<Centroid> centroids) {
        double minDistance = Double.POSITIVE_INFINITY;
        Centroid closestCentroid = null;
        for (Centroid centroid : centroids) {
            double distance = distance(centroid.getVector(), document.getVector());
            if (distance < minDistance) {
                minDistance = distance;
                closestCentroid = centroid;
            }
        }
        return closestCentroid;
    }

    private boolean areWeThereYet(final List<Centroid> centroids) {
        if (totalCounter > MAX_RUNS) {
            return true;
        }
        assert oldCentroids.size() == centroids.size();
        for (Centroid oldCentroid : oldCentroids) {
            for (Centroid centroid : centroids) {
                if ( Arrays.equals(oldCentroid.getVector(), centroid.getVector())) {
                   return false;
                }
            }
        }
        return true;
    }

    private List<Centroid> initializeCentroids(final List<Document> documents, final int numClusters) {
        // just pick first documents
        List<Centroid> centroids = new ArrayList<>(numClusters);
        for (int i = 0; i < numClusters; i++) {
            centroids.add(new Centroid(documents.get(i).getVector()));
        }
        return centroids;
    }

//    private double distance(final Document x, final Document y) {
//        return distance(x.getVector(), y.getVector());
//    }

    private double distance(double[] x, double[] y) {
        double sumXY2 = 0.0;
        for (int i = 0, n = x.length; i < n; i++) {
            sumXY2 += Math.pow(x[i] - y[i], 2);
        }
        return Math.sqrt(sumXY2);
    }
}
