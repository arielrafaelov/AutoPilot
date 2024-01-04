package com.etech7.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.etech7.entity.ConnectWiseClients;

@Repository
public interface ConnectWiseClientsRepository extends MongoRepository<ConnectWiseClients, String> {
    List<ConnectWiseClients> findByMspCustomDomain(String mspCustomDomain);

}