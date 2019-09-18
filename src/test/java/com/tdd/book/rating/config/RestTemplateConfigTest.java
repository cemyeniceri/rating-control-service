package com.tdd.book.rating.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RestTemplateConfigTest {

    @Autowired
    private RestTemplateConfig restTemplateConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void shouldHaveValidBeanCreatedForRestTemplate(){
        assertNotNull("RestTemplate bean is null", restTemplateConfig.restTemplate());
    }
}
