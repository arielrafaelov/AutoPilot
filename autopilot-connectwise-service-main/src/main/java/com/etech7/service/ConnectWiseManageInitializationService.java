package com.etech7.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.etech7.dto.ConnectWiseBoards;
import com.etech7.dto.ConnectWiseCategories;
import com.etech7.dto.ConnectWiseClients;
import com.etech7.dto.ConnectWiseClientsCW;
import com.etech7.dto.ConnectWiseCommunicationItems;
import com.etech7.dto.ConnectWiseConfigurations;
import com.etech7.dto.ConnectWiseContactsCW;
import com.etech7.dto.ConnectWiseMembersCW;
import com.etech7.dto.ConnectWiseSubCategories;
import com.etech7.dto.Priorities;

@Service
public class ConnectWiseManageInitializationService {

	@Autowired
	DBService dbService;

	private final WebClient webClient;

	public ConnectWiseManageInitializationService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("https://api-na.myconnectwise.net/v4_6_release/apis/3.0").build();
	}

	public List<ConnectWiseBoards> getAllConnectWiseBoards(HttpHeaders headers) {
		return fetchAll("/service/boards", headers, ConnectWiseBoards.class);
	}

	public List<ConnectWiseCategories> getAllConnectWiseCategories(int boardId, HttpHeaders headers) {
		String path = String.format("/service/boards/%d/types", boardId);
		return fetchAll(path, headers, ConnectWiseCategories.class);
	}

	public List<ConnectWiseSubCategories> getAllConnectWiseSubCategories(int boardId, HttpHeaders headers) {
		String path = String.format("/service/boards/%d/subtypes", boardId);
		return fetchAll(path, headers, ConnectWiseSubCategories.class);
	}

	public List<ConnectWiseMembersCW> getAllConnectWiseMembers(HttpHeaders headers) {
		return fetchAll("/system/members", headers, ConnectWiseMembersCW.class);
	}

	public List<ConnectWiseClientsCW> getAllConnectWiseClientsCW(HttpHeaders headers) {
		List<ConnectWiseClientsCW> connectWiseClientsCW = new ArrayList<>();
		AtomicInteger page = new AtomicInteger(1);
		do {
			List<ConnectWiseClientsCW> pageConnectWiseClientsCW = webClient.get()
					.uri(uriBuilder -> uriBuilder.path("/company/companies")
							.queryParam("conditions", "deletedFlag=false").queryParam("page", page.get()).build())
					.headers(httpHeaders -> httpHeaders.addAll(headers)).retrieve()
					.bodyToFlux(ConnectWiseClientsCW.class).collectList().block();
			if (pageConnectWiseClientsCW != null && !pageConnectWiseClientsCW.isEmpty()) {
				connectWiseClientsCW.addAll(pageConnectWiseClientsCW);
				page.incrementAndGet();
			} else {
				break;
			}
		} while (true);

		return connectWiseClientsCW;
	}

	public List<ConnectWiseContactsCW> getAllConnectWiseContactsCW(HttpHeaders headers, String mspCustomDomain) {
		List<ConnectWiseClients> connectWiseClients = dbService.getConnectWiseClients(mspCustomDomain).block();
		List<ConnectWiseContactsCW> connectWiseContactsCW = new ArrayList<>();

		for (ConnectWiseClients client : connectWiseClients) {
			AtomicInteger page = new AtomicInteger(1);
			do {
				List<ConnectWiseContactsCW> pageConnectWiseClientsCW = webClient.get()
						.uri(uriBuilder -> uriBuilder.path("/company/contacts")
								.queryParam("conditions", "company/id=" + client.getConnectWiseCompanyId())
								.queryParam("page", page.get()).build())
						.headers(httpHeaders -> httpHeaders.addAll(headers)).retrieve()
						.bodyToFlux(ConnectWiseContactsCW.class).collectList().block();

				if (pageConnectWiseClientsCW != null && !pageConnectWiseClientsCW.isEmpty()) {
					for (ConnectWiseContactsCW contact : pageConnectWiseClientsCW) {
						int companyId = contact.getCompany().getId();
						contact.setConnectWiseCompanyId(companyId);
						if (contact.getCommunicationItems() != null) {

							for (ConnectWiseCommunicationItems item : contact.getCommunicationItems()) {
								if ("Email".equals(item.getCommunicationType())) {
									contact.setConnectWiseEmailId(item.getValue());
								} else if ("Phone".equals(item.getCommunicationType())) {
									contact.setConnectWisePhoneNumber(item.getValue());
								}
							}
						}
						List<ConnectWiseConfigurations> configurations = fetchConfigurationsForContact(contact.getId(),
								companyId, headers);
						contact.setConfiguration(configurations);

					}

					connectWiseContactsCW.addAll(pageConnectWiseClientsCW);
					page.incrementAndGet();
				} else {
					break;
				}
			} while (true);
		}

		return connectWiseContactsCW;
	}

	private List<ConnectWiseConfigurations> fetchConfigurationsForContact(int contactId, int companyId,
			HttpHeaders headers) {
		List<ConnectWiseConfigurations> configurations = new ArrayList<>();
		int configPage = 1;
		boolean hasMoreConfigPages;
		do {
			hasMoreConfigPages = false;
			String configApiUrl = "/company/configurations?conditions=contact/id=" + contactId + " AND company/id="
					+ companyId + "&page=" + configPage;

			List<ConnectWiseConfigurations> pageConfigurations = webClient.get().uri(configApiUrl)
					.headers(httpHeaders -> httpHeaders.addAll(headers)).retrieve()
					.bodyToFlux(ConnectWiseConfigurations.class).collectList().block();
			if (pageConfigurations != null && !pageConfigurations.isEmpty()) {
				configurations.addAll(pageConfigurations);
				configPage++;
				hasMoreConfigPages = true;
			}
		} while (hasMoreConfigPages);
		return configurations;
	}

	public List<Priorities> getAllPriorities(HttpHeaders headers) {
		List<Priorities> priorities = new ArrayList<>();
		AtomicInteger page = new AtomicInteger(1);
		do {
			List<Priorities> pagepriorities = webClient.get()
					.uri(uriBuilder -> uriBuilder.path("/service/priorities").queryParam("page", page.get()).build())
					.headers(httpHeaders -> httpHeaders.addAll(headers)).retrieve().bodyToFlux(Priorities.class)
					.collectList().block();

			if (pagepriorities != null && !pagepriorities.isEmpty()) {
				priorities.addAll(pagepriorities);
				page.incrementAndGet();
			} else {
				break;
			}
		} while (true);

		return priorities;
	}

	private <T> List<T> fetchAll(String path, HttpHeaders headers, Class<T> type) {
		List<T> items = new ArrayList<>();
		AtomicInteger page = new AtomicInteger(1);
		boolean fetchNextPage;

		do {
			List<T> pageItems = webClient.get()
					.uri(uriBuilder -> uriBuilder.path(path).queryParam("conditions", "inactiveFlag=false")
//                            .queryParam("pageSize", 1000) 
							.queryParam("page", page.getAndIncrement()).build())
					.headers(httpHeaders -> httpHeaders.addAll(headers)).retrieve().bodyToFlux(type).collectList()
					.block();
			fetchNextPage = pageItems != null && !pageItems.isEmpty();
			if (fetchNextPage) {
				items.addAll(pageItems);
			}
		} while (fetchNextPage);

		return items;
	}
}
