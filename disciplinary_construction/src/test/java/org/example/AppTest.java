package org.example;


import org.example.model.StudentModel;
import org.example.repository.StudentRepository;
import org.example.service.AlumniService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class AppTest {
    @Autowired
    private AlumniService alumniService;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    void contextLoads() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


        Integer id = 10240002;

        for (int i = 0; i < 5; i++) {
            StudentModel student = new StudentModel();
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
            student.setBirthDate(new java.util.Date(99, 0, 1)); // 1999-01-01
            student.setWorkStatus("年级干部");
            student.setStudentId(String.valueOf(id));
            student.setName("默认学生" + i);
            studentRepository.save(student);
            id++;
        }
    }

}