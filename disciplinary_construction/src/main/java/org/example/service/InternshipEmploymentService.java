package org.example.service;

import org.example.model.InternshipEmploymentModel;
import java.util.List;

public interface InternshipEmploymentService {
    InternshipEmploymentModel add(InternshipEmploymentModel model);
    InternshipEmploymentModel update(InternshipEmploymentModel model);
    void delete(String id);
    InternshipEmploymentModel getById(String id);
    List<InternshipEmploymentModel> getByUserId(String userId);
    List<InternshipEmploymentModel> getAll();
} 