package Autotests.duck_action_controller;
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
public class QuackDuck extends TestNGCitrusSpringSupport{

    @Test(description = "Корректный звук уточки с правильным (существующим) нечетным id",enabled = true, invocationCount = 2)
    @CitrusTest
    public void CorrectOddIdQuacking(@Optional @CitrusResource TestCaseRunner runner)
    {
        createDuck(runner,"yellow","0.15","rubber","quack","FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));

        runner.$(
                action(context -> {
                    String duckId=context.getVariable("duckId");
                    double dDuckId= Integer.parseInt(duckId);
                    if (dDuckId % 2 == 0) {
                        throw new AssertionError("ID is even, not odd: " + duckId);
                    }
                }));
        quackDuck(runner,"${duckId}","1","1");
        validateResponse(runner,"{\n" + "  \"sound\": \"quack\"\n" + "}");
        deleteDuck(runner,"${duckId}");
    }


    @Test(description = "Корректный звук уточки с правильным (существующим) четным id",enabled = true,invocationCount = 2)
    @CitrusTest
    public void CorrectEvenIdQuacking(@Optional @CitrusResource TestCaseRunner runner)
    {
        createDuck(runner,"yellow","0.15","rubber","quack","FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        runner.$(
                action(context -> {
                    String duckId=context.getVariable("duckId");
                    double dDuckId= Integer.parseInt(duckId);
                    if (dDuckId % 2 == 1) {
                        throw new AssertionError("ID is even, not odd: " + duckId);
                    }
                }));
        quackDuck(runner,"${duckId}","1","1");
        validateResponse(runner,"{\n" + "  \"sound\": \"quack\"\n" + "}");
        deleteDuck(runner,"${duckId}");
    }

    public void duckSwim(TestCaseRunner runner, String id) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }

    public void createDuck(TestCaseRunner runner, String color, String height, String material, String sound, String wingsState) {
        runner.$(http().client("http://localhost:2222")
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

    public void deleteDuck(TestCaseRunner runner, String id) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .delete("/api/duck/delete")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id",id));
    }
    public void validateResponse(TestCaseRunner runner, String responseMessage) {
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(responseMessage));
    }

    public void updateDuck(TestCaseRunner runner,String id, String color, String height, String material, String sound, String wingsState) {
        runner.$(http().client("http://localhost:2222")
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

    public void quackDuck(TestCaseRunner runner,String id, String repetitionCount, String soundCount) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/quack")
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id",id)
                .queryParam("repetitionCount",repetitionCount)
                .queryParam("soundCount",soundCount));

    }

    public void duckFly(TestCaseRunner runner, String id) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/fly")
                .queryParam("id", id));
    }

    public void showProperties(TestCaseRunner runner, String id) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/properties")
                .queryParam("id", id));
    }

}
