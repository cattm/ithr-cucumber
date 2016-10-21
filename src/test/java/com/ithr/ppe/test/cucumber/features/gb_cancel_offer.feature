@cancel
Feature: GB Cancel Offers
  This feature will attempt to exercise the cancel flow for GB offers
  It will first make a purchase and then refresh and cancel
  Variant behaviour will be verified elsewhere

  Scenario Outline: Purchase and then cancel Sky
    Given I am a "GB" customer Who Initially purchases "SKY" offer
    And the offer is defined by package <package> and usergroup <usergroup> with json <contained in>
    And I can see the "SKY" subscription defined by <contained in>
    Then I will cancel the offer

    Examples: 
      | package     | usergroup    | contained in   |
      | PK_4GTariff | 4gsmall      | Sky hardbundle |
      | PK_4GTariff | 4gmbblarge   | Sky standalone |
      | Not Valid   | No usergroup | Sky standalone |

  Scenario Outline: Purchase and then Cancel Spotify
    Given I am a "GB" customer Who Initially purchases "SPOTIFY" offer
    And the offer is defined by package <package> and usergroup <usergroup> with json <contained in>
    And I can see the "SPOTIFY" subscription defined by <contained in>
    Then I will cancel the offer

    Examples: 
      | package     | usergroup | contained in             |
      | PK_4GTariff | 4gsmall   | Spotify hardbundle       |
      | Not Valid   | Not Valid | Spotify standalone trial |

  @ignore
  Scenario Outline: Purchase and then Cancel NowTV
    Given I am a "GB" customer Who Initially purchases "NOWTV" offer
    And the offer is defined by package <package> and usergroup <usergroup> with json <contained in>
    And I can see the "NOWTV" subscription defined by <contained in>
    Then I will cancel the offer

    Examples: 
      | package     | usergroup | contained in     |
      | PK_4GTariff | 4gsmall   | NowTV hardbundle |

  Scenario Outline: Purchase and then Cancel Netflix
    Given I am a "GB" customer Who Initially purchases "NETFLIX" offer
    And the offer is defined by package <package> and usergroup <usergroup> with json <contained in>
    And I can see the "NETFLIX" subscription defined by <contained in>
    Then I will cancel the offer

    Examples: 
      | package   | usergroup    | contained in             |
      | Not Valid | No usergroup | Netflix standalone trial |
