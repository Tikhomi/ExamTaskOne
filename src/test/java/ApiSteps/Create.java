package ApiSteps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class Create {
    public static String nameCheck1;
    public static String jobCheck1;
    public static String nameCheck2;
    public static String jobCheck2;
    public static JSONObject body;
    public static String Body1;


    @Дано("Создание пользователя")
    public static void create() throws IOException {
        body = new JSONObject(new String(Files.readAllBytes(Paths.get("src/test/resources/json/create.json"))));
        body.put("name", "Tomato");
        body.put("job", "Eat market");
        Body1 = body.toString();
    }
    @Затем("Отправка POST запроса")
    public static void sendRequest() {
        Response sendingRequest = given()
                .header("Content-type", "application/json")
                .baseUri(Utils.Util.getConfig("urlCreate"))
                .body(Body1)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .response();
        nameCheck1 = (new JSONObject(sendingRequest.getBody().asString()).get("name").toString()).toLowerCase();
        jobCheck1 = (new JSONObject(sendingRequest.getBody().asString()).get("job").toString()).toLowerCase();
        nameCheck2 = (body.get("name").toString()).toLowerCase();
        jobCheck2 = (body.get("job").toString()).toLowerCase();
    }
    @Тогда("Проверка результатов")
    public static void checkData(){
        Assertions.assertEquals( nameCheck1, nameCheck2,"Значение ключа name не совпадают!");
        Assertions.assertEquals( jobCheck1, jobCheck2,"Значение ключа job не совпадают!");
    }
}
