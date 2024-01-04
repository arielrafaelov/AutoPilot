package com.etech7.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConnectWiseDetails {
    private int contactId;
    private int companyId;
    private List<Integer> agentId;
    private List<String> workstationName;
    private List<String> site;

}