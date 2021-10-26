package steps.app;

import com.google.inject.Inject;
import constants.Endpoints;
import constants.Path;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import steps.CommonSteps;
import utilities.RestAssuredUtilities;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.fail;

@ScenarioScoped
public class appSteps {

    private static Response response;

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;


    public static String loginToken;

    @Inject
    public appSteps() {

        requestSpec = RestAssuredUtilities.getRequestSpecification();
        requestSpec.baseUri(Path.APP_BASE_URI);
        responseSpec = RestAssuredUtilities.getResponseSpecification();

    }

    @When("I perform GET call to Organizations endpoint to retrieve all organizations")
    public void getCallToOrganizationsEndpointToRetrieveAllOrganizations() {
        response = given()
                .contentType("application/json")
                .spec(requestSpec)
                .header("authorization", "Bearer " + CommonSteps.loginToken)
                .when()
                .get(Endpoints.APP_GET_ALL_ORGANIZATIONS)
                .then()
                .time(lessThan(5L), TimeUnit.SECONDS)
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }

    @And("I should be able to verify Organizations")
    public void verifyOrganizations() {
        JsonPath jsPath = RestAssuredUtilities.getJsonPath(response);

        Assert.assertNull("active_subscription value is not null ", jsPath.get("active_subscription[0]"));
        Assert.assertEquals("Id does not match ", jsPath.get("id[0]").toString(), "6163fb4ddddabaa38ff694c2");
        Assert.assertEquals("is_deleted value does not match ", jsPath.get("is_deleted[0]"), false);
        Assert.assertEquals("title value does not match ", jsPath.get("title[0]").toString(), "TestDevLab");

    }

    @When("I perform GET call to Projects endpoint to retrieve all projects")
    public void getCallToProjectsEndpointToRetrieveAllProjects() {
        response = given()
                .contentType("application/json")
                .spec(requestSpec)
                .header("authorization", "Bearer " + CommonSteps.loginToken)
                .when()
                .get(Endpoints.APP_GET_ALL_PROJECTS)
                .then()
                .time(lessThan(5L), TimeUnit.SECONDS)
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }


    @Then("I should see {int} App response status")
    public void verifyAppResponseStatus(int statusCode) {
        assertThat("Status code is not: " + statusCode, response.getStatusCode(), is(statusCode));

    }

    @And("I should be able to verify Projects")
    public void verifyAllProjects() {
        JsonPath jsPath = RestAssuredUtilities.getJsonPath(response);

        Assert.assertEquals("Creation date does not match ", jsPath.get("created_at[1]").toString(), "2021-10-11T08:53:06.700000");
        Assert.assertNull("Description value is not null ", jsPath.get("description[1]"));
        Assert.assertEquals("Id does not match ", jsPath.get("id[1]").toString(), "6163fb72e81bcf4c14b5590f");
        Assert.assertEquals("is_active value does not match ", jsPath.get("is_active[1]"), true);
        Assert.assertEquals("languages.code value does not match ", jsPath.get("languages[1].code[0]").toString(), "en");
        Assert.assertEquals("languages.is_active value does not match ", jsPath.get("languages[1].is_active[0]"), true);
        Assert.assertEquals("languages.is_default value does not match ", jsPath.get("languages[1].is_default[0]"), true);
        Assert.assertEquals("languages.nlp.is_active value does not match ", jsPath.get("languages[1].nlp[0].is_active"), true);
        Assert.assertEquals("languages.nlp.percentage value does not match ", jsPath.get("languages[1].nlp[0].percentage").toString(), "100");
        Assert.assertNull("languages.variation_code value is not null ", jsPath.get("languages[1].variation_code[0]"));

    }


    @When("I perform GET call for specific project with id {string}")
    public void getCallForSpecificProjectWithId(String projectId) {
        response = given()
                .contentType("application/json")
                .spec(requestSpec)
                .header("authorization", "Bearer " + CommonSteps.loginToken)
                .pathParam("projectId", projectId)
                .when()
                .get(Endpoints.APP_GET_SPECIFIC_PROJECT)
                .then()
                .time(lessThan(5L), TimeUnit.SECONDS)
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }

