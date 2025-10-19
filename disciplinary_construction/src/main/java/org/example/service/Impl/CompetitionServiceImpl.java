package org.example.service.Impl;

import org.example.model.CompetitionModel;
import org.example.repository.CompetitionRepository;
import org.example.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map; 
import java.util.stream.Collectors; 

@Service
public class CompetitionServiceImpl implements CompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;

    @Override
    public CompetitionModel addCompetition(CompetitionModel competition) {
        competition.setCreateTime(LocalDateTime.now());
        competition.setUpdateTime(LocalDateTime.now());
        competition.setAuditStatus("待审核");
        return competitionRepository.save(competition);
    }

    @Override
    public CompetitionModel updateCompetition(CompetitionModel competition) {
        competition.setUpdateTime(LocalDateTime.now());
        return competitionRepository.save(competition);
    }

    @Override
    public void deleteCompetition(String id) {
        competitionRepository.deleteById(id);
    }

    @Override
    public List<CompetitionModel> getStudentCompetitions(String studentId) {
        return competitionRepository.findByStudentId(studentId);
    }

    @Override
    public List<CompetitionModel> getPendingCompetitions() {
        return competitionRepository.findByAuditStatus("待审核");
    }

    @Override
    public CompetitionModel auditCompetition(String id, String auditStatus, String auditComment, String auditorId) {
        CompetitionModel competition = competitionRepository.findById(id).orElse(null);
        if (competition != null) {
            competition.setAuditStatus(auditStatus);
            competition.setAuditComment(auditComment);
            competition.setAuditorId(auditorId);
            competition.setAuditTime(LocalDateTime.now());
            return competitionRepository.save(competition);
        }
        return null;
    }

    @Override
    public List<CompetitionModel> getAllCompetitions() {
        return competitionRepository.findAll();
    }

     // 根据ID获取单个竞赛
    @Override
    public CompetitionModel getCompetitionById(String id) {
        return competitionRepository.findById(id).orElse(null);
    }


    @Override
    public List<CompetitionModel> getCompetitionsByStudentId(String studentId) {
        return getStudentCompetitions(studentId); // 直接调用已有方法
    }

    @Override
    public CompetitionModel saveCompetition(CompetitionModel competition) {
        if (competition.getId() == null || competition.getId().isEmpty()) {
            return addCompetition(competition);
        } else {
            return updateCompetition(competition);
        }
    }

    @Override
    public void deleteCompetition(Long id) {
        deleteCompetition(id.toString()); 
    }

    @Override
    public CompetitionModel getCompetitionById(Long id) {
        return getCompetitionById(id.toString()); 
    }

    // ==================== 新增：学生端需要的方法 ====================
    
    @Override
    public List<CompetitionModel> getActiveCompetitions() {
        return competitionRepository.findAll()
                .stream()
                .filter(competition -> 
                    competition.getAuditStatus() != null && 
                    ("已通过".equals(competition.getAuditStatus()) || "进行中".equals(competition.getAuditStatus()))
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<CompetitionModel> searchCompetitions(Map<String, String> params) {
        String keyword = params.get("keyword");
        String category = params.get("category");
        String status = params.get("status");
        
        List<CompetitionModel> allCompetitions = competitionRepository.findAll();
        
        return allCompetitions.stream()
                .filter(competition -> {
                    boolean matches = true;
                    
                    if (keyword != null && !keyword.isEmpty()) {
                        matches = matches && (
                            (competition.getCompetitionName() != null && competition.getCompetitionName().toLowerCase().contains(keyword.toLowerCase())) ||
                            (competition.getStudentName() != null && competition.getStudentName().toLowerCase().contains(keyword.toLowerCase())) ||
                            (competition.getInstructorName() != null && competition.getInstructorName().toLowerCase().contains(keyword.toLowerCase()))
                        );
                    }
                    
                    if (category != null && !category.isEmpty()) {
                        matches = matches && category.equals(competition.getCompetitionLevel());
                    }
                    
                    if (status != null && !status.isEmpty()) {
                        matches = matches && status.equals(competition.getAuditStatus());
                    }
                    
                    return matches;
                })
                .collect(Collectors.toList());
    }

    /**
     * 根据审核状态统计数量
     */
    @Override
    public int countByAuditStatus(String auditStatus) {
        try {
            return (int) competitionRepository.countByAuditStatus(auditStatus);
        } catch (Exception e) {
            return 0;
        }
    }
}