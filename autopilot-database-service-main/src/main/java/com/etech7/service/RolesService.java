package com.etech7.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.etech7.entity.Roles;
import com.etech7.repository.RolesRepository;

@Service
public class RolesService {

	
	@Autowired
	private MongoTemplate mongoTemplate;
	
    @Autowired
    private RolesRepository rolesRepository;

    public Roles createRole(Roles role) {
        return rolesRepository.save(role);
    }

    public Optional<Roles> getRole(String id) {
        return rolesRepository.findById(id);
    }
    
    public Roles getRoleByName(String name) {
        Query query = new Query(Criteria.where("name").is(name)
                                        .andOperator(Criteria.where("deleted").is(false),
                                                     Criteria.where("deleted").exists(false)));
        return mongoTemplate.findOne(query, Roles.class);
    }


    public List<Roles> getAllRoles(String mspCustomDomain) {
        List<Roles> roles = new ArrayList<>();
        Query queryCustomFalse = new Query(Criteria.where("isCustom").is(false));
        roles.addAll(mongoTemplate.find(queryCustomFalse, Roles.class));
        Criteria commonCriteria = new Criteria().orOperator(
                Criteria.where("deleted").is(false),
                Criteria.where("deleted").exists(false)
        );
        if (mspCustomDomain != null && !mspCustomDomain.isEmpty()) {
            Query queryCustomTrueAndDomainMatch = new Query(Criteria.where("isCustom").is(true)
                                                                    .and("mspCustomDomain").is(mspCustomDomain) .andOperator(commonCriteria));
            roles.addAll(mongoTemplate.find(queryCustomTrueAndDomainMatch, Roles.class));
        }
        return roles;
    }
    
	public Roles updateToDeleteRoleById(String id) {
	    Query query = new Query(Criteria.where("id").is(id));
	    Update update = new Update();
	    update.set("deleted", true);	    
	    FindAndModifyOptions options = new FindAndModifyOptions();
	    options.returnNew(true);
	    return mongoTemplate.findAndModify(query, update, options, Roles.class);

	}
}
