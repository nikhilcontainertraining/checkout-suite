package com.nikhil.containers.checkoutorchestrator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikhil.containers.checkoutorchestrator.model.Card;
import com.nikhil.containers.checkoutorchestrator.model.GetCardsResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class CardsHandler {

    static Logger log = LogManager.getLogger(CardsHandler.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FileHandler fileHandler;

    public List<Card> getCards(boolean newSet) {
        log.info("Request to get cards from downstream/backend STARTED");

        ResponseEntity<List> getCardsResponse = getCardFromDownstream(newSet);

        List<Card> cards = getCardsResponse.getBody();

        saveToCrypt(cards);

        log.info("Request to get cards from downstream/backend COMPLETE");
        return cards;
    }

    public InputStream getCardsOnFile() {
        log.info("Request to get cards from FILE STARTED");

        String fileName = "card_on_file.json";

        InputStream cards = loadFromCrypt(fileName);

        log.info("Request to get cards from FILE COMPLETE");
        return cards;
    }






















    private ResponseEntity<List> getCardFromDownstream(boolean newSet) {
        ResponseEntity<List> getCardsResponse = null;
        try {
            getCardsResponse = newSet
                    ? restTemplate.getForEntity("http://payment-data-services:8080/cards/new", List.class)
                    : restTemplate.getForEntity("http://payment-data-services:8080/cards", List.class);
        } catch (Exception e) {
            log.error("ERROR Occurred : Request to get cards from downstream/backend: " + e.getMessage());
            log.error(e.getStackTrace());
            throw new RuntimeException("INTERNAL_SERVER_ERROR");
        }
        return getCardsResponse;

//        return getCardsFromPublicApi();
    }

    private boolean saveToCrypt(List<Card> cards) {
        ObjectMapper mapper = new ObjectMapper();
        String fileName = "card_on_file" + ".json";

        byte[] content = new byte[0];

        try {
            content = mapper.writeValueAsString(cards).getBytes();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return fileHandler.saveToFile(content, fileName);
    }

    private InputStream loadFromCrypt(String fileName) {
        try {
            return fileHandler.getFile(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseEntity<List> getCardsFromPublicApi() {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("_quantity", "5");

        ResponseEntity<GetCardsResponse> getCardsResponse = null;
        try {
            getCardsResponse = restTemplate.getForEntity("https://fakerapi.it/api/v1/credit_cards?_quantity=5",
                    GetCardsResponse.class);
        } catch (RestClientException e) {
            log.error("ERROR Occurred : Request to getCards from backend");
            log.error(e.getStackTrace());
            throw new RuntimeException("INTERNAL_SERVER_ERROR");
        }

        List<Card> cards = Objects.requireNonNull(
                getCardsResponse.getBody() ).getData();

        return ResponseEntity.ok(cards);
    }


}
