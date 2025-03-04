package api;

import io.restassured.RestAssured;
import Constants.ConstantData;


public class ApiBase {

    protected static void setUp(){
        RestAssured.baseURI = ConstantData.BURGER_URL;
    }
}