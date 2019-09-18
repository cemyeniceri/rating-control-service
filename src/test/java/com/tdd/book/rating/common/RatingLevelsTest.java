package com.tdd.book.rating.common;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;

public class RatingLevelsTest {

    private Map<String, Integer> actualRatingCodeMap =  RatingLevels.RATING_CODE_LEVEL;

    @Test
    public void shouldReturnParentalCodeLevelsBasedForRequestedParentalCode(){
        assertThat("Mismatch rating code level order for U", actualRatingCodeMap.get("U"), Matchers.equalTo(0));
        assertThat("Mismatch rating code level order for 8", actualRatingCodeMap.get("8"), Matchers.equalTo(1));
        assertThat("Mismatch rating code level order for 12", actualRatingCodeMap.get("12"), Matchers.equalTo(2));
        assertThat("Mismatch rating code level order for 15", actualRatingCodeMap.get("15"), Matchers.equalTo(3));
        assertThat("Mismatch rating code level order for 18", actualRatingCodeMap.get("18"), Matchers.equalTo(4));
    }
}
