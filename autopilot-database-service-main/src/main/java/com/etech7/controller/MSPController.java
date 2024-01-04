package com.etech7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.etech7.entity.MSP;
import com.etech7.service.MSPService;

@RestController
@RequestMapping("/msp")
public class MSPController {

    @Autowired
    private MSPService mspService;

    @GetMapping
    public ResponseEntity<MSP> getMSP(@RequestParam String customDomain) {
        MSP msp = mspService.getMSPByCustomDomain(customDomain);
        if (msp == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(msp);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<MSP>> getMSP1() {
        return ResponseEntity.ok(mspService.getMSPs());
       
    }

    @PostMapping
    public ResponseEntity<MSP> createMSP(@RequestBody MSP msp) {
    	
    	if (msp == null || msp.getCustomDomain() == null) {
            return ResponseEntity.badRequest().body(null);
        }
        MSP savedMSP = mspService.createMSP(msp);
        return ResponseEntity.ok(savedMSP);
    }

    @PutMapping
    public ResponseEntity<MSP> editMSP(@RequestParam String customDomain, @RequestBody MSP msp) {
        MSP updatedMSP = mspService.editMSP(customDomain, msp);
        if (updatedMSP == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedMSP);
    }

	@GetMapping("/deleteMSP")
    public ResponseEntity<MSP> deleteMSP(@RequestParam String customDomain) {
    	return ResponseEntity.ok(mspService.deleteMSP(customDomain));
    }
}
