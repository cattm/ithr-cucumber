@ignore
Feature: ES Purchase Dropbox
  This feature will take each package/usergroup combination and attempt to obtain the correct dropbox package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  #There is a file format problem with some of the JSON files at the moment
  Scenario Outline: Purchase ES Offer from DROPBOX
    Given I am a "ES" customer purchasing the "DROPBOX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package   | usergroup             | contained in                                  |
      | Not Valid | ug_storage_small      | Dropbox standalone promo 3 months free trial  |
      | Not Valid | ug_storage_xlarge     | Dropbox standalone promo 24 months free trial |
      | Not Valid | ug_storage_standalone | Dropbox standalone paid offer 2 x month       |
      | Not Valid | ug_storage_xlarge3    | Dropbox standalone SKU 100gb                  |
      | Not Valid | ug_storage_premium2   | Dropbox standalone SKU 50gb                   |
