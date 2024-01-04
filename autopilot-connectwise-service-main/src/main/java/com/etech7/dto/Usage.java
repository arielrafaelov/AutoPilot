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
public class Usage {

	  @JsonProperty("prompt_tokens")
	  private Integer promptTokens;
	  
	  @JsonProperty("completion_tokens")
	  private Integer completionTokens;
	  
	  @JsonProperty("total_tokens")
	  private Integer totalTokens;
	  
	  @JsonProperty("prompt_tokens")
	  public void setPromptTokens(Integer promptTokens) {
	    this.promptTokens = promptTokens;
	  }
	  
	  @JsonProperty("completion_tokens")
	  public void setCompletionTokens(Integer completionTokens) {
	    this.completionTokens = completionTokens;
	  }
	  
	  @JsonProperty("total_tokens")
	  public void setTotalTokens(Integer totalTokens) {
	    this.totalTokens = totalTokens;
	  }
}
