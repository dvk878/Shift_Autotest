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
public class FlyDuck extends TestNGCitrusSpringSupport {
    private final DuckMethods duck = new DuckMethods();

    @Test(description = "Полет уточки с правильным (существующим) id и с активными крыльями: ACTIVE", enabled = true)
    @CitrusTest
    public void ActiveWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        duck.createDuck(runner, "yellow", "0.15", "rubber", "quack", "ACTIVE");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        duck.duckFly(runner, "${duckId}");
        duck.validateResponse(runner, "{\n" + "  \"message\": \"I am flying :)\"\n" + "}");
        duck.deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Полет уточки с правильным (существующим) id и со связанными крыльями: FIXED", enabled = true)
    @CitrusTest
    public void FixedWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        duck.createDuck(runner, "yellow", "0.15", "rubber", "quack", "FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        duck.duckFly(runner, "${duckId}");
        duck.validateResponse(runner, "{\n" + "  \"message\": \"I can not fly :C\"\n" + "}");
        duck.deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Полет уточки с правильным (существующим) id и с крыльями в неопределенном состоянии: ACTIVE", enabled = true)
    @CitrusTest
    public void UndefinedWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        duck.createDuck(runner, "yellow", "0.15", "rubber", "quack", "UNDEFINED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        duck.duckFly(runner, "${duckId}");
        duck.validateResponse(runner, "{\n" + "  \"message\": \"Wings are not detected :(\"\n" + "}");
        duck.deleteDuck(runner, "${duckId}");
    }
}