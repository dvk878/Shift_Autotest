package autotests.duckController;
import autotests.clients.DuckActionsClient;
import autotests.payloads.createDuck.CreateDuckPayload;
import autotests.payloads.createDuck.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import com.consol.citrus.testng.CitrusParameters;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("duckController Tests")
@Feature("Endpoint /api/duck/create")
public class CreateDuck extends DuckActionsClient {

    CreateDuckPayload crDuck1=new CreateDuckPayload()
            .color("yellow")
            .height(2.0)
            .material("rubber")
            .sound("quack")
            .wingsState(WingsState.ACTIVE);

    CreateDuckPayload crDuck2=new CreateDuckPayload()
            .color("red")
            .height(3.0)
            .material("rubber")
            .sound("quack")
            .wingsState(WingsState.ACTIVE);

    CreateDuckPayload crDuck3=new CreateDuckPayload()
            .color("yellow")
            .height(4.0)
            .material("metal")
            .sound("quack")
            .wingsState(WingsState.ACTIVE);

    CreateDuckPayload crDuck4=new CreateDuckPayload()
            .color("yellow")
            .height(2.0)
            .material("rubber")
            .sound("roar")
            .wingsState(WingsState.FIXED);

    CreateDuckPayload crDuck5=new CreateDuckPayload()
            .color("blac")
            .height(3.2)
            .material("wood")
            .sound("bark")
            .wingsState(WingsState.ACTIVE);
    @Test(description = "Creating duck with material=rubber", enabled = true,dataProvider = "duckList")
    @CitrusTest
    @CitrusParameters({"payload","response","runner"})
    public void createRubberDuckEndpoint(Object payload, Object response, @Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFromDb(runner);
        createDuck(runner, payload);
        validateResponse(runner,response,HttpStatus.OK);

    }
    @DataProvider(name="duckList")
    public Object[][] DuckProvider() {
        return new Object[][] {
                {crDuck1, crDuck1.id(), null},
                {crDuck2, crDuck2.id(), null},
                {crDuck3, crDuck3.id(), null},
                {crDuck4, crDuck4.id(), null},
                {crDuck5, crDuck5.id(), null},
        };
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




