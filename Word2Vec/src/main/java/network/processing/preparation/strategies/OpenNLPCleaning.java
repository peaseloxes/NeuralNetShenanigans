package network.processing.preparation.strategies;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import org.apache.commons.lang.text.StrBuilder;
import util.IOUtil;

/**
 * Created by alex on 2/22/16.
 */
public class OpenNLPCleaning extends BasicCleaning {

    @Override
    public List<String> reformatLines(final List<String> input) {

        SentenceModel model;
        SentenceDetectorME sentenceDetector;
        try {
            // TODO improve
            model = new SentenceModel(new BufferedInputStream(new FileInputStream(IOUtil.getFullPath("model/en-sent.bin"))));
            sentenceDetector = new SentenceDetectorME(model);
            final StrBuilder strBuilder = new StrBuilder(input.size());
            input.forEach(strBuilder::append);
            final String superDuperLongString = strBuilder.toString();
            return Arrays.asList(sentenceDetector.sentDetect(superDuperLongString));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO proper
        return new ArrayList<>();
    }


}
