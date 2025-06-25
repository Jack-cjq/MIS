package org.example.service;


import org.example.model.StudentModel;

import java.util.List;

public interface StudentService  {
    // StudentModel saveStudent(StudentModel student) throws IllegalAccessException;
    // List<StudentModel> getAllStudent();

    // 登录
    StudentModel login(String account, String password);
}
