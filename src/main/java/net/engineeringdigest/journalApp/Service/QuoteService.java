package net.engineeringdigest.journalApp.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class QuoteService {

    private final RestTemplate restTemplate = new RestTemplate();


    private String cachedQuote;

    public String getRandomQuote() {
        if (cachedQuote == null) {
            cachedQuote = fetchQuoteFromAPI();
        }
        return cachedQuote;
    }

    public void refreshQuote() {
        cachedQuote = fetchQuoteFromAPI();
    }
    public String fetchQuoteFromAPI() {
        String apiUrl = "https://zenquotes.io/api/random";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response.getBody());

            String quote = root.get(0).get("q").asText();
            String author = root.get(0).get("a").asText();

            return "\"" + quote + "\"\n— " + author;
        } catch (Exception e) {
            log.error("Error fetching quote: ", e);
            return "Stay strong. Keep journaling. 💪";
        }
    }
}
