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

import static com.consol.citrus.container.FinallySequence.Builder.doFinally;

@Epic("duckActionController Tests")
@Feature("Endpoint /api/duck/action/swim")
public class SwimDuck extends DuckActionsClient {

    @Test(description = "Duck swimming with correct(existing) id", enabled = true)
    @CitrusTest
    public void existingDuckIdSwim(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDuckPayload crDuck=new CreateDuckPayload()
                .id(null)
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        createDuck(runner, crDuck);
        getDuckId(runner);
        duckSwim(runner, "${duckId}");
        validateResponse(runner, "duckActionController/swimDuck/existingDuckIdSwim.json", HttpStatus.OK);
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Duck swimming with correct(existing) id", enabled = true)
    @CitrusTest
    public void existingDuckIdSwimDb(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFromDb(runner);
        runner.variable("duckId","1234567");
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId},'yellow',2.0,'wood','quack','ACTIVE');");
        duckSwim(runner,"${duckId}");
        validateResponse(runner, "duckActionController/swimDuck/existingDuckIdSwim.json", HttpStatus.OK);

    }


    @Test(description = "Duck swimming with correct(nonexistent) id", enabled = true)
    @CitrusTest
    public void nonexistentDuckIdSwim(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDuckPayload crDuck=new CreateDuckPayload()
                .id(null)
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        createDuck(runner, crDuck);
        getDuckId(runner);
        deleteDuck(runner, "${duckId}");
        duckSwim(runner, "${duckId}");
        validateResponse(runner, "duckActionController/swimDuck/nonexistentDuckIdSwim.json", HttpStatus.NOT_FOUND);
    }

    @Test(description = "Duck swimming with correct(nonexistent) id", enabled = true)
    @CitrusTest
    public void nonexistentDuckIdSwimDb(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFromDb(runner);
        runner.variable("duckId","-1");
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId},'yellow',2.0,'wood','quack','ACTIVE');");
        duckSwim(runner,"${duckId}");
        validateResponse(runner, "duckActionController/swimDuck/nonexistentDuckIdSwim.json", HttpStatus.NOT_FOUND);
    }

}