@spotifypurchase
Feature: Purchasing the offers for Spotify

 
  Scenario Outline: Purchase Offers from Spotify
    Given I am a "IT" customer purchasing spotify
    When my spotify profile has a <package> with a <usergroup>
    Then my spotify offer will come from <contained in>
    And I will accept the spotify offer

    Examples: 
      | package   | usergroup | contained in                                 |
      | Not Valid | 4guser    | Spotify standalone all v1.json               |
 #     | Not Valid | gold      | Spotify standalone gold trial v1.json        |
 #     | Not Valid | silver    | Spotify standalone switchable all v1.json    |
 #     | Not Valid | bronze    | Spotify standalone switchable summer v1.json |
 #     | Not Valid | summer    | Spotify standalone switchable summer v1.json |


  Scenario Outline: Purchase Offers from spotify
    Given I am a "GB" customer purchasing spotify
    When my spotify profile has a <package> with a <usergroup>
    Then my spotify offer will come from <contained in>
    And I will accept the spotify offer

    Examples: 
      | package     | usergroup    | contained in               |
 #     | PK_4GTariff | 4glarge      | Spotify hardbundle v1.json |
 #     | PK_4GTariff | 4gextralarge | Spotify hardbundle v1.json |
 #     | PK_4GTariff | 4gmedium     | Spotify hardbundle v1.json |
 #     | PK_4GTariff | 4gsmall      | Spotify hardbundle v1.json |
 #     | PK_4GTariff | 4gmbblarge   | Spotify standalone v1.json |
