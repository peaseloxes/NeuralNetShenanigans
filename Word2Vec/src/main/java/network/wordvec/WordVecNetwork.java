package network.wordvec;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import network.abs.Network;
import network.processing.preparation.DataPreProcessor;
import network.processing.result.NetworkResult;
import network.processing.result.NetworkResultItem;
import network.processing.result.boosting.DoubleBackBoosting;
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
        setBoostingStrategy(new DoubleBackBoosting(this));
    }

    @Override
    public void load(final String fileName) {
        LOGGER.info("Loading: " + fileName);
        try {
            word2Vec = WordVectorSerializer.loadTxtVectors(new File(IOUtil.getFullPathFromClasspath(fileName)));
        } catch (FileNotFoundException e) {
            LOGGER.error("Could not load the model from file.", e);
        }
        LOGGER.info("Loaded model.");
    }

    @Override
    public void loadBinary(final String outputFileName) {
        LOGGER.info("Loading: " + outputFileName);
        try {
            word2Vec = WordVectorSerializer.loadGoogleModel(new File(IOUtil.getFullPathFromClasspath(outputFileName)), true);
        } catch (IOException e) {
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
        SentenceIterator iter = new LineSentenceIterator(new File(IOUtil.getFullPathFromClasspath(dataFileName)));
        iter.setPreProcessor((SentencePreProcessor) String::toLowerCase);

        final EndingPreProcessor preProcessor = new EndingPreProcessor();
        TokenizerFactory tokenizer = new DefaultTokenizerFactory();
        tokenizer.setTokenPreProcessor(token -> {
            String base = preProcessor.preProcess(token);
            base = base.replaceAll("\\d", "d");
            // TODO stemming
            return base;
        });

        final List<String> stopWords = IOUtil.readLinesFromClasspath(getStopwordsFileName());

        Word2Vec word2Vec = new Word2Vec.Builder()
                .batchSize(getBatchSize()) //# words per minibatch.
                .minWordFrequency(getMinWordOccurrence()) //
                .useAdaGrad(false) //
                .layerSize(getNumVectors()) // word feature vector size
                .iterations(getIterations()) // # iterations to train
                .epochs(getEpochs())
                .stopWords(stopWords)
                .learningRate(getLearningRate()) //
                .minLearningRate(getMinLearningRate()) // learning rate decays wrt # words. floor learning
                .negativeSample(getNegativeSample()) // sample size 10 words
                .iterate(iter) //
                .tokenizerFactory(tokenizer)
                .build();
        LOGGER.info("Training model...");
        word2Vec.fit();
        LOGGER.info("Saving model as: " + saveFileName);
        try {
            WordVectorSerializer.writeWordVectors(word2Vec, IOUtil.getFullPathFromClasspath(saveFileName));
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
    public NetworkResult suggestionsFor(final String term) {
        if (word2Vec == null) {
            throw new IllegalStateException("No network available. Please use load() to load a network model.");
        }
        NetworkResult result = new NetworkResult();
        Collection<String> responses = word2Vec.wordsNearest(term, 10);
        for (final String response : responses) {
            NetworkResultItem networkResultItem = new NetworkResultItem(response, 0, word2Vec.similarity(term, response));
            getBoostingStrategy().determineBoost(networkResultItem, responses.toArray(new String[]{}));
            result.add(networkResultItem);
        }
        return result;
    }

    @Override
    public NetworkResult suggestionsForNoBoost(final String term) {
        if (word2Vec == null) {
            throw new IllegalStateException("No network available. Please use load() to load a network model.");
        }
        NetworkResult result = new NetworkResult();
        Collection<String> responses = word2Vec.wordsNearest(term, 10);
        for (final String response : responses) {
            NetworkResultItem networkResultItem = new NetworkResultItem(response, 0, word2Vec.similarity(term, response));
            result.add(networkResultItem);
        }
        return result;
    }
}
