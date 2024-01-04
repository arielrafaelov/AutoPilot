package com.etech7.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.etech7.entity.SupportTickets;

@Repository
public interface SupportTicketRepository extends MongoRepository<SupportTickets, String> {

//    public Optional<SupportTicket> findById(String ID);
//
//    public List<SupportTicket> findByBusinessEmail(String Type);
    
}