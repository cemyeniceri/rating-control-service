package com.tdd.book.rating.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RatingControlServiceConfig {

    @Value("${api.bookService.endpoint}")
    private String bookServiceEndpoint;

    public String getBookServiceEndpoint() {
        return bookServiceEndpoint;
    }

    public void setBookServiceEndpoint(String bookServiceEndpoint) {
        this.bookServiceEndpoint = bookServiceEndpoint;
    }
}
