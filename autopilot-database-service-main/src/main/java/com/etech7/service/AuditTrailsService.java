package com.etech7.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.etech7.entity.AuditTrails;
import com.etech7.repository.AuditTrailsRepository;
import java.util.List;

@Service
public class AuditTrailsService {

    private final AuditTrailsRepository auditTrailsRepository;

    @Autowired
    public AuditTrailsService(AuditTrailsRepository auditTrailsRepository) {
        this.auditTrailsRepository = auditTrailsRepository;
    }

    public List<AuditTrails> getAllAuditTrailsByMspId(String mspId) {
        return auditTrailsRepository.findByMspId(mspId);
    }

    public List<AuditTrails> getAllAuditTrailsByMspCustomDomain(String mspCustomDomain) {
        return auditTrailsRepository.findByMspCustomDomain(mspCustomDomain);
    }

    public AuditTrails addAuditTrail(AuditTrails auditTrail) {
        return auditTrailsRepository.save(auditTrail);
    }
}
