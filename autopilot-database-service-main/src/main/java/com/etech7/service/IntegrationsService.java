package com.etech7.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.etech7.dto.ConnectWiseAutomateIntegration;
import com.etech7.dto.ConnectWiseManageIntegration;
import com.etech7.dto.EmailConnectorIntegration;
import com.etech7.dto.MicrosoftGraphIntegration;
import com.etech7.entity.Integrations;
import com.etech7.repository.IntegrationsRepository;

@Service
public class IntegrationsService {

	@Autowired
	private IntegrationsRepository integrationsRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	public Integrations findIntegrationsByMspCustomDomain(String mspCustomDomain) {
		return integrationsRepository.findByMspCustomDomain(mspCustomDomain);
	}

	public Integrations editIntegrations(String mspCustomDomain, Integrations integrations) {
		Integrations existingIntegrations = integrationsRepository.findByMspCustomDomain(mspCustomDomain);
		if (Objects.isNull(existingIntegrations)) {
			return integrationsRepository.save(integrations);
		}
		if (!Objects.isNull(integrations.getMspCustomDomain())) {
			existingIntegrations.setMspCustomDomain(integrations.getMspCustomDomain());
		}
		if (!Objects.isNull(integrations.isConnectWiseManageIntegrator())) {
			existingIntegrations.setConnectWiseManageIntegrator(integrations.isConnectWiseManageIntegrator());
		}
		if (!Objects.isNull(integrations.isConnectWiseAutomateIntegrator())) {
			existingIntegrations.setConnectWiseAutomateIntegrator(integrations.isConnectWiseAutomateIntegrator());
		}
		if (!Objects.isNull(integrations.isMicrosoftGraphIntegrator())) {
			existingIntegrations.setMicrosoftGraphIntegrator(integrations.isMicrosoftGraphIntegrator());
		}
		if (!Objects.isNull(integrations.isEmailIntegrator())) {
			existingIntegrations.setEmailIntegrator(integrations.isEmailIntegrator());
		}

		updateConnectWiseManageIntegration(existingIntegrations, integrations);
		updateConnectWiseAutomateIntegration(existingIntegrations, integrations);
		updateEmailConnectorIntegration(existingIntegrations, integrations);
		updateMicrosoftGraphIntegration(existingIntegrations, integrations);

		return integrationsRepository.save(existingIntegrations);
	}

	private void updateConnectWiseManageIntegration(Integrations existingIntegrations, Integrations integrations) {
		ConnectWiseManageIntegration newCWMI = integrations.getConnectWiseManageIntegration();
		ConnectWiseManageIntegration existingCWMI = existingIntegrations.getConnectWiseManageIntegration();
		if (!Objects.isNull(newCWMI) && !Objects.isNull(existingCWMI)) {
			if (!Objects.isNull(newCWMI.getClientId())) {
				existingCWMI.setClientId(newCWMI.getClientId());
			}
			if (!Objects.isNull(newCWMI.getCompanyId())) {
				existingCWMI.setCompanyId(newCWMI.getCompanyId());
			}
			if (!Objects.isNull(newCWMI.getPublicKey())) {
				existingCWMI.setPublicKey(newCWMI.getPublicKey());
			}
			if (!Objects.isNull(newCWMI.getPrivateKey())) {
				existingCWMI.setPrivateKey(newCWMI.getPrivateKey());
			}
		} else if (!Objects.isNull(newCWMI)) {
			existingIntegrations.setConnectWiseManageIntegration(newCWMI);
		}
	}

	private void updateConnectWiseAutomateIntegration(Integrations existingIntegrations, Integrations integrations) {
		ConnectWiseAutomateIntegration newCWAI = integrations.getConnectWiseAutomateIntegration();
		ConnectWiseAutomateIntegration existingCWAI = existingIntegrations.getConnectWiseAutomateIntegration();
		if (!Objects.isNull(newCWAI) && !Objects.isNull(existingCWAI)) {
			if (!Objects.isNull(newCWAI.getBaseUrl())) {
				existingCWAI.setBaseUrl(newCWAI.getBaseUrl());
			}
			if (!Objects.isNull(newCWAI.getClientId())) {
				existingCWAI.setClientId(newCWAI.getClientId());
			}
			if (!Objects.isNull(newCWAI.getCompanyId())) {
				existingCWAI.setCompanyId(newCWAI.getCompanyId());
			}
			if (!Objects.isNull(newCWAI.getOneTimeSecurePassword())) {
				existingCWAI.setOneTimeSecurePassword(newCWAI.getOneTimeSecurePassword());
			}
		} else if (!Objects.isNull(newCWAI)) {
			existingIntegrations.setConnectWiseAutomateIntegration(newCWAI);
		}
	}

	private void updateEmailConnectorIntegration(Integrations existingIntegrations, Integrations integrations) {
		EmailConnectorIntegration newECI = integrations.getEmailConnectorIntegration();
		EmailConnectorIntegration existingECI = existingIntegrations.getEmailConnectorIntegration();
		if (!Objects.isNull(newECI) && !Objects.isNull(existingECI)) {
			if (!Objects.isNull(newECI.getEmailId())) {
				existingECI.setEmailId(newECI.getEmailId());
			}
			if (!Objects.isNull(newECI.getPassword())) {
				existingECI.setPassword(newECI.getPassword());
			}
		} else if (!Objects.isNull(newECI)) {
			existingIntegrations.setEmailConnectorIntegration(newECI);
		}
	} 

	private void updateMicrosoftGraphIntegration(Integrations existingIntegrations, Integrations integrations) {
		MicrosoftGraphIntegration newMGI = integrations.getMicrosoftGraphIntegration();
		MicrosoftGraphIntegration existingMGI = existingIntegrations.getMicrosoftGraphIntegration();
		if (!Objects.isNull(newMGI) && !Objects.isNull(existingMGI)) {
			if (!Objects.isNull(newMGI.getTenantId())) {
				existingMGI.setTenantId(newMGI.getTenantId());
			}
			if (!Objects.isNull(newMGI.getSecretId())) {
				existingMGI.setSecretId(newMGI.getSecretId());
			}
		} else if (!Objects.isNull(newMGI)) {
			existingIntegrations.setMicrosoftGraphIntegration(newMGI);
		}
	}

}
