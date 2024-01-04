package com.etech7.controller;

import com.etech7.entity.AuditTrails;
import com.etech7.service.AuditTrailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{mspCustomDomain}/audits")
public class AuditTrailsController {
	
	@Autowired
    private AuditTrailsService auditTrailsService;

	@GetMapping("/byMspId")
	public List<AuditTrails> getAllAuditTrailsByMspId(
	        @PathVariable String mspCustomDomain, 
	        @RequestParam String mspId) {
	    return auditTrailsService.getAllAuditTrailsByMspId(mspId);
	}

    @GetMapping
    public List<AuditTrails> getAllAuditTrailsByMspCustomDomain(@PathVariable String mspCustomDomain) {
        return auditTrailsService.getAllAuditTrailsByMspCustomDomain(mspCustomDomain);
    }

    @PostMapping
    public AuditTrails addAuditTrail(@PathVariable String mspCustomDomain, @RequestBody AuditTrails auditTrail) {
        return auditTrailsService.addAuditTrail(auditTrail);
    }
}
