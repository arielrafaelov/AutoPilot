package com.etech7.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.etech7.entity.Notes;

@Repository
public interface NoteRepository extends MongoRepository<Notes, String> {

}