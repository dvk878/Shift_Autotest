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
public class PropertiesDuck extends TestNGCitrusSpringSupport {
    private final DuckMethods duck = new DuckMethods();

    @Test(description = "Просмотр свойств уточки с четным (существующим) id и утка с material=wood", enabled = true, invocationCount = 2)
    @CitrusTest
    public void showEvenIdWoodenDuck(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("color", "yellow");
        runner.variable("height", "23.0");
        runner.variable("material", "wood");
        runner.variable("sound", "quack");
        runner.variable("wingsState", "FIXED");
        duck.createDuck(runner, "${color}", "${height}", "${material}", "${sound}", "${wingsState}");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        runner.$(
                action(context -> {
                    String duckId = context.getVariable("duckId");
                    double dDuckId = Integer.parseInt(duckId);
                    if (dDuckId % 2 == 1) {
                        throw new AssertionError("ID is odd, not even: " + duckId);
                    }
                }));
        duck.showProperties(runner, "${duckId}");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .body("{"
                        + "\"color\": \"${color}\","
                        + "\"height\": ${height},"
                        + "\"material\": \"${material}\","
                        + "\"sound\": \"${sound}\","
                        + "\"wingsState\": \"${wingsState}\""
                        + "}"));
        duck.deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Просмотр свойств уточки с нечетным (существующим) id и утка с material=rubber", enabled = true, invocationCount = 2)
    @CitrusTest
    public void showOddIdRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        runner.variable("color", "yellow");
        runner.variable("height", "23.0");
        runner.variable("material", "rubber");
        runner.variable("sound", "quack");
        runner.variable("wingsState", "FIXED");
        duck.createDuck(runner, "${color}", "${height}", "${material}", "${sound}", "${wingsState}");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        runner.$(
                action(context -> {
                    String duckId = context.getVariable("duckId");
                    double dDuckId = Integer.parseInt(duckId);
                    if (dDuckId % 2 == 0) {
                        throw new AssertionError("ID is even, not odd: " + duckId);
                    }
                }));
        duck.showProperties(runner, "${duckId}");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .body("{"
                        + "\"color\": \"${color}\","
                        + "\"height\": ${height},"
                        + "\"material\": \"${material}\","
                        + "\"sound\": \"${sound}\","
                        + "\"wingsState\": \"${wingsState}\""
                        + "}"));
        duck.deleteDuck(runner, "${duckId}");
    }
}

