package network.processing.preparation.strategies;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.apache.commons.lang.text.StrBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import util.IOUtil;

/**
 * Created by alex on 2/22/16.
 */
public class OpenNLPCleaning extends BasicCleaning {
    private static final Logger LOGGER = LogManager.getLogger(OpenNLPCleaning.class.getName());

    @Override
    public List<String> reformatLines(final List<String> input) {

        final SentenceModel model;
        final SentenceDetectorME sentenceDetector;
        try {
            // TODO improve
            model = new SentenceModel(new BufferedInputStream(new FileInputStream(IOUtil.getFullPathFromClasspath("model/en-sent.bin"))));
            sentenceDetector = new SentenceDetectorME(model);
            final StrBuilder strBuilder = new StrBuilder(input.size());
            input.forEach(strBuilder::append);
            final String superDuperLongString = strBuilder.toString();
            return Arrays.asList(sentenceDetector.sentDetect(superDuperLongString));
        } catch (IOException e) {
            LOGGER.error("Could not load sentence model. ",e);
        }
        LOGGER.error("Failed to apply reformatting to lines.");
        return input;
    }


}
