package com.etech7.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.etech7.dto.ConnectWiseBoards;
import com.etech7.facade.ConnectWiseManageInitializationFacade;

@Service
public class ConnectWiseBoardsService {

	private final WebClient webClient;

	public ConnectWiseBoardsService(WebClient.Builder webClientBuilder) {
		this.webClient = webClientBuilder.baseUrl("https://api-na.myconnectwise.net/v4_6_release/apis/3.0").build();
	}

	public List<ConnectWiseBoards> getAllConnectWiseBoards( HttpHeaders headers) {
		List<ConnectWiseBoards> boards = new ArrayList<>();
		AtomicInteger page = new AtomicInteger(1);
		do {
			List<ConnectWiseBoards> pageBoards = webClient.get()
					.uri(uriBuilder -> uriBuilder.path("/service/boards").queryParam("conditions", "inactiveFlag=false")
							.queryParam("page", page.get()).build())
					.headers(httpHeaders -> httpHeaders.addAll(headers))
					.retrieve().bodyToFlux(ConnectWiseBoards.class).collectList().block(); 
																							

			if (pageBoards != null && !pageBoards.isEmpty()) {
				boards.addAll(pageBoards);
				page.incrementAndGet();
			} else {
				break;
			}
		} while (true);

		return boards;
	}
}
