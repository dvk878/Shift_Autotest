package Autotests;
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
public class DuckActionTest extends TestNGCitrusSpringSupport{

    @Test(description = "Создание уточки со значением material=rubber.",enabled = false)
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner)
    {
//        runner.variable("color", "yellow");
//        runner.variable("height", "yellow");
//        runner.variable("material", "yellow");
//        runner.variable("sound", "yellow");
//        runner.variable("wingState", "yellow");
        createDuck(runner,"yellow",1.0,"rubber","quack","FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        validateResponse(runner,
                "{\n" + "  \"id\": " + "${duckId}" + ",\n"
                                + "  \"color\": \"" + "yellow" + "\",\n"
                                + "  \"height\": " + 1.0 + ",\n"
                                + "  \"material\": \"" + "rubber" + "\",\n"
                                + "  \"sound\": \"" + "quack" + "\",\n"
                                + "  \"wingsState\": \"" + "Fixed"
                                + "\"\n" + "}");


    }

    @Test(description = "Создание уточки со значением material=wood.",enabled = false)
    @CitrusTest
    public void CreateWoodenDuck(@Optional @CitrusResource TestCaseRunner runner)
    {
        createDuck(runner,"yellow",1.0,"wood","quack","FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
    }

    @Test(description = "Создание уточки со значением material=wood.")
    @CitrusTest
    public void DeleteDuck(@Optional @CitrusResource TestCaseRunner runner)
    {
        createDuck(runner,"yellow",1.0,"wood","quack","FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        deleteDuck(runner,"${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"Duck is deleted\"\n" + "}");
    }

    @Test(description = "Создание уточки со значением material=wood.",enabled = false)
    @CitrusTest
    public void UpdateColorAndHeight(@Optional @CitrusResource TestCaseRunner runner)
    {
        createDuck(runner,"yellow",1.0,"wood","quack","FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        updateDuck(runner,"${duckId}","red","2.0","wood","quack","FIXED");
        validateResponse(runner,"{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
    }

    @Test(description = "Создание уточки со значением material=wood.",enabled = false)
    @CitrusTest
    public void UpdateColorAndSound(@Optional @CitrusResource TestCaseRunner runner)
    {
        createDuck(runner,"yellow",1.0,"wood","quack","FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        updateDuck(runner,"${duckId}","red","1.0","wood","roar","FIXED");
        validateResponse(runner,"{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
    }

    @Test(description = "Создание уточки со значением material=wood.",enabled = false)
    @CitrusTest
    public void CorrectOddIdQuacking(@Optional @CitrusResource TestCaseRunner runner)
    {
        createDuck(runner,"yellow",1.0,"wood","quack","FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        quackDuck(runner,"${duckId}","2","2");
       // validateResponse(runner,"{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
    }


    @Test(description = "Создание уточки со значением material=wood.",enabled = false)
    @CitrusTest
    public void CorrectEvenIdQuacking(@Optional @CitrusResource TestCaseRunner runner)
    {
        createDuck(runner,"yellow",1.0,"wood","quack","FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        updateDuck(runner,"${duckId}","red","1.0","wood","roar","FIXED");
        //validateResponse(runner,"{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
    }

    @Test(description = "Создание уточки со значением material=wood.",enabled = false)
    @CitrusTest
    public void ActiveWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        duckFly(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"I am flying :)\"\n" + "}");
    }

    @Test(description = "Создание уточки со значением material=wood.",enabled = false)
    @CitrusTest
    public void FixedWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        duckFly(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"I can not fly :C\"\n" + "}");
    }

    @Test(description = "Создание уточки со значением material=wood.",enabled = false)
    @CitrusTest
    public void UndefinedWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "UNDEFINED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        duckFly(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"Wings are not detected :(\"\n" + "}");
    }

    @Test(description = "",enabled = false)
    @CitrusTest
    public void existingDuckIdSwim(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        duckSwim(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"I'm swimming\"\n" + "}");
    }

    @Test(description = "",enabled = false)
    @CitrusTest
    public void nonexistentDuckIdSwim(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        duckSwim(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"I'm swimming\"\n" + "}");
    }


    @Test(description = "Создание уточки со значением material=wood.",enabled = true)
    @CitrusTest
    public void showEvenIdWoodenDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "wood", "quack", "ACTIVE");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        showProperties(runner, "${duckId}");
        //validateResponse(runner, "{\"@ignore@\"}");
    }

    @Test(description = "Создание уточки со значением material=wood.",enabled = true)
    @CitrusTest
    public void showOddIdRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        showProperties(runner, "${duckId}");
        //validateResponse(runner, "{\"@ignore@\"}");
    }

    public void duckSwim(TestCaseRunner runner, String id) {
        runner.$(http().client("http://localhost:2222")
                .send()
                .get("/api/duck/action/swim")
                .queryParam("id", id));
    }

    public void createDuck(TestCaseRunner runner, String color, double height, String material, String sound, String wingsState) {
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
                .contentType(MediaType.APPLICATION_JSON_VALUE).body(responseMessage));
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






