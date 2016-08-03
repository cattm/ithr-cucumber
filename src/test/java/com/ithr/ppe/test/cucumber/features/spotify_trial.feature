@ignore
Feature: Spotify Trial demonstration

  Background: Previously Purchased Spotify
    Given I am a "IT" customer already purchased Spotify with:
      | package   | usergroup | contained in                   |
      | Not Valid | 4guser    | Spotify standalone all v1.json |

  # I want to use the same code as for purchase spotify
  # but I dont want to call the "TEST" to do this
  # so I need standard utility classes to complete this
  # msisdnFromAdmin is ok can migrate
  # as is loginToPPE
  # registerForSpotify and getSpotifyUser require some work
  # need a model "operation"
  Scenario: Delete spotify user and Purchase Offer with new MSISDN
    Given I a have deleted my user in spotify
    When I subscribe to spotify with a new msisdn
    Then I will purchase spotify trial

    # we can then repeat with same msisdn and we should not get trial - but chargeable instead