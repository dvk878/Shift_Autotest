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

@Epic("Тесты на duckController")
@Feature("Эндпоинт /api/duck/create")
public class CreateDuck extends DuckActionsClient {

    @Test(description = "Создание уточки со значением material=rubber", enabled = true)
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDuckPayload crDuck=new CreateDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        createDuck(runner, crDuck);
        getDuckId(runner);
        validateResponse(runner,crDuck.id(),HttpStatus.OK);
        deleteDuck(runner,"${duckId}");
    }

    @Test(description = "Создание уточки со значением material=wood", enabled = true)
    @CitrusTest
    public void CreateWoodenDuck(@Optional @CitrusResource TestCaseRunner runner) {

        CreateDuckPayload crDuck=new CreateDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("wood")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);
        createDuck(runner, crDuck);
        getDuckId(runner);
        validateResponse(runner,crDuck.id(), HttpStatus.OK);
        deleteDuck(runner,"${duckId}");

    }

}




