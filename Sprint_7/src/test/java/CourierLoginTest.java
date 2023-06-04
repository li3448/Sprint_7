package ru.yandex.praktikum.couriers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.service.Service;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CourierLoginTest {

    private final CourierAPI CourierAPI = new CourierAPI();
    private final Generate_Courier Generate_Courier = new Generate_Courier();
    private Courier courierRandom;

    @Before
    public void setUp() {
        RestAssured.baseURI = Service.BASE_URL;
        courierRandom = Generate_Courier.generateRandom();
    }

    @After
    public void tearDown() {
        try {
            Response responseLogin = courierLogin(courierRandom);
            String courierId = responseLogin.then().extract().path("id").toString();
            courierDelete(courierId);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("Вход курьера без логина")
    @Description("Проверяем невозможность авторизации без логина")
    public void loginCourierWithoutLoginNameTest() {
        courierRandom.setLogin("");
        Response response = courierLogin(courierRandom);
        compareResultMessageToText(response, SC_BAD_REQUEST, "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Вход курьера без пароля")
    @Description("Проверяем невозможность авторизации без пароля")
    public void loginCourierWithoutPasswordTest() {
        courierRandom.setPassword("");
        Response response = courierLogin(courierRandom);
        compareResultMessageToText(response, SC_BAD_REQUEST, "Недостаточно данных для входа");
    }

    @Test
    @DisplayName("Вход курьера без данных")
    @Description("Проверяем невозможность входа незарегистрированного курьера ")
    public void loginCourierNotExistedTest() {
        Response response = courierLogin(courierRandom);
        compareResultMessageToText(response, SC_NOT_FOUND, "Учетная запись не найдена");
    }

    @Test
    @DisplayName("Логин существующего курьера")
    @Description("Авторизация с корректными данными")
    public void PositiveLoginTest() {
        courierCreate(courierRandom);
        Response response = courierLogin(courierRandom);
        compareIdNotNull(response);
    }

    //Создать курьера":
    @Step("Create courier")
    public void courierCreate(Courier courier){
        Response response = courierApi.create(courier);
        printResponseBodyToConsole("Создание курьера: ", response, Service.NEED_DETAIL_LOG);
    }

    // "Авторизация курьера":
    @Step("Login courier")
    public Response courierLogin(Courier courier){
        Response response = courierApi.login(courier);
        printResponseBodyToConsole("Авторизация курьера: ", response, Service.NEED_DETAIL_LOG);
        return response;
    }

    // "Удалить курьера":
    @Step("Delete courier by id")
    public void courierDelete(String courierId){
        Response response = courierApi.delete(courierId);
        printResponseBodyToConsole("Удаление курьера: ", response, Service.NEED_DETAIL_LOG);
    }

    @Step("Compare id is not null")
    public void compareIdNotNull(Response response){
        response
                .then()
                .assertThat()
                .log().all()
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Step("Compare result message to something")
    public void compareResultMessageToText(Response response, int statusCode, String text){
        response
                .then()
                .log().all()
                .statusCode(statusCode)
                .and()
                .assertThat()
                .body("message", is(text));
    }

    //  "Вывести тело ответа в консоль":
    @Step("Print response body to console")
    public void printResponseBodyToConsole(String headerText, Response response, boolean detailedLog){
        if (detailedLog)
            System.out.println(headerText + response.body().asString());
    }

}
