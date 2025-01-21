package autotests.duck_controller;
import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class DeleteDuck extends DuckActionsClient {

    @Test(description = "Удаление уточки",enabled = true)
    @CitrusTest
    public void DeleteDuck(@Optional @CitrusResource TestCaseRunner runner)
    {
        createDuck(runner,"yellow",1.0,"wood","quack","FIXED");
        getDuckId(runner);
        deleteDuck(runner,"${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"Duck is deleted\"\n" + "}");
    }
}

