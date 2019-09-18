package com.tdd.book.rating.service;

import com.tdd.book.rating.common.RatingLevels;
import com.tdd.book.rating.config.RatingControlServiceConfig;
import com.tdd.book.rating.exceptions.BookNotFoundException;
import com.tdd.book.rating.exceptions.TechnicalFailureException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RatingControlServiceImpl implements RatingControlService {

    private RestTemplate restTemplate;
    private RatingControlServiceConfig ratingControlServiceConfig;

    public RatingControlServiceImpl(RestTemplate restTemplate, RatingControlServiceConfig ratingControlServiceConfig) {
        this.restTemplate = restTemplate;
        this.ratingControlServiceConfig = ratingControlServiceConfig;
    }

    @Override
    public boolean canReadBook(String controlLevel, String bookId) {
        Map<String, Integer> ratingControlLevelMap = RatingLevels.RATING_CODE_LEVEL;
        Integer customerProvidedRatingLevelOrder = ratingControlLevelMap.get(controlLevel);
        HttpEntity<?> requestEntity = new HttpEntity<>(generateHeader());
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    ratingControlServiceConfig.getBookServiceEndpoint() + bookId,
                    HttpMethod.GET,
                    requestEntity,
                    String.class);

            if (HttpStatus.OK == responseEntity.getStatusCode()) {
                Integer bookRatingControlLevel = ratingControlLevelMap.get(responseEntity.getBody());
                if (containsValidRatingLevelCodes(bookRatingControlLevel, customerProvidedRatingLevelOrder)) {
                    return bookRatingControlLevel <= customerProvidedRatingLevelOrder;
                }
            }
            return false;
        } catch (TechnicalFailureException te) {
            return false;
        }catch (BookNotFoundException be){
            throw new BookNotFoundException("Book not Found");
        }
    }

    private boolean containsValidRatingLevelCodes(Integer bookRatingControlLevel, Integer customerProvidedRatingLevelOrder) {
        return !StringUtils.isEmpty(bookRatingControlLevel) && !StringUtils.isEmpty(customerProvidedRatingLevelOrder);
    }

    private MultiValueMap<String, String> generateHeader() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Accept", "application/json");
        headers.add("Contetnt-Type", "application/json");
        return headers;
    }
}
