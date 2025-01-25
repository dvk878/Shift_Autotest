package autotests.duckActionController;
import autotests.clients.DuckActionsClient;
import autotests.payloads.createDuck.CreateDuckPayload;
import autotests.payloads.createDuck.WingsState;
import autotests.payloads.messageAnswer;
import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.annotations.CitrusResource;
import com.consol.citrus.annotations.CitrusTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class FlyDuck extends DuckActionsClient {

    @Test(description = "Полет уточки с правильным (существующим) id и с активными крыльями: ACTIVE", enabled = true)
    @CitrusTest
    public void ActiveWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDuckPayload crDuck=new CreateDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.ACTIVE);

        messageAnswer answer = new messageAnswer().message("I’m flying");

        createDuck(runner, crDuck);
        getDuckId(runner);
        duckFly(runner, "${duckId}");
        validateResponseOkMessageAnswer(runner,answer);
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Полет уточки с правильным (существующим) id и со связанными крыльями: FIXED", enabled = true)
    @CitrusTest
    public void FixedWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDuckPayload crDuck=new CreateDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.FIXED);

        createDuck(runner, crDuck);
        getDuckId(runner);
        duckFly(runner, "${duckId}");
        validateResponseOk(runner, "duckActionController/flyDuck/fixedFlyDuck.json");
        deleteDuck(runner, "${duckId}");
    }

    @Test(description = "Полет уточки с правильным (существующим) id и с крыльями в неопределенном состоянии: ACTIVE", enabled = true)
    @CitrusTest
    public void UndefinedWingsFly(@Optional @CitrusResource TestCaseRunner runner) {
        CreateDuckPayload crDuck=new CreateDuckPayload()
                .color("yellow")
                .height(2.0)
                .material("rubber")
                .sound("quack")
                .wingsState(WingsState.UNDEFINED);

        createDuck(runner, crDuck);
        getDuckId(runner);
        duckFly(runner, "${duckId}");
        validateResponseOk(runner, "duckActionController/flyDuck/undefinedFlyDuck.json");
        deleteDuck(runner, "${duckId}");
    }


}