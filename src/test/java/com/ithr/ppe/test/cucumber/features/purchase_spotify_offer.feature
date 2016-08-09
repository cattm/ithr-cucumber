@spotifypurchase
Feature: Purchasing the offers for Spotify
This is a primitive purchasing scenario 
Takes a basic package/usergroup combination and attempts to purchase Spotify
Only text message checking in the basic flow is performed
No previous alternate offers are assumed
Variant behaviour will be verified in more complex and specific features
 
  Scenario Outline: Purchase IT Offers from Spotify
    Given I am a "IT" customer purchasing spotify
    When my spotify profile has a <package> with a <usergroup>
    Then my spotify offer will come from <contained in>
    And I will accept the spotify offer

    Examples: 
      | package   | usergroup | contained in                                 |
      | Not Valid | 4guser    | Spotify standalone all v1.json               |
      | Not Valid | gold      | Spotify standalone gold trial v1.json        |
      | Not Valid | silver    | Spotify standalone switchable all v1.json    |
      | Not Valid | bronze    | Spotify standalone switchable summer v1.json |
      | Not Valid | summer    | Spotify standalone switchable summer v1.json |


  Scenario Outline: Purchase GB Offers from spotify
    Given I am a "GB" customer purchasing spotify
    When my spotify profile has a <package> with a <usergroup>
    Then my spotify offer will come from <contained in>
    And I will accept the spotify offer

    Examples: 
      | package     | usergroup    | contained in               |
      | PK_4GTariff | 4glarge      | Spotify hardbundle v1.json |
      | PK_4GTariff | 4gextralarge | Spotify hardbundle v1.json |
      | PK_4GTariff | 4gmedium     | Spotify hardbundle v1.json |
      | PK_4GTariff | 4gsmall      | Spotify hardbundle v1.json |
      | PK_4GTariff | 4gmbblarge   | Spotify standalone v1.json |
