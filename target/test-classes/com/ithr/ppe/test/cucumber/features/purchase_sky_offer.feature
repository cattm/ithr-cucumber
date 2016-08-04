@skypurchase
Feature: Purchasing the offers for SKY


  Scenario Outline: Purchase GB Offers from sky
    Given I am a "GB" customer purchasing sky package
    When my sky profile has a <package> with a <usergroup>
    Then my sky offer will come from <contained in>
    And I will accept the sky offer

    Examples: 
        | package     | usergroup | contained in           |
         | PK_4GTariff | 4glarge   | Sky hardbundle v1.json |
#         | PK_4GTariff      | 4gextralarge | Sky hardbundle v1.json |
#         | PK_4GTariff      | 4gmedium     | Sky hardbundle v1.json |
#         | PK_4GTariff      | 4gsmall      | Sky hardbundle v1.json |
#         | PK_4GTariff      | 4gmbblarge   | Sky standalone v1.json |
#         | PK_4GTariff      | 4gmbbmedium  | Sky standalone v1.json |
#         | PK_4GTariff      | 4gmbbsmall   | Sky standalone v1.json |
#         | PK_4GTariffPromo | 4glarge      | Sky hardbundle v1.json |
#         | PK_4GTariffPromo | 4gextralarge | Sky hardbundle v1.json |
#        | PK_TVTariff      | ugstb        | Not Valid              |
#        | PK_4GTVTariff    | Not Valid    | Sky standalone v1.json |
 
  Scenario Outline: Purchase DE Offers from sky
    Given I am a "DE" customer purchasing sky package
    When my sky profile has a <package> with a <usergroup>
    Then my sky offer will come from <contained in>
    And I will accept the sky offer

    Examples: 
      | package                | usergroup        | contained in                            |
#       | PK_RedTariff           | ug_ppe_red       | SkyTV hardbundle 3 months trial v3.json |
#      | PK_RedTariff           | ug_ppe_young     | SkyTV hardbundle 3 months trial v3.json |
#      | PK_RedTariff           | ug_ppe_tier1     | Not Valid |
#      | No parent subscription | ug_storage_small | Not Valid |
#      |    PK_RedTariff                | Not Valid        | Not Valid                                   |


 Scenario Outline: Purchase IE Offers from sky
    Given I am a "IE" customer purchasing sky package
    When my sky profile has a <package> with a <usergroup>
    Then my sky offer will come from <contained in>
    And I will accept the sky offer

    Examples: 
       | package                | usergroup        | contained in                            |
 #      | PK_ROVERTariff | rover | Sky hardbundle v4.json |
  #      | No parent subscription | roverprempayg | Not Valid |
  #      | No parent subscription | roverpayg | Not Valid |
  #      | No parent subscription | roverpaym | Not Valid | 
      