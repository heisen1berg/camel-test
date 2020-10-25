package core.route;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.data.MsgA;
import core.data.MsgB;
import core.weather.OpenWeatherService;
import core.weather.WeatherService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Component
public class MainRoute extends RouteBuilder {
    private static final RestTemplate restTemplate= new RestTemplate();
    private static final ObjectMapper objectMapper=new ObjectMapper();
    private WeatherService weatherService;
    @Autowired
    public MainRoute(WeatherService weatherService){
        this.weatherService=weatherService;
    }
    @Override
    public void configure() throws Exception {

        rest("/temperature").post().to("direct:getTemperature");

        from("direct:getTemperature")
                .process(new Processor(){
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        final String inputJson=exchange.getIn().getBody(String.class);
                        try {
                            final MsgA msgA=objectMapper.readValue(inputJson, MsgA.class);


                            if(!msgA.getLng().equals("ru"))return;
                            if(msgA.getMsg().length()==0){
                                exchange.getOut().setBody("error: empty message");
                                return;
                            }

                            final MsgB msgB=new MsgB(
                                    msgA.getMsg(),
                                    new Date().toString(),
                                    weatherService.getTemperatureByCoordinates(msgA.extractLatitude(),msgA.extractLongtitude()));
                            exchange.getOut().setBody("OK");


                            //отправка клиенту, закоментировано, так как некому отправлять
                            //final HttpEntity<MsgB> msgEntity=new HttpEntity<>(msgB);
                            //restTemplate.postForLocation("url",msgEntity, MsgB.class);
                        }
                        catch(JsonParseException | NullPointerException | NumberFormatException exception){
                            //возникнет в случае некорректного входящего джейсона
                            exchange.getOut().setBody("error: invalid JSON");
                        }
                        catch(org.springframework.web.client.HttpClientErrorException exception1){
                            //сервис либо недоступен, либо были переданы неправильные параметры
                            exchange.getOut().setBody("error: could not get weather data");
                        }
                        catch(Exception unhandledException){
                            exchange.getOut().setBody("error: unhandled exception has occured");
                            unhandledException.printStackTrace();
                        }
                    }
                }).to("mock:result");


    }
}