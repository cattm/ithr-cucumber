@ignore
Feature: IE Purchase Dropbox
  This feature will take each package/usergroup combination and attempt to obtain the correct dropbox package.
  It will check all displayed text conforms to the expected json definition
  No previous alternate offers are assumed
  Variant behaviour will be verified elsewhere

  #There is a file format problem with some of the JSON files at the moment
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
