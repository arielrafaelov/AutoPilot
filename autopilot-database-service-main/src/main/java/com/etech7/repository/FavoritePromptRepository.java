package com.etech7.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.etech7.entity.FavoritePrompts;

@Repository
public interface FavoritePromptRepository extends MongoRepository<FavoritePrompts, String> {   
		
	
}