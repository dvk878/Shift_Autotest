package autotests.duckActionController;
import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class QuackDuck extends DuckActionsClient {

    @Test(description = "Корректный звук уточки с правильным (существующим) нечетным id", enabled = true)
    @CitrusTest
    public void CorrectOddIdQuacking(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "FIXED");
        getDuckId(runner);
        oddDuckId(runner);
        quackDuck(runner, "${duckId}", "1", "1");
        validateResponseOk(runner, "{\n" + "  \"sound\": \"quack\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }


    @Test(description = "Корректный звук уточки с правильным (существующим) четным id", enabled = true)
    @CitrusTest
    public void CorrectEvenIdQuacking(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "FIXED");
        getDuckId(runner);
        evenDuckId(runner);
        quackDuck(runner, "${duckId}", "1", "1");
        validateResponseOk(runner, "{\n" + "  \"sound\": \"quack\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }

}
