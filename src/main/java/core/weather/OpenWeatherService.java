package core.weather;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

@Component
public class OpenWeatherService extends WeatherService {

    @Override
    public Double getTemperatureByCoordinates(String latitude, String longtitude) throws JsonProcessingException {

        final String weatherDataJson=restTemplate.getForObject(
                "http://api.openweathermap.org/data/2.5/weather?lat="+latitude+
                        "&lon="+longtitude+"&appid=6a22a3b190640923d4a1b14c0e9949fe", String.class);
        return Double.parseDouble(
                objectMapper.readTree(weatherDataJson).get("main").get("temp").toString()
        ) - 273.15;

    }
}
