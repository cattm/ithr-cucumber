@purchase
Feature: GB Purchasing NOWTV for all Tariffs
  This feature will take each package/usergroup combination and attempt to obtain the correct NowTV package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase GB Offers from NOWTV
    Given I am a "GB" customer purchasing the "NOWTV" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package         | usergroup        | contained in     |
      | PK_4GTariff     | 4gsmall          | NowTV hardbundle |
      | PK_4GTariff     | 4gmedium         | NowTV hardbundle |
      | PK_4GTariff     | 4glarge          | NowTV hardbundle |
      | PK_4GTariff     | 4gextralarge     | NowTV hardbundle |
      | PK_4GTariffSIMO | 4gsimosmall      | NowTV hardbundle |
      | PK_4GTariffSIMO | 4gsimolarge      | NowTV hardbundle |
      | PK_4GTariffSIMO | 4gsimomedium     | NowTV hardbundle |
      | PK_4GTariffSIMO | 4gsimoextralarge | NowTV hardbundle |
