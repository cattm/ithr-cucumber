@purchase
Feature: PT Purchase Spotify
  This feature will take each package/usergroup combination and attempt to obtain the correct spotify package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase PT Offers from SPOTIFY
	Given I am a "PT" customer purchasing the "SPOTIFY" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    # TODO: there are several definition files not used in the scenario
    # Check out exact circumstances and add/append them
    Examples: 
      | package   | usergroup   | contained in                         |
      #    | Not Valid | mp3nonyorn | This is special case I think - 3 different offers |
      #    | Not valid | Not Valid | This is special case I think - 3 different offers |
      | Not Valid | 5gbdata     | Spotify standalone 5gbdata           |
      | Not Valid | yorn        | Spotify standalone yorn trial         |
      | Not Valid | yornstandard | Spotify standalone yornstandard trial |
