@purchase
Feature: IE Purchasing SKY
  This feature will take each package/usergroup combination and attempt to obtain the correct sky package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

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
