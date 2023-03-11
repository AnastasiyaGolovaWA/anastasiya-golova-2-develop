Feature: Login

  Scenario: Search by title
    Given a news article with title "smart"
    When I search for news with title "smart"
    Then I should see a news article with title "smart"
