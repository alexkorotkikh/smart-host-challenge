package com.smarthost.challenge;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@SpringBootApplication
public class ChallengeApplication {

    private static final Logger log = LoggerFactory.getLogger(ChallengeApplication.class);
    private static final String URL = "https://gist.githubusercontent.com/fjahr/b164a446db285e393d8e4b36d17f4143/raw/0eb2e48bf4d1b8ae97631aa341a9c72489a90e43/smarthost_hotel_guests.json";

    public static void main(String args[]) {
        SpringApplication.run(ChallengeApplication.class);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(JsonParser.Feature.ALLOW_TRAILING_COMMA, true);
    }

    @Bean
    public List<Integer> guestBids(RestTemplate restTemplate,
                                   ObjectMapper objectMapper) throws Exception {
        final ResponseEntity<String> res = restTemplate.getForEntity(URL, String.class);
        final JsonNode jsonNode = objectMapper.readTree(res.getBody());
        final Integer[] o = objectMapper.readerFor(Integer[].class).readValue(jsonNode);
        final List<Integer> bids = Arrays.asList(o);
        bids.sort(Comparator.comparingInt(b -> -b));
        log.info("guest bids: " + bids.toString());
        return bids;
    }
}
