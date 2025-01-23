package autotests.duckController;
import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class UpdateDuck extends DuckActionsClient {

    @Test(description = "Изменение цвета и высоты уточки",enabled = true)
    @CitrusTest
    public void UpdateColorAndHeight(@Optional @CitrusResource TestCaseRunner runner)
    {
        createDuck(runner,"yellow",1.0,"wood","quack","FIXED");
        getDuckId(runner);
        updateDuck(runner,"${duckId}","red","2.0","wood","quack","FIXED");
        validateResponse(runner,"{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
        deleteDuck(runner,"${duckId}");
    }

    @Test(description = "Изменение цвета и звука уточки",enabled = true)
    @CitrusTest
    public void UpdateColorAndSound(@Optional @CitrusResource TestCaseRunner runner)
    {
        createDuck(runner,"yellow",1.0,"wood","quack","FIXED");
        getDuckId(runner);
        updateDuck(runner,"${duckId}","red","1.0","wood","roar","FIXED");
        validateResponse(runner,"{\n" + "  \"message\": \"Duck with id = ${duckId} is updated\"\n" + "}");
        deleteDuck(runner,"${duckId}");
    }
}
