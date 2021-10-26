package steps.widget;

import com.google.inject.Inject;
import constants.Endpoints;
import constants.Path;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import utilities.RestAssuredUtilities;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.fail;

@ScenarioScoped
public class widgetSteps {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    private static Response response;



    @Inject
    public widgetSteps() {

        requestSpec = RestAssuredUtilities.getRequestSpecification();
        requestSpec.baseUri(Path.BASE_URI);
        responseSpec = RestAssuredUtilities.getResponseSpecification();

    }

    @Given("I perform conversations endpoint GET call for a {string} widget id with a solvemate id header {string}")
    public void getCallForAWidgetIdWithASolvemateIdHeader(String projectId, String solvemateIdHeader) {

         response = given()
                .contentType("application/json")
                .spec(requestSpec)
                .header("solvemate-user-id", solvemateIdHeader)
                .pathParam("projectId", projectId)
                .when()
                .get(Endpoints.WIDGET_CONVERSATION_ENDPOINT)
                .then()
                .time(lessThan(5L), TimeUnit.SECONDS)
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }

    @Then("I should see {int} Widget response status")
    public void verifyWidgetResponseStatus(int statusCode) {
        assertThat("Status code is not 200 ", response.statusCode() , is(statusCode));
    }

    @And("I should be able to retrieve the conversations endpoint response")
    public void verifyTheConversationsEndpointResponse() {
        JsonPath jsPath = RestAssuredUtilities.getJsonPath(response);

        Assert.assertEquals("Code name does not match ", jsPath.get("created_at[0]").toString(), "2021-10-26T07:04:13.862000+00:00");
        Assert.assertEquals("Id does not match ", jsPath.get("id[0]").toString(), "6177a86db39929d35afdce32");
        Assert.assertEquals("Name does not match ", jsPath.get("language[0]").toString(), "en");
        Assert.assertEquals("Short Code does not match ", jsPath.get("updated_at[0]").toString(), "2021-10-26T07:04:13.956000+00:00");


    }

    @Given("I perform endpoint GET call for a {string} widget id without a solvemate id header")
    public void getCallForAWidgetIdWithoutASolvemateIdHeader(String projectId) {
        response = given()
                .contentType("application/json")
                .spec(requestSpec)
                .pathParam("projectId", projectId)
                .when()
                .get(Endpoints.WIDGET_CONVERSATION_ENDPOINT)
                .then()
                .time(lessThan(5L), TimeUnit.SECONDS)
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }


    @And("I should see the Widget error message {string}")
    public void verifyWidgetErrorMessage(String errorMessage) {
        JsonPath jsPath = RestAssuredUtilities.getJsonPath(response);
        Assert.assertEquals("Error message does not match ", jsPath.get("messages").toString(), errorMessage);
    }

    @Given("I perform POST call with a project id {string} to receive start of the conversation response")
    public void postCallWithAProjectIdToReceiveStartOfTheConversationResponse(String projectId) {
        response = given()
                .contentType("application/json")
                .spec(requestSpec)
                .header("solvemate-user-id", "176d1b31-3233-11ec-ac48-c1ec41cba115")
                .pathParam("projectId", projectId)
                .queryParam("is_testing", "true")
                .queryParam("language_code", "en")
                .queryParam("dry_run", "true")
                .queryParam("integration_type", "beacon")
                .when()
                .post(Endpoints.WIDGET_CONVERSATION_ENDPOINT)
                .then()
                .body("" , hasKey("created_at"))
                .body("" , hasKey("id"))
                .body("", hasKey("updated_at"))
                .time(lessThan(5L), TimeUnit.SECONDS)
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }

