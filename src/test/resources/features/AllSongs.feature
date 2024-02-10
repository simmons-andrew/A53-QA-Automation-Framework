Feature: All Songs Feature

  Scenario: Add Song To Playlist
    Given I am logged in
    And I search for the song "Dark Days"
    When I click the View All Button
    And I click the Add To Button
    And I select a Playlist
    Then I add the song to the Playlist