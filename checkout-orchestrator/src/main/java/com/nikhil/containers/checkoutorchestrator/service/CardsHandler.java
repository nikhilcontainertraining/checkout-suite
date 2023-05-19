package com.nikhil.containers.checkoutorchestrator.service;

import com.nikhil.containers.checkoutorchestrator.model.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CardsHandler {

    static Logger log = LogManager.getLogger(CardsHandler.class);

    @Autowired
    private RestTemplate restTemplate;

    public List<Card> getCards(boolean newSet) {
        log.info("Request to get cards from downstream/backend STARTED");

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

        List<Card> cards = getCardsResponse.getBody();


        log.info("Request to get cards from downstream/backend COMPLETE");
        return cards;
    }

}
