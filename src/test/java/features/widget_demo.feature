@widgetDemo
Feature: Solvemate - Widget Demo feature
  I should be able to get api responses for the widget



  @getConversationsEndpointResponse
  Scenario: I should be able to GET the api response for 'conversations' endpoint
    Given I perform conversations endpoint GET call for a "6163fdba0413a8aa1ad82e20" widget id with a solvemate id header "176d1b31-3233-11ec-ac48-c1ec41cba115"
    Then  I should see 200 Widget response status
    And   I should be able to retrieve the conversations endpoint response

  @getConversationsEndpointResponseNegativeScenario
  Scenario Outline: I should not be able to GET the api response without providing a solvemate id header
    Given I perform endpoint GET call for a "6163fdba0413a8aa1ad82e20" widget id without a solvemate id header
    Then  I should see <status_code> Widget response status
    And   I should see the Widget error message "<error_message>"
    Examples:
      | status_code | error_message                                          |
      | 422         | {Solvemate-User-ID=[Missing data for required field.]} |


    @getSpecificProjectStartOfConversationResponse
    Scenario: I should be able to POST start of conversation response for 'Test bot (wizard)' project in the chat bot
      Given I perform POST call with a project id "6163fb72e81bcf4c14b5590f" to receive start of the conversation response
      Then  I should see 201 Widget response status
      And   I should be able to see "6163fb72e81bcf4c14b5590f" project Id start of conversation response