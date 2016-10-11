@purchase
Feature: NZ Primary Purchase Offers
  This feature will take each package/usergroup combination and attempt to obtain the correct partner package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase NZ Offer from DROPBOX
    Given I am a "NZ" customer purchasing the "DROPBOX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package   | usergroup             | contained in            |
      | Not Valid | ug_storage_standalone | Dropbox standalone paid |
      | Not Valid | ug_storage_premium1   | Dropbox standalone 25Gb |
      | Not Valid | ug_storage_premium2   | Dropbox standalone 50Gb |
