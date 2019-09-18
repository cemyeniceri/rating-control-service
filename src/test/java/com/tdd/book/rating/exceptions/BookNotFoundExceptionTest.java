package com.tdd.book.rating.exceptions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BookNotFoundExceptionTest {

    @Rule
    public ExpectedException expectedException= ExpectedException.none();

    @Test
    public void shouldThrowBookNotFoundExceptionWithMessage_whenBookIsNotFound(){
        expectedException.expect(BookNotFoundException.class);
        expectedException.expectMessage("The Book Service could not find the given book");
        throw new BookNotFoundException("The Book Service could not find the given book");
    }
}
