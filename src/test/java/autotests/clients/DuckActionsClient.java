package autotests.clients;

import autotests.EndpointConfig;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.DefaultTestActionBuilder.action;
import static com.consol.citrus.actions.ExecuteSQLAction.Builder.sql;
import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.container.FinallySequence.Builder.doFinally;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class DuckActionsClient extends TestNGCitrusSpringSupport {

    @Autowired
    protected HttpClient duckService;

    @Autowired
    protected SingleConnectionDataSource testDb;

    public void databaseUpdate(TestCaseRunner runner, String sql) {
        runner.$(sql(testDb)
                .statement(sql));
    }

    //СRUD endpoints
    @Step("Create duck endpoint")
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
    @Step("Create duck endpoint")
    public void createDuck(TestCaseRunner runner, Object body) {
        runner.$(http().client(duckService)
                .send()
                .post("/api/duck/create")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));

    }

    @Step("Delete duck endpoint")
    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(http().client(duckService)
                .send()
                .delete("/api/duck/delete")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id",id));
    }

    @Step("Delete duck DB")
    public void deleteDuckFromDb(TestCaseRunner runner) {
        runner.$(doFinally().actions(context ->
                databaseUpdate(runner, "DELETE FROM DUCK WHERE ID=${duckId}")));
    }
    @Step("Update duck endpoint")
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
    //Duck actiоns endpoints
    @Step("Swim duck endpoint")
    public void duckSwim(TestCaseRunner runner, String id) {
        runner.$(http().client(duckService)
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }
    @Step("Quack duck endpoint")
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
    @Step("Fly duck endpoint")
    public void duckFly(TestCaseRunner runner, String id) {
        runner.$(http().client(duckService)
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }
    @Step("Properties duck endpoint")
    public void showProperties(TestCaseRunner runner, String id) {
        runner.$(http().client(duckService)
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }

    //Validation
    @Step("Duck validation using resource files")
    public void validateResponse(TestCaseRunner runner, String expectedPayload, HttpStatus status) {
        runner.$(http().client(duckService)
                .receive()
                .response(status)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(expectedPayload)));
    }
    @Step("Duck validation using payloads")
    public void validateResponse(TestCaseRunner runner, Object expectedPayload, HttpStatus status) {
        runner.$(http().client(duckService)
                .receive()
                .response(status)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(expectedPayload,new ObjectMapper())));
    }

    public void validateDuckInDatabase(TestCaseRunner runner, String id, String color, String height,
                                       String material, String sound, String wingsState) {
        runner.$(query(testDb)
                .statement("SELECT * FROM DUCK WHERE ID=" + id)
                .validate("COLOR",color)
                .validate("HEIGHT",height)
                .validate("MATERIAL",material)
                .validate("SOUND",sound)
                .validate("WINGS_STATE",wingsState));
    }

    public void validateDuckInDatabase(TestCaseRunner runner, String id) {
        runner.$(query(testDb)
                .statement("SELECT * FROM DUCK WHERE ID=" + id));
    }

    //Duck id computing
    @Step("Get duck id")
    public void getDuckId(TestCaseRunner runner) {
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
    }
    @Step("Even duck id")
    //Odd or even id
    public void evenDuckId(TestCaseRunner runner,Object body) {
        runner.$(
                action(context -> {
                    String duckId = context.getVariable("duckId");
                    double dDuckId = Integer.parseInt(duckId);
                    if (dDuckId % 2 == 1) {
                        createDuck(runner,body);
                        getDuckId(runner);
                    }
                }));
    }

    @Step("Odd duck id")
    public void oddDuckId(TestCaseRunner runner,Object body) {
        runner.$(
                action(context -> {
                    String duckId = context.getVariable("duckId");
                    double dDuckId = Integer.parseInt(duckId);
                    if (dDuckId % 2 == 0) {
                        createDuck(runner, body);
                        getDuckId(runner);
                    }
                }));
    }



}