package autotests;

import com.consol.citrus.TestCaseRunner;
import com.consol.citrus.http.client.HttpClient;
import com.consol.citrus.message.builder.ObjectMappingPayloadBuilder;
import com.consol.citrus.testng.spring.TestNGCitrusSpringSupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.context.ContextConfiguration;

import static com.consol.citrus.actions.ExecuteSQLQueryAction.Builder.query;
import static com.consol.citrus.dsl.MessageSupport.MessageBodySupport.fromBody;
import static com.consol.citrus.http.actions.HttpActionBuilder.http;

@ContextConfiguration(classes = {EndpointConfig.class})
public class BaseTest extends TestNGCitrusSpringSupport {
    @Autowired
    protected HttpClient duckService;

    @Autowired
    protected SingleConnectionDataSource testDb;


    protected void sendDeleteRequest(TestCaseRunner runner, HttpClient URL, String path, String id) {
        runner.$(http().client(URL)
                .send()
                .delete(path)
                .queryParam("id", id));
    }

    protected void sendGetRequest(TestCaseRunner runner, HttpClient URL, String path, String id){
        runner.$(http().client(URL)
                .send()
                .get(path)
                .queryParam("id", id));
    }

    protected void sendPostRequest(TestCaseRunner runner, HttpClient URL, String path, Object body){
        runner.$(http().client(URL)
                .send()
                .post(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ObjectMappingPayloadBuilder(body, new ObjectMapper())));
    }

    protected void sendPutRequest(TestCaseRunner runner, HttpClient URL, String path, String id, String color, String height, String material, String sound, String wingsState){
        runner.$(http().client(URL)
                .send()
                .put(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id",id)
                .queryParam("color",color)
                .queryParam("height",height)
                .queryParam("material",material)
                .queryParam("sound",sound)
                .queryParam("wingsState",wingsState));
    }

    protected void sendGetRequestQuack(TestCaseRunner runner, HttpClient URL, String path, String id, String repetitionCount, String soundCount){
        runner.$(http().client(URL)
                .send()
                .get(path)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("id",id)
                .queryParam("repetitionCount",repetitionCount)
                .queryParam("soundCount",soundCount));
    }

    protected void validateResponseResource(TestCaseRunner runner, HttpClient URL, HttpStatus status, String expectedPayload){
        runner.$(http()
                .client(URL)
                .receive()
                .response(status)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ClassPathResource(expectedPayload)));
    }

    protected void validateResponsePayload(TestCaseRunner runner, HttpClient URL, HttpStatus status, Object expectedPayload){
        runner.$(http().client(duckService)
                .receive()
                .response(status)
                .message()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract(fromBody().expression("$.id", "duckId"))
                .body(new ObjectMappingPayloadBuilder(expectedPayload,new ObjectMapper())));
    }

    protected void validateDuckInDb(TestCaseRunner runner, String id, String color, String height,
                                    String material, String sound, String wingsState){
        runner.$(query(testDb)
                .statement("SELECT * FROM DUCK WHERE ID=" + id)
                .validate("COLOR",color)
                .validate("HEIGHT",height)
                .validate("MATERIAL",material)
                .validate("SOUND",sound)
                .validate("WINGS_STATE",wingsState));
    }
}
