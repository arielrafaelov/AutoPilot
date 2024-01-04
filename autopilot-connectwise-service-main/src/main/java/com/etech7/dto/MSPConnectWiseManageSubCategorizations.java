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
public class MSPConnectWiseManageSubCategorizations {

    private int subCategoryId;
    private String subCategoryName;
    private String priority;
    private int priorityId;
    private String impact;
    private String severity;
    private String tier;
    private double durationToResolve;
//  private List<MSPConnectWiseManageSubCategorizationTechnicians> mspConnectWiseManageSubCategorizationTechnicians;
    
	public MSPConnectWiseManageSubCategorizations(int subCategoryId, String subCategoryName) {
		super();
		this.subCategoryId = subCategoryId;
		this.subCategoryName = subCategoryName;
	}
	
}
