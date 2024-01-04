package com.etech7.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.etech7.entity.Roles;

@Repository
public interface RolesRepository extends MongoRepository<Roles, String> {
    
}