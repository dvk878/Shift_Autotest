package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.DefaultTestActionBuilder.action;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
import static java.lang.Double.parseDouble;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    //СRUD endpoints
    public void createDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
        runner.$(http().client(duckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("{\n" + "  \"color\": \"" + color + "\",\n"
                        + "  \"height\": " + height + ",\n"
                        + "  \"material\": \"" + material + "\",\n"
                        + "  \"sound\": \"" + sound + "\",\n"
                        + "  \"wingsState\": \"" + wingsState
                        + "\"\n" + "}"));
    }

    public void createDuck(TestCaseRunner runner, Object body) {
        runner.$(http().client(duckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));

    }

    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(http().client(duckService)
                .send()
                .delete("/api/duck/delete")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id",id));
    }
    public void updateDuck(TestCaseRunner runner,String id, String color, String height, String material, String sound, String wingsState) {
        runner.$(http().client(duckService)
                .send()
                .put("/api/duck/update")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id",id)
                .queryParam("color",color)
                .queryParam("height",height)
                .queryParam("material",material)
                .queryParam("sound",sound)
                .queryParam("wingsState",wingsState));

    }

    //Duck actinons endpoints

    public void duckSwim(TestCaseRunner runner, String id) {
        runner.$(http().client(duckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }

    public void quackDuck(TestCaseRunner runner,String id, String repetitionCount, String soundCount) {
        runner.$(http().client(duckService)
                .send()
                .get("/api/duck/action/quack")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id",id)
                .queryParam("repetitionCount",repetitionCount)
                .queryParam("soundCount",soundCount));

    }

    public void duckFly(TestCaseRunner runner, String id) {
        runner.$(http().client(duckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }

    public void showProperties(TestCaseRunner runner, String id) {
        runner.$(http().client(duckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }

    //Validation

    public void validateResponseString(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract(fromBody().expression("$.id", "duckId"))
                .body(responseMessage));
    }

    public void validateResponseOk(TestCaseRunner runner) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract(fromBody().expression("$.id", "duckId"))
                .body("{"
                        + "  \"id\": " + "${duckId}" + ","
                        + "  \"color\": \"" + "${color}" + "\","
                        + "  \"height\": " + "${height}" + ","
                        + "  \"material\": \"" + "${material}" + "\","
                        + "  \"sound\": \"" + "${sound}" + "\","
                        + "  \"wingsState\": \"" +"${wingsState}"
                        + "\"" + "}"));
    }

    public void validateResponseOk(TestCaseRunner runner,String expectedPayload) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract(fromBody().expression("$.id", "duckId"))
                .body(new ClassPathResource(expectedPayload)));
    }

    public void validateResponseOk(TestCaseRunner runner, Object expectedPayload) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract(fromBody().expression("$.id", "duckId"))
                .body(new ObjectMappingPayloadBuilder(expectedPayload,new ObjectMapper())));
    }

    public void validateResponseNotFound(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.NOT_FOUND)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(responseMessage));
    }



    //Duck id computing

    public void getDuckId(TestCaseRunner runner) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
    }

    //Odd or even id
    public void evenDuckId(TestCaseRunner runner) {
        runner.$(
                action(context -> {
                    String duckId = context.getVariable("duckId");
                    double dDuckId = Integer.parseInt(duckId);
                    if (dDuckId % 2 == 1) {
                        createDuck(runner, "${color}", parseDouble("23.0"), "${material}", "${sound}", "${wingsState}");
                    }
                }));
    }

    public void oddDuckId(TestCaseRunner runner) {
        runner.$(
                action(context -> {
                    String duckId = context.getVariable("duckId");
                    double dDuckId = Integer.parseInt(duckId);
                    if (dDuckId % 2 == 0) {
                        createDuck(runner, "${color}", parseDouble("23.0"), "${material}", "${sound}", "${wingsState}");
                    }
                }));
    }



}