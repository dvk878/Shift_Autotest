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

import static com.consol.citrus.DefaultTestActionBuilder.action;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;
public class QuackDuck extends DuckActionsClient {

    @Test(description = "Корректный звук уточки с правильным (существующим) нечетным id", enabled = true, invocationCount = 2)
    @CitrusTest
    public void CorrectOddIdQuacking(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", "0.15", "rubber", "quack", "FIXED");
        runner.$(http().client(duckService)
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
        quackDuck(runner, "${duckId}", "1", "1");
        validateResponse(runner, "{\n" + "  \"sound\": \"quack\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }


    @Test(description = "Корректный звук уточки с правильным (существующим) четным id", enabled = true, invocationCount = 2)
    @CitrusTest
    public void CorrectEvenIdQuacking(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", "0.15", "rubber", "quack", "FIXED");
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        runner.$(
                action(context -> {
                    String duckId = context.getVariable("duckId");
                    double dDuckId = Integer.parseInt(duckId);
                    if (dDuckId % 2 == 1) {
                        throw new AssertionError("ID is even, not odd: " + duckId);
                    }
                }));
        quackDuck(runner, "${duckId}", "1", "1");
        validateResponse(runner, "{\n" + "  \"sound\": \"quack\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }

}
