package autotests.duckController;
import autotests.clients.DuckActionsClient;
import autotests.payloads.createDuck.CreateDuckPayload;
import autotests.payloads.createDuck.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("duckController Tests")
@Feature("Endpoint /api/duck/create")
public class CreateDuck extends DuckActionsClient {

    @Test(description = "Creating duck with material=rubber", enabled = true)
    @CitrusTest
    public void createRubberDuckEndpoint(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFromDb(runner);
        CreateDuckPayload crDuck=new CreateDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        createDuck(runner, crDuck);
        getDuckId(runner);
        validateDuckInDatabase(runner,"${duckId}","yellow","2.0","rubber","quack","ACTIVE");

    }

    @Test(description = "Creating duck with material=rubber", enabled = true)
    @CitrusTest
    public void createRubberDuckDb(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFromDb(runner);
        runner.variable("duckId","1234567");
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId},'yellow',2.0,'rubber','quack','ACTIVE');");
        validateDuckInDatabase(runner,"${duckId}","yellow","2.0","rubber","quack","ACTIVE");

    }



    @Test(description = "Creating duck with material=wood", enabled = true)
    @CitrusTest
    public void CreateWoodenDuckEndpoint(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFromDb(runner);
        CreateDuckPayload crDuck=new CreateDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("wood")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);
        createDuck(runner, crDuck);
        getDuckId(runner);
        validateDuckInDatabase(runner,"${duckId}","yellow","2.0","wood","quack","ACTIVE");


    }

    @Test(description = "Creating duck with material=wood", enabled = true)
    @CitrusTest
    public void CreateWoodenDuckDb(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFromDb(runner);
        runner.variable("duckId","1234567");
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId},'yellow',2.0,'wood','quack','ACTIVE');");
        validateDuckInDatabase(runner,"${duckId}","yellow","2.0","wood","quack","ACTIVE");


    }

}




