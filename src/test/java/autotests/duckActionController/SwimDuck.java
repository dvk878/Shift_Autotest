package autotests.duckActionController;
import autotests.clients.DuckActionsClient;
import autotests.payloads.createDuck.CreateDuckPayload;
import autotests.payloads.createDuck.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class SwimDuck extends DuckActionsClient {

    @Test(description = "Плавание уточки с правильным (существующим) id", enabled = true)
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
        validateResponseOk(runner, "duckActionController/swimDuck/existingDuckIdSwim.json");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Плавание уточки с правильным (несуществующим) id", enabled = true)
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
        validateResponseNotFound(runner, "duckActionController/swimDuck/nonexistentDuckIdSwim.json");
    }

}