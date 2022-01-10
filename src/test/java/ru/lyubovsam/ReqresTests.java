package ru.lyubovsam;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresTests {

    @BeforeAll
    static void SetUp(){
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void successfulLoginTest(){
        String data = "{     \"email\": \"eve.holt@reqres.in\",     \"password\": \"cityslicka\" }";
        given().
                body(data)
                .contentType(JSON)
                .when()
                .post("/api/login")
                .then()
                .statusCode(200)
                .body("token" , is("QpwL5tke4Pnpja7X4"));
    }
    @Test
    void getListUsersTest(){

        given()
                .contentType(JSON)
                .when()
                .get("/api/users?page=2")
                .then()
                .statusCode(200);
    }
    @Test
    void singleUserNotFoundTest(){
        given()
                .contentType(JSON)
                .when()
                .get("/api/users/23")
                .then()
                .statusCode(404);
    }
    @Test
    void createUserTest(){
        String data = "{     \"name\": \"morpheus\",     \"job\": \"leader\" }";
        given()
                .body(data)
                .contentType(JSON)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .body("name", is("morpheus"), "job",
                        is("leader"), "id", notNullValue());
    }
    @Test
    void updateUserTest(){
        String data = "{     \"name\": \"morpheus\",     \"job\": \"zion resident\" }";
        given()
                .body(data)
                .contentType(JSON)
                .when()
                .put("/api/users/2")
                .then().log().all()
                .statusCode(200)
                .body("name", is("morpheus"), "job",
                        is("zion resident"));
    }
}
