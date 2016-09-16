@purchase
Feature: GB Primary Purchase Offers
  This feature will take each package/usergroup combination and attempt to obtain the correct partner package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase GB Offers from NETFLIX
    Given I am a "GB" customer purchasing the "NETFLIX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package   | usergroup    | contained in             |
      | Not Valid | No usergroup | Netflix standalone trial |

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

  Scenario Outline: Purchase GB Offers from SKY
    Given I am a "GB" customer purchasing the "SKY" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

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
      | Not Valid        | No usergroup     | Sky standalone |
      | PK_4GTariffSIMO  | 4gsimosmall      | Sky hardbundle |
      | PK_4GTariffSIMO  | 4gsimolarge      | Sky hardbundle |
      | PK_4GTariffSIMO  | 4gsimomedium     | Sky hardbundle |
      | PK_4GTariffSIMO  | 4gsimoextralarge | Sky hardbundle |

  Scenario Outline: Purchase GB Offers from spotify
    Given I am a "GB" customer purchasing the "SPOTIFY" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    # TODO: there are several definition files not used in the scenario
    # Check out exact circumstances and add/append them
    Examples: 
      | package          | usergroup        | contained in             |
      | PK_4GTariff      | 4gsmall          | Spotify hardbundle       |
      | PK_4GTariffSIMO  | 4gsimosmall      | Spotify hardbundle       |
      | PK_4GTariff      | 4gmedium         | Spotify hardbundle       |
      | PK_4GTariffSIMO  | 4gsimomedium     | Spotify hardbundle       |
      | PK_4GTariff      | 4glarge          | Spotify hardbundle       |
      | PK_4GTariffSIMO  | 4gsimolarge      | Spotify hardbundle       |
      | PK_4GTariff      | 4gextralarge     | Spotify hardbundle       |
      | PK_4GTariffSIMO  | 4gsimoextralarge | Spotify hardbundle       |
      | PK_4GTariff      | 4gmbbsmall       | Spotify standalone trial |
      | PK_4GTariff      | 4gmbbmedium      | Spotify standalone trial |
      | PK_4GTariff      | 4gmbblarge       | Spotify standalone trial |
      | PK_4GTariffPromo | 4glarge          | Spotify standalone trial |
      | PK_4GTariffPromo | 4gextralarge     | Spotify standalone trial |
      # This one may be invalid need to verify
      #    | PK_TVTariff      | ugstb            | Spotify standalone trial |
      | Not Valid        | No usergroup     | Spotify standalone trial |
      | Not Valid        | Not Valid        | Spotify standalone trial |
