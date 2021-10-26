package utilities;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class RestAssuredUtilities {

    public static String ENDPOINT;
    public static RequestSpecBuilder REQUEST_BUILDER;
    public static RequestSpecification REQUEST_SPEC;
    public static ResponseSpecBuilder RESPONSE_BUILDER;
    public static ResponseSpecification RESPONSE_SPEC;

    public static void setEndPoint(String endpoint) {
        ENDPOINT = endpoint;
    }

    public  static String getENDPOINT() {
        return getENDPOINT();
    }

    public static RequestSpecification getRequestSpecification() {
        REQUEST_BUILDER = new RequestSpecBuilder();
        REQUEST_SPEC = REQUEST_BUILDER.build();
        return REQUEST_SPEC;
    }

    public static ResponseSpecification getResponseSpecification() {
        RESPONSE_BUILDER = new ResponseSpecBuilder();
//        RESPONSE_BUILDER.expectResponseTime(lessThan(5L), TimeUnit.SECONDS);
        RESPONSE_SPEC = RESPONSE_BUILDER.build();

        return RESPONSE_SPEC;
    }

    public static RequestSpecification createQueryParam(RequestSpecification rspec, String param, String value) {
        return rspec.queryParam(param, value);
    }

    public static RequestSpecification createQueryParam(RequestSpecification rspec, HashMap<String, String> queryMap) {
        return rspec.queryParams(queryMap);
    }

    public static RequestSpecification createQueryParam(RequestSpecification rspec, String param, Boolean value) {
        return rspec.queryParam(param, value);
    }

    public static RequestSpecification createPathParam(RequestSpecification rspec, String param, String value) {
        return rspec.pathParam(param, value);
    }

    public static Response getResponse() {
        return given().get(ENDPOINT);
    }

    public static Response getResponse(RequestSpecification reqSpec, String type) { // here we add .spec(requestSpec) portion (check exmaple from - BothTogetherCleanVersion java class)
        REQUEST_SPEC.spec(reqSpec); // with this we can add another spec (global RequestSpecification will add the custom RequestSpec here)
        Response response = null; // we define variable "response" here
        if (type.equalsIgnoreCase("get")) { // here we gonna capture all the response (get, post, put, delete etc)
            response = given().spec(REQUEST_SPEC).get(ENDPOINT);
        } else if (type.equalsIgnoreCase("post")) {
            response = given().spec(REQUEST_SPEC).post(ENDPOINT);
        } else if (type.equalsIgnoreCase("put")) {
            response = given().spec(REQUEST_SPEC).put(ENDPOINT);
        } else if (type.equalsIgnoreCase("delete")) {
            response = given().spec(REQUEST_SPEC).delete(ENDPOINT);
        } else {
            System.out.println("Type is not supported/does not match");
        }
        //response.then().log().all();
        response.then().spec(RESPONSE_SPEC);

        return response;
    }

    public static JsonPath getJsonPath(Response res) {
        String path = res.asString();

        return new JsonPath(path);
    }

    public static XmlPath getXmlPath(Response res) { // get XML Path method
        String path = res.asString();

        return new XmlPath(path);
    }

    public static void resetBasePath() {
        RestAssured.basePath = null; // reset base Path
    }

    public static void contentType(ContentType type) {
        given().contentType(type);
    }
}