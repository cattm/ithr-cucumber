@skypurchase
Feature: IE Purchasing SKY for all Tariffs
  This feature will take each package/usergroup combination and attempt to obtain the correct sky package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase IE Offers from sky
    Given I am a "IE" customer purchasing sky package
    When my sky profile has a <package> tariff with a <usergroup> usergroup
    Then my sky offer details will come from <contained in>
    And I will accept the sky offer

    # TODO: there are several definition files not used in the scenario
    # They generate a multiple offer scenario - we are limited here currently!
    # No User Group scenario has no offer? Check this
    Examples: 
      | package        | usergroup | contained in   |
      #    | No parent subscription | roverprempayg | Not Valid              |
      #    | No parent subscription | roverpayg     | Not Valid              |
      #    | No parent subscription | roverpaym     | Not Valid              |
      #    | Not Valid              | Not Valid     | Sky standalone trial             |
      | PK_ROVERTariff | rover     | Sky hardbundle |
