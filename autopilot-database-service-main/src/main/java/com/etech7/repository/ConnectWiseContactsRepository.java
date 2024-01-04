package com.etech7.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.etech7.entity.ConnectWiseContacts;

@Repository
public interface ConnectWiseContactsRepository extends MongoRepository<ConnectWiseContacts, String> {
    List<ConnectWiseContacts> findByMspCustomDomain(String mspCustomDomain);

}