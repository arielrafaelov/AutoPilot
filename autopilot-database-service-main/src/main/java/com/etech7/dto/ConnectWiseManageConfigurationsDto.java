package com.etech7.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConnectWiseManageConfigurationsDto {

	private int id;
    private String name;
    private String deviceIdentifier;
    private Site site;
//    private Location location;
    
    
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Site {
        private int id;
        private String name;
       
    }
    
 
}
