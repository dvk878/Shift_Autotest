package autotests.duckActionController;
import autotests.clients.DuckActionsClient;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class FlyDuck extends DuckActionsClient {

    @Test(description = "Полет уточки с правильным (существующим) id и с активными крыльями: ACTIVE", enabled = true)
    @CitrusTest
    public void ActiveWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "ACTIVE");
        getDuckId(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"I am flying\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Полет уточки с правильным (существующим) id и со связанными крыльями: FIXED", enabled = true)
    @CitrusTest
    public void FixedWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "FIXED");
        getDuckId(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"I can't fly\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Полет уточки с правильным (существующим) id и с крыльями в неопределенном состоянии: ACTIVE", enabled = true)
    @CitrusTest
    public void UndefinedWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        createDuck(runner, "yellow", 0.15, "rubber", "quack", "UNDEFINED");
        getDuckId(runner);
        duckFly(runner, "${duckId}");
        validateResponse(runner, "{\n" + "  \"message\": \"Wings are not detected :(\"\n" + "}");
        deleteDuck(runner, "${duckId}");
    }


}