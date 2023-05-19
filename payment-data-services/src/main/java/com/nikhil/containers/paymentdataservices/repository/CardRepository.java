package com.nikhil.containers.paymentdataservices.repository;

import com.nikhil.containers.paymentdataservices.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
}
