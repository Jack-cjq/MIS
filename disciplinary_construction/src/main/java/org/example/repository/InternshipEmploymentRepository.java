package org.example.repository;

import org.example.model.InternshipEmploymentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;
import java.util.Date;

/**
 * 实习就业数据访问层
 * 提供各种查询方法，支持计算机学院特色的数据查询
 */
public interface InternshipEmploymentRepository extends MongoRepository<InternshipEmploymentModel, String> {
    
    // 基础查询方法
    List<InternshipEmploymentModel> findByUserId(String userId);
    List<InternshipEmploymentModel> findByStudentId(String studentId);
    List<InternshipEmploymentModel> findByStudentNameContaining(String studentName);
    List<InternshipEmploymentModel> findByStudentClass(String studentClass);
    List<InternshipEmploymentModel> findByMajor(String major);
    List<InternshipEmploymentModel> findByGrade(String grade);
    
    // 类型和分类查询
    List<InternshipEmploymentModel> findByType(String type);
    List<InternshipEmploymentModel> findByCategory(String category);
    List<InternshipEmploymentModel> findByTypeAndCategory(String type, String category);
    
    // 状态查询
    List<InternshipEmploymentModel> findByStatus(String status);
    List<InternshipEmploymentModel> findByApprovalStatus(String approvalStatus);
    List<InternshipEmploymentModel> findByStatusAndApprovalStatus(String status, String approvalStatus);
    
    // 公司相关查询
    List<InternshipEmploymentModel> findByCompanyContaining(String company);
    List<InternshipEmploymentModel> findByCompanyType(String companyType);
    List<InternshipEmploymentModel> findByIndustry(String industry);
    List<InternshipEmploymentModel> findByCompanySize(String companySize);
    List<InternshipEmploymentModel> findByCompanyLocation(String companyLocation);
    
    // 职位相关查询
    List<InternshipEmploymentModel> findByPositionContaining(String position);
    List<InternshipEmploymentModel> findByDepartment(String department);
    List<InternshipEmploymentModel> findByWorkLocation(String workLocation);
    List<InternshipEmploymentModel> findByWorkType(String workType);
    
    // 薪资相关查询
    List<InternshipEmploymentModel> findBySalaryRange(String salaryRange);
    List<InternshipEmploymentModel> findBySalaryType(String salaryType);
    
    // 软删除过滤的查询方法
    List<InternshipEmploymentModel> findByStatusAndIsDeletedFalse(String status);
    List<InternshipEmploymentModel> findByTypeAndIsDeletedFalse(String type);
    List<InternshipEmploymentModel> findByCompanyTypeAndIsDeletedFalse(String companyType);
    List<InternshipEmploymentModel> findByIndustryAndIsDeletedFalse(String industry);
    List<InternshipEmploymentModel> findByWorkTypeAndIsDeletedFalse(String workType);
    List<InternshipEmploymentModel> findByStudentIdAndStatusAndIsDeletedFalse(String studentId, String status);
    List<InternshipEmploymentModel> findByStudentIdAndApprovalStatusAndIsDeletedFalse(String studentId, String approvalStatus);
    List<InternshipEmploymentModel> findByApprovalStatusAndTypeAndIsDeletedFalse(String approvalStatus, String type);
    @Query("{'startDate': {$gte: ?0, $lte: ?1}, 'isDeleted': false}")
    List<InternshipEmploymentModel> findByStartDateBetweenAndIsDeletedFalse(Date startDate, Date endDate);
    
    // 技术相关查询（计算机学院特色）
    List<InternshipEmploymentModel> findByTechnologyStackContaining(String technologyStack);
    List<InternshipEmploymentModel> findByProgrammingLanguagesContaining(String programmingLanguages);
    List<InternshipEmploymentModel> findByFrameworksContaining(String frameworks);
    List<InternshipEmploymentModel> findByDatabasesContaining(String databases);
    List<InternshipEmploymentModel> findByCloudPlatformsContaining(String cloudPlatforms);
    List<InternshipEmploymentModel> findByDevelopmentToolsContaining(String developmentTools);
    List<InternshipEmploymentModel> findByProjectType(String projectType);
    
    // 时间范围查询
    @Query("{'startDate': {$gte: ?0, $lte: ?1}}")
    List<InternshipEmploymentModel> findByStartDateBetween(Date startDate, Date endDate);
    
    @Query("{'endDate': {$gte: ?0, $lte: ?1}}")
    List<InternshipEmploymentModel> findByEndDateBetween(Date startDate, Date endDate);
    
    @Query("{'createTime': {$gte: ?0, $lte: ?1}}")
    List<InternshipEmploymentModel> findByCreateTimeBetween(Date startDate, Date endDate);
    
    // 复合查询
    List<InternshipEmploymentModel> findByStudentIdAndStatus(String studentId, String status);
    List<InternshipEmploymentModel> findByStudentIdAndApprovalStatus(String studentId, String approvalStatus);
    List<InternshipEmploymentModel> findByApprovalStatusAndType(String approvalStatus, String type);
    List<InternshipEmploymentModel> findByIndustryAndCompanyType(String industry, String companyType);
    List<InternshipEmploymentModel> findByTechnologyStackAndProjectType(String technologyStack, String projectType);
    
    // 高级查询（使用MongoDB查询语法）
    @Query("{'salaryRange': {$in: ?0}}")
    List<InternshipEmploymentModel> findBySalaryRangeIn(List<String> salaryRanges);
    
    @Query("{'technologyStack': {$in: ?0}}")
    List<InternshipEmploymentModel> findByTechnologyStackIn(List<String> technologyStacks);
    
    @Query("{'industry': {$in: ?0}}")
    List<InternshipEmploymentModel> findByIndustryIn(List<String> industries);
    
    @Query("{'satisfactionScore': {$gte: ?0}}")
    List<InternshipEmploymentModel> findBySatisfactionScoreGreaterThanEqual(Integer score);
    
    @Query("{'satisfactionScore': {$gte: ?0, $lte: ?1}}")
    List<InternshipEmploymentModel> findBySatisfactionScoreBetween(Integer minScore, Integer maxScore);
    
    // 统计查询
    @Query(value = "{}", count = true)
    long countByApprovalStatus(String approvalStatus);
    
    @Query(value = "{}", count = true)
    long countByStatus(String status);
    
    @Query(value = "{}", count = true)
    long countByType(String type);
    
    @Query(value = "{}", count = true)
    long countByIndustry(String industry);
    
    @Query(value = "{}", count = true)
    long countByCompanyType(String companyType);
    
    @Query(value = "{}", count = true)
    long countByTechnologyStack(String technologyStack);
    
    @Query(value = "{}", count = true)
    long countByProjectType(String projectType);
    
    // 按年级统计
    @Query(value = "{}", count = true)
    long countByGrade(String grade);
    
    // 按专业统计
    @Query(value = "{}", count = true)
    long countByMajor(String major);
    
    // 按薪资范围统计
    @Query(value = "{}", count = true)
    long countBySalaryRange(String salaryRange);
    
    // 查找未删除的记录
    List<InternshipEmploymentModel> findByIsDeletedFalse();
    
    // 查找待审批的记录
    List<InternshipEmploymentModel> findByApprovalStatusAndIsDeletedFalse(String approvalStatus);
    
    // 查找特定学生的所有记录
    List<InternshipEmploymentModel> findByStudentIdAndIsDeletedFalse(String studentId);
    
    // 查找特定用户的所有记录
    List<InternshipEmploymentModel> findByUserIdAndIsDeletedFalse(String userId);
} 