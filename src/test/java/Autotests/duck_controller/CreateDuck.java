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
public class CreateDuck extends TestNGCitrusSpringSupport{

    private final DuckMethods duck = new DuckMethods();
    @Test(description = "Создание уточки со значением material=rubber",enabled = true)
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner)
    {

        runner.variable("color", "yellow");
        runner.variable("height", "23.0");
        runner.variable("material", "rubber");
        runner.variable("sound", "quack");
        runner.variable("wingsState", "FIXED");
        duck.createDuck(runner,"${color}","${height}","${material}","${sound}","${wingsState}");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId"))
                .body("{"
                                + "\"id\": ${duckId},"
                                + "\"color\": \"${color}\","
                                + "\"height\": ${height},"
                                + "\"material\": \"${material}\","
                                + "\"sound\": \"${sound}\","
                                + "\"wingsState\": \"${wingsState}\""
                                + "}"));
        duck.deleteDuck(runner,"${duckId}");
    }

    @Test(description = "Создание уточки со значением material=wood",enabled = true)
    @CitrusTest
    public void createWoodenDuck(@Optional @CitrusResource TestCaseRunner runner)
    {
        runner.variable("color", "yellow");
        runner.variable("height", "17.1");
        runner.variable("material", "wood");
        runner.variable("sound", "quack");
        runner.variable("wingsState", "FIXED");
        duck.createDuck(runner,"${color}","${height}","${material}","${sound}","${wingsState}");
        runner.$(http().client("http://localhost:2222")
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId"))
                .body("{"
                        + "\"id\": ${duckId},"
                        + "\"color\": \"${color}\","
                        + "\"height\": ${height},"
                        + "\"material\": \"${material}\","
                        + "\"sound\": \"${sound}\","
                        + "\"wingsState\": \"${wingsState}\""
                        + "}"));
        duck.deleteDuck(runner,"${duckId}");
    }}







