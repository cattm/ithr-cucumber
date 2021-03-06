@purchase
Feature: DE Primary Purchase Offers
  This feature will take each package/usergroup combination and attempt to obtain the correct partner package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase DE Offer from DROPBOX
    Given I am a "DE" customer purchasing the "DROPBOX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package          | usergroup      | contained in                    |
      | Not Valid        | No usergroup   | Dropbox standalone no usergroup |
      | PK_RedTariff     | ug_ppe_red     | Dropbox standalone no usergroup |
      #      | PK_RedTariff     | ug_ppe_young   | Dropbox standalone no usergroup  |
      #      | PK_RedTariff     | ug_ppe_tier1   | Dropbox standalone no usergroup  |
      | PK_RedPlusTariff | ug_ppe_redplus | Dropbox standalone no usergroup |

  @purchase
  Scenario Outline: Purchase DE Offers from SKY
    Given I am a "DE" customer purchasing the "SKY" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      # TODO - need to check this out as of 16/8/2016 on DIT I cant get an offer displayed for any combination
      | package                | usergroup | contained in     |
      | No parent subscription | ug_ppe_sa | SkyTV standalone |
  #      | PK_RedTariff | ug_ppe_red   | SkyTV hardbundle 3 months trial |
  #      | PK_RedTariff | ug_ppe_young | SkyTV hardbundle 3 months trial |
  #       | PK_RedTariff | ug_ppe_tier1 | Not Valid |
  #       | No parent subscription | ug_storage_small | Not Valid |
  #	| Not Valid | Not Valid | I dont know which one this should be |
  
  
  Scenario Outline: Purchase DE Offer from DEEZER
    Given I am a "DE" customer purchasing the "DEEZER" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package      | usergroup  | contained in                     |
      | PK_RedTariff | ug_ppe_red | Deezer hardbundle 3 months trial |

  Scenario Outline: Purchase DE Offer from BILD+
    Given I am a "DE" customer purchasing the "BILDPLUS" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package                | usergroup  | contained in                       |
      | PK_RedTariff           | ug_ppe_red | Bildplus hardbundle 3 months trial |
      | No parent subscription | ug_ppe_sa  | Bildplus standalone 3 months       |
