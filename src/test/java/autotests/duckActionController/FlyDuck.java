package autotests.duckActionController;
import autotests.clients.DuckActionsClient;
import autotests.payloads.createDuck.CreateDuckPayload;
import autotests.payloads.createDuck.WingsState;
import autotests.payloads.messageAnswer;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
@Epic("duckActionController Tests")
@Feature("Endpoint /api/duck/action/fly")
public class FlyDuck extends DuckActionsClient {

    @Test(description = "Duck flight with correct(existing) id and wingsState=ACTIVE", enabled = true)
    @CitrusTest
    public void ActiveWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDuckPayload crDuck=new CreateDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        messageAnswer answer = new messageAnswer().message("I’m flying");

        createDuck(runner, crDuck);
        getDuckId(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner,answer,HttpStatus.OK);
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Duck flight with correct(existing) id and wingsState=ACTIVE", enabled = true)
    @CitrusTest
    public void ActiveWingsFlyDb(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFromDb(runner);
        runner.variable("duckId","1234568");
        messageAnswer answer = new messageAnswer().message("I’m flying");
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId},'yellow',2.0,'wood','quack','ACTIVE');");
        duckFly(runner, "${duckId}");
        validateResponse(runner, answer,HttpStatus.OK);
    }

    @Test(description = "Duck flight with correct(existing) id and wingsState=FIXED", enabled = true)
    @CitrusTest
    public void FixedWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDuckPayload crDuck=new CreateDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.FIXED);

        createDuck(runner, crDuck);
        getDuckId(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner, "duckActionController/flyDuck/fixedFlyDuck.json", HttpStatus.OK);
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Duck flight with correct(existing) id and wingsState=FIXED", enabled = true)
    @CitrusTest
    public void FixedWingsFlyDb(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFromDb(runner);
        runner.variable("duckId","1234568");
        messageAnswer answer = new messageAnswer().message("I’m flying");
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId},'yellow',2.0,'wood','quack','FIXED');");
        duckFly(runner, "${duckId}");
        validateResponse(runner, "duckActionController/flyDuck/fixedFlyDuck.json",HttpStatus.OK);
    }

    @Test(description = "Duck flight with correct(existing) id and wingsState=UNDEFINED", enabled = true)
    @CitrusTest
    public void UndefinedWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDuckPayload crDuck=new CreateDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.UNDEFINED);

        createDuck(runner, crDuck);
        getDuckId(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner, "duckActionController/flyDuck/undefinedFlyDuck.json",HttpStatus.OK);
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Duck flight with correct(existing) id and wingsState=UNDEFINED", enabled = true)
    @CitrusTest
    public void UndefinedWingsFlyDb(@Optional @CitrusResource TestCaseRunner runner) {
        deleteDuckFromDb(runner);
        runner.variable("duckId","1234568");
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId},'yellow',2.0,'wood','quack','UNDEFINED');");
        duckFly(runner, "${duckId}");
        validateResponse(runner, "duckActionController/flyDuck/undefinedFlyDuck.json",HttpStatus.OK);
    }


}