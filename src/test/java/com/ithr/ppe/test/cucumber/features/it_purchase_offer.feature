@purchase
Feature: IT Primary Purchase Offers
  This feature will take each package/usergroup combination and attempt to obtain the correct partner package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase IT Offer from DROPBOX
    Given I am a "IT" customer purchasing the "DROPBOX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package   | usergroup           | contained in                |
      | Not Valid | 4guser              | Dropbox standalone          |
      | Not Valid | No usergroup        | Dropbox standalone          |
      | Not Valid | ug_storage_premium1 | Dropbox standalone premium1 |
      | Not Valid | ug_storage_premium2 | Dropbox standalone premium2 |

  Scenario Outline: Purchase IT Offers from SPOTIFY
    Given I am a "IT" customer purchasing the "SPOTIFY" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer
    
# not exercised currently
#Spotify standalone all 
#Spotify standalone gold non-available  
#Spotify standalone gold 
#Spotify standalone non-available trial 
#Spotify standalone silver non-available 
#Spotify standalone summer trial 
#Spotify standalone summer  
#Spotify standalone switchable all 
#Spotify standalone switchable gold trial 
#Spotify standalone switchable summer trial 
#Spotify standalone switchable summer 

    Examples: 
      | package   | usergroup    | contained in                            |
      | Not Valid | 4guser       | Spotify standalone all trial            |
      | Not Valid | Not Valid    | Spotify standalone all trial            |
      | Not Valid | gold         | Spotify standalone gold trial           |
      | Not Valid | silver       | Spotify standalone switchable all trial |

