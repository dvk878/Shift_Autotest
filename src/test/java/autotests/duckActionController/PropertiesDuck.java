package autotests.duckActionController;
import autotests.clients.DuckActionsClient;
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

        runner.variable("color", "yellow");
        runner.variable("height", "23.0");
        runner.variable("material", "wood");
        runner.variable("sound", "quack");
        runner.variable("wingsState", "FIXED");

        createDuck(runner, "${color}", parseDouble("23.0"), "${material}", "${sound}", "${wingsState}");
        getDuckId(runner);
        evenDuckId(runner);
        showProperties(runner, "${duckId}");
        validateResponse(runner,
                "{" + "  \"color\": \"" + "${color}" + "\","
                        + "  \"height\": " + 23.0 + ","
                        + "  \"material\": \"" + "${material}" + "\","
                        + "  \"sound\": \"" + "${sound}" + "\","
                        + "  \"wingsState\": \"" +"${wingsState}"
                        + "\"" + "}");
        deleteDuck(runner, "${duckId}");
    }
    
    @Test(description = "Просмотр свойств уточки с нечетным (существующим) id и утка с material=rubber", enabled = true)
    @CitrusTest
    public void showOddIdRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("color", "yellow");
        runner.variable("height", "23.0");
        runner.variable("material", "rubber");
        runner.variable("sound", "quack");
        runner.variable("wingsState", "FIXED");

        createDuck(runner, "${color}", parseDouble("23"), "${material}", "${sound}", "${wingsState}");
        getDuckId(runner);
        oddDuckId(runner);
        showProperties(runner, "${duckId}");
        validateResponse(runner,
                "{" + "  \"color\": \"" + "${color}" + "\","
                        + "  \"height\": " + 23.0 + ","
                        + "  \"material\": \"" + "${material}" + "\","
                        + "  \"sound\": \"" + "${sound}" + "\","
                        + "  \"wingsState\": \"" +"${wingsState}"
                        + "\"" + "}");
        deleteDuck(runner, "${duckId}");
    }


}
