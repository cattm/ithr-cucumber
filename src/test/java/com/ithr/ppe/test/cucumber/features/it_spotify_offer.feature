@purchase
Feature: IT Purchase Spotify
  This feature will take each package/usergroup combination and attempt to obtain the correct spotify package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase IT Offers from SPOTIFY
    Given I am a "IT" customer purchasing the "SPOTIFY" offer
    When my profile has a <package> tariff with a <usergroup> usergroup
    Then my offer details will come from <contained in>
    And I will accept and confirm the offer

    # TODO: there are several minor issues with this file
    # It has not been checked for correctness
    # the priceplan in DIT is incorrect - some invalid cases pass
    # There appears to be a limit on the number of times we can access Spotify (quickly) - so we will not run all tests
    Examples: 
      | package   | usergroup    | contained in                            |
      | Not Valid | 4guser       | Spotify standalone all trial            |
      | Not Valid | Not Valid    | Spotify standalone all trial            |
      | Not Valid | gold         | Spotify standalone gold trial           |
      | Not Valid | silver       | Spotify standalone switchable all trial |
      #     | Not Valid | bronze       | Spotify standalone all trial               |
      #     | Not Valid | summer       | Spotify standalone switchable summer trial |
      | Not Valid | No usergroup | Spotify standalone all                  |
