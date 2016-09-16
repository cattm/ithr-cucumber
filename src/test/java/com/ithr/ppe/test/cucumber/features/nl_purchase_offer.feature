@purchase
Feature: NL Primary Purchase Offers
  This feature will take each package/usergroup combination and attempt to obtain the correct partner package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase NL Offers from NETFLIX
    Given I am a "NL" customer purchasing the "NETFLIX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package        | usergroup    | contained in                                     |
      | PK_RedTariff2  | contentred2  | Netflix hardbundle 3m free promo rollover to CTB |
      | PK_SmartTariff | contentsmart | Netflix hardbundle 3m free promo rollover to CTB |
      | PK_BlackTariff | contentblack | Netflix hardbundle 3m free promo rollover to CTB |
      | Not Valid      | No usergroup | Netflix standalone trial                         |

  Scenario Outline: Purchase NL Offer from DROPBOX
    Given I am a "NL" customer purchasing the "DROPBOX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package                | usergroup           | contained in                                           |
      | Not Valid              | No usergroup        | Dropbox standalone paid offer 1.99                     |
      | No parent subscription | ug_storage_small    | Dropbox standalone trial small 25Gb 3m free then 1.99  |
      | No parent subscription | ug_storage_medium   | Dropbox standalone trial medium 25Gb 6m free then 1.99 |
      | No parent subscription | ug_storage_premium1 | Dropbox standalone premium1 ongoing                    |
      | No parent subscription | ug_storage_premium2 | Dropbox standalone premium2 ongoing                    |
      | PK_RedTariff           | contentred          | Dropbox standalone paid offer 1.99                     |
