@spotifypurchase
Feature: GB Purchase Spotify for all Tariffs
  This feature will take each package/usergroup combination and attempt to obtain the correct spotify package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase GB Offers from spotify
    Given I am a "GB" customer purchasing spotify
    When my spotify profile has a <package> tariff with a <usergroup> usergroup
    Then my spotify offer details will come from <contained in>
    And I will accept the spotify offer

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