    @And("I should be able to see {string} project Id start of conversation response")
    public void verifyProjectIdStartOfConversationResponse(String projectId) {
        JsonPath jsPath = RestAssuredUtilities.getJsonPath(response);

        if(projectId.equals("6163fb72e81bcf4c14b5590f")) {
            Assert.assertEquals("language does not match ", jsPath.get("language").toString(), "en");
            Assert.assertNull("messages[0].data.body value is not null ", jsPath.get("messages[0].data.body"));
            Assert.assertEquals("messages[0].data.choices[0].data.payload value does not match ", jsPath.get("messages[0].data.choices[0].data.payload").toString(), "feature-616433ea1eccd58342f2b2e7-y");
            Assert.assertNull("messages[0].data.choices[0].data.template value is not null ", jsPath.get("messages[0].data.choices[0].data.template"));
            Assert.assertEquals("messages[0].data.choices[0].data.title value does not match ", jsPath.get("messages[0].data.choices[0].data.title").toString(), "<p>Science</p>");
            Assert.assertEquals("messages[0].data.choices[0].type value does not match ", jsPath.get("messages[0].data.choices[0].type").toString(), "answer");

            Assert.assertEquals("messages[0].data.choices[1].data.payload value does not match ", jsPath.get("messages[0].data.choices[1].data.payload").toString(), "feature-616433ea1eccd58342f2b2e9-y");
            Assert.assertNull("messages[0].data.choices[1].data.template value is not null ", jsPath.get("messages[0].data.choices[1].data.template"));
            Assert.assertEquals("messages[0].data.choices[1].data.title value does not match ", jsPath.get("messages[0].data.choices[1].data.title").toString(), "<p>Google</p>");
            Assert.assertEquals("messages[0].data.choices[1].type value does not match ", jsPath.get("messages[0].data.choices[1].type").toString(), "answer");

            Assert.assertEquals("messages[0].data.choices[2].data.payload value does not match ", jsPath.get("messages[0].data.choices[2].data.payload").toString(), "feature-616433ea1eccd58342f2b2eb-y");
            Assert.assertNull("messages[0].data.choices[2].data.template value is not null ", jsPath.get("messages[0].data.choices[2].data.template"));
            Assert.assertEquals("messages[0].data.choices[2].data.title value does not match ", jsPath.get("messages[0].data.choices[2].data.title").toString(), "<p>3.14</p>");
            Assert.assertEquals("messages[0].data.choices[2].type value does not match ", jsPath.get("messages[0].data.choices[2].type").toString(), "answer");

            Assert.assertEquals("messages[0].data.choices[3].data.payload value does not match ", jsPath.get("messages[0].data.choices[3].data.payload").toString(), "feature-616433ea1eccd58342f2b2ed-y");
            Assert.assertNull("messages[0].data.choices[3].data.template value is not null ", jsPath.get("messages[0].data.choices[3].data.template"));
            Assert.assertEquals("messages[0].data.choices[3].data.title value does not match ", jsPath.get("messages[0].data.choices[3].data.title").toString(), "<p>Test</p>");
            Assert.assertEquals("messages[0].data.choices[3].type value does not match ", jsPath.get("messages[0].data.choices[3].type").toString(), "answer");

            Assert.assertNull("messages[0].image value is not null ", jsPath.get("messages[0].data.image"));
            Assert.assertNull("messages[0].template value is not null ", jsPath.get("messages[0].data.template"));
            Assert.assertEquals("messages[0].title value does not match ", jsPath.get("messages[0].data.title").toString(), "<p>How can I help you?</p>");

            Assert.assertEquals("messages[0].type value does not match ", jsPath.get("messages[0].type").toString(), "question");

            Assert.assertEquals("project_id value does not match ", jsPath.get("project_id").toString(), "6163fb72e81bcf4c14b5590f");

            Assert.assertEquals("user_id value does not match ", jsPath.get("user_id").toString(), "176d1b31-3233-11ec-ac48-c1ec41cba115");
            Assert.assertNull("verified value is not null ", jsPath.get("verified"));

        } else if(projectId.equals("123")) {

        } else {
            fail("Unsupported Project Id" + projectId);
        }
    }
}
