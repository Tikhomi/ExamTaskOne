package ApiSteps;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.Тогда;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;

public class RickAndMorty {
        public static String charId;
        public static int lastEpisode;
        public static int episode;
        public static int lastCharacter;
        public static int idLastCharacter;
        public static String speciesLast;
        public static String speciesMorty;
        public static String nameLocationMorty;
        public static String nameLocation;

        @Дано("Получение персонажа по id")
        public static void getCharacter(){
            Response gettingChar = given()
                    .baseUri(Utils.Util.getConfig("urlRickAndMorty"))
                    .when()
                    .get("/character/" + Utils.Util.getConfig("idMorty"))
                    .then()
                    .extract()
                    .response();
            charId = new JSONObject(gettingChar.getBody().asString()).get("id").toString();
            speciesMorty = new JSONObject(gettingChar.getBody().asString()).get("species").toString();
            JSONObject locationMorty = new JSONObject(gettingChar.getBody().asString()).getJSONObject("location");
            nameLocationMorty = locationMorty.get("name").toString();
        }

        @Затем("Получение последнего эпизода")
        public static void getEpisode(){
            Response gettingLastEpisode = given()
                    .baseUri(Utils.Util.getConfig("urlRickAndMorty"))
                    .when()
                    .get("/character/" + charId)
                    .then()
                    .extract()
                    .response();
            episode = (new JSONObject(gettingLastEpisode.getBody().asString()).getJSONArray("episode").length()-1);
            lastEpisode = Integer.parseInt(new JSONObject(gettingLastEpisode.getBody().asString()).getJSONArray("episode")
                    .get(episode).toString().replaceAll("[^0-9]",""));
        }

        @Дано("Получение id последнего персонажа эпизода")
        public static void getIdPerson(){
            Response gettingIdLastPerson = given()
                    .baseUri(Utils.Util.getConfig("urlRickAndMorty"))
                    .when()
                    .get("/episode/" + lastEpisode )
                    .then()
                    .extract()
                    .response();
            lastCharacter = (new JSONObject(gettingIdLastPerson.getBody().asString()).getJSONArray("characters").length()-1);
            idLastCharacter = Integer.parseInt(new JSONObject(gettingIdLastPerson.getBody().asString()).getJSONArray("characters")
                    .get(lastCharacter).toString().replaceAll("[^0-9]",""));
        }

        @Затем("Получение локации последнего персонажа эпизода")
        public static void getLocationPerson(){
            Response gettingLocationPerson = given()
                    .baseUri(Utils.Util.getConfig("urlRickAndMorty"))
                    .when()
                    .get("/character/" + idLastCharacter )
                    .then()
                    .extract()
                    .response();
            JSONObject location = (new JSONObject(gettingLocationPerson.getBody().asString()).getJSONObject("location"));
            nameLocation = location.get("name").toString();
            speciesLast = new JSONObject(gettingLocationPerson.getBody().asString()).get("species").toString();
        }
        @Тогда("Проверка расы")
        public static void raceCheck(){
            Assertions.assertEquals(speciesMorty, speciesLast);
        }

        @Тогда("Проверка локации")
        public static void locationCheck(){
            try {
                Assertions.assertEquals(nameLocation, nameLocationMorty,"Локации не совпадают!");
            } catch (Throwable t) {
            }
        }
}
