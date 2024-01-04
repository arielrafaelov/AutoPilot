package com.etech7.entity;

import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.etech7.dto.MSPConnectWiseManageCategorizations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "MSPConnectWiseManageDetails")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MSPConnectWiseManageDetails {

    private String id;
    @Indexed(unique = true)
    private String mspCustomDomain;
    private int boardId;
    private String boardName;
    private List<MSPConnectWiseManageCategorizations> mspConnectWiseManageCategorizations;
	
}
