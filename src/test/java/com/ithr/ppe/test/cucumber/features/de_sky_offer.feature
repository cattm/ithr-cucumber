 @ignore
 Feature: DE Purchasing SKY
 This feature will take each package/usergroup combination and attempt to obtain the correct sky package.
 It will check all displayed text conforms to the expected json definition
 No previous alternate offers are assumed
 Variant behaviour will be verified elsewhere
 
 Scenario Outline: Purchase DE Offers from SKY
	Given I am a "DE" customer purchasing the "SKY" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer
    
    Examples: 
  # TODO - need to check this out as of 16/8/2016 on DIT I cant get an offer displayed for any combination
      | package | usergroup | contained in |
  #      | PK_RedTariff | ug_ppe_red   | SkyTV hardbundle 3 months trial |
  #      | PK_RedTariff | ug_ppe_young | SkyTV hardbundle 3 months trial |
  #       | PK_RedTariff | ug_ppe_tier1 | Not Valid |
  #       | No parent subscription | ug_storage_small | Not Valid |
  #       | Not Valid | Not Valid | I dont know which one this should be |
 
