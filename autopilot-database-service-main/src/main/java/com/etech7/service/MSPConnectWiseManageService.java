package com.etech7.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etech7.dto.MSPConnectWiseManageCategorizations;
import com.etech7.dto.MSPConnectWiseManageSubCategorizationTechnicians;
import com.etech7.dto.MSPConnectWiseManageSubCategorizations;
import com.etech7.entity.ConnectWiseClients;
import com.etech7.entity.ConnectWiseContacts;
import com.etech7.entity.ConnectWiseMembers;
import com.etech7.entity.MSPConnectWiseManageDetails;
import com.etech7.exception.CustomException;
import com.etech7.repository.ConnectWiseClientsRepository;
import com.etech7.repository.ConnectWiseContactsRepository;
import com.etech7.repository.ConnectWiseMembersRepository;
import com.etech7.repository.MSPConnectWiseManageDetailsRepository;

@Service
public class MSPConnectWiseManageService {

    @Autowired
    private MSPConnectWiseManageDetailsRepository mspConnectWiseManageDetailsRepository;
    
    @Autowired
    private ConnectWiseClientsRepository connectWiseClientsRepository;
    
    @Autowired
    private ConnectWiseContactsRepository connectWiseContactsRepository;

    @Autowired
    private ConnectWiseMembersRepository connectWiseMembersRepository;

    public MSPConnectWiseManageDetails findDetailsByMspCustomDomain(String mspCustomDomain) {
        return mspConnectWiseManageDetailsRepository.findByMspCustomDomain(mspCustomDomain).orElse(null);
    }
    
    public ConnectWiseMembers updateMemberTier(String memberId, String newTier) {
        Optional<ConnectWiseMembers> memberOpt = connectWiseMembersRepository.findById(memberId);

        if (memberOpt.isPresent()) {
            ConnectWiseMembers member = memberOpt.get();
            member.setTier(newTier);
            return connectWiseMembersRepository.save(member);
        } else {
            throw new CustomException("Member not found with ID: " + memberId);
        }
    }

    public MSPConnectWiseManageDetails saveOrUpdateDetails(String mspCustomDomain, MSPConnectWiseManageDetails details) {
        MSPConnectWiseManageDetails existingDetails = mspConnectWiseManageDetailsRepository.findByMspCustomDomain(mspCustomDomain)
                .orElse(null);

        if (existingDetails == null) {
            details.setMspCustomDomain(mspCustomDomain);
            return mspConnectWiseManageDetailsRepository.save(details);
        }

        existingDetails.setMspCustomDomain(details.getMspCustomDomain() != null ? details.getMspCustomDomain() : existingDetails.getMspCustomDomain());
        existingDetails.setBoardId(details.getBoardId() != 0 ? details.getBoardId() : existingDetails.getBoardId());
        existingDetails.setBoardName(details.getBoardName() != null ? details.getBoardName() : existingDetails.getBoardName());

    
        if (existingDetails.getMspConnectWiseManageCategorizations() == null) {
            existingDetails.setMspConnectWiseManageCategorizations(new ArrayList<>());
        }
        updateCategorizations(existingDetails.getMspConnectWiseManageCategorizations(), details.getMspConnectWiseManageCategorizations());

        return mspConnectWiseManageDetailsRepository.save(existingDetails);
    }

    private void updateCategorizations(List<MSPConnectWiseManageCategorizations> existingCats, List<MSPConnectWiseManageCategorizations> newCats) {
        if (newCats == null) return; 

        for (MSPConnectWiseManageCategorizations newCat : newCats) {
            Optional<MSPConnectWiseManageCategorizations> existingCatOpt = existingCats.stream()
                    .filter(ec -> ec.getCategoryId() == newCat.getCategoryId())
                    .findFirst();

            if (existingCatOpt.isPresent()) {
                MSPConnectWiseManageCategorizations existingCat = existingCatOpt.get();
                existingCat.setCategoryName(newCat.getCategoryName() != null ? newCat.getCategoryName() : existingCat.getCategoryName());

                if (existingCat.getMspConnectWiseManageSubCategorizations() == null) {
                    existingCat.setMspConnectWiseManageSubCategorizations(new ArrayList<>());
                }
                updateSubCategorizations(existingCat.getMspConnectWiseManageSubCategorizations(), newCat.getMspConnectWiseManageSubCategorizations());
            } else {
                existingCats.add(newCat);
            }
        }
    }

