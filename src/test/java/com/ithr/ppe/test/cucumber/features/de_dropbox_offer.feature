@purchase
Feature: DE Purchase Dropbox
  This feature will take each package/usergroup combination and attempt to obtain the correct dropbox package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase DE Offer from DROPBOX
    Given I am a "DE" customer purchasing the "DROPBOX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    # TODO: I can either HARD code the drop box uid - or grab a new one each time - so dont need it here
    Examples: 
      | package          | usergroup      | contained in                    |
      | Not Valid        | No usergroup   | Dropbox standalone no usergroup |
      | PK_RedTariff     | ug_ppe_red     | Dropbox standalone no usergroup |
      #      | PK_RedTariff     | ug_ppe_young   | Dropbox standalone no usergroup  |
      #      | PK_RedTariff     | ug_ppe_tier1   | Dropbox standalone no usergroup  |
      | PK_RedPlusTariff | ug_ppe_redplus | Dropbox standalone no usergroup |
