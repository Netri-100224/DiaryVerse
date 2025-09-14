package net.engineeringdigest.journalApp.Service;


import net.engineeringdigest.journalApp.ExternalAPI.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apikey;

    private static final String BASE_URL = "http://api.weatherstack.com/current";

    @Autowired
    private RestTemplate resttemp;

    public WeatherResponse getweather(String city) {
        String finalAPI = BASE_URL + "?access_key=" + apikey + "&query=" + city;
        ResponseEntity<WeatherResponse> response = resttemp.exchange(
                finalAPI,
                HttpMethod.GET,
                null,
                WeatherResponse.class
        );
        return response.getBody();
    }
}
