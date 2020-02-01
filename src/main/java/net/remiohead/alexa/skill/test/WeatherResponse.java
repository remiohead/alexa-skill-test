package net.remiohead.alexa.skill.test;

import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.SimpleCard;
import com.google.common.base.Preconditions;

import java.util.Map;
import java.util.function.Supplier;

public class WeatherResponse implements Supplier<SpeechletResponse> {

    private final IntentRequest request;
    private final Session session;
    

    public WeatherResponse(
            final IntentRequest request,
            final Session session) {
        this.request = Preconditions.checkNotNull(request);
        this.session = Preconditions.checkNotNull(session);
    }

    @Override
    public SpeechletResponse get() {
        final Map<String, Object> result =
                new WeatherTask("75093,us").get();

        final StringBuilder out = new StringBuilder();
        out.append("The temperature is ");
        out.append(result.get("temp"));
        out.append(" degrees with ");
        out.append(result.get("humidity"));
        out.append(" percent humidity");

        String speechText = out.toString();

        // Create the Simple card content.
        SimpleCard card = new SimpleCard();
        card.setTitle("Weather");
        card.setContent(speechText);

        // Create the plain text output.
        PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
        speech.setText(speechText);

        return SpeechletResponse.newTellResponse(speech, card);
    }


}
