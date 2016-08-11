@ignore
Feature: Purchasing the offers for SKY
  This is a primitive purchasing scenario 
  Takes a basic package/usergroup combination and attempts to purchase Sky
  Only text message checking in the basic flow is performed
  No previous alternate offers are assumed
  Variant behaviour will be verified in more complex and specific features

  Scenario Outline: Purchase GB Offers from sky
    Given I am a "GB" customer purchasing sky package
    When my sky profile has a <package> with a <usergroup>
    Then my sky offer will come from <contained in>
    And I will accept the sky offer

    # key variants to include in alternate scenarios are
    # ER credit variations
    # already active subscription
    # outcome checking scenarios based on package/usergroup and other switches such as CTB/SkySA and bolt ons
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

  Scenario Outline: Purchase DE Offers from sky
    Given I am a "DE" customer purchasing sky package
    When my sky profile has a <package> with a <usergroup>
    Then my sky offer will come from <contained in>
    And I will accept the sky offer

    Examples: 
      | package | usergroup | contained in |

  #      | PK_RedTariff | ug_ppe_red   | SkyTV hardbundle 3 months trial v3.json |
  #      | PK_RedTariff | ug_ppe_young | SkyTV hardbundle 3 months trial v3.json |
  Scenario Outline: Purchase IE Offers from sky
    Given I am a "IE" customer purchasing sky package
    When my sky profile has a <package> with a <usergroup>
    Then my sky offer will come from <contained in>
    And I will accept the sky offer

    Examples: 
      | package        | usergroup | contained in           |
  #    | PK_ROVERTariff | rover     | Sky hardbundle v4.json |
