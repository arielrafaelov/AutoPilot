package com.etech7.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.etech7.entity.Agents;

@Repository
public interface AgentsRepository extends MongoRepository<Agents, String> {

    Optional<Agents> findByAgentName(String agentName);

}