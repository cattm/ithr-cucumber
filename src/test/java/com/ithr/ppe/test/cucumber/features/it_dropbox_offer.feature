@dropboxpurchase
Feature: IT Purchase Dropbox for all Tariffs
  This feature will take each package/usergroup combination and attempt to obtain the correct dropbox package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase IT Offer from DROPBOX
    Given I am a "IT" customer purchasing the "DROPBOX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package   | usergroup           | contained in                |
      | Not Valid | 4guser              | Dropbox standalone          |
      | Not Valid | No usergroup        | Dropbox standalone          |
      | Not Valid | ug_storage_premium1 | Dropbox standalone premium1 |
      | Not Valid | ug_storage_premium2 | Dropbox standalone premium2 |
