package org.example.service;

import org.example.model.PartyCourseModel;
import java.util.List;

public interface PartyCourseService {
    // 学生操作
    PartyCourseModel addPartyCourse(PartyCourseModel partyCourse);
    PartyCourseModel updatePartyCourse(PartyCourseModel partyCourse);
    void deletePartyCourse(String id);
    List<PartyCourseModel> getStudentPartyCourses(String studentId);
    PartyCourseModel getPartyCourseById(String id);

    // 管理员操作
    List<PartyCourseModel> getPendingPartyCourses();
    PartyCourseModel auditPartyCourse(String id, String auditStatus, String auditComment, String auditorId);
    List<PartyCourseModel> getAllPartyCourses();
    List<PartyCourseModel> searchPartyCourses(String keyword);
    List<PartyCourseModel> getPartyCoursesByType(String courseType);
    List<PartyCourseModel> getPartyCoursesByStatus(String status);
    List<PartyCourseModel> getPartyCoursesByInstructor(String instructor);
} 