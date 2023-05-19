package com.nikhil.containers.paymentdataservices.service;
import com.nikhil.containers.paymentdataservices.repository.model.Card;
import com.nikhil.containers.paymentdataservices.model.GetCardsResponse;
//import com.nikhil.containers.paymentdataservices.repository.CardRepository;
import com.nikhil.containers.paymentdataservices.repository.CardRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class CardsHandler {

    static Logger log = LogManager.getLogger(CardsHandler.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired(required = false)
    private CardRepository cardRepository;

    public List<Card> getCardsFromRepository() {
        log.info("Request to getCardsFromRepository STARTED");
        List<Card> cards = new ArrayList<>();
        try {
            cards = cardRepository.findAll();
        } catch (Exception e) {
            log.error("ERROR: DB : Failed to fetch cards");
        }
        log.info("Request to getCardsFromRepository COMPLETE");
        return cards;
    }

    public List<Card> getCards() {
        log.info("Request to getCards STARTED");

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

        try {
            cards.forEach(card -> cardRepository.save(card));
        } catch (Exception e) {
            log.error("ERROR: DB : Failed to save cards");
        }

        log.info("Request to getCards COMPLETE");

        return cards;
    }

}
