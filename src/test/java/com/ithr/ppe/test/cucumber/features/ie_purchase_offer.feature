@purchase
Feature: IE Primary Purchase Offers
  This feature will take each package/usergroup combination and attempt to obtain the correct partner package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase IE Offers from SPOTIFY
    Given I am a "IE" customer purchasing the "SPOTIFY" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    # TODO: there are several definition files not used in the scenario
    # Check out exact circumstances and add/append them
    Examples: 
      | package        | usergroup | contained in       |
      #    | No parent subscription | roverprempayg | Not Valid              |
      #    | No parent subscription | roverpayg     | Not Valid              |
      #    | No parent subscription | roverpaym     | Not Valid              |
      #    | Not Valid              | Not Valid     | Not Valid              |
      | PK_ROVERTariff | rover     | Spotify hardbundle |

  Scenario Outline: Purchase IE Offers from SKY
    Given I am a "IE" customer purchasing the "SKY" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    # TODO: there are several definition files not used in the scenario
    # They generate a multiple offer scenario - we are limited here currently!
    # No User Group scenario has no offer? Check this
    Examples: 
      | package        | usergroup | contained in   |
      #    | No parent subscription | roverprempayg | Not Valid              |
      #    | No parent subscription | roverpayg     | Not Valid              |
      # Might get 2 offers TV PK 1 and TV PK 2 - not sure which if either is valid
      #    | No parent subscription | roverpaym     | Not Valid              |
      #    | Not Valid              | Not Valid     | Sky standalone trial             |
      | PK_ROVERTariff | rover     | Sky hardbundle |

  Scenario Outline: Purchase IE Offer from DROPBOX
    Given I am a "IE" customer purchasing the "DROPBOX" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    Examples: 
      | package                | usergroup     | contained in                       |
      | Not Valid              | No usergroup  | Dropbox standalone paid offer      |
      | PK_ROVERTariff         | rover         | Dropbox standalone premium ongoing |
      | No parent subscription | roverprempayg | Dropbox standalone paid offer      |
      | No parent subscription | roverpayg     | Dropbox standalone paid offer      |
      | No parent subscription | roverpaym     | Dropbox standalone                 |
