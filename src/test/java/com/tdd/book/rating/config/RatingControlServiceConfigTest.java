package com.tdd.book.rating.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RatingControlServiceConfigTest {

    private static final String BOOK_SERVICE_URL = "https://my-third-party.service.com/fetch/book/rating/";

    @Autowired
    private RatingControlServiceConfig ratingControlServiceConfig;

    @Test
    public void loadContext() {
    }

    @Test
    public void shouldLoadBookServiceEndpoint() {
        assertFalse("Book Service Endpoint value is null", StringUtils.isEmpty(ratingControlServiceConfig.getBookServiceEndpoint()));
        assertEquals("Book Service Endpoint value mismatch", BOOK_SERVICE_URL, ratingControlServiceConfig.getBookServiceEndpoint());
    }
}
