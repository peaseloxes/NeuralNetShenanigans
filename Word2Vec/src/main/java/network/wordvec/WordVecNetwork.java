package network.wordvec;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import network.abs.Network;
import network.processing.preparation.DataPreProcessor;
import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentencePreProcessor;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.EndingPreProcessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import util.IOUtil;

/**
 * @author peaseloxes
 */
public class WordVecNetwork extends Network {

    private WordVectors word2Vec;

    /**
     * Set some default values for lazy users.
     */
    public WordVecNetwork() {
        setDataPreProcessor(new DataPreProcessor());
    }

    @Override
    public void load(final String fileName) {
        LOGGER.info("Loading: " + fileName);
        try {
            word2Vec = WordVectorSerializer.loadTxtVectors(new File(IOUtil.getFullPath(fileName)));
        } catch (FileNotFoundException e) {
            LOGGER.error("Could not load the model from file.", e);
        }
        LOGGER.info("Loaded model.");
    }

    @Override
    public void save(final String fileName) {
        LOGGER.error("Saved:" + fileName);
    }

    @Override
    public void create(final String dataFileName, final String saveFileName) {
        LOGGER.info("Creating model as: " + dataFileName);

        // TODO hide behind abstraction
        SentenceIterator iter = new LineSentenceIterator(new File(IOUtil.getFullPath(dataFileName)));
        iter.setPreProcessor((SentencePreProcessor) String::toLowerCase);

        final EndingPreProcessor preProcessor = new EndingPreProcessor();
        TokenizerFactory tokenizer = new DefaultTokenizerFactory();
        tokenizer.setTokenPreProcessor(token -> {
            String base = preProcessor.preProcess(token);
            base = base.replaceAll("\\d", "d");
            // TODO stemming
            return base;
        });

        // TODO make settings variable
        Word2Vec word2Vec = new Word2Vec.Builder()
                .batchSize(5) //# words per minibatch.
                .minWordFrequency(5) //
                .useAdaGrad(false) //
                .layerSize(150) // word feature vector size
                .iterations(1) // # iterations to train
                .epochs(1)
                .learningRate(0.025) //
                .minLearningRate(1e-3) // learning rate decays wrt # words. floor learning
                .negativeSample(10) // sample size 10 words
                .iterate(iter) //
                .tokenizerFactory(tokenizer)
                .build();
        LOGGER.info("Training model...");
        word2Vec.fit();
        LOGGER.info("Saving model as: " + saveFileName);
        try {
            WordVectorSerializer.writeWordVectors(word2Vec, IOUtil.getFullPath(saveFileName));
        } catch (IOException e) {
            LOGGER.error("Could not write the model to file.", e);
        }
        LOGGER.info("Model saved.");
    }

    @Override
    public void prepareData(final String rawInputFileName, final String cleanedInputFileName) {
        LOGGER.info("Preparing: " + rawInputFileName);
        getDataPreProcessor().process(rawInputFileName, cleanedInputFileName);
        LOGGER.info("Saved data as: " + cleanedInputFileName);
    }

    @Override
    public Map<String, Double> suggestionsFor(final String term) {
        if (word2Vec == null) {
            throw new IllegalStateException("No network available. Please use load() to load a network model.");
        }
        Map<String, Double> responseMap = new LinkedHashMap<>();
        List<WordVecResult> result = new ArrayList<>();
        // TODO proper boosting implementation
        double boost = 0;
        for (final String response : word2Vec.wordsNearest(term, 10)) {
            result.add(new WordVecResult(response, boost++, word2Vec.similarity(term, response)));
        }
        result.parallelStream()
                .sorted(Comparator.comparing(WordVecResult::getBoost).reversed())
                .forEachOrdered(r -> responseMap.put(r.getTerm(), r.getBoost()));
        return responseMap;
    }
}
