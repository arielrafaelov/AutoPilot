package com.etech7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etech7.dto.ConnectWiseBoards;
import com.etech7.dto.ConnectWiseContacts;
import com.etech7.dto.ConnectWiseMembers;
import com.etech7.dto.Integrations;
import com.etech7.facade.ConnectWiseManageInitializationFacade;
import com.etech7.service.ConnectWiseManageInitializationService;
import com.etech7.service.DBService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import reactor.core.publisher.Mono;

@RestController
public class Test {


	@Autowired
	DBService dbSerice;
	
	@Autowired
	ConnectWiseManageInitializationFacade cf;
	
	@Autowired
	ConnectWiseManageInitializationService cs;
	
	@GetMapping("/hi")
	public Mono<Integrations> get() {
		return dbSerice.getIntegration("et7");
	}
	
	@GetMapping("/board")
	public List<ConnectWiseBoards> get1() {
		return cf.getConnectWiseBoardsService("et7");
	}
	
//	
//	@GetMapping("/board1")
//	public List<ConnectWiseBoards> get2() {
//		return cf.board("et7");
//	}
//	
//	@GetMapping("/cat")
//    public List<ConnectWiseCategories> getCategories() {
//        // Assuming "et7" is your mspCustomDomain for testing
//        return cf.cat("et7");
//    }
//
//    @GetMapping("/sub")
//    public List<ConnectWiseSubCategories> getSubCategories() {
//        // Assuming "et7" is your mspCustomDomain for testing
//        return cf.subCat("et7");
//    }
//
//    @GetMapping("/mem")
//    public List<ConnectWiseMembers> getMembers() {
//        // Assuming "et7" is your mspCustomDomain for testing
//        return cf.mem("et7");
//    }
    
    @GetMapping("/me")
    public List<ConnectWiseMembers> getMembe() throws JsonMappingException, JsonProcessingException {
        // Assuming "et7" is your mspCustomDomain for testing
        return cf.getConnectWiseMembersCW("et7");
        
    }
}
