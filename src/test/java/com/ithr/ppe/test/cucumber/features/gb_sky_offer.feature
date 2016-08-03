@ignore
Feature: GB Sky Offer
  This is a simple demonstration for a GB MSIDN of the sky offer
  Its pretty poor BDD but it will do to check the basic logic

  @xdemo
  Scenario: First scenario
    Given I am a "gb" Customer with a valid msisdn
    And have "PK_4GTariff" subscription
    And am in "4glarge" usergroup
    Then I will see a sky tv off containing text from "Sky hardbundle v1.json"
    And I will be able to accept the offer

  # will need some scenarios to check cancel and error text
  # and deal with "oops" messages