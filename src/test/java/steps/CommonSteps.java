package steps;

import com.google.inject.Inject;
import constants.Endpoints;
import constants.Path;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utilities.RestAssuredUtilities;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ScenarioScoped
public class CommonSteps {

    Response responseLoginToken;

    Response responseStatusCode;

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    public static String loginToken;

    @Inject
    public CommonSteps() {

        requestSpec = RestAssuredUtilities.getRequestSpecification();
        requestSpec.baseUri(Path.APP_BASE_URI);
        responseSpec = RestAssuredUtilities.getResponseSpecification();

    }

    @Given("I login into the Solvemate App")
    public void loginIntoTheSolvemateApp() {
        responseLoginToken = given()
                .spec(requestSpec)
                .param("email", "renalds.trakims@testdevlab.com")
                .param("password", "Secure8RT")
                .when().redirects().follow(false)
                .post(Endpoints.POST_LOGIN)
                .then()
                .statusCode(200)
                .log().all()
                .extract().response();

        JsonPath jsPath = RestAssuredUtilities.getJsonPath(responseLoginToken);
        loginToken = jsPath.get("access_token");

        System.out.println("The token is: " + loginToken);
    }

//    @Then("^I should see \"(.+)\" response status$")
//    public void iShouldSeeResponseStatus(int statusCode) {
//        assertThat("Status code is not 200 ", responseStatusCode.getStatusCode(), is(statusCode));
//    }
}
