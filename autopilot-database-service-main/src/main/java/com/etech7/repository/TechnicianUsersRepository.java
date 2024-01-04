package com.etech7.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.etech7.entity.TechnicianUsers;

@Repository
public interface TechnicianUsersRepository extends MongoRepository<TechnicianUsers, String> {

	@Query("{'mspCustomDomain': ?0, $or: [{'deleted': false}, {'deleted': {$exists: false}}]}")
	List<TechnicianUsers> findActiveTechniciansByMspCustomDomain(String mspCustomDomain);

	Optional<TechnicianUsers> findByEmailAndMspCustomDomain(String email, String mspCustomDomain);

	List<TechnicianUsers> findByMspCustomDomain(String mspCustomDomain);

}