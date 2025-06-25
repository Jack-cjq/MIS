package org.example.repository;

import org.example.model.InternshipEmploymentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface InternshipEmploymentRepository extends MongoRepository<InternshipEmploymentModel, String> {
    List<InternshipEmploymentModel> findByUserId(String userId);
    List<InternshipEmploymentModel> findByType(String type);
} 