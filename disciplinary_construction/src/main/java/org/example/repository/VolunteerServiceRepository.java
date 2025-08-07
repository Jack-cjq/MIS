package org.example.repository;

import org.example.model.VolunteerServiceModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerServiceRepository extends MongoRepository<VolunteerServiceModel, String> {
    List<VolunteerServiceModel> findByStudentId(String studentId);
    List<VolunteerServiceModel> findByAuditStatus(String auditStatus);
    List<VolunteerServiceModel> findByStudentIdAndAuditStatus(String studentId, String auditStatus);
    List<VolunteerServiceModel> findByServiceType(String serviceType);
    List<VolunteerServiceModel> findByStatus(String status);
    List<VolunteerServiceModel> findByOrganization(String organization);
    List<VolunteerServiceModel> findByServiceNameContaining(String serviceName);
    List<VolunteerServiceModel> findByStudentNameContaining(String studentName);
} 