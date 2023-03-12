package Test;

import Hooks.ApiHook;
import io.qameta.allure.Epic;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.IOException;

import static ApiSteps.ApiSteps.*;
@Epic("API Тесты.")
public class ApiTest extends ApiHook {

    @DisplayName("Тесты Рик и морти")
    @Test
    public void task1 () {
        getCharacter("2");
        getEpisode();
        getIdPerson();
        getLocationPerson();
        raceCheck();
        locationCheck();
    }

    @DisplayName("Создание пользователя")
    @Test
    public void task2 () throws IOException {
        sendRequest();
        checkData();
    }
}
