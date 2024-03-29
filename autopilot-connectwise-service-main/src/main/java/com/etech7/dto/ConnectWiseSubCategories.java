package com.etech7.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectWiseSubCategories {
	private int id;
	private String name;
	private List<Integer> typeAssociationIds;
}