    @Then("I should be able to see the Project {string} details")
    public void verifySpecificProjectDetails(String projectId) {
        assertThat("Status code is not 200 ", response.getStatusCode(), is(200));
        JsonPath jsPath = RestAssuredUtilities.getJsonPath(response);

        if(projectId.equals("6163fdba0413a8aa1ad82e20")) {
            Assert.assertEquals("created_at does not match ", jsPath.get("created_at").toString(), "2019-09-13T07:56:01.816000");
            Assert.assertNull("Description value is not null ", jsPath.get("description"));
            Assert.assertEquals("Id does not match ", jsPath.get("id").toString(), "6163fdba0413a8aa1ad82e20");
            Assert.assertEquals("is_active value does not match ", jsPath.get("is_active"), true);
            Assert.assertEquals("languages.code value does not match ", jsPath.get("languages.code[0]").toString(), "en");
            Assert.assertEquals("languages.is_active value does not match ", jsPath.get("languages.is_active[0]"), true);
            Assert.assertEquals("languages.is_default value does not match ", jsPath.get("languages.is_default[0]"), true);
            Assert.assertEquals("languages.nlp.is_active value does not match ", jsPath.get("languages.nlp[0].is_active"), false);
            Assert.assertEquals("languages.nlp.percentage value does not match ", jsPath.get("languages.nlp[0].percentage").toString(), "20");
            Assert.assertNull("languages.variation_code value is not null ", jsPath.get("languages.variation_code[0]"));
            Assert.assertEquals("org_id does not match ", jsPath.get("org_id").toString(), "6163fb4ddddabaa38ff694c2");
            Assert.assertNull("question_group_config value is not null ", jsPath.get("question_group_config"));
            Assert.assertEquals("released_at does not match ", jsPath.get("released_at").toString(), "2019-09-13T05:56:01.816000");
            Assert.assertEquals("title does not match ", jsPath.get("title").toString(), "Moda bot");
            Assert.assertEquals("updated_at does not match ", jsPath.get("updated_at").toString(), "2021-10-12T09:11:48.438000");

            Assert.assertEquals("widget_2 value does not match ", jsPath.get("widget_2"), true);
            Assert.assertEquals("widget_settings.background value does not match ", jsPath.get("widget_settings.background").toString(), "background-3");
            Assert.assertEquals("widget_settings.beacon_display_delay value does not match ", jsPath.get("widget_settings.beacon_display_delay").toString(), "0");
            Assert.assertEquals("widget_settings.is_branding_enabled value does not match ", jsPath.get("widget_settings.is_branding_enabled"), false);
            Assert.assertEquals("widget_settings.launcher_logo value does not match ", jsPath.get("widget_settings.launcher_logo").toString(), "beacon-chat");
            Assert.assertEquals("widget_settings.media_url value does not match ", jsPath.get("widget_settings.media_url").toString(), "https://images.solvemate.com/6163fdba0413a8aa1ad82e20/Project-611cd6dffbb5b89e686ff99c-1629280521.jpeg");
            Assert.assertEquals("widget_settings.primary_color value does not match ", jsPath.get("widget_settings.primary_color").toString(), "#99A285");
            Assert.assertEquals("widget_settings.theme value does not match ", jsPath.get("widget_settings.theme").toString(), "light");
            Assert.assertEquals("widget_settings.title value does not match ", jsPath.get("widget_settings.title").toString(), "Moda\uD83E\uDD16");

        } else if(projectId.equals("123")) {

        } else {
            fail("Unsupported Project Id" + projectId);
        }
    }

    @And("I should see the App error message {string}")
    public void verifyTheAppErrorMessage(String errorMessage) {
        JsonPath jsPath = RestAssuredUtilities.getJsonPath(response);
        Assert.assertEquals("Error message does not match ", jsPath.get("messages").toString(), errorMessage);
    }

    @When("I perform GET call to receive logged in user details with user id {string}")
    public void getCallToReceiveLoggedInUserDetailsWithUserId(String userID) {
        response = given()
                .contentType("application/json")
                .spec(requestSpec)
                .header("authorization", "Bearer " + CommonSteps.loginToken)
                .pathParam("userId", userID)
                .when()
                .get(Endpoints.APP_GET_LOGGED_IN_USER_DETAILS)
                .then()
                .time(lessThan(5L), TimeUnit.SECONDS)
                .spec(responseSpec)
                .log().all()
                .extract().response();
    }

    @Then("I should be able to see the logged {string} user details")
    public void iShouldBeAbleToSeeTheLoggedUserDetails(String userId) {
        assertThat("Status code is not 200 ", response.getStatusCode(), is(200));
        JsonPath jsPath = RestAssuredUtilities.getJsonPath(response);

        if(userId.equals("6163fe53a8cac314d3d3e4c3")) {
            Assert.assertEquals("created_at does not match ", jsPath.get("created_at").toString(), "2021-10-11T09:05:23.799000+00:00");
            Assert.assertEquals("Email does not match ", jsPath.get("email").toString(), "renalds.trakims@testdevlab.com");
            Assert.assertEquals("first_name does not match ", jsPath.get("first_name").toString(), "Renalds");
            Assert.assertEquals("Id does not match ", jsPath.get("id").toString(), "6163fe53a8cac314d3d3e4c3");
            Assert.assertEquals("is_active value does not match ", jsPath.get("is_active"), true);
            Assert.assertEquals("is_confirmed value does not match ", jsPath.get("is_confirmed"), true);
            Assert.assertEquals("is_staff value does not match ", jsPath.get("is_staff"), false);
            Assert.assertEquals("last_name does not match ", jsPath.get("last_name").toString(), "Trakims");

        } else if(userId.equals("123")) {
        } else {
            fail("Unsupported User Id" + userId);
        }
    }
}
