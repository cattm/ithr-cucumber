@spotifypurchase
Feature: IT Purchase Spotify for all Tariffs
  This feature will take each package/usergroup combination and attempt to obtain the correct spotify package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase IT Offers from Spotify
    Given I am a "IT" customer purchasing spotify
    When my spotify profile has a <package> tariff with a <usergroup> usergroup
    Then my spotify offer details will come from <contained in>
    And I will accept the spotify offer

    # TODO: there are several definition files not used in the scenario
    # Check out exact circumstances and add/append them
    Examples: 
      | package   | usergroup | contained in                               |
      | Not Valid | 4guser    | Spotify standalone all trial               |
      | Not Valid | Not Valid | Spotify standalone all trial               |
      | Not Valid | gold      | Spotify standalone gold trial              |
      | Not Valid | silver    | Spotify standalone switchable all trial    |
      | Not Valid | bronze    | Spotify standalone all trial               |
      | Not Valid | summer    | Spotify standalone switchable summer trial |
