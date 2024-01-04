package com.etech7.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectWiseWorkflowConfiguration {
	private boolean createTicket;
    private boolean categorization;
    private boolean dispatcher;
    private boolean automation;

}