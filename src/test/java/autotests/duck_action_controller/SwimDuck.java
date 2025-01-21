package autotests.duck_action_controller;
import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
public class SwimDuck extends DuckActionsClient {

    @Test(description = "Плавание уточки с правильным (существующим) id", enabled = true)
    @CitrusTest
    public void existingDuckIdSwim(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", "0.15", "rubber", "quack", "FIXED");
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        duckSwim(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"@ignore@\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Плавание уточки с правильным (несуществующим) id", enabled = true)
    @CitrusTest
    public void nonexistentDuckIdSwim(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", "0.15", "rubber", "quack", "FIXED");
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        deleteDuck(runner, "${duckId}");
        duckSwim(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"@ignore@\"\n" + "}");
    }

}