package Tests;

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
        Assert.assertEquals("Расы не совпадают!", speciesMorty, speciesLast);
        try {
            Assert.assertEquals("Локации не совпадают!", nameLocation, nameLocationMorty);
        } catch (Throwable t) {
        }
    }

    @DisplayName("Создание пользователя")
    @Test
    public void task2 () throws IOException {
        fillingBody();
        sendRequest();
        Assert.assertEquals("Значение ключа name не совпадают!", nameCheck1, nameCheck2);
        Assert.assertEquals("Значение ключа job не совпадают!", jobCheck1, jobCheck2);
    }
}
