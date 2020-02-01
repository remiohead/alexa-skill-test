package net.remiohead.alexa.skill.test;

import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;

import java.util.HashSet;
import java.util.Set;

public class MainSpeechletRequestStreamHandler extends SpeechletRequestStreamHandler {
    private static final Set<String> supportedApplicationIds = new HashSet<String>();
    static {
        /*
         * This Id can be found on https://developer.amazon.com/edw/home.html#/ "Edit" the relevant
         * Alexa Skill and put the relevant Application Ids in this Set.
         */
        supportedApplicationIds.add("amzn1.ask.skill.9d3d91eb-6ea4-47e1-8fef-2df3026269ae");
    }

    public MainSpeechletRequestStreamHandler() {
        super(new MainSpeechlet(), supportedApplicationIds);
    }
}