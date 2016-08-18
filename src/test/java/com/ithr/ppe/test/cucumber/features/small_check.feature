Feature: GB Test Feature

  @checkspotify
  Scenario Outline: Purchase GB Offers from spotify
    Given I am a "GB" customer purchasing spotify
    When my spotify profile has a <package> tariff with a <usergroup> usergroup
    Then my spotify offer details will come from <contained in>
    And I will accept the spotify offer

    # TODO: there are several definition files not used in the scenario
    # Check out exact circumstances and add/append them
    Examples: 
       | package          | usergroup        | contained in             |
 #     | PK_4GTariff      | 4gmbbmedium  | Spotify standalone trial |
 #     | PK_4GTariffPromo | 4glarge      | Spotify standalone trial |
 #     | PK_4GTariffPromo | 4gextralarge | Spotify standalone trial |
      # this is no valid tariff - offer does not appear?
      | PK_TVTariff      | ugstb        | Spotify standalone trial |
  #    | Not Valid        | Not Valid    | Spotify standalone trial |
