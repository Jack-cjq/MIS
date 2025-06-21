package org.example.service.Impl;


import org.example.model.StudentModel;
import org.example.repository.StudentRepository;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServeiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentModel saveStudent(StudentModel student) throws IllegalAccessException {
        //业务逻辑
        if(student.getAge() < 0 ){
            throw new IllegalAccessException("年龄不能为负数");
        }
        return studentRepository.save(student);
    }

    @Override
    public List<StudentModel> getAllStudent() {
        return studentRepository.findAll();
    }

}
