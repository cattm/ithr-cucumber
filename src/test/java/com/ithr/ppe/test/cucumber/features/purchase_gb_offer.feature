Feature: Purchasing the offers for GB

  @xpurchase
  Scenario Outline: Purchase Offers from sky
    Given I am a GB customer with <package>
    When I am in <usergroup>
    Then my offer will come from <contained in>
    And I will accept the offer

    Examples: 
      | package          | usergroup    | contained in           |
      | PK_4GTariff      | 4glarge      | Sky hardbundle v1.json |
      | PK_4GTariff      | 4gextralarge | Sky hardbundle v1.json |
      | PK_4GTariff      | 4gmedium     | Sky hardbundle v1.json |
      | PK_4GTariff      | 4gsmall      | Sky hardbundle v1.json |
      | PK_4GTariff      | 4gmbblarge   | Sky standalone v1.json |
      | PK_4GTariff      | 4gmbbmedium  | Sky standalone v1.json |
      | PK_4GTariff      | 4gmbbsmall   | Sky standalone v1.json |
      | PK_4GTariffPromo | 4glarge      | Sky hardbundle v1.json |
      | PK_4GTariffPromo | 4gextralarge | Sky hardbundle v1.json |
      | PK_TVTariff      | ugstb        | Not Valid              |
      | PK_4GTVTariff    | Not Valid    | Sky standalone v1.json |
