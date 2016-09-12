@ignore
Feature: NL Purchasing Netflix
  This feature will take each package/usergroup combination and attempt to obtain the correct Netflix package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase GB Offers from NETFLIX
    Given I am a "NL" customer purchasing the "NETFLIX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package        | usergroup    | contained in                                     |
      | PK_RedTariff2  | contentred2  | Netflix hardbundle 3m free promo rollover to CTB |
      | PK_SmartTariff | contentsmart | Netflix hardbundle 3m free promo rollover to CTB |
      | PK_BlackTariff | contentblack | Netflix hardbundle 3m free promo rollover to CTB |
      | Not Valid      | No usergroup | Netflix standalone trial                         |
