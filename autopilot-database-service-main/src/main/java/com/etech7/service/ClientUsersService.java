package com.etech7.service;

import com.etech7.dto.Address;
import com.etech7.entity.ClientUsers;
import com.etech7.entity.Messages;
import com.etech7.repository.ClientUsersRepository;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientUsersService {

    @Autowired
    private ClientUsersRepository clientUsersRepository;
    
	@Autowired
	private MongoTemplate mongoTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    
    public ClientUsers getClientByIdAndMspCustomDomain(String id, String mspCustomDomain) {
        Criteria criteria = new Criteria();
        criteria.andOperator(
                Criteria.where("id").is(id),
                Criteria.where("mspCustomDomain").is(mspCustomDomain),
                new Criteria().orOperator(
                        Criteria.where("deleted").is(false),
                        Criteria.where("deleted").exists(false)
                )
        );
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, ClientUsers.class);
    }
    
//    public ClientUsers getClient(String email, String mspCustomDomain) {
//        return clientUsersRepository.findByEmailAndMspCustomDomain(email, mspCustomDomain).orElse(null);
//    }
    
    
    public ClientUsers getClient(String email, String mspCustomDomain) {
        Criteria criteria = new Criteria();
        criteria.andOperator(
                Criteria.where("email").is(email),
                Criteria.where("mspCustomDomain").is(mspCustomDomain),
                new Criteria().orOperator(
                        Criteria.where("deleted").is(false),
                        Criteria.where("deleted").exists(false)
                )
        );
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, ClientUsers.class);
    }

    public ClientUsers addClientUser(ClientUsers clientUser) {
        String hashedPassword = bCryptPasswordEncoder.encode(clientUser.getPassword());
        clientUser.setPassword(hashedPassword);
        return clientUsersRepository.save(clientUser);
    }

    public boolean verifyPassword(String email, String mspCustomDomain, String password) {
        ClientUsers client = getClient(email, mspCustomDomain);
        return client != null && bCryptPasswordEncoder.matches(password, client.getPassword());
    }
    
	public ClientUsers updateToDeleteUsersById(String id) {
	    Query query = new Query(Criteria.where("id").is(id));
	    Update update = new Update();
	    update.set("deleted", true);
//        return mongoTemplate.findAndModify(query, update, Message.class);
	    
	    FindAndModifyOptions options = new FindAndModifyOptions();
	    options.returnNew(true);

	    return mongoTemplate.findAndModify(query, update, options, ClientUsers.class);

	}
	
    public ClientUsers editClientUser(ClientUsers clientUser, String mspCustomDomain) {
        ClientUsers existingUser = getClient(clientUser.getEmail(), mspCustomDomain);
        if (existingUser == null) {
            return null;
        }

//        if (clientUser.getMspId() != null) {
//            existingUser.setMspId(clientUser.getMspId());
//        }
        if (clientUser.getRoleId() != null) {
            existingUser.setRoleId(clientUser.getRoleId());
        }
        if (clientUser.getMspCustomDomain() != null) {
            existingUser.setMspCustomDomain(clientUser.getMspCustomDomain());
        }
        if (clientUser.getConnectWiseClientsAutopilotDbId() != null) {
            existingUser.setConnectWiseClientsAutopilotDbId((clientUser.getConnectWiseClientsAutopilotDbId()));
        }
        if (clientUser.getConnectWiseContactsAutopilotDbId() != null) {
            existingUser.setConnectWiseContactsAutopilotDbId((clientUser.getConnectWiseContactsAutopilotDbId()));
        }
        if (clientUser.getFirstName() != null) {
            existingUser.setFirstName(clientUser.getFirstName());
        }
        if (clientUser.getLastName() != null) {
            existingUser.setLastName(clientUser.getLastName());
        }
        if (clientUser.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(clientUser.getPhoneNumber());
        }
        if (clientUser.getCompanyName() != null) {
            existingUser.setCompanyName(clientUser.getCompanyName());
        }
         
        Address newAddress = clientUser.getCompanyAddress();
        Address existingAddress = existingUser.getCompanyAddress();

        if (!Objects.isNull(newAddress) && !Objects.isNull(existingAddress)) {
            if (!Objects.isNull(newAddress.getStreet())) {
                existingAddress.setStreet(newAddress.getStreet());
            }
            if (!Objects.isNull(newAddress.getAdditionalInformation())) {
                existingAddress.setAdditionalInformation(newAddress.getAdditionalInformation());
            }
            if (!Objects.isNull(newAddress.getCity())) {
                existingAddress.setCity(newAddress.getCity());
            }
            if (!Objects.isNull(newAddress.getState())) {
                existingAddress.setState(newAddress.getState());
            }
            if (!Objects.isNull(newAddress.getZipcode())) {
                existingAddress.setZipcode(newAddress.getZipcode());
            }
        }

        return clientUsersRepository.save(existingUser);
    }


    public boolean resetPassword(ClientUsers client, String oldPassword, String newPassword) {
        if (bCryptPasswordEncoder.matches(oldPassword, client.getPassword())) {
            String hashedNewPassword = bCryptPasswordEncoder.encode(newPassword);
            client.setPassword(hashedNewPassword);
            clientUsersRepository.save(client);
            return true;
        }
        return false;
    }

}
