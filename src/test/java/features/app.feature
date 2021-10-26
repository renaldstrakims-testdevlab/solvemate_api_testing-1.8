@appFeature
Feature: Solvemate - App feature
  I should be able to interact with Solvemate App endpoints

  Background:
    Given I login into the Solvemate App

  @getAllOrganizations
  Scenario: I should be able to GET all Organizations I have in the App
    When  I perform GET call to Organizations endpoint to retrieve all organizations
    Then  I should see 200 App response status
    And   I should be able to verify Organizations

  @getAllProjects
  Scenario: I should be able to see all Projects I have in the App
    When  I perform GET call to Projects endpoint to retrieve all projects
    Then  I should see 200 App response status
    And   I should be able to verify Projects

  @getASpecificProject
  Scenario: I should be able to GET specific project
    When I perform GET call for specific project with id "6163fdba0413a8aa1ad82e20"
    Then I should be able to see the Project "6163fdba0413a8aa1ad82e20" details

  @getASpecificProjectNegativeScenario
  Scenario Outline: I should not be able to GET specific project with inccorect details
    When I perform GET call for specific project with id "<projectId>"
    Then I should see <status_code> App response status
    Examples:
    | projectId                      | status_code |
    | none                           | 404         |
    | 123                            | 404         |
    | 6163fdba04134g431ad82e20       | 404         |

  @getLoggedInUserDetails
  Scenario: I should be able to GET logeed in user details
    When I perform GET call to receive logged in user details with user id "6163fe53a8cac314d3d3e4c3"
    Then I should be able to see the logged "6163fe53a8cac314d3d3e4c3" user details
