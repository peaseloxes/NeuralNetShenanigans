package kmeans.cluster;

import java.util.*;
import kmeans.Document;
import kmeans.calc.Similarity;

/**
 * @author peaseloxes
 */
public class KMeansClustering {
    private int globalCounter;

    public List<Centroid> prepareCluster(final int numClusters, final List<Document> documents) {
        globalCounter = 0;
        List<Centroid> centroids = new ArrayList<>(numClusters);
        Set<Integer> randomNumbers = new HashSet<>();
        do {
            randomNumbers.add(new Random().nextInt(documents.size()));
        } while (randomNumbers.size() < numClusters);
        assert centroids.size() == numClusters;
        for (Integer randomNumber : randomNumbers) {
            Centroid centroid = new Centroid();
            centroid.getDocumentList().add(documents.get(randomNumber));
            centroids.add(centroid);
        }
        boolean stop;
        List<Centroid> result = initializeClusterCentroid(numClusters);
        List<Centroid> prevResult = new ArrayList<>();

        do {
            prevResult = centroids;
            for (Document document : documents) {
                int index = findClosestCentroid(centroids, document);
                result.get(index).getDocumentList().add(document);
            }
            centroids = initializeClusterCentroid(numClusters);
            centroids = calculateMeanPoints(result);
            stop = checkStopConditions(prevResult, centroids);
        } while (!stop);
        globalCounter++;
        return result;
    }

    private boolean checkStopConditions(final List<Centroid> prevResult, final List<Centroid> centroids) {
        globalCounter++;
        if (globalCounter > 11000) {
            return true;
        }
        boolean stop = false;
        int[] changeIndex = new int[centroids.size()];
        int index = 0;
        do {
            int count = 0;
            if (centroids.get(index).getDocumentList().size() == 0 && prevResult.get(index).getDocumentList().size() == 0) {
                index++;
            } else if (centroids.get(index).getDocumentList().size() != 0 && prevResult.get(index).getDocumentList().size() != 0) {
                for (int i = 0; i < centroids.get(index).getDocumentList().get(0).getVectorSpace().length; i++) {
                    if (centroids.get(index).getDocumentList().get(0).getVectorSpace()[i] == prevResult.get(index).getDocumentList().get(0).getVectorSpace()[i]) {
                        count++;
                    }
                }
                if (count == centroids.get(index).getDocumentList().get(0).getVectorSpace().length) {
                    changeIndex[index] = 0;
                } else {
                    changeIndex[index] = 1;
                }
                index++;
            } else {
                index++;
            }
        } while (index < centroids.size());
        return !Arrays.asList(changeIndex).contains(1);
    }

    private List<Centroid> calculateMeanPoints(final List<Centroid> centroids) {
        for (Centroid centroid : centroids) {
            if (centroid.getDocumentList().size() > 0) {
                for (int i = 0; i < centroid.getDocumentList().get(0).getVectorSpace().length; i++) {
                    double total = 0;
                    for (Document document : centroid.getDocumentList()) {
                        total += document.getVectorSpace()[i];
                    }
                    centroid.getDocumentList().get(0).getVectorSpace()[i] = total / centroid.getDocumentList().size();
                }
            }
        }
        return centroids;
    }

    private int findClosestCentroid(final List<Centroid> centroids, final Document document) {
        Double[] similarityCluster = new Double[centroids.size()];
        for (int i = 0; i < centroids.size(); i++) {
            similarityCluster[i] = Similarity.calc(centroids.get(i).getDocumentList().get(0), document);
        }
        int index = 0;
        double max = similarityCluster[0];
        for (int i = 0; i < similarityCluster.length; i++) {
            if (similarityCluster[i] > max) {
                max = similarityCluster[i];
                index = i;
            }
        }
        return index;
    }

    private List<Centroid> initializeClusterCentroid(final int size) {
        List<Centroid> newList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Centroid centroid = new Centroid();
            newList.add(centroid);
        }
        return newList;
    }
}
