Feature: Rating Control

  Scenario: Rating Control Level Decision to Read Book
    Given I am a customer who have set rating control level 12
    When I request to read equal level book B1234
    Then I get decision to read the book

    Given I am a customer who have set rating control level 12
    When I request to read higher level book BH1234
    Then I get decision not to read the book