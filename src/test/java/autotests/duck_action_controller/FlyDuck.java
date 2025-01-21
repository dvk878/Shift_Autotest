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
public class FlyDuck extends DuckActionsClient {

    @Test(description = "Полет уточки с правильным (существующим) id и с активными крыльями: ACTIVE", enabled = true)
    @CitrusTest
    public void ActiveWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", "0.15", "rubber", "quack", "ACTIVE");
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        duckFly(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"I am flying :)\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Полет уточки с правильным (существующим) id и со связанными крыльями: FIXED", enabled = true)
    @CitrusTest
    public void FixedWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", "0.15", "rubber", "quack", "FIXED");
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        duckFly(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"I can not fly :C\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Полет уточки с правильным (существующим) id и с крыльями в неопределенном состоянии: ACTIVE", enabled = true)
    @CitrusTest
    public void UndefinedWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", "0.15", "rubber", "quack", "UNDEFINED");
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        duckFly(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"Wings are not detected :(\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }


}