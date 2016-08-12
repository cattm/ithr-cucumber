@spotifypurchase
Feature: GB Purchase Spotify for all Tariffs
  This feature will take each package/usergroup combination and attempt to obtain the correct sky package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase GB Offers from spotify
    Given I am a "GB" customer purchasing spotify
    When my spotify profile has a <package> tariff with a <usergroup> usergroup
    Then my spotify offer details will come from <contained in>
    And I will accept the spotify offer

    Examples: 
      | package     | usergroup    | contained in               |
      | PK_4GTariff | 4glarge      | Spotify hardbundle v1.json |
      | PK_4GTariff | 4gextralarge | Spotify hardbundle v1.json |
      | PK_4GTariff | 4gmedium     | Spotify hardbundle v1.json |
      | PK_4GTariff | 4gsmall      | Spotify hardbundle v1.json |
      | PK_4GTariff | 4gmbblarge   | Spotify standalone v1.json |
