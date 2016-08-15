@check
Feature: GB Purchasing SKY for all Tariffs
  This feature will take each package/usergroup combination and attempt to obtain the correct sky package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase GB Offers from sky
    Given I am a "GB" customer purchasing sky package
    When my sky profile has a <package> tariff with a <usergroup> usergroup
    Then my sky offer details will come from <contained in>
    And I will accept the sky offer

    Examples: 
      | package     | usergroup | contained in   |
      | PK_4GTariff | 4gsmall   | Sky hardbundle |
