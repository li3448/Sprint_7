package ru.yandex.praktikum.service;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;
//класс для сервисных параметров
public class Service {
    public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/";
    public final static boolean NEED_DETAIL_LOG = true;

    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URI)
                .setContentType(ContentType.JSON)
                .build();
    }
}