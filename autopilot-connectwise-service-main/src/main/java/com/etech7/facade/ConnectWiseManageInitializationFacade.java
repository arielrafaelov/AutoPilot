package com.etech7.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.etech7.dto.ConnectWiseBoards;
import com.etech7.dto.ConnectWiseCategories;
import com.etech7.dto.ConnectWiseClients;
import com.etech7.dto.ConnectWiseClientsCW;
import com.etech7.dto.ConnectWiseContacts;
import com.etech7.dto.ConnectWiseContactsCW;
import com.etech7.dto.ConnectWiseManageInitializationDto;
import com.etech7.dto.ConnectWiseManageIntegration;
import com.etech7.dto.ConnectWiseMembers;
import com.etech7.dto.ConnectWiseMembersCW;
import com.etech7.dto.ConnectWiseSubCategories;
import com.etech7.dto.GPTMessage;
import com.etech7.dto.MSPConnectWiseManageCategorizations;
import com.etech7.dto.MSPConnectWiseManageSubCategorizations;
import com.etech7.dto.Priorities;
import com.etech7.exception.CustomException;
import com.etech7.service.ConnectWiseBoardsService;
import com.etech7.service.ConnectWiseManageInitializationService;
import com.etech7.service.CustomGPTService;
import com.etech7.service.DBService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ConnectWiseManageInitializationFacade {

	@Autowired
	DBService dbService;

	@Autowired
	ConnectWiseManageInitializationService connectWiseManageInitializationService;

	@Autowired
	ConnectWiseBoardsService connectWiseBoardsService;

	@Autowired
	CustomGPTService customGPTService;

	public HttpHeaders createHeaders(String mspCustomDomain) {
		ConnectWiseManageIntegration connectWiseManageIntegration = dbService.getIntegration(mspCustomDomain)
				.blockOptional()
				.orElseThrow(
						() -> new CustomException("Integration data is not available for domain: " + mspCustomDomain))
				.getConnectWiseManageIntegration();
		if (connectWiseManageIntegration.getClientId() == null || connectWiseManageIntegration.getCompanyId() == null
				|| connectWiseManageIntegration.getPublicKey() == null
				|| connectWiseManageIntegration.getPrivateKey() == null) {
			throw new CustomException("One or more required ConnectWiseManageIntegration fields are null");
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("clientId", connectWiseManageIntegration.getClientId());
		String auth = connectWiseManageIntegration.getCompanyId() + "+" + connectWiseManageIntegration.getPublicKey()
				+ ":" + connectWiseManageIntegration.getPrivateKey();
		String encodedAuth = Base64Utils.encodeToString(auth.getBytes());
		headers.set("Authorization", "Basic " + encodedAuth);
		return headers;
	}

	public List<ConnectWiseBoards> getConnectWiseBoardsService(String mspCustomDomain) {
		HttpHeaders header = createHeaders(mspCustomDomain);
		return connectWiseBoardsService.getAllConnectWiseBoards(header);
	}

	public List<ConnectWiseBoards> connectWiseBoards(String mspCustomDomain) {
		HttpHeaders header = createHeaders(mspCustomDomain);
		return connectWiseManageInitializationService.getAllConnectWiseBoards(header);
	}

	public List<ConnectWiseCategories> connectWiseCategories(String mspCustomDomain, int boardId) {
		HttpHeaders header = createHeaders(mspCustomDomain);
		return connectWiseManageInitializationService.getAllConnectWiseCategories(boardId, header);
	}

	public List<ConnectWiseSubCategories> connectWiseSubCategories(String mspCustomDomain, int boardId) {
		HttpHeaders header = createHeaders(mspCustomDomain);
		return connectWiseManageInitializationService.getAllConnectWiseSubCategories(boardId, header);
	}

//	public List<ConnectWiseMembersCW> connectWiseMembers(String mspCustomDomain) {
//		HttpHeaders header = createHeaders(mspCustomDomain);
//		return connectWiseManageInitializationService.getAllConnectWiseMembers(header);
//	}

	public List<Priorities> getPriorities(String mspCustomDomain) {
		HttpHeaders header = createHeaders(mspCustomDomain);
		return connectWiseManageInitializationService.getAllPriorities(header);

	}

	public List<ConnectWiseContacts> getConnectWiseContactsCW(String mspCustomDomain) {
		HttpHeaders header = createHeaders(mspCustomDomain);
		List<ConnectWiseContactsCW> allConnectWiseContactsCW = connectWiseManageInitializationService
				.getAllConnectWiseContactsCW(header, mspCustomDomain);
		List<ConnectWiseContacts> allconnectWiseContacts = new ArrayList<>();

		for (ConnectWiseContactsCW contactCW : allConnectWiseContactsCW) {
			ConnectWiseContacts contact = new ConnectWiseContacts();
			contact.setMspCustomDomain(mspCustomDomain);
			contact.setConnectWiseContactId(contactCW.getId());
			contact.setConnectWiseCompanyId(contactCW.getConnectWiseCompanyId());
			contact.setCompany(contactCW.getCompany());
			contact.setFirstName(contactCW.getFirstName());
			contact.setLastName(contactCW.getLastName());
			contact.setTitle(contactCW.getTitle());
			contact.setDefaultPhoneType(contactCW.getDefaultPhoneType());
			contact.setDefaultPhoneNbr(contactCW.getDefaultPhoneNbr());
			contact.setConnectWiseEmailId(contactCW.getConnectWiseEmailId());
			contact.setConnectWisePhoneNumber(contactCW.getConnectWisePhoneNumber());
			contact.setConfiguration(contactCW.getConfiguration());

			allconnectWiseContacts.add(contact);
		}
		return allconnectWiseContacts;
	}

	public List<ConnectWiseClients> getConnectWiseClientsCW(String mspCustomDomain) {
		HttpHeaders header = createHeaders(mspCustomDomain);
		List<ConnectWiseClientsCW> allConnectWiseClientsCW = connectWiseManageInitializationService
				.getAllConnectWiseClientsCW(header);
		List<ConnectWiseClients> allconnectWiseClients = new ArrayList<>();

		for (ConnectWiseClientsCW connectWiseClientsCW : allConnectWiseClientsCW) {
			ConnectWiseClients connectWiseClients = new ConnectWiseClients();
			connectWiseClients.setMspCustomDomain(mspCustomDomain);
			connectWiseClients.setConnectWiseCompanyId(connectWiseClientsCW.getId());
			connectWiseClients.setIdentifier(connectWiseClientsCW.getIdentifier());
			connectWiseClients.setName(connectWiseClientsCW.getName());
			connectWiseClients.setStatus(connectWiseClientsCW.getStatus());
			connectWiseClients.setAddressLine1(connectWiseClientsCW.getAddressLine1());
			connectWiseClients.setAddressLine2(connectWiseClientsCW.getAddressLine2());
			connectWiseClients.setCity(connectWiseClientsCW.getCity());
			connectWiseClients.setState(connectWiseClientsCW.getState());
			connectWiseClients.setZip(connectWiseClientsCW.getZip());
			connectWiseClients.setCountry(connectWiseClientsCW.getCountry());
			connectWiseClients.setPhoneNumber(connectWiseClientsCW.getPhoneNumber());
			connectWiseClients.setDefaultContact(connectWiseClientsCW.getDefaultContact());
			connectWiseClients.setTypes(connectWiseClientsCW.getTypes());
			connectWiseClients.setSite(connectWiseClientsCW.getSite());

			allconnectWiseClients.add(connectWiseClients);
		}
		return allconnectWiseClients;
	}

	public List<ConnectWiseMembers> getConnectWiseMembersCW(String mspCustomDomain) {
		HttpHeaders header = createHeaders(mspCustomDomain);
		List<ConnectWiseMembersCW> allConnectWiseMembersCW = connectWiseManageInitializationService
				.getAllConnectWiseMembers(header);
		List<ConnectWiseMembers> allconnectWiseMembers = new ArrayList<>();

		for (ConnectWiseMembersCW membersCW : allConnectWiseMembersCW) {
			ConnectWiseMembers member = new ConnectWiseMembers();
			member.setMspCustomDomain(mspCustomDomain);
			member.setConnectWiseMembersId(membersCW.getId());
			member.setIdentifier(membersCW.getIdentifier() != null ? membersCW.getIdentifier() : "");
			member.setFirstName(membersCW.getFirstName() != null ? membersCW.getFirstName() : "");
			member.setLastName(membersCW.getLastName() != null ? membersCW.getLastName() : "");
			member.setPrimaryEmail(membersCW.getPrimaryEmail() != null ? membersCW.getPrimaryEmail() : "");
			member.setMobilePhone(membersCW.getMobilePhone() != null ? membersCW.getMobilePhone() : "");
			member.setOfficeEmail(membersCW.getOfficeEmail() != null ? membersCW.getOfficeEmail() : "");
			member.setOfficePhone(membersCW.getOfficePhone() != null ? membersCW.getOfficePhone() : "");
			allconnectWiseMembers.add(member);
		}
		return allconnectWiseMembers;
	}

//	public List<MSPConnectWiseManageCategorizations> mergeCategoriesAndSubCategories() {
//		List<ConnectWiseCategories> categoriesList = cat("et7");
//		List<ConnectWiseSubCategories> subCategoriesList = subCat("et7");
//		Map<Integer, String> categoryMap = new HashMap<>();
//		for (ConnectWiseCategories category : categoriesList) {
//			categoryMap.put(category.getId(), category.getName());
//		}
//		Map<Integer, List<MSPConnectWiseManageSubCategorizations>> categorizationMap = new HashMap<>();
//		for (ConnectWiseSubCategories subCategory : subCategoriesList) {
//			for (Integer typeId : subCategory.getTypeAssociationIds()) {
//				categorizationMap.computeIfAbsent(typeId, k -> new ArrayList<>());
//
//				categorizationMap.get(typeId)
//						.add(new MSPConnectWiseManageSubCategorizations(subCategory.getId(), subCategory.getName()));
//			}
//		}
//		List<MSPConnectWiseManageCategorizations> mergedList = new ArrayList<>();
//		for (Map.Entry<Integer, String> categoryEntry : categoryMap.entrySet()) {
//			mergedList.add(new MSPConnectWiseManageCategorizations(categoryEntry.getKey(), categoryEntry.getValue(),
//					categorizationMap.getOrDefault(categoryEntry.getKey(), new ArrayList<>())));
//		}
//		return mergedList;
//	}
//
//	public List<MSPConnectWiseManageCategorizations> mergeCategoriesAndSubCategories1() {
//		List<ConnectWiseCategories> categoriesList = cat("et7");
//		List<ConnectWiseSubCategories> subCategoriesList = subCat("et7");
//		List<Priorities> prioritesList = getPriorities("et7");
//		Map<Integer, String> categoryMap = new HashMap<>();
//		for (ConnectWiseCategories category : categoriesList) {
//			categoryMap.put(category.getId(), category.getName());
//		}
//		Map<Integer, List<MSPConnectWiseManageSubCategorizations>> categorizationMap = new HashMap<>();
//		for (ConnectWiseSubCategories subCategory : subCategoriesList) {
//			for (Integer typeId : subCategory.getTypeAssociationIds()) {
//				categorizationMap.computeIfAbsent(typeId, k -> new ArrayList<>());
//				categorizationMap.get(typeId)
//						.add(new MSPConnectWiseManageSubCategorizations(subCategory.getId(), subCategory.getName()));
//			}
//		}
//		List<MSPConnectWiseManageCategorizations> mergedList = new ArrayList<>();
//		for (Map.Entry<Integer, String> categoryEntry : categoryMap.entrySet()) {
//			List<MSPConnectWiseManageSubCategorizations> subCats = categorizationMap
//					.getOrDefault(categoryEntry.getKey(), new ArrayList<>());
//			for (MSPConnectWiseManageSubCategorizations subCat : subCats) {
//				List<GPTMessage> messages = new ArrayList<>();
//				messages.add(new GPTMessage("system", "You are a helpful assistant designed to output JSON."));
//				messages.add(new GPTMessage("user",
//						"Provide impact (High Medium Low), severity (High Medium Low), tier (Tier1, Tier2, Tier3), and duration ( this is in Hours and give me only the average time dont give me the range), priorityId, priority "
//								+ prioritesList.toString() + "to resolve for the subcategory '"
//								+ subCat.getSubCategoryName()
//								+ "'. As you are an intellignet assistant designed to output JSON, Respond me in JSON with only this attrbiutes String priority; int priorityId; String impact; String severity; String tier; double durationToResolve;"));
//				String jsonResponse = customGPTService.getGPTJSONMessage(messages).getChoices().get(0).getMessage()
//						.getContent();
//				System.out.println(jsonResponse);
//			}
//			mergedList.add(
//					new MSPConnectWiseManageCategorizations(categoryEntry.getKey(), categoryEntry.getValue(), subCats));
//		}
//		return mergedList;
//	}

	public ConnectWiseManageInitializationDto mergeCategoriesAndSubCategories(String mspCustomDomain, int boardId)
			throws JsonMappingException, JsonProcessingException {
		ConnectWiseManageInitializationDto connectWiseManageInitializationDto = new ConnectWiseManageInitializationDto();
		HttpHeaders header = createHeaders(mspCustomDomain);
		List<ConnectWiseCategories> categoriesList = connectWiseManageInitializationService
				.getAllConnectWiseCategories(boardId, header);
		List<ConnectWiseSubCategories> subCategoriesList = connectWiseManageInitializationService
				.getAllConnectWiseSubCategories(boardId, header);
		List<Priorities> prioritiesList = connectWiseManageInitializationService.getAllPriorities(header);

		Map<Integer, String> categoryMap = new HashMap<>();
		for (ConnectWiseCategories category : categoriesList) {
			categoryMap.put(category.getId(), category.getName());
		}

		Map<Integer, List<MSPConnectWiseManageSubCategorizations>> categorizationMap = new HashMap<>();
		for (ConnectWiseSubCategories subCategory : subCategoriesList) {
			for (Integer typeId : subCategory.getTypeAssociationIds()) {
				categorizationMap.computeIfAbsent(typeId, k -> new ArrayList<>());
				categorizationMap.get(typeId)
						.add(new MSPConnectWiseManageSubCategorizations(subCategory.getId(), subCategory.getName()));
			}
		}

		List<MSPConnectWiseManageCategorizations> mergedList = new ArrayList<>();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		for (Map.Entry<Integer, String> categoryEntry : categoryMap.entrySet()) {
			List<MSPConnectWiseManageSubCategorizations> subCats = categorizationMap
					.getOrDefault(categoryEntry.getKey(), new ArrayList<>());

			for (MSPConnectWiseManageSubCategorizations subCat : subCats) {
				List<GPTMessage> messages = createGPTMessages(subCat, prioritiesList);

				String jsonResponse = customGPTService.getGPTJSONMessage(messages).getChoices().get(0).getMessage()
						.getContent();
				MSPConnectWiseManageSubCategorizations updatedSubCat = objectMapper.readValue(jsonResponse,
						MSPConnectWiseManageSubCategorizations.class);
				updateSubCategoryDetails(subCat, updatedSubCat);

//	                try {
//	                    String jsonResponse = customGPTService.getGPTJSONMessage(messages).getChoices().get(0).getMessage().getContent();
//	                    MSPConnectWiseManageSubCategorizations updatedSubCat = objectMapper.readValue(jsonResponse, MSPConnectWiseManageSubCategorizations.class);
//	                    updateSubCategoryDetails(subCat, updatedSubCat);
//	                } catch (IOException e) {
//	                    // Log and handle JSON parsing exceptions
//	                }
			}

			mergedList.add(
					new MSPConnectWiseManageCategorizations(categoryEntry.getKey(), categoryEntry.getValue(), subCats));
		}
		connectWiseManageInitializationDto.setMspConnectWiseManageCategorizations(mergedList);
		connectWiseManageInitializationDto.setPrioritiesList(prioritiesList);
		return connectWiseManageInitializationDto;
	}

	private List<GPTMessage> createGPTMessages(MSPConnectWiseManageSubCategorizations subCat,
			List<Priorities> prioritiesList) {
		List<GPTMessage> messages = new ArrayList<>();
		messages.add(new GPTMessage("system", "You are a helpful assistant designed to output JSON."));
		messages.add(new GPTMessage("user",
				"Provide impact (High Medium Low), severity (High Medium Low), tier (Tier1, Tier2, Tier3), and duration ( this is in Hours and give me only the average time dont give me the range, and make sure maximum is 3 hours for complex issues), priorityId, priority "
						+ prioritiesList.toString() + "to resolve for the subcategory '" + subCat.getSubCategoryName()
						+ "'. As you are an intelligent assistant designed to output JSON, Respond me in JSON with only this attrbiutes nothing else apart from this : String priority; int priorityId; String impact; String severity; String tier; double durationToResolve;."));
		return messages;
	}

	private void updateSubCategoryDetails(MSPConnectWiseManageSubCategorizations originalSubCat,
			MSPConnectWiseManageSubCategorizations updatedSubCat) {
		originalSubCat.setPriority(updatedSubCat.getPriority());
		originalSubCat.setPriorityId(updatedSubCat.getPriorityId());
		originalSubCat.setImpact(updatedSubCat.getImpact());
		originalSubCat.setSeverity(updatedSubCat.getSeverity());
		originalSubCat.setTier(updatedSubCat.getTier());
		originalSubCat.setDurationToResolve(updatedSubCat.getDurationToResolve());
	}

}
