package org.example.service;

import org.example.model.CompetitionDictModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CompetitionDictService {
    List<CompetitionDictModel> searchCompetitions(String keyword);
    List<CompetitionDictModel> getAllActiveCompetitions();
    CompetitionDictModel getCompetitionByName(String name);

    // 管理员功能
    CompetitionDictModel addCompetition(CompetitionDictModel competition);
    CompetitionDictModel updateCompetition(CompetitionDictModel competition);
    void deleteCompetition(String id);
    List<CompetitionDictModel> getAllCompetitions();

}