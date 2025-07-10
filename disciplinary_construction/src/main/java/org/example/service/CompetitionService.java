package org.example.service;

import org.example.model.CompetitionModel;
import java.util.List;

public interface CompetitionService {
    // 学生操作
    CompetitionModel addCompetition(CompetitionModel competition);
    CompetitionModel updateCompetition(CompetitionModel competition);
    void deleteCompetition(String id);
    List<CompetitionModel> getStudentCompetitions(String studentId);

    // 管理员操作
    List<CompetitionModel> getPendingCompetitions();
    CompetitionModel auditCompetition(String id, String auditStatus, String auditComment, String auditorId);
    List<CompetitionModel> getAllCompetitions();
}