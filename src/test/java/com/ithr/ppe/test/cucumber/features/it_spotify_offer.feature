@spotifypurchase
Feature: IT Purchase Spotify for all Tariffs
  This feature will take each package/usergroup combination and attempt to obtain the correct sky package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase IT Offers from Spotify
    Given I am a "IT" customer purchasing spotify
    When my spotify profile has a <package> tariff with a <usergroup> usergroup
    Then my spotify offer details will come from <contained in>
    And I will accept the spotify offer

    Examples: 
      | package   | usergroup | contained in                                 |
      | Not Valid | 4guser    | Spotify standalone all v1.json               |
      | Not Valid | gold      | Spotify standalone gold trial v1.json        |
      | Not Valid | silver    | Spotify standalone switchable all v1.json    |
      | Not Valid | bronze    | Spotify standalone switchable summer v1.json |
      | Not Valid | summer    | Spotify standalone switchable summer v1.json |
