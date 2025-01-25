package autotests.duckActionController;
import autotests.clients.DuckActionsClient;
import autotests.payloads.createDuck.CreateDuckPayload;
import autotests.payloads.createDuck.WingsState;
import autotests.payloads.propertiesDuck.PropertiesDuckPayload;
import autotests.payloads.propertiesDuck.PropertiesWingsState;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static java.lang.Double.parseDouble;

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
        validateResponseOkWoodenDuck(runner);
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

        PropertiesDuckPayload properties = new PropertiesDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(PropertiesWingsState.ACTIVE);

        getDuckId(runner);
        oddDuckId(runner,crDuck);
        showProperties(runner, "${duckId}");
        validateResponseOkMessageAnswer(runner,properties);
        deleteDuck(runner, "${duckId}");
    }


}
