package com.etech7.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Choice {

	  private Integer index;
	  
	//  private String logprobs;
	  
	  @JsonProperty("finish_reason")
	  private String finishReason;
	  
	  private GPTMessage message;
	  
	  @JsonProperty("finish_reason")
	  public void setFinishReason(String finishReason) {
	    this.finishReason = finishReason;
	  }
	
}
