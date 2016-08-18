@spotifypurchase
Feature: ES Purchase Spotify for all Tariffs
  This feature will take each package/usergroup combination and attempt to obtain the correct spotify package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase ES Offers from spotify
    Given I am a "ES" customer purchasing spotify
    When my spotify profile has a <package> tariff with a <usergroup> usergroup
    Then my spotify offer details will come from <contained in>
    And I will accept the spotify offer

# TODO: there are several definition files not used in the scenario 
# Check out exact circumstances and correct

    Examples: 
      | package             | usergroup     | contained in                                         |
#      | No parent subscription | options -  I dont know if valid | Not Valid |
# Not sure this one is correct - think it should be switchable
      | PK_RedTariff        | bundling_low  | Spotify hardbundle 3 months trial |
      | PK_RedTariff        | bundling_high | Spotify hardbundle 6 months trial             |
      | PK_ConvergentTariff | onetivo_low   | Spotify standalone trial             |
# Not sure this one is correct - think it should be switchable
      | PK_ConvergentTariff | onetivo_high  | Spotify hardbundle 3 months trial   |
      | PK_PrepaidTariff    | yu_high       | Spotify hardbundle 6 months trial             |
# check in price plan      
#      | PK_TVTariff         | tvtotal       | Spotify hardbundle 3 months trial             |
#      | PK_TVTarriff         | hboreseller   | Spotify hardbundle 3 months trial             |
      | Not Valid           | No usergroup     | Spotify standalone trial             |

