package com.tdd.book.rating.common;

import java.util.LinkedHashMap;
import java.util.Map;

public class RatingLevels {
    public static final Map<String, Integer> RATING_CODE_LEVEL = new LinkedHashMap<>();

    static {
        RATING_CODE_LEVEL.put("U", 0);
        RATING_CODE_LEVEL.put("8", 1);
        RATING_CODE_LEVEL.put("12", 2);
        RATING_CODE_LEVEL.put("15", 3);
        RATING_CODE_LEVEL.put("18", 4);
    }
}
