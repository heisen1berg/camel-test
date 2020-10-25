package core.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;


public abstract class WeatherService {
    protected static final RestTemplate restTemplate=new RestTemplate();
    protected static final ObjectMapper objectMapper=new ObjectMapper();

    public abstract Double getTemperatureByCoordinates(String latitude, String longtitude) throws JsonProcessingException;

}
