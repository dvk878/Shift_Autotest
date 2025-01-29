package autotests.duckActionController;
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
@Epic("duckActionController Tests")
@Feature("Endpoint /api/duck/action/properties")
public class PropertiesDuck extends DuckActionsClient {

    @Test(description = "Просмотр свойств уточки с четным (существующим) id и утка с material=wood", enabled = true)
    @CitrusTest
    public void showEvenIdWoodenDuck(@Optional @CitrusResource TestCaseRunner runner) {

        CreateDuckPayload crDuck=new CreateDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("wood")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        createDuck(runner, crDuck);
        getDuckId(runner);
        evenDuckId(runner,crDuck);
        showProperties(runner, "${duckId}");
        validateResponse(runner, crDuck,HttpStatus.OK);
        deleteDuck(runner, "${duckId}");
    }
    
    @Test(description = "Просмотр свойств уточки с нечетным (существующим) id и утка с material=rubber", enabled = true)
    @CitrusTest
    public void showOddIdRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {

        CreateDuckPayload crDuck=new CreateDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);
        createDuck(runner, crDuck);
        getDuckId(runner);
        oddDuckId(runner,crDuck);
        showProperties(runner, "${duckId}");
        validateResponse(runner,crDuck, HttpStatus.OK);
        deleteDuck(runner, "${duckId}");
    }


}
