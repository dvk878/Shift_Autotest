package autotests.duck_controller;
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

public class UpdateDuck extends DuckActionsClient {

    @Test(description = "Изменение цвета и высоты уточки",enabled = true)
    @CitrusTest
    public void UpdateColorAndHeight(@Optional @CitrusResource TestCaseRunner runner)
    {
        createDuck(runner,"yellow","1.0","wood","quack","FIXED");
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        updateDuck(runner,"${duckId}","red","2.0","wood","quack","FIXED");
        validateResponse(runner,"{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
        deleteDuck(runner,"${duckId}");
    }

    @Test(description = "Изменение цвета и звука уточки",enabled = true)
    @CitrusTest
    public void UpdateColorAndSound(@Optional @CitrusResource TestCaseRunner runner)
    {
        createDuck(runner,"yellow","1.0","wood","quack","FIXED");
        runner.$(http().client(duckService)
                .receive()
                .response(HttpStatus.OK)
                .message()
                .extract(fromBody().expression("$.id", "duckId")));
        updateDuck(runner,"${duckId}","red","1.0","wood","roar","FIXED");
        validateResponse(runner,"{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
        deleteDuck(runner,"${duckId}");
    }
}
