package org.example.service;

import org.example.model.PatentModel;
import java.util.List;

public interface PatentService {
    // 学生操作
    PatentModel addPatent(PatentModel patent);
    PatentModel updatePatent(PatentModel patent);
    void deletePatent(String id);
    List<PatentModel> getStudentPatents(String studentId);

    // 管理员操作
    List<PatentModel> getPendingPatents();
    PatentModel auditPatent(String id, String auditStatus, String auditComment, String auditorId);
    List<PatentModel> getAllPatents();
}