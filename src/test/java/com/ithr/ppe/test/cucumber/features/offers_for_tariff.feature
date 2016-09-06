@ignore
Feature: Offered for each tariff Combination
  This is a simple check that investigates the number of offers for each tariff combination match those expected

  Scenario: simple example of what should be seen on intial offer
  # do a primitive example then think about how we can make this self governing!!
    Given I am on the login page
    And I have required data to Login:
      | 4guser | bronze | 7801510567 | 0000 |
    Then I should be logged and see the offers:
      | Sky Trial AND Spotify Trial AND Something else |
