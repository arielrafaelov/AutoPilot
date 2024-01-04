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
public class MSPConnectWiseManageCategorizations {

    private int categoryId;
    private String categoryName;
    private List<MSPConnectWiseManageSubCategorizations> mspConnectWiseManageSubCategorizations;
}
