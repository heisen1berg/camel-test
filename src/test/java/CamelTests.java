import com.fasterxml.jackson.databind.ObjectMapper;
import core.data.MsgA;
import core.route.MainRoute;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class  CamelTests extends CamelTestSupport {
    private static final ObjectMapper objectMapper=new ObjectMapper();

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new MainRoute();
    }

    @Produce(uri = "direct:getTemperature")
    protected ProducerTemplate template;
    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Test
    public void testGetTemperature() throws Exception {
        resultEndpoint.expectedBodiesReceived(
                "error: invalid JSON",
                "error: empty message",
                "error: could not get weather data",
                "OK"
        );
        template.sendBody(objectMapper.writeValueAsString(new MsgA(null,"ru","20","20")));
        template.sendBody(objectMapper.writeValueAsString(new MsgA("","ru","20","20")));
        template.sendBody(objectMapper.writeValueAsString(new MsgA("message","ru","20asd","20")));
        template.sendBody(objectMapper.writeValueAsString(new MsgA("message","ru","20","20")));

        resultEndpoint.assertIsSatisfied();
    }

}

