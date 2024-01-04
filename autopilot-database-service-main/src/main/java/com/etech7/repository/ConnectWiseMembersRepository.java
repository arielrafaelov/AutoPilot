package com.etech7.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.etech7.entity.ConnectWiseMembers;

@Repository
public interface ConnectWiseMembersRepository extends MongoRepository<ConnectWiseMembers, String> {
    List<ConnectWiseMembers> findByMspCustomDomain(String mspCustomDomain);

}