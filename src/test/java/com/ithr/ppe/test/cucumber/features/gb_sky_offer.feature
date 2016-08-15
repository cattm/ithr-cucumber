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

    # TODO: there are several definition files not used in the scenario
    # Check out exact circumstances and add/append them
    Examples: 
      | package          | usergroup        | contained in   |
      | PK_4GTariff      | 4gsmall          | Sky hardbundle |
      | PK_4GTariff      | 4glarge          | Sky hardbundle |
      | PK_4GTariff      | 4gmedium         | Sky hardbundle |
      | PK_4GTariff      | 4gextralarge     | Sky hardbundle |
      | PK_4GTariff      | 4gmbblarge       | Sky standalone |
      | PK_4GTariff      | 4gmbbmedium      | Sky standalone |
      | PK_4GTariff      | 4gmbbsmall       | Sky standalone |
      | PK_4GTariffPromo | 4glarge          | Sky hardbundle |
      | PK_4GTariffPromo | 4gextralarge     | Sky hardbundle |
      | Not Valid        | Not Valid        | Sky standalone |
      | PK_4GTariffSIMO  | 4gsimosmall      | Sky hardbundle |
      | PK_4GTariffSIMO  | 4gsimolarge      | Sky hardbundle |
      | PK_4GTariffSIMO  | 4gsimomedium     | Sky hardbundle |
      | PK_4GTariffSIMO  | 4gsimoextralarge | Sky hardbundle |
