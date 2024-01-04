package com.etech7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.etech7.entity.ConnectWiseClients;
import com.etech7.entity.ConnectWiseContacts;
import com.etech7.entity.ConnectWiseMembers;
import com.etech7.entity.MSPConnectWiseManageDetails;
import com.etech7.service.MSPConnectWiseManageService;

@RestController
public class MSPConnectWiseManageController {

    @Autowired
    private MSPConnectWiseManageService service;

    @GetMapping("/{mspCustomDomain}/connectWiseManageDetails")
    public ResponseEntity<MSPConnectWiseManageDetails> getDetails(@PathVariable String mspCustomDomain) {
        MSPConnectWiseManageDetails details = service.findDetailsByMspCustomDomain(mspCustomDomain);
        if (details == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(details);
    }
    
    @GetMapping("/{mspCustomDomain}/connectWiseClients")
    public ResponseEntity<List<ConnectWiseClients>> getconnectWiseClients(@PathVariable String mspCustomDomain) {
    	List<ConnectWiseClients> details = service.getConnectWiseClientsByMspCustomDomain(mspCustomDomain);
        if (details == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(details);
    }
    
    @PostMapping("/{mspCustomDomain}/addConnectWiseClients")
    public ResponseEntity<List<ConnectWiseClients>> addconnectWiseClients(@PathVariable String mspCustomDomain, @RequestBody List<ConnectWiseClients> clientsList) {
    	List<ConnectWiseClients> details = service.addConnectWiseClients(mspCustomDomain, clientsList);
        if (details == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(details);
    }  
    
    
    @PostMapping("/{mspCustomDomain}/addConnectWiseClient")
    public ResponseEntity<ConnectWiseClients> addconnectWiseClient(@PathVariable String mspCustomDomain, @RequestBody ConnectWiseClients client) {
    	ConnectWiseClients details = service.addConnectWiseClient(mspCustomDomain, client);
        if (details == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(details);
    } 

    @GetMapping("/{mspCustomDomain}/connectWiseContacts")
    public ResponseEntity<List<ConnectWiseContacts>> getconnectWiseContacts(@PathVariable String mspCustomDomain) {
    	List<ConnectWiseContacts> details = service.getConnectWiseContactsByMspCustomDomain(mspCustomDomain);
        if (details == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(details);
    }
    
    @PostMapping("/{mspCustomDomain}/addConnectWiseContacts")
    public ResponseEntity<List<ConnectWiseContacts>> addconnectWiseContacts(@PathVariable String mspCustomDomain, @RequestBody List<ConnectWiseContacts> contactsList) {
    	List<ConnectWiseContacts> details = service.addConnectWiseContacts(mspCustomDomain, contactsList);
        if (details == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(details);
    }    
    
    @PostMapping("/{mspCustomDomain}/addConnectWiseContact")
    public ResponseEntity<ConnectWiseContacts> addconnectWiseContact(@PathVariable String mspCustomDomain, @RequestBody ConnectWiseContacts client) {
    	ConnectWiseContacts details = service.addConnectWiseContact(mspCustomDomain, client);
        if (details == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(details);
    }

    @GetMapping("/{mspCustomDomain}/connectWiseMembers")
    public ResponseEntity<List<ConnectWiseMembers>> getconnectWiseMembers(@PathVariable String mspCustomDomain) {
    	List<ConnectWiseMembers> details = service.getConnectWiseMembersByMspCustomDomain(mspCustomDomain);
        if (details == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(details);
    }
    
    @GetMapping("/{mspCustomDomain}/updateConnectWiseMemberTier")
    public ResponseEntity<ConnectWiseMembers> updateConnectWiseMemberTier(@PathVariable String mspCustomDomain, @RequestParam String id, @RequestParam String tier) {
    	ConnectWiseMembers details = service.updateMemberTier(id, tier);
        if (details == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(details);
    }
    
    @PostMapping("/{mspCustomDomain}/addConnectWiseMembers")
    public ResponseEntity<List<ConnectWiseMembers>> addconnectWiseMembers(@PathVariable String mspCustomDomain, @RequestBody List<ConnectWiseMembers> membersList) {
    	List<ConnectWiseMembers> details = service.addConnectWiseMembers(mspCustomDomain, membersList);
        if (details == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(details);
    }    
    
    @PostMapping("/{mspCustomDomain}/addConnectWiseMember")
    public ResponseEntity<ConnectWiseMembers> addconnectWiseMember(@PathVariable String mspCustomDomain, @RequestBody ConnectWiseMembers member) {
    	ConnectWiseMembers details = service.addConnectWiseMember(mspCustomDomain, member);
        if (details == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(details);
    }

    @PostMapping("/{mspCustomDomain}/connectWiseManageDetails/update")
    public ResponseEntity<MSPConnectWiseManageDetails> saveOrUpdateDetails(@PathVariable String mspCustomDomain, @RequestBody MSPConnectWiseManageDetails details) {
        if (!mspCustomDomain.equals(details.getMspCustomDomain())) {
            return ResponseEntity.badRequest().body(null);
        }
        MSPConnectWiseManageDetails updatedDetails = service.saveOrUpdateDetails(mspCustomDomain, details);
        return ResponseEntity.ok(updatedDetails);
    }
}
