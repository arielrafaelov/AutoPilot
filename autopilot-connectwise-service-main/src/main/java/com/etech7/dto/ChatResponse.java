package com.etech7.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {

	  private String id;
	  
	  private String object;
	  
	  private LocalDate created;
	  
	  private String model;
	  
	  private List<Choice> choices;
	  
	  private Usage usage;
	
}
