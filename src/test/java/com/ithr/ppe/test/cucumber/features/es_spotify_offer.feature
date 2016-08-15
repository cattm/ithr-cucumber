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
# Check out exact circumstances and add/append them

    Examples: 
      | package             | usergroup     | contained in                                         |
#      | No parent subscription | options -  I dont know if valid | Not Valid |
      | PK_RedTariff        | bundling_low  | Spotify hardbundle switchable trial 3 months  |
      | PK_RedTariff        | bundling_high | Spotify hardbundle 3 months trial             |
      | PK_ConvergentTariff | onetivo_low   | Spotify hardbundle 3 months trial             |
      | PK_ConvergentTariff | onetivo_high  | Spotify hardbundle switchable trial 3 months  |
      | PK_PrepaidTariff    | yu_high       | Spotify hardbundle 3 months trial             |
      | PK_TVTariff         | tvtotal       | Spotify hardbundle 3 months trial             |
      | PK_TVTarrif         | hboreseller   | Spotify hardbundle 3 months trial             |
      | Not Valid           | Not Valid     | Spotify hardbundle 3 months trial             |

