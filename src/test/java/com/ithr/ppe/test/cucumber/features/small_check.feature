Feature: GB Test Feature

@ignore
Scenario Outline: Purchase GB Offers from NETFLIX
    Given I am a "GB" customer purchasing the "NETFLIX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer
  
    Examples: 
      | package         | usergroup        | contained in     |      
      
         | Not Valid        | No usergroup     | Netflix hardbundle 12 months |

  @checksky
  Scenario Outline: Purchase GB Offers from sky
    Given I am a "GB" customer purchasing the "SKY" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    # TODO: there are several definition files not used in the scenario
    # Check out exact circumstances and add/append them
    Examples: 
      | package     | usergroup | contained in   |
      | PK_4GTariff | 4gsmall   | Sky hardbundle |

  @checknow
  Scenario Outline: Purchase GB Offers from NOWTV
    Given I am a "GB" customer purchasing the "NOWTV" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer
  
    Examples: 
      | package         | usergroup        | contained in     |
      | PK_4GTariff     | 4gsmall          | NowTV hardbundle |
      
 