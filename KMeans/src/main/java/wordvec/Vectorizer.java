package wordvec;

import java.util.Arrays;
import java.util.List;
import kmeans.Document;
import kmeans.Documents;
import org.deeplearning4j.bagofwords.vectorizer.BagOfWordsVectorizer;
import org.deeplearning4j.bagofwords.vectorizer.TextVectorizer;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.EndingPreProcessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import util.IOUtil;

/**
 * @author peaseloxes
 */
public class Vectorizer {

    public void vectorize(final Documents documents) {
        // TODO hide behind abstraction

        final EndingPreProcessor preProcessor = new EndingPreProcessor();
        TokenizerFactory tokenizer = new DefaultTokenizerFactory();
        tokenizer.setTokenPreProcessor(token -> {
            String base = preProcessor.preProcess(token);
            base = base.replaceAll("\\d", "d");
            // TODO stemming
            return base;
        });

        final List<String> stopWords = IOUtil.readLinesFromClasspath("model/stopwords.txt");

        TextVectorizer textVectorizer = new BagOfWordsVectorizer.Builder()
                .stopWords(stopWords)
                .batchSize(100)
                .labels(Arrays.asList("woop","di","doo"))
                .tokenize(tokenizer)
                .build();

        for (final Document document : documents.getDocumentList()) {
            System.out.println(textVectorizer.vectorize(document.getContent(), "di"));
        }

    }
}
