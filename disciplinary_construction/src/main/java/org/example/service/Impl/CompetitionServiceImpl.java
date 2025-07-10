package org.example.service.Impl;

import org.example.model.CompetitionModel;
import org.example.repository.CompetitionRepository;
import org.example.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
}