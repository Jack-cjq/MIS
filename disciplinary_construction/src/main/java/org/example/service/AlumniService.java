package org.example.service;

import org.example.model.AlumniModel;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public interface AlumniService {
    void insertAlumni(AlumniModel alumniModel);//插入一条校友信息

    void insertAlumniByAdmin(AlumniModel alumniModel);//插入一条校友信息（管理员使用）

    void updateAlumni(AlumniModel alumniModel);//修改校友信息

    void updateAlumniByAdmin(AlumniModel alumniModel);//修改校友信息（管理员使用）

    AlumniModel findAlumniByStudentId(String studentId);//查询当前学生的校友信息

    List<AlumniModel> searchAlumniList(String searchValue, Date searchYear, Integer currentPage, Integer pageSize);//查询校友信息（模糊查询：学号、姓名、所在单位），提供分页

    Integer getTotalCount(String searchValue, Date searchYear);
}
