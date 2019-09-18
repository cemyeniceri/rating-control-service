package com.tdd.book.rating.service;

import com.tdd.book.rating.config.RatingControlServiceConfig;
import com.tdd.book.rating.exceptions.BookNotFoundException;
import com.tdd.book.rating.exceptions.TechnicalFailureException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RatingControlServiceImplTest {

    private static final String VALID_URL_BOOK_SERVICE = "https://my-third-party.service.com/fetch/book/rating/{book_Id}";
    private static final String TEST_SAMPLE_BOOK_ID = "M1211";
    private static final String CUSTOMER_RATING_LEVEL_CODE_12 = "12";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_12 = "12";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_U = "U";
    private static final String CUSTOMER_RATING_LEVEL_CODE_U = "U";
    private static final String CUSTOMER_RATING_LEVEL_CODE_8 = "8";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_8 = "8";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_15 = "15";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_18 = "18";
    private static final String CUSTOMER_RATING_LEVEL_CODE_18 = "18";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_XX = "XX";

    @Mock
    private RestTemplate restTemplate;
    @Mock
    private RatingControlServiceConfig ratingControlServiceConfig;

    private RatingControlServiceImpl ratingControlService;

    @Before
    public void setUp() {
        when(ratingControlServiceConfig.getBookServiceEndpoint()).thenReturn(VALID_URL_BOOK_SERVICE);
        ratingControlService = new RatingControlServiceImpl(restTemplate, ratingControlServiceConfig);
    }

    @Test
    public void shouldReturnFalse_whenTechnicalFailureExceptionIsThrownFromBookService() {
        when(restTemplate.exchange(anyString(), any(), any(), Mockito.<Class<String>>any())).thenThrow(TechnicalFailureException.class);
        assertFalse("Read Book eligibility is true", ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_SAMPLE_BOOK_ID));
    }

    @Test(expected = BookNotFoundException.class)
    public void shouldReturnBookNotFoundException_whenExceptionIsThrownFromBookServiceForBookNotFound() {
        when(restTemplate.exchange(anyString(), any(), any(), Mockito.<Class<String>>any())).thenThrow(new BookNotFoundException("Book not Found"));
        ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_SAMPLE_BOOK_ID);
    }

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturnAsU_andCustomerProvidedRatingCodeIsU() {
        when(restTemplate.exchange(anyString(), any(), any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_U, HttpStatus.OK));
        assertTrue("Read book eligibility is false", ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_U, TEST_SAMPLE_BOOK_ID));
    }

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturnAsU_andCustomerProvidedRatingCodeIs8() {
        when(restTemplate.exchange(anyString(), any(), any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_U, HttpStatus.OK));
        assertTrue("Read book eligibility is false", ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_8, TEST_SAMPLE_BOOK_ID));
    }

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturnAs8_andCustomerProvidedRatingCodeIs12() {
        when(restTemplate.exchange(anyString(), any(), any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_8, HttpStatus.OK));
        assertTrue("Read book eligibility is false", ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_SAMPLE_BOOK_ID));
    }

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturnAs12_andCustomerProvidedRatingCodeIs12() {
        when(restTemplate.exchange(anyString(), any(), any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_12, HttpStatus.OK));
        assertTrue("Read book eligibility is false", ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_SAMPLE_BOOK_ID));
    }

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturnAs15_andCustomerProvidedRatingCodeIs18() {
        when(restTemplate.exchange(anyString(), any(), any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_15, HttpStatus.OK));
        assertTrue("Read book eligibility is false", ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_18, TEST_SAMPLE_BOOK_ID));
    }

    @Test
    public void shouldReturnFalse_whenBookCodeLevelReturnAs15_andCustomerProvidedRatingCodeIs12() {
        when(restTemplate.exchange(anyString(), any(), any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_15, HttpStatus.OK));
        assertFalse("Read book eligibility is false", ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_SAMPLE_BOOK_ID));
    }

    @Test
    public void shouldReturnFalse_whenBookCodeLevelReturnAs18_andCustomerProvidedRatingCodeIs12() {
        when(restTemplate.exchange(anyString(), any(), any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_18, HttpStatus.OK));
        assertFalse("Read book eligibility is false", ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_SAMPLE_BOOK_ID));
    }

    @Test
    public void shouldReturnFalse_whenBookCodeLevelReturnAsXX_andCustomerProvidedRatingCodeIs18() {
        when(restTemplate.exchange(anyString(), any(), any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_XX, HttpStatus.OK));
        assertFalse("Read book eligibility is false", ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_18, TEST_SAMPLE_BOOK_ID));
    }
}