package net.remiohead.alexa.skill.test;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.fluent.Request;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Supplier;

import static com.google.common.base.Preconditions.checkNotNull;

public class WeatherTask implements Supplier<Map<String, Object>> {

    private final String location;

    private static final String API_KEY =
            "a3c82ba0ab571211477815a9de17bcf7";

    public WeatherTask(final String location) {
        this.location = checkNotNull(location);
    }


    @Override
    public Map<String, Object> get() {
        try {
            final String response = Request
                    .Post(this.generateUrl())
                    .execute()
                    .returnContent()
                    .asString(StandardCharsets.UTF_8);
            final JsonObject root =
                    new JsonParser()
                            .parse(response)
                            .getAsJsonObject();
            final JsonObject main =
                    root.getAsJsonObject("main");
            final int temp = Math.round(
                    main.get("temp").getAsFloat());
            final int humidity = main.get("humidity").getAsInt();

            final JsonObject weather =
                    root.getAsJsonArray("weather")
                            .get(0).getAsJsonObject();
            final String desc = weather.get("main").getAsString();

            return ImmutableMap.of(
                    "temp", temp,
                    "humidity", humidity,
                    "desc", desc
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateUrl() {
        final StringBuilder url = new StringBuilder();
        url.append("https://api.openweathermap.org/data/2.5/weather");
        url.append("?zip="+this.location);
        url.append("&APPID="+API_KEY);
        url.append("&units=imperial");
        return url.toString();
    }

    public static void main(String[] args) {
        String loc = "75093,us";
        System.out.println(new WeatherTask(loc).get());
    }
}
