package com.tdd.book.rating.fixture;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class BookServiceFixture {
    public static void stubBookServiceResponseForBook_B1234_Rating12(String bookId) {
        stubFor(get(urlEqualTo("/fetch/book/rating/" + bookId))
                .withHeader("Accept", equalTo("application/json"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("12")));
    }

    public static void stubBookServiceResponseForBook_BH1234_Rating15(String bookId) {
        stubFor(get(urlEqualTo("/fetch/book/rating/" + bookId))
                .withHeader("Accept", equalTo("application/json"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("15")));
    }
}
