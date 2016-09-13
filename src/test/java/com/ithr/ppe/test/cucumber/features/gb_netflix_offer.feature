@ignore
Feature: GB Purchasing Netflix
  This feature will take each package/usergroup combination and attempt to obtain the correct Netflix package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  # This feature is currently set to ignore because it always fails on Jenkins
  # The error returned in related to not being able to find the JSON file - but it is there!
  Scenario Outline: Purchase GB Offers from NETFLIX
    Given I am a "GB" customer purchasing the "NETFLIX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package   | usergroup    | contained in             |
      | Not Valid | No usergroup | Netflix standalone trial |
