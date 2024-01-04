package com.etech7.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.etech7.entity.MSP;

@Repository
public interface MSPRepository extends MongoRepository<MSP, String> {
    MSP findByCustomDomain(String customDomain);
}