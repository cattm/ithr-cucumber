@secpurchase
Feature: GB RePurchase Offers
  This feature will attempt to purchase an offer; cancel it and then purchase a similar offer 
  It will first make a purchase and then refresh and cancel. 
  It will then look for a similar partenr offer defined by a new file
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase, Cancel, Purchase - different JSON
    Given I am a "GB" customer Who Initially purchases "SKY" offer
    And the offer is defined by package <package> and usergroup <usergroup> with json <contained in>
    And I can see the "SKY" subscription defined by <contained in>
    Then I will cancel the offer
    When I can see the "SKY" offer now defined by <now contained in>
    Then I will purchase the new offer

    Examples: 
      | package     | usergroup | contained in   | now contained in |
      | PK_4GTariff | 4gsmall   | Sky hardbundle | Sky standalone   |

  Scenario Outline: Purchase and then purchase again
    Given I am a "GB" customer Who Initially purchases "NOWTV" offer
    And the offer is defined by package <package> and usergroup <usergroup> with json <contained in>
    When I can see the "NOWTV" offer now defined by <now contained in>
    Then I will purchase the secondary offer

    Examples: 
      | package     | usergroup | contained in         | now contained in               |
      | PK_TVTariff | ugstb     | NowTV hardbundle STB | NowTV bolton STB Movies        |
      | PK_TVTariff | ugstb     | NowTV hardbundle STB | NowTV bolton STB Sports Daily  |
      | PK_TVTariff | ugstb     | NowTV hardbundle STB | NowTV bolton STB Sports Weekly |
      | PK_TVTariff | ugstb     | NowTV hardbundle STB | NowTV bolton STB Sports        |
