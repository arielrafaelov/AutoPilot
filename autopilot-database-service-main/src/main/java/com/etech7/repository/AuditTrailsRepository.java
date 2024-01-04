package com.etech7.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.etech7.entity.AuditTrails;
import java.util.List;

@Repository
public interface AuditTrailsRepository extends MongoRepository<AuditTrails, String> {

    List<AuditTrails> findByMspId(String mspId);

    List<AuditTrails> findByMspCustomDomain(String mspCustomDomain);

}
