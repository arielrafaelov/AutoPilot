package com.etech7.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.etech7.entity.ClientUsers;

@Repository
public interface ClientUsersRepository extends MongoRepository<ClientUsers, String> {   
	
    Optional<ClientUsers> findByEmailAndMspCustomDomain(String email, String mspCustomDomain);
    Optional<ClientUsers> findByIdAndMspCustomDomain(String id, String mspCustomDomain);

}