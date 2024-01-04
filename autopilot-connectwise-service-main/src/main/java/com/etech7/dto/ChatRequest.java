package com.etech7.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRequest {

	  private String model;
	  
	  private List<GPTMessage> messages;
	  
	  @JsonProperty("max_tokens")
	  private Integer maxTokens;
	  
	  private Double temperature;
	  
	  @JsonProperty("top_p")
	  private Double topP;
	  
	  @JsonProperty("response_format")
	  private ResponseFormat responseFormat;

	public ChatRequest(String model, List<GPTMessage> messages) {
		super();
		this.model = model;
		this.messages = messages;
	}
	  
}

