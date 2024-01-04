 package com.etech7.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.etech7.entity.Integrations;

@Repository
public interface IntegrationsRepository extends MongoRepository<Integrations, String> {   
    Integrations findByMspCustomDomain(String mspCustomDomain);

	
}