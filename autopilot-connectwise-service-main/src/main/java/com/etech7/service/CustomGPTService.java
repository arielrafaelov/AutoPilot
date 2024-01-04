package com.etech7.service;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.etech7.dto.ChatRequest;
import com.etech7.dto.ChatResponse;
import com.etech7.dto.GPTMessage;
import com.etech7.dto.ResponseFormat;

import reactor.core.publisher.Mono;

@Service
public class CustomGPTService {

    private final WebClient webClient;
	
    public CustomGPTService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com").build();
    }
    
    
    public ChatResponse getGPTJSONMessage(List<GPTMessage> messages) {
        messages.add(0, new GPTMessage("system", "You are a helpful assistant designed to output JSON."));

        ChatRequest chatRequest = new ChatRequest("gpt-4-1106-preview", messages);
        chatRequest.setResponseFormat(new ResponseFormat("json_object")); 

        return webClient.post()
                .uri("/v1/chat/completions")
                .header("Authorization", "Bearer " + "GPTKey")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(chatRequest), ChatRequest.class)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block();
    }
	
}
