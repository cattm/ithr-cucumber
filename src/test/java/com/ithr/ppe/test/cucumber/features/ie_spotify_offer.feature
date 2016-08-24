@spotifypurchase
Feature: IE Purchase Spotify for all Tariffs
  This feature will take each package/usergroup combination and attempt to obtain the correct spotify package.
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
