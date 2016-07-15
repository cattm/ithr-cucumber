Feature: Purchasing the offers for DE

  @xxskypurchase
  Scenario Outline: Purchase Offers from sky
    Given I am a "DE" customer with <package>
    When I am in <usergroup>
    Then my offer will come from <contained in>
    And I will accept the offer

    Examples: 
      | package      | usergroup  | contained in                            |
      | PK_RedTariff | ug_ppe_red | SkyTV hardbundle 3 months trial v3.json |
