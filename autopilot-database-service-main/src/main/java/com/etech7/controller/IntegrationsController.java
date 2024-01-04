package com.etech7.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.etech7.entity.Integrations;
import com.etech7.service.IntegrationsService;

@RestController
@RequestMapping("/{mspCustomDomain}/integrations")
public class IntegrationsController {

    @Autowired
    private IntegrationsService integrationsService;

    @GetMapping
    public ResponseEntity<Integrations> getIntegration(@PathVariable String mspCustomDomain) {
        Integrations integrations = integrationsService.findIntegrationsByMspCustomDomain(mspCustomDomain);
        return ResponseEntity.ok(integrations);
    }

    @PostMapping("/update")
    public ResponseEntity<Integrations> updateIntegration(@PathVariable String mspCustomDomain, @RequestBody Integrations integrations) {
        Integrations updatedIntegrations = integrationsService.editIntegrations(mspCustomDomain, integrations);
        return ResponseEntity.ok(updatedIntegrations);
    }

}
