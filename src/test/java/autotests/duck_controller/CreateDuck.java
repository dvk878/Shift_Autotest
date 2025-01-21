package autotests.duck_controller;
import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class CreateDuck extends DuckActionsClient {

    @Test(description = "Создание уточки со значением material=rubber", enabled = true)
    @CitrusTest
    public void createRubberDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("color", "yellow");
        runner.variable("height", "23.0");
        runner.variable("material", "rubber");
        runner.variable("sound", "quack");
        runner.variable("wingsState", "FIXED");

        createDuck(runner, "${color}", Double.parseDouble("23.0"), "${material}", "${sound}", "${wingsState}");
        validateResponse(runner,
                "{" + "  \"color\": \"" + "${color}" + "\","
                                + "  \"height\": " + 23.0 + ","
                                + "  \"material\": \"" + "${material}" + "\","
                                + "  \"sound\": \"" + "${sound}" + "\","
                                + "  \"wingsState\": \"" +"${wingsState}"
                                + "\"" + "}");
        getDuckId(runner);
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Создание уточки со значением material=wood", enabled = true)
    @CitrusTest
    public void CreateWoodenDuck(@Optional @CitrusResource TestCaseRunner runner) {

        runner.variable("color", "yellow");
        runner.variable("height", "23.0");
        runner.variable("material", "wood");
        runner.variable("sound", "quack");
        runner.variable("wingsState", "FIXED");

        createDuck(runner, "${color}", Double.parseDouble("23.0"), "${material}", "${sound}", "${wingsState}");
        validateResponse(runner,
                "{" + "  \"color\": \"" + "${color}" + "\","
                        + "  \"height\": " + 23.0 + ","
                        + "  \"material\": \"" + "${material}" + "\","
                        + "  \"sound\": \"" + "${sound}" + "\","
                        + "  \"wingsState\": \"" +"${wingsState}"
                        + "\"" + "}");
        getDuckId(runner);
        deleteDuck(runner, "${duckId}");

    }

}




