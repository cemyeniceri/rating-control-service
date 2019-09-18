package com.tdd.book.rating.controller;

import com.tdd.book.rating.service.RatingControlService;
import com.tdd.book.swagger.api.RclApi;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingControlLevelController implements RclApi {

    private static final String ALPHABET_REGEX = "[a-zA-z]+";
    private static final String NUMBER_REGEX = "[0-9]+";
    private static final String NO_SPECIAL_CHAR_REGEX = "[a-zA-z0-9 ]*+";

    @Autowired
    private RatingControlService ratingControlService;

    @GetMapping(value = "/rcl/book/v1/read/eligibility/{control_level}/{book_id}")
    public ResponseEntity<Boolean> getControlAccess(@ApiParam(value = "set control level", required = true) @PathVariable("control_level") String controlLevel,
                                                    @ApiParam(value = "book id of desired book", required = true) @PathVariable("book_id") String bookId) {

       if(!isValidControlLevel(controlLevel) || containsSpecialCharacters(bookId)){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
        boolean isCanReadBook = ratingControlService.canReadBook(controlLevel, bookId);
        return new ResponseEntity<>(isCanReadBook, HttpStatus.OK);
    }

    private boolean containsSpecialCharacters(String bookId) {
        return !bookId.matches(NO_SPECIAL_CHAR_REGEX);
    }

    private boolean isValidControlLevel(String controlLevel) {
        return controlLevel.matches(ALPHABET_REGEX) || controlLevel.matches(NUMBER_REGEX);
    }
}
