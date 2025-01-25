package autotests.duckController;
import autotests.clients.DuckActionsClient;
import autotests.payloads.CreateDuckPayload;
import autotests.payloads.WingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.apache.commons.lang.ObjectUtils;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class CreateDuck extends DuckActionsClient {

    @Test(description = "Создание уточки со значением material=rubber", enabled = true)
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {

        CreateDuckPayload cr=new CreateDuckPayload()
                .id(null)
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        createDuck(runner, cr);
        validateResponseOk(runner,cr);
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Создание уточки со значением material=wood", enabled = true)
    @CitrusTest
    public void CreateWoodenDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("color", "yellow");
        runner.variable("height", 23.0);
        runner.variable("material", "wood");
        runner.variable("sound", "quack");
        runner.variable("wingsState", "FIXED");

        createDuck(runner, "${color}", 23, "${material}", "${sound}", "${wingsState}");
        validateResponseOk(runner);

        deleteDuck(runner, "${duckId}");

    }

}




