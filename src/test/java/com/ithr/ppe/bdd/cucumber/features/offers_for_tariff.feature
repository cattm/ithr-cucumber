@ignore
Feature: This is a test feature file that we run only to help do a debug

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
      | package     | usergroup | contained in     |
      | PK_4GTariff | 4gsmall   | NowTV hardbundle |
      | PK_4GTariff | 4gmedium  | NowTV hardbundle |


  Scenario Outline: Purchase GB Offers from SKY
    Given I am a "GB" customer purchasing the "SKY" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    # TODO: there are several definition files not used in the scenario
    # Check out exact circumstances and add/append them
    Examples: 
      | package     | usergroup | contained in   |
      | PK_4GTariff | 4gsmall   | Sky hardbundle |
      | PK_4GTariff | 4glarge   | Sky hardbundle |

  Scenario Outline: Purchase GB Offers from spotify
    Given I am a "GB" customer purchasing the "SPOTIFY" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    # TODO: there are several definition files not used in the scenario
    # Check out exact circumstances and add/append them
    Examples: 
      | package     | usergroup | contained in             |
      | PK_4GTariff | 4gsmall   | Spotify hardbundle       |
      | Not Valid   | Not Valid | Spotify standalone trial |
