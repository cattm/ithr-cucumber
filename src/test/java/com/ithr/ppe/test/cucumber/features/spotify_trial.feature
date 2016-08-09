@trial
Feature: Spotify Trial demonstration
This feature will test two spotify scenarios where the suer terminates an already purchased trial offer
and then looks to repeat the operation with either the same or changed MSISDN
the trail should still be available if the MSISDN is changed

  Background: Previously Purchased Spotify
    Given I am a "GB" customer already purchased Spotify with:
      | package     | usergroup | contained in               |
      | PK_4GTariff | 4glarge   | Spotify hardbundle v1.json |

  Scenario: Delete spotify user and Purchase Offer with new MSISDN
    Given I can delete my user in spotify
    When I subscribe to spotify with a new msisdn
    Then I will purchase spotify trial

  # we can then repeat with same msisdn and we should not get trial - but chargeable instead
  Scenario: Delete spotify user and Purchase Offer with  SameMSISDN
    Given I can delete my user in spotify
    When I subscribe to spotify with same msisdn
    Then I will purchase spotify paid
