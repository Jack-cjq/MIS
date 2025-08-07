package org.example.service.Impl;

import org.example.model.VolunteerServiceModel;
import org.example.repository.VolunteerServiceRepository;
import org.example.service.VolunteerServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VolunteerServiceServiceImpl implements VolunteerServiceService {

    @Autowired
    private VolunteerServiceRepository volunteerServiceRepository;

    @Override
    public VolunteerServiceModel addVolunteerService(VolunteerServiceModel volunteerService) {
        volunteerService.setCreateTime(LocalDateTime.now());
        volunteerService.setUpdateTime(LocalDateTime.now());
        volunteerService.setAuditStatus("待审核");
        return volunteerServiceRepository.save(volunteerService);
    }

    @Override
    public VolunteerServiceModel updateVolunteerService(VolunteerServiceModel volunteerService) {
        volunteerService.setUpdateTime(LocalDateTime.now());
        return volunteerServiceRepository.save(volunteerService);
    }

    @Override
    public void deleteVolunteerService(String id) {
        volunteerServiceRepository.deleteById(id);
    }

    @Override
    public List<VolunteerServiceModel> getStudentVolunteerServices(String studentId) {
        return volunteerServiceRepository.findByStudentId(studentId);
    }

    @Override
    public VolunteerServiceModel getVolunteerServiceById(String id) {
        return volunteerServiceRepository.findById(id).orElse(null);
    }

    @Override
    public List<VolunteerServiceModel> getPendingVolunteerServices() {
        return volunteerServiceRepository.findByAuditStatus("待审核");
    }

    @Override
    public VolunteerServiceModel auditVolunteerService(String id, String auditStatus, String auditComment, String auditorId) {
        VolunteerServiceModel volunteerService = volunteerServiceRepository.findById(id).orElse(null);
        if (volunteerService != null) {
            volunteerService.setAuditStatus(auditStatus);
            volunteerService.setAuditComment(auditComment);
            volunteerService.setAuditorId(auditorId);
            volunteerService.setAuditTime(LocalDateTime.now());
            return volunteerServiceRepository.save(volunteerService);
        }
        return null;
    }

    @Override
    public List<VolunteerServiceModel> getAllVolunteerServices() {
        return volunteerServiceRepository.findAll();
    }

    @Override
    public List<VolunteerServiceModel> searchVolunteerServices(String keyword) {
        List<VolunteerServiceModel> byServiceName = volunteerServiceRepository.findByServiceNameContaining(keyword);
        List<VolunteerServiceModel> byStudentName = volunteerServiceRepository.findByStudentNameContaining(keyword);
        byServiceName.addAll(byStudentName);
        return byServiceName;
    }

    @Override
    public List<VolunteerServiceModel> getVolunteerServicesByType(String serviceType) {
        return volunteerServiceRepository.findByServiceType(serviceType);
    }

    @Override
    public List<VolunteerServiceModel> getVolunteerServicesByStatus(String status) {
        return volunteerServiceRepository.findByStatus(status);
    }

    @Override
    public List<VolunteerServiceModel> getVolunteerServicesByOrganization(String organization) {
        return volunteerServiceRepository.findByOrganization(organization);
    }
} 