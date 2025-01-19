package Autotests.duck_controller;
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

public class UpdateDuck extends TestNGCitrusSpringSupport{

    private final DuckMethods duck = new DuckMethods();
    @Test(description = "Изменение цвета и высоты уточки",enabled = true)
    @CitrusTest
    public void updateColorAndHeight(@Optional @CitrusResource TestCaseRunner runner)
    {
        duck.createDuck(runner,"yellow","1.0","wood","quack","FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        duck.updateDuck(runner,"${duckId}","red","2.0","wood","quack","FIXED");
        duck.validateResponse(runner,"{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
        duck.deleteDuck(runner,"${duckId}");
    }

    @Test(description = "Изменение цвета и звука уточки",enabled = true)
    @CitrusTest
    public void updateColorAndSound(@Optional @CitrusResource TestCaseRunner runner)
    {
        duck.createDuck(runner,"yellow","1.0","wood","quack","FIXED");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        duck.updateDuck(runner,"${duckId}","red","1.0","wood","roar","FIXED");
        duck.validateResponse(runner,"{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
        duck.deleteDuck(runner,"${duckId}");
    }}

