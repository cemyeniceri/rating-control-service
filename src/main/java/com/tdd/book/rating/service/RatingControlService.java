package com.tdd.book.rating.service;

public interface RatingControlService {
    boolean canReadBook(String controlLevel, String bookId);
}
