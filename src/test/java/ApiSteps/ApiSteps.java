package ApiSteps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class ApiSteps {
    public static String charId;
    public static int lastEpisode;
    public static int episode;
    public static int lastCharacter;
    public static int idLastCharacter;
    public static String speciesLast;
    public static String speciesMorty;
    public static String nameLocationMorty;
    public static String nameLocation;
    public static String nameCheck1;
    public static String jobCheck1;
    public static String nameCheck2;
    public static String jobCheck2;
    public static JSONObject body;

    @Step("Получаем персоонажа по номеру: {id}")
    public static void getCharacter(String id){
        Response gettingChar = given()
                .baseUri("https://rickandmortyapi.com/api")
                .when()
                .get("/character/" + id)
                .then()
                .extract()
                .response();
        charId = new JSONObject(gettingChar.getBody().asString()).get("id").toString();
        speciesMorty = new JSONObject(gettingChar.getBody().asString()).get("species").toString();
        JSONObject locationMorty = new JSONObject(gettingChar.getBody().asString()).getJSONObject("location");
        nameLocationMorty = locationMorty.get("name").toString();
    }

    @Step("Получение последнего эпизода")
    public static void getEpisode(){
        Response gettingLastEpisode = given()
                .baseUri("https://rickandmortyapi.com/api")
                .when()
                .get("/character/" + charId)
                .then()
                .extract()
                .response();
        episode = (new JSONObject(gettingLastEpisode.getBody().asString()).getJSONArray("episode").length()-1);
        lastEpisode = Integer.parseInt(new JSONObject(gettingLastEpisode.getBody().asString()).getJSONArray("episode")
                .get(episode).toString().replaceAll("[^0-9]",""));
    }

    @Step("Получение id последнего персонажа {lastEpisode} эпизода")
    public static void getIdPerson(){
        Response gettingIdLastPerson = given()
                .baseUri("https://rickandmortyapi.com/api")
                .when()
                .get("/episode/" + lastEpisode )
                .then()
                .extract()
                .response();
        lastCharacter = (new JSONObject(gettingIdLastPerson.getBody().asString()).getJSONArray("characters").length()-1);
        idLastCharacter = Integer.parseInt(new JSONObject(gettingIdLastPerson.getBody().asString()).getJSONArray("characters")
                .get(lastCharacter).toString().replaceAll("[^0-9]",""));
    }

    @Step("Получение локации персонажа с id {idLastCharacter}")
    public static void getLocationPerson(){
        Response gettingLocationPerson = given()
                .baseUri("https://rickandmortyapi.com/api")
                .when()
                .get("/character/" + idLastCharacter )
                .then()
                .extract()
                .response();
        JSONObject location = (new JSONObject(gettingLocationPerson.getBody().asString()).getJSONObject("location"));
        nameLocation = location.get("name").toString();
        speciesLast = new JSONObject(gettingLocationPerson.getBody().asString()).get("species").toString();
    }
    @Step("Проверка расы")
    public static void raceCheck(){
        Assertions.assertEquals(speciesMorty, speciesLast);
    }

    @Step("Проверка локации")
    public static void locationCheck(){
        try {
            Assertions.assertEquals(nameLocation, nameLocationMorty,"Локации не совпадают!");
        } catch (Throwable t) {
            throw new RuntimeException("Failed to read file");
        }
    }

    @Step("Отправка на регресс")
    public static void sendRequest() throws IOException {
        body = new JSONObject(new String(Files.readAllBytes(Paths.get("src/test/resources/json/create.json"))));
        body.put("name", "Tomato");
        body.put("job", "Eat market");
        Response sendingRequest = given()
                .header("Content-type", "application/json")
                .baseUri("https://reqres.in/api")
                .body(body.toString())
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
    @Step("Проверка результатов")
    public static void checkData(){
        Assertions.assertEquals( nameCheck1, nameCheck2,"Значение ключа name не совпадают!");
        Assertions.assertEquals( jobCheck1, jobCheck2,"Значение ключа job не совпадают!");
    }
}