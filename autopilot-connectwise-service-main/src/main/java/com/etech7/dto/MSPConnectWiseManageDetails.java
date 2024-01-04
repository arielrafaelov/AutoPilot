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
public class MSPConnectWiseManageDetails {

    private String id;
    private String mspCustomDomain;
    private int boardId;
    private String boardName;
    private List<MSPConnectWiseManageCategorizations> mspConnectWiseManageCategorizations;
	
}
