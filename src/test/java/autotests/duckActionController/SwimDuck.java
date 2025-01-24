package autotests.duckActionController;
import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class SwimDuck extends DuckActionsClient {

    @Test(description = "Плавание уточки с правильным (существующим) id", enabled = true)
    @CitrusTest
    public void existingDuckIdSwim(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "FIXED");
        getDuckId(runner);
        duckSwim(runner, "${duckId}");
        validateResponseOk(runner, "{\n" + "  \"message\": \"I’m swimming\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Плавание уточки с правильным (несуществующим) id", enabled = true)
    @CitrusTest
    public void nonexistentDuckIdSwim(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "FIXED");
        getDuckId(runner);
        deleteDuck(runner, "${duckId}");
        duckSwim(runner, "${duckId}");
        validateResponseNotFound(runner, "{\n" + "  \"message\": \"Paws are not found ((((\"\n" + "}");
    }

}