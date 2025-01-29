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
@Feature("Endpoint /api/duck/update")
public class UpdateDuck extends DuckActionsClient {

    @Test(description = "Изменение цвета и высоты уточки",enabled = true)
    @CitrusTest
    public void UpdateColorAndHeight(@Optional @CitrusResource TestCaseRunner runner)
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
        updateDuck(runner,"${duckId}","red","3.0","rubber","quack","ACTIVE");
        validateResponse(runner, "duckController/updateDuck/updatingValues.json", HttpStatus.OK);
        deleteDuck(runner,"${duckId}");
    }

    @Test(description = "Изменение цвета и звука уточки",enabled = true)
    @CitrusTest
    public void UpdateColorAndSound(@Optional @CitrusResource TestCaseRunner runner)
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
        updateDuck(runner,"${duckId}","red","2.0","rubber","roar","ACTIVE");
        validateResponse(runner, "duckController/updateDuck/updatingValues.json", HttpStatus.OK);
        deleteDuck(runner,"${duckId}");
    }
}
