Feature: Login feature

  Scenario: Login Success
    Given I open Login Page
    When I enter email "andrew.simmons@testpro.io"
    And I enter password "Andrew.Simmons24"
    And I click Submit
    Then I am logged in

#  Scenario Outline: Login
#    Given I open Login Page
#    When I enter email "<email>"
#    And I enter password "<password>"
#    And I click Submit
#    Then I am logged in

#    Examples:
#    |  email                       |  password          |
#    |  andrew.simmons@testpro.io   |  Andrew.Simmons24  |
#    |  andrew.simmons@testpro.io   |  andrew,Syms24     |