    private void updateSubCategorizations(List<MSPConnectWiseManageSubCategorizations> existingSubCats, List<MSPConnectWiseManageSubCategorizations> newSubCats) {
        if (newSubCats == null) return; // If newSubCats is null, there is nothing to update

        for (MSPConnectWiseManageSubCategorizations newSubCat : newSubCats) {
            Optional<MSPConnectWiseManageSubCategorizations> existingSubCatOpt = existingSubCats.stream()
                    .filter(esc -> esc.getSubCategoryId() == newSubCat.getSubCategoryId())
                    .findFirst();

            if (existingSubCatOpt.isPresent()) {
                MSPConnectWiseManageSubCategorizations existingSubCat = existingSubCatOpt.get();
                existingSubCat.setSubCategoryName(newSubCat.getSubCategoryName() != null ? newSubCat.getSubCategoryName() : existingSubCat.getSubCategoryName());
                existingSubCat.setPriority(newSubCat.getPriority() != null ? newSubCat.getPriority() : existingSubCat.getPriority());
                existingSubCat.setPriorityId(newSubCat.getPriorityId() != 0 ? newSubCat.getPriorityId() : existingSubCat.getPriorityId());
                existingSubCat.setImpact(newSubCat.getImpact() != null ? newSubCat.getImpact() : existingSubCat.getImpact());
                existingSubCat.setSeverity(newSubCat.getSeverity() != null ? newSubCat.getSeverity() : existingSubCat.getSeverity());
                existingSubCat.setTier(newSubCat.getTier() != null ? newSubCat.getTier() : existingSubCat.getTier());
                existingSubCat.setDurationToResolve(newSubCat.getDurationToResolve() != 0 ? newSubCat.getDurationToResolve() : existingSubCat.getDurationToResolve());

                if (existingSubCat.getMspConnectWiseManageSubCategorizationTechnicians() == null) {
                    existingSubCat.setMspConnectWiseManageSubCategorizationTechnicians(new ArrayList<>());
                }
                updateTechnicians(existingSubCat.getMspConnectWiseManageSubCategorizationTechnicians(), newSubCat.getMspConnectWiseManageSubCategorizationTechnicians());
            } else {
                existingSubCats.add(newSubCat);
            }
        }
    }

    private void updateTechnicians(List<MSPConnectWiseManageSubCategorizationTechnicians> existingTechs, List<MSPConnectWiseManageSubCategorizationTechnicians> newTechs) {
        if (newTechs == null) return; 

        for (MSPConnectWiseManageSubCategorizationTechnicians newTech : newTechs) {
            Optional<MSPConnectWiseManageSubCategorizationTechnicians> existingTechOpt = existingTechs.stream()
                    .filter(et -> et.getTechnicianId() == newTech.getTechnicianId())
                    .findFirst();

            if (existingTechOpt.isPresent()) {
                MSPConnectWiseManageSubCategorizationTechnicians existingTech = existingTechOpt.get();
                existingTech.setTechnicianName(newTech.getTechnicianName() != null ? newTech.getTechnicianName() : existingTech.getTechnicianName());
            } else {
                existingTechs.add(newTech); 
            }
        }
    }
    
    
    public List<ConnectWiseClients> getConnectWiseClientsByMspCustomDomain(String mspCustomDomain) {
        return connectWiseClientsRepository.findByMspCustomDomain(mspCustomDomain);
    }
    
    public List<ConnectWiseContacts> getConnectWiseContactsByMspCustomDomain(String mspCustomDomain) {
        return connectWiseContactsRepository.findByMspCustomDomain(mspCustomDomain);
    }
    
    public List<ConnectWiseMembers> getConnectWiseMembersByMspCustomDomain(String mspCustomDomain) {
        return connectWiseMembersRepository.findByMspCustomDomain(mspCustomDomain);
    }
    
    
    public List<ConnectWiseClients> addConnectWiseClients(String mspCustomDomain, List<ConnectWiseClients> clientsList) {
        List<ConnectWiseClients> savedClients = new ArrayList<>();
        for (ConnectWiseClients client : clientsList) {
            client.setMspCustomDomain(mspCustomDomain); 
            ConnectWiseClients savedClient = connectWiseClientsRepository.save(client);
            savedClients.add(savedClient);
        }
        return savedClients;
    }
    
    
    public List<ConnectWiseContacts> addConnectWiseContacts(String mspCustomDomain, List<ConnectWiseContacts> contactList) {
        List<ConnectWiseContacts> savedContacts = new ArrayList<>();
        for (ConnectWiseContacts contact : contactList) {
            contact.setMspCustomDomain(mspCustomDomain);
            ConnectWiseContacts savedClient = connectWiseContactsRepository.save(contact);
            savedContacts.add(savedClient);
        }
        return savedContacts;
    }
    
    public List<ConnectWiseMembers> addConnectWiseMembers(String mspCustomDomain, List<ConnectWiseMembers> memberList) {
        List<ConnectWiseMembers> savedMembers = new ArrayList<>();
        for (ConnectWiseMembers member : memberList) {
            member.setMspCustomDomain(mspCustomDomain);
            ConnectWiseMembers savedClient = connectWiseMembersRepository.save(member);
            savedMembers.add(savedClient);
        }
        return savedMembers;
    }
    
    public ConnectWiseClients addConnectWiseClient(String mspCustomDomain, ConnectWiseClients client) {
        return connectWiseClientsRepository.save(client);
    }
    
    public ConnectWiseContacts addConnectWiseContact(String mspCustomDomain, ConnectWiseContacts contact) {
        return connectWiseContactsRepository.save(contact);
    }
    
    public ConnectWiseMembers addConnectWiseMember(String mspCustomDomain, ConnectWiseMembers member) {
        return connectWiseMembersRepository.save(member);
    }

}
