package autotests.duckActionController;
import autotests.clients.DuckActionsClient;
import autotests.payloads.createDuck.CreateDuckPayload;
import autotests.payloads.createDuck.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.springframework.http.HttpStatus;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class QuackDuck extends DuckActionsClient {

    @Test(description = "Корректный звук уточки с правильным (существующим) нечетным id", enabled = true)
    @CitrusTest
    public void CorrectOddIdQuacking(@Optional @CitrusResource TestCaseRunner runner) {
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


    @Test(description = "Корректный звук уточки с правильным (существующим) четным id", enabled = true)
    @CitrusTest
    public void CorrectEvenIdQuacking(@Optional @CitrusResource TestCaseRunner runner) {
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

}
