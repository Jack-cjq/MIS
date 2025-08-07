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

    @Override
    public List<StudentModel> findAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public StudentModel findById(String id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public StudentModel createStudent(StudentModel student) {
        student.setCreateTime(new java.util.Date());
        student.setUpdateTime(new java.util.Date());
        // 加密密码
        if (student.getPassword() != null && !student.getPassword().startsWith("$2a$")) {
            student.setPassword(passwordEncoder.encode(student.getPassword()));
        }
        return studentRepository.save(student);
    }

    @Override
    public StudentModel updateStudent(StudentModel student) {
        student.setUpdateTime(new java.util.Date());
        // 如果密码没有加密，则加密
        if (student.getPassword() != null && !student.getPassword().startsWith("$2a$")) {
            student.setPassword(passwordEncoder.encode(student.getPassword()));
        }
        return studentRepository.save(student);
    }

    @Override
    public boolean deleteStudent(String id) {
        try {
            studentRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public StudentModel findByStudentId(String studentId) {
        return studentRepository.findByStudentId(studentId);
    }

    @Override
    public List<StudentModel> findByName(String name) {
        return studentRepository.findByName(name);
    }

    @Override
    public List<StudentModel> findByMajor(String major) {
        return studentRepository.findByMajor(major);
    }

    @Override
    public List<StudentModel> findByGrade(String grade) {
        return studentRepository.findByGrade(grade);
    }

    // 初始化默认学生用户
    public void createDefaultStudent() {
        // 创建第一个默认学生
        if (studentRepository.findByStudentId("10240001") == null) {
            StudentModel student1 = new StudentModel();
            student1.setStudentId("10240001");
            student1.setName("默认学生");
            student1.setGender("男");
            student1.setAge(24);
            student1.setPassword(passwordEncoder.encode("123456"));
            student1.setEmail("default@student.edu.cn");
            student1.setPhone("13800000000");
            student1.setMajor("计算机科学与技术");
            student1.setGrade("2024");
            student1.setClassName("研一1班");
            student1.setSupervisor("张老师");
            student1.setResearchDirection("人工智能");
            student1.setStatus("在读");
            student1.setCreateTime(new java.util.Date());
            student1.setUpdateTime(new java.util.Date());
            student1.setPoliticalStatus("中共党员");
            student1.setAddress("湖北省武汉市洪山区");
            student1.setNativePlace("湖北省");
            student1.setIdCard("420100199901010000");
            student1.setEducationType("全日制");
            student1.setMaritalStatus("未婚");
            student1.setNation("汉族");
            student1.setBirthDate(new java.util.Date(99,0,1)); // 1999-01-01
            student1.setWorkStatus("年级干部");
            studentRepository.save(student1);
        }
        
        // 创建第二个学生账号（用于测试）
        if (studentRepository.findByStudentId("20240001") == null) {
            StudentModel student2 = new StudentModel();
            student2.setStudentId("20240001");
            student2.setName("学生111");
            student2.setGender("女");
            student2.setAge(23);
            student2.setPassword(passwordEncoder.encode("123456"));
            student2.setEmail("student111@edu.cn");
            student2.setPhone("13900000001");
            student2.setMajor("软件工程");
            student2.setGrade("2024");
            student2.setClassName("研一2班");
            student2.setSupervisor("李老师");
            student2.setResearchDirection("软件工程");
            student2.setStatus("在读");
            student2.setCreateTime(new java.util.Date());
            student2.setUpdateTime(new java.util.Date());
            student2.setPoliticalStatus("共青团员");
            student2.setAddress("湖北省武汉市武昌区");
            student2.setNativePlace("湖北省");
            student2.setIdCard("420100200001010000");
            student2.setEducationType("全日制");
            student2.setMaritalStatus("未婚");
            student2.setNation("汉族");
            student2.setBirthDate(new java.util.Date(100,0,1)); // 2000-01-01
            student2.setWorkStatus("班级干部");
            studentRepository.save(student2);
        }
    }

}
