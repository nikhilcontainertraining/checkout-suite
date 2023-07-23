package com.nikhil.containers.checkoutorchestrator.api;

import com.nikhil.containers.checkoutorchestrator.model.Card;
import com.nikhil.containers.checkoutorchestrator.service.CardsHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
public class AllCardsApi {

    @Autowired
    private CardsHandler cardsHandler;

    @CrossOrigin
    @GetMapping(value = "/test")
    @ResponseBody
    public ResponseEntity<String> getApp() {

        String userEnv = System.getenv("userEnv");

        System.out.println("userEnv :: " + userEnv);

        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<h1>FRONT END CONTAINER</h1>\n" +
                "\n" +
                "<p>CHECKOUT ORCHESTRATOR</p>\n" +
                "\n" +

                "\n" +
                "<p>" + userEnv + "</p>\n" +
                "\n" +

                "</body>\n" +
                "</html>\n" +
                "\n";
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(html);
    }

    @GetMapping(value = "/card/credit")
    public ResponseEntity<List<Card>> getCards() {
        return ResponseEntity.ok(cardsHandler.getCards(false));
    }

    @GetMapping(value = "/card/credit/new")
    public ResponseEntity<List<Card>> getNewCards() {
        return ResponseEntity.ok(cardsHandler.getCards(true));
    }

    @GetMapping(value = "/card/credit/file")
    public ResponseEntity<InputStreamResource> getCardsOnFile() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(new InputStreamResource(cardsHandler.getCardsOnFile()));
    }

    @GetMapping(value = "/kill")
    public ResponseEntity<List<Card>> getKill() {
        System.exit(0);
        return ResponseEntity.ok(cardsHandler.getCards(true));
    }


}
