package org.example.service.Impl;


import org.example.model.StudentModel;
import org.example.repository.StudentRepository;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServeiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // @Override
    // public StudentModel saveStudent(StudentModel student) throws IllegalAccessException {
    //     //业务逻辑
    //     if(student.getAge() < 0 ){
    //         throw new IllegalAccessException("年龄不能为负数");
    //     }
    //     return studentRepository.save(student);
    // }

    // @Override
    // public List<StudentModel> getAllStudent() {
    //     return studentRepository.findAll();
    // }

    @Override
    public StudentModel login(String account, String password) {
        StudentModel student = studentRepository.findByStudentId(account);
        System.out.print(student);
        if (student == null) {
            student = studentRepository.findByIdCard(account);
        }
        if (student == null) {
            return null;
        }
        if (passwordEncoder.matches(password, student.getPassword())) {
            return student;
        }
        return null;
    }

    // 初始化一个默认学生用户
    public void createDefaultStudent() {
        if (studentRepository.findByStudentId("10240001") == null) {
            StudentModel student = new StudentModel();
            student.setStudentId("10240001");
            student.setName("默认学生");
            student.setGender("男");
            student.setAge(24);
            student.setPassword(passwordEncoder.encode("123456"));
            student.setEmail("default@student.edu.cn");
            student.setPhone("13800000000");
            student.setMajor("计算机科学与技术");
            student.setGrade("2024");
            student.setClassName("研一1班");
            student.setSupervisor("张老师");
            student.setResearchDirection("人工智能");
            student.setStatus("在读");
            student.setCreateTime(new java.util.Date());
            student.setUpdateTime(new java.util.Date());
            student.setPoliticalStatus("中共党员");
            student.setAddress("湖北省武汉市洪山区");
            student.setNativePlace("湖北省");
            student.setIdCard("420100199901010000");
            student.setEducationType("全日制");
            student.setMaritalStatus("未婚");
            student.setNation("汉族");
            student.setBirthDate(new java.util.Date(99,0,1)); // 1999-01-01
            student.setWorkStatus("年级干部");
            studentRepository.save(student);
        }
    }

}
