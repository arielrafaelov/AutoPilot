package com.etech7.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.etech7.dto.Address;
import com.etech7.dto.ConnectWiseWorkflowConfiguration;
import com.etech7.dto.MSPIntegrations;
import com.etech7.entity.MSP;
import com.etech7.repository.MSPRepository;

@Service
public class MSPService {

    @Autowired
    private MSPRepository mspRepository;
    
	@Autowired
	private MongoTemplate mongoTemplate;

	  public List<MSP> getMSPs() {
	        return mspRepository.findAll();
	    }
	
	public MSP getMSPByCustomDomain(String customDomain) {
        return mspRepository.findByCustomDomain(customDomain);
    }

    public MSP createMSP(MSP msp) {
        return mspRepository.save(msp);
    }

    public MSP editMSP(String customDomain, MSP msp) {
        MSP existingMSP = mspRepository.findByCustomDomain(customDomain);
        if (Objects.isNull(existingMSP)) {
            return null;  // MSP not found
        }

        // Update only non-null fields
        if (!Objects.isNull(msp.getMspName())) {
            existingMSP.setMspName(msp.getMspName());
        }
        if (!Objects.isNull(msp.getBrandLogoUrl())) {
            existingMSP.setBrandLogoUrl(msp.getBrandLogoUrl());
        }

        Address newAddress = msp.getCompanyAddress();
        Address existingAddress = existingMSP.getCompanyAddress();
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
        } else if (!Objects.isNull(newAddress)) {
            existingMSP.setCompanyAddress(newAddress);  
        }

//        MSPIntegrations newIntegrations = msp.getMspIntegrations();
//        MSPIntegrations existingIntegrations = existingMSP.getMspIntegrations();
//        if (!Objects.isNull(newIntegrations) && !Objects.isNull(existingIntegrations)) {
//            if (!Objects.isNull(newIntegrations.isConnectWiseManageIntegration())) {
//                existingIntegrations.setConnectWiseManageIntegration(newIntegrations.isConnectWiseManageIntegration());
//            }
//            if (!Objects.isNull(newIntegrations.isConnectWiseAutomateIntegration())) {
//                existingIntegrations.setConnectWiseAutomateIntegration(newIntegrations.isConnectWiseAutomateIntegration());
//            }
//            if (!Objects.isNull(newIntegrations.isMicrosoftGraphIntegration())) {
//                existingIntegrations.setMicrosoftGraphIntegration(newIntegrations.isMicrosoftGraphIntegration());
//            }
//            if (!Objects.isNull(newIntegrations.isEmailIntegration())) {
//                existingIntegrations.setEmailIntegration(newIntegrations.isEmailIntegration());
//            }
//
//            ConnectWiseWorkflowConfiguration newWorkflowConfig = newIntegrations.getConnectWiseWorkflowConfiguration();
//            ConnectWiseWorkflowConfiguration existingWorkflowConfig = existingIntegrations.getConnectWiseWorkflowConfiguration();
//            if (!Objects.isNull(newWorkflowConfig) && !Objects.isNull(existingWorkflowConfig)) {
//                if (!Objects.isNull(newWorkflowConfig.isCreateTicket())) {
//                    existingWorkflowConfig.setCreateTicket(newWorkflowConfig.isCreateTicket());
//                }
//                if (!Objects.isNull(newWorkflowConfig.isCategorization())) {
//                    existingWorkflowConfig.setCategorization(newWorkflowConfig.isCategorization());
//                }
//                if (!Objects.isNull(newWorkflowConfig.isDispatcher())) {
//                    existingWorkflowConfig.setDispatcher(newWorkflowConfig.isDispatcher());
//                }
//                if (!Objects.isNull(newWorkflowConfig.isAutomation())) {
//                    existingWorkflowConfig.setAutomation(newWorkflowConfig.isAutomation());
//                }
//            } else if (!Objects.isNull(newWorkflowConfig)) {
//                existingIntegrations.setConnectWiseWorkflowConfiguration(newWorkflowConfig); 
//                }
//        } else if (!Objects.isNull(newIntegrations)) {
//            existingMSP.setMspIntegrations(newIntegrations); 
//        }

        return mspRepository.save(existingMSP);
    }


    public MSP deleteMSP(String customDomain) {
        MSP msp = mspRepository.findByCustomDomain(customDomain);
        
    	Query query = new Query(Criteria.where("customDomain").is(customDomain));
		Update update = new Update();
		update.set("deleted", true);
		return mongoTemplate.findAndModify(query, update, MSP.class);
        
    }
}
