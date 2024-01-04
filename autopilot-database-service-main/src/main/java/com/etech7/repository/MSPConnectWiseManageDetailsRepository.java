package com.etech7.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.etech7.entity.MSPConnectWiseManageDetails;

@Repository
public interface MSPConnectWiseManageDetailsRepository extends MongoRepository<MSPConnectWiseManageDetails, String> {

    Optional<MSPConnectWiseManageDetails> findByMspCustomDomain(String mspCustomDomain);

	
}