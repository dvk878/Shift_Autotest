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
@Feature("Endpoint /api/duck/action/quack")
public class QuackDuck extends DuckActionsClient {

    @Test(description = "Correct duck sound with correct(existing) odd id", enabled = true)
    @CitrusTest
    public void correctOddIdQuacking(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDuckPayload crDuck=new CreateDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        createDuck(runner, crDuck);
        getDuckId(runner);
        oddDuckId(runner,crDuck);
        quackDuck(runner, "${duckId}", "1", "1");
        validateResponse(runner, "duckActionController/quackDuck/correctIdQuackingOneTimeOneRepeat.json", HttpStatus.OK);
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Correct duck sound with correct(existing) odd id", enabled = true)
    @CitrusTest
    public void correctOddIdQuackingDb(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFromDb(runner);
        runner.variable("duckId","1234567");
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId},'yellow',2.0,'wood','quack','ACTIVE');");
        quackDuck(runner,"${duckId}","1","1");
        validateResponse(runner, "duckActionController/quackDuck/correctIdQuackingOneTimeOneRepeat.json", HttpStatus.OK);
        deleteDuckFromDb(runner);
    }


    @Test(description = "Correct duck sound with correct(existing) even id", enabled = true)
    @CitrusTest
    public void correctEvenIdQuacking(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDuckPayload crDuck=new CreateDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        createDuck(runner, crDuck);
        getDuckId(runner);
        evenDuckId(runner,crDuck);
        quackDuck(runner, "${duckId}", "1", "1");
        validateResponse(runner, "duckActionController/quackDuck/correctIdQuackingOneTimeOneRepeat.json", HttpStatus.OK);
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Correct duck sound with correct(existing) even id", enabled = true)
    @CitrusTest
    public void correctEvenIdQuackingId(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFromDb(runner);
        runner.variable("duckId","1234568");
        deleteDuckFromDb(runner);
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId},'yellow',2.0,'wood','quack','ACTIVE');");
        quackDuck(runner,"${duckId}","1","1");
        validateResponse(runner, "duckActionController/quackDuck/correctIdQuackingOneTimeOneRepeat.json", HttpStatus.OK);

    }

}
