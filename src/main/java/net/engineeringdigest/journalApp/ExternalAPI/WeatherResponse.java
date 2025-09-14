package net.engineeringdigest.journalApp.ExternalAPI;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class WeatherResponse {

    @Getter
    @Setter
   private Current current;
    @Getter
    @Setter
    public static class Current {
            private int temperature;
            @JsonProperty("weather_descriptions")
            private List<String> weatherDescriptions;
        @JsonProperty("feelslike")
        private int feelslike;
        }


    }

