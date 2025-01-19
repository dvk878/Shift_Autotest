package Autotests.duck_action_controller;
import Autotests.DuckMethods;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.DefaultTestActionBuilder.action;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
public class SwimDuck extends TestNGCitrusSpringSupport{

    private final DuckMethods duck = new DuckMethods();
    @Test(description = "Плавание уточки с правильным (существующим) id",enabled = true)
    @CitrusTest
    public void existingDuckIdSwim(@Optional @CitrusResource TestCaseRunner runner) {
        duck.createDuck(runner, "yellow", "0.15", "rubber", "quack", "FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        duck.duckSwim(runner, "${duckId}");
        duck.validateResponse(runner, "{\n" + "  \"message\": \"@ignore@\"\n" + "}");
        duck.deleteDuck(runner,"${duckId}");
    }

    @Test(description = "Плавание уточки с правильным (несуществующим) id",enabled = true)
    @CitrusTest
    public void nonexistentDuckIdSwim(@Optional @CitrusResource TestCaseRunner runner) {
        duck.createDuck(runner, "yellow", "0.15", "rubber", "quack", "FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        duck.deleteDuck(runner,"${duckId}");
        duck.duckSwim(runner,"${duckId}");
        duck.validateResponse(runner, "{\n" + "  \"message\": \"@ignore@\"\n" + "}");
    }}


