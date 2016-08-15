 @skypurchase
 Feature: DE Purchasing SKY for all Tariffs
 This feature will take each package/usergroup combination and attempt to obtain the correct sky package.
 It will check all displayed text conforms to the expected json definition
 No previous alternate offers are assumed
 Variant behaviour will be verified elsewhere
 
 Scenario Outline: Purchase DE Offers from sky
    Given I am a "DE" customer purchasing sky package
    When my sky profile has a <package> tariff with a <usergroup> usergroup
    Then my sky offer details will come from <contained in>
    And I will accept the sky offer
    Examples: 
      | package | usergroup | contained in |
  #      | PK_RedTariff | ug_ppe_red   | SkyTV hardbundle 3 months trial |
  #      | PK_RedTariff | ug_ppe_young | SkyTV hardbundle 3 months trial |
  #       | PK_RedTariff | ug_ppe_tier1 | Not Valid |
  #       | No parent subscription | ug_storage_small | Not Valid |
  #       | Not Valid | Not Valid | I dont know which one this should be |
 
