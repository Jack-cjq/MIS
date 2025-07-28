package org.example.service;

import org.example.model.StudentModel;
import java.util.List;

public interface StudentService {
    // 登录
    StudentModel login(String account, String password);
    
    // 获取所有学生
    List<StudentModel> findAllStudents();
    
    // 根据ID查找学生
    StudentModel findById(String id);
    
    // 创建学生
    StudentModel createStudent(StudentModel student);
    
    // 更新学生信息
    StudentModel updateStudent(StudentModel student);
    
    // 删除学生
    boolean deleteStudent(String id);
    
    // 根据学号查找学生
    StudentModel findByStudentId(String studentId);
    
    // 根据姓名查找学生
    List<StudentModel> findByName(String name);
    
    // 根据专业查找学生
    List<StudentModel> findByMajor(String major);
    
    // 根据年级查找学生
    List<StudentModel> findByGrade(String grade);
}
