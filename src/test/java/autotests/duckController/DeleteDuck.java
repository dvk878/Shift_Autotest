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
@Epic("duckController Tests")
@Feature("Endpoint /api/duck/delete")
public class DeleteDuck extends DuckActionsClient {

    @Test(description = "Deleting duck",enabled = true)
    @CitrusTest
    public void DeleteDuck(@Optional @CitrusResource TestCaseRunner runner)
    {
        CreateDuckPayload crDuck=new CreateDuckPayload()
                .id(null)
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        createDuck(runner, crDuck);
        getDuckId(runner);
        deleteDuck(runner,"${duckId}");
        validateResponse(runner, "duckController/deleteDuck/deleteDuck.json", HttpStatus.OK);
    }

    @Test(description = "Deleting duck",enabled = true)
    @CitrusTest
    public void DeleteDuckDb(@Optional @CitrusResource TestCaseRunner runner)
    {
        deleteDuckFromDb(runner);
        runner.variable("duckId","1234567");
        databaseUpdate(runner,
                "insert into DUCK (id, color, height, material, sound, wings_state)\n" +
                        "values (${duckId},'yellow',2.0,'wood','quack','ACTIVE');");
        deleteDuck(runner,"${duckId}");
        validateResponse(runner,"duckController/deleteDuck/deleteDuck.json", HttpStatus.OK);

    }
}

