Feature: GB Test Feature

  @ignore
  Scenario Outline: Purchase GB Offers from sky
    Given I am a "GB" customer purchasing sky package
    When my sky profile has a <package> tariff with a <usergroup> usergroup
    Then my sky offer details will come from <contained in>
    And I will accept the sky offer

    # TODO: there are several definition files not used in the scenario
    # Check out exact circumstances and add/append them
    Examples: 
      | package     | usergroup | contained in   |
      | PK_4GTariff | 4gsmall   | Sky hardbundle |
      
  @ignore
  Scenario Outline: Purchase GB Offers from spotify
    Given I am a "GB" customer purchasing spotify
    When my spotify profile has a <package> tariff with a <usergroup> usergroup
    Then my spotify offer details will come from <contained in>
    And I will accept the spotify offer

    Examples: 
      | package     | usergroup | contained in       |
      | PK_4GTariff | 4gsmall   | Spotify hardbundle |
      
      
  @ignore   
 Scenario Outline: Purchase PT Offers from spotify
    Given I am a "PT" customer purchasing spotify
    When my spotify profile has a <package> tariff with a <usergroup> usergroup
    Then my spotify offer details will come from <contained in>
    And I will accept the spotify offer

    # TODO: there are several definition files not used in the scenario
    # Check out exact circumstances and add/append them
    Examples: 
      | package   | usergroup   | contained in                         |
      #    | Not Valid | mp3nonyorn | This is special case I think - 3 different offers |
      #    | Not valid | Not Valid | This is special case I think - 3 different offers |
 #     | Not Valid | 5gbdata     | Spotify standalone 5gbdata           |
 #     | Not Valid | yorn        | Spotify standalone yorn trial         |
      | Not Valid | yornstandard | Spotify standalone yornstandard trial |

   
