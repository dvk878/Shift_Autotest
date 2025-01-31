package autotests.duckActionController;
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
@Epic("duckActionController Tests")
@Feature("Endpoint /api/duck/action/properties")
public class PropertiesDuck extends DuckActionsClient {

    @Test(description = "Reviewing duck properties with even(existing) id and material=wood", enabled = true)
    @CitrusTest
    public void showEvenIdWoodenDuck(@Optional @CitrusResource TestCaseRunner runner) {

        CreateDuckPayload crDuck=new CreateDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("wood")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        createDuck(runner, crDuck);
        getDuckId(runner);
        evenDuckId(runner,crDuck);
        showProperties(runner, "${duckId}");
        validateResponse(runner, crDuck,HttpStatus.OK);
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Reviewing duck properties with even(existing) id and material=wood", enabled = true)
    @CitrusTest
    public void showEvenIdWoodenDuckDb(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFromDb(runner);
        runner.variable("duckId","1234568");
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId},'yellow',2.0,'wood','quack','ACTIVE');");
        showProperties(runner, "${duckId}");
        validateResponse(runner, "duckActionController/propertiesDuck/showEvenIdWoodenDuck.json",HttpStatus.OK);
    }
    
    @Test(description = "Reviewing duck properties with odd(existing) id and material=rubber", enabled = true)
    @CitrusTest
    public void showOddIdRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {

        CreateDuckPayload crDuck=new CreateDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);
        createDuck(runner, crDuck);
        getDuckId(runner);
        oddDuckId(runner,crDuck);
        showProperties(runner, "${duckId}");
        validateResponse(runner,crDuck, HttpStatus.OK);
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Reviewing duck properties with odd(existing) id and material=rubber", enabled = true)
    @CitrusTest
    public void showOddIdRubberDuckDb(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFromDb(runner);
        runner.variable("duckId","1234567");
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId},'yellow',2.0,'rubber','quack','ACTIVE');");
        showProperties(runner, "${duckId}");
        validateResponse(runner, "duckActionController/propertiesDuck/showOddIdRubberDuck.json",HttpStatus.OK);
    }


}
