@purchase
Feature: PT Primary Purchase Offers
  This feature will take each package/usergroup combination and attempt to obtain the correct partner package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase PT Offers from NETFLIX
    Given I am a "PT" customer purchasing the "NETFLIX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package   | usergroup | contained in                      |
      | Not Valid | netflix3p | Netflix hardbundle 3 months trial |
      | Not Valid | netflix6p | Netflix hardbundle 6 months trial |

  Scenario Outline: Purchase PT Offer from DROPBOX
    Given I am a "PT" customer purchasing the "DROPBOX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    # TODO: I can either HARD code the drop box uid - or grab a new one each time - so dont need it here
    Examples: 
      | package   | usergroup           | contained in                    |
      | Not Valid | No usergroup        | Dropbox standalone paid         |
      | Not Valid | ug_storage_large    | Dropbox standalone large trial  |
      | Not Valid | ug_storage_medium   | Dropbox standalone medium trial |
      | Not Valid | ug_storage_premium1 | Dropbox standalone premium1     |
      | Not Valid | ug_storage_premium2 | Dropbox standalone premium2     |
      | Not Valid | ug_storage_xlarge   | Dropbox standalone xlarge trial |

  Scenario Outline: Purchase PT Offers from SPOTIFY
    Given I am a "PT" customer purchasing the "SPOTIFY" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    # TODO: there are several definition files not used in the scenario
    # Check out exact circumstances and add/append them
    Examples: 
      | package   | usergroup    | contained in                          |
      #    | Not Valid | mp3nonyorn | This is special case I think - 3 different offers |
      #    | Not valid | Not Valid | This is special case I think - 3 different offers |
      | Not Valid | 5gbdata      | Spotify standalone 5gbdata            |
      | Not Valid | yorn         | Spotify standalone yorn trial         |
      | Not Valid | yornstandard | Spotify standalone yornstandard trial |
