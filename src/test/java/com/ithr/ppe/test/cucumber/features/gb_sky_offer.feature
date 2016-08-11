 @skypurchase
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
      | package          | usergroup        | contained in           |
      | PK_4GTariff      | 4gsmall          | Sky hardbundle v1.json |
      | PK_4GTariff      | 4glarge          | Sky hardbundle v1.json |
      | PK_4GTariff      | 4gmedium         | Sky hardbundle v1.json |
      | PK_4GTariff      | 4gextralarge     | Sky hardbundle v1.json |
      | PK_4GTariff      | 4gmbblarge       | Sky standalone v1.json |
      | PK_4GTariff      | 4gmbbmedium      | Sky standalone v1.json |
      | PK_4GTariff      | 4gmbbsmall       | Sky standalone v1.json |
      | PK_4GTariffPromo | 4glarge          | Sky hardbundle v1.json |
      | PK_4GTariffPromo | 4gextralarge     | Sky hardbundle v1.json |
      | Not Valid        | Not Valid        | Sky standalone v1.json |
      | PK_4GTariffSIMO  | 4gsimosmall      | Sky hardbundle v1.json |
      | PK_4GTariffSIMO  | 4gsimolarge      | Sky hardbundle v1.json |
      | PK_4GTariffSIMO  | 4gsimomedium     | Sky hardbundle v1.json |
      | PK_4GTariffSIMO  | 4gsimoextralarge | Sky hardbundle v1.json |
