package com.etech7.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.etech7.entity.Roles;
import com.etech7.entity.TechnicianUsers;
import com.etech7.repository.TechnicianUsersRepository;

@Service
public class TechnicianUsersService {

    @Autowired
    private TechnicianUsersRepository technicianUsersRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<TechnicianUsers> getActiveTechniciansByMspCustomDomain(String mspCustomDomain) {
        return technicianUsersRepository.findActiveTechniciansByMspCustomDomain(mspCustomDomain);
    }

    public TechnicianUsers getTechnicianUserByIdAndMspCustomDomain(String id, String mspCustomDomain) {
        Criteria criteria = new Criteria();
        criteria.andOperator(
                Criteria.where("id").is(id),
                Criteria.where("mspCustomDomain").is(mspCustomDomain),
                new Criteria().orOperator(
                        Criteria.where("deleted").is(false),
                        Criteria.where("deleted").exists(false)));
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, TechnicianUsers.class);
    }

    public TechnicianUsers getTechnician(String email, String mspCustomDomain) {
        Criteria criteria = new Criteria();
        criteria.andOperator(
                Criteria.where("email").is(email),
                Criteria.where("mspCustomDomain").is(mspCustomDomain),
                new Criteria().orOperator(
                        Criteria.where("deleted").is(false),
                        Criteria.where("deleted").exists(false)));
        Query query = new Query(criteria);
        return mongoTemplate.findOne(query, TechnicianUsers.class);
    }

    public TechnicianUsers updateToDeleteUsersById(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        Update update = new Update();
        update.set("deleted", true);
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);
        return mongoTemplate.findAndModify(query, update, options, TechnicianUsers.class);

    }
    // public TechnicianUsers addTechnicianUser(TechnicianUsers technicianUser) {
    // String hashedPassword =
    // bCryptPasswordEncoder.encode(technicianUser.getPassword());
    // technicianUser.setPassword(hashedPassword);
    // return technicianUsersRepository.save(technicianUser);
    // }

    public TechnicianUsers addTechnicianUser(String mspCustomDomain, TechnicianUsers technicianUser) {
        List<TechnicianUsers> existingTechnicianUsers = getActiveTechniciansByMspCustomDomain(mspCustomDomain);
        technicianUser.setMspCustomDomain(mspCustomDomain);
        if (technicianUser.getRoleId() == null) {
            // If this is the first technician user for this MSP, set the roleId to
            // mspSuperAdmin
            if (existingTechnicianUsers.isEmpty()) {
                technicianUser.setRoleId("653ff2126a55f75b62a1b558");
            }
            // If there are already technician users for this MSP, set the roleId to regular
            // -> no permissions
            else {
                technicianUser.setRoleId("654002b96a55f75b62a1b55b");
            }
        }
        String hashedPassword = bCryptPasswordEncoder.encode(technicianUser.getPassword());
        technicianUser.setPassword(hashedPassword);
        return technicianUsersRepository.save(technicianUser);
    }

    public TechnicianUsers verifyPasswordAndGetTechUser(String email, String mspCustomDomain, String password) {
        TechnicianUsers technician = getTechnician(email, mspCustomDomain);
        if (technician != null && bCryptPasswordEncoder.matches(password, technician.getPassword())) {
            return technician;
        } else {
            return null;
        }
    }

    // public boolean verifyPassword(String email, String mspCustomDomain, String
    // password) {
    // TechnicianUsers technician = getTechnician(email, mspCustomDomain);
    // return technician != null && bCryptPasswordEncoder.matches(password,
    // technician.getPassword());
    // }

    public TechnicianUsers editTechnicianUser(TechnicianUsers technicianUser, String mspCustomDomain) {
        TechnicianUsers existingUser = getTechnician(technicianUser.getEmail(), mspCustomDomain);
        if (existingUser == null) {
            return null;
        }

        // if (technicianUser.getMspId() != null) {
        // existingUser.setMspId(technicianUser.getMspId());
        // }
        if (technicianUser.getMspCustomDomain() != null) {
            existingUser.setMspCustomDomain(technicianUser.getMspCustomDomain());
        }
        if (technicianUser.getRoleId() != null) {
            existingUser.setRoleId(technicianUser.getRoleId());
        }
        if (technicianUser.getFirstName() != null) {
            existingUser.setFirstName(technicianUser.getFirstName());
        }
        if (technicianUser.getLastName() != null) {
            existingUser.setLastName(technicianUser.getLastName());
        }
        if (technicianUser.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(technicianUser.getPhoneNumber());
        }
        if (technicianUser.getPassword() != null) {
            existingUser.setPassword(technicianUser.getPassword());
        }
        // existingUser.setDeleted(technicianUser.isDeleted());
        // if (technicianUser.getTierLevel() != null) {
        // existingUser.setTierLevel(technicianUser.getTierLevel());
        // }
        if (technicianUser.getConnectWiseTechnicanId() != 0) {

            existingUser.setConnectWiseTechnicanId(technicianUser.getConnectWiseTechnicanId());
        }

        return technicianUsersRepository.save(existingUser);
    }

    public boolean resetPassword(TechnicianUsers technician, String oldPassword, String newPassword) {
        if (bCryptPasswordEncoder.matches(oldPassword, technician.getPassword())) {
            String hashedNewPassword = bCryptPasswordEncoder.encode(newPassword);
            technician.setPassword(hashedNewPassword);
            technicianUsersRepository.save(technician);
            return true;
        }
        return false;
    }
}
