@purchase
Feature: ES Primary Purchase Offers
  This feature will take each package/usergroup combination and attempt to obtain the correct partner package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase ES Offer from DROPBOX
    Given I am a "ES" customer purchasing the "DROPBOX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package   | usergroup             | contained in                                  |
      | Not Valid | ug_storage_small      | Dropbox standalone promo 3 months free trial  |
      | Not Valid | ug_storage_xlarge     | Dropbox standalone promo 24 months free trial |
      | Not Valid | ug_storage_standalone | Dropbox standalone paid offer 2 x month       |
      | Not Valid | ug_storage_xlarge3    | Dropbox standalone SKU 100gb                  |
      | Not Valid | ug_storage_premium2   | Dropbox standalone SKU 50gb                   |

  Scenario Outline: Purchase ES Offers from SPOTIFY
    Given I am a "ES" customer purchasing the "SPOTIFY" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    # TODO: there are several definition files not used in the scenario
    # Check out exact circumstances and correct
    Examples: 
      | package                | usergroup             | contained in                      |
      | No parent subscription | ug_storage_small      | Spotify standalone trial          |
      | No parent subscription | ug_storage_xlarge     | Spotify standalone trial          |
      | No parent subscription | ug_storage_standalone | Spotify standalone trial          |
      | No parent subscription | ug_storage_xlarge3    | Spotify standalone trial          |
      | No parent subscription | ug_storage_premium2   | Spotify standalone trial          |
      # Not sure this one is correct - think it should be switchable
      | PK_RedTariff           | bundling_low          | Spotify hardbundle 3 months trial |
      | PK_RedTariff           | bundling_high         | Spotify hardbundle 6 months trial |
      | PK_ConvergentTariff    | onetivo_low           | Spotify standalone trial          |
      # Not sure this one is correct - think it should be switchable
      | PK_ConvergentTariff    | onetivo_high          | Spotify hardbundle 3 months trial |
      | PK_PrepaidTariff       | yu_high               | Spotify hardbundle 6 months trial |
      # check in price plan - dont think these are valid
      #      | PK_TVTariff         | tvtotal       | Spotify hardbundle 3 months trial             |
      #      | PK_TVTarriff         | hboreseller   | Spotify hardbundle 3 months trial             |
      | Not Valid              | No usergroup          | Spotify standalone trial          |

  Scenario Outline: Purchase ES Offer from HBO
    Given I am a "ES" customer purchasing the "HBO" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package      | usergroup     | contained in                   |
      | PK_RedTariff | bundling_high | HBO hardbundle agency 24m      |
      | PK_RedTariff | bundling_low  | HBO hardbundle agency 3m       |
      | Not Valid    | No usergroup  | HBO standalone agency 1m trial |

  Scenario Outline: Purchase ES Offer from HBO
    Given I am a "ES" customer purchasing the "HBO" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    And I have added group "hboreseller"
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package      | usergroup     | contained in                |
      | PK_RedTariff | bundling_high | HBO hardbundle reseller 24m |
      | PK_RedTariff | bundling_low  | HBO hardbundle reseller 3m  |

  Scenario Outline: Purchase ES Offers from NETFLIX
    Given I am a "ES" customer purchasing the "NETFLIX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package      | usergroup     | contained in                |
      | Not Valid    | No usergroup  | Netflix standalone trial    |
      | PK_RedTariff | bundling_high | Netflix hardbundle trial 6m |
