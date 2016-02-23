package main;

import java.io.IOException;

/**
 * @author peaseloxes
 */
public class Runner {
//    public static void main(String[] args) {
//        Documents documents = new Documents();
//        documents.setDocumentList(Arrays.asList(new Document[]{new Document("this is a document")}));
//        new Vectorizer().vectorize(documents);
//    }

    public static void main(String[] args) throws IOException {
//        int iterations = 100;
//        Nd4j.dtype = DataBuffer.Type.DOUBLE;
//        Nd4j.factory().setDType(DataBuffer.Type.DOUBLE);
//        List<String> cacheList = new ArrayList<>();
//
//        System.out.println("Load & Vectorize data....");
////        File wordFile = new ClassPathResource("model.txt").getFile();
//        File wordFile = new File(IOUtil.getFullPathFromClasspath("model/model.txt"));
//        Pair<InMemoryLookupTable,VocabCache> vectors = WordVectorSerializer.loadTxt(wordFile);
//        VocabCache cache = vectors.getSecond();
//        INDArray weights = vectors.getFirst().getSyn0();
//
//        for(int i = 0; i < cache.numWords(); i++)
//            cacheList.add(cache.wordAtIndex(i));
//
//        System.out.println("Build model....");
//        BarnesHutTsne tsne = new BarnesHutTsne.Builder()
//                .setMaxIter(iterations).theta(0.5)
//                .normalize(false)
//                .learningRate(500)
//                .useAdaGrad(false)
//                .usePca(false)
//                .build();
//
//        System.out.println("Store TSNE Coordinates for Plotting....");
//        String outputFile = "target/archive-tmp/tsne-standard-coords.csv";
//        (new File(outputFile)).getParentFile().mkdirs();
//        tsne.plot(weights,2,cacheList,outputFile);
    }
}
