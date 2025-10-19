package org.example.repository;

import org.example.model.CompetitionDictModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitionDictRepository extends MongoRepository<CompetitionDictModel, String> {
    List<CompetitionDictModel> findByCompetitionNameContaining(String keyword);
    List<CompetitionDictModel> findByCompetitionStatus(String competitionStatus);
    CompetitionDictModel findByCompetitionName(String competitionName);
    List<CompetitionDictModel> findByCompetitionLevelAndCompetitionStatus(String competitionLevel, String competitionStatus);
    CompetitionDictModel findBySerialNumber(Integer serialNumber);
}