package org.example.service;

import org.example.model.InternshipEmploymentModel;
import java.util.List;
import java.util.Map;
import java.util.Date;

/**
 * 实习就业服务接口
 * 定义实习就业相关的业务方法
 */
public interface InternshipEmploymentService {
    // 基础CRUD操作
    InternshipEmploymentModel add(InternshipEmploymentModel model);
    InternshipEmploymentModel update(InternshipEmploymentModel model);
    void delete(String id);
    InternshipEmploymentModel getById(String id);
    List<InternshipEmploymentModel> getByUserId(String userId);
    List<InternshipEmploymentModel> getAll();
    
    // 查询方法
    List<InternshipEmploymentModel> getByStudentId(String studentId);
    List<InternshipEmploymentModel> getByStatus(String status);
    List<InternshipEmploymentModel> getByApprovalStatus(String approvalStatus);
    List<InternshipEmploymentModel> getByType(String type);
    List<InternshipEmploymentModel> getByCompanyType(String companyType);
    List<InternshipEmploymentModel> getByIndustry(String industry);
    List<InternshipEmploymentModel> getByWorkType(String workType);
    List<InternshipEmploymentModel> getByDateRange(Date startDate, Date endDate);
    List<InternshipEmploymentModel> getByStudentIdAndStatus(String studentId, String status);
    List<InternshipEmploymentModel> getByStudentIdAndApprovalStatus(String studentId, String approvalStatus);
    List<InternshipEmploymentModel> getByApprovalStatusAndType(String approvalStatus, String type);
    
    // 审批相关
    InternshipEmploymentModel approve(String id, String approvalRemark, String approvalBy);
    InternshipEmploymentModel reject(String id, String approvalRemark, String approvalBy);
    
    // 基础统计方法
    Map<String, Object> getStatistics();
    Map<String, Long> getStatusStatistics();
    Map<String, Long> getIndustryStatistics();
    Map<String, Long> getCompanyTypeStatistics();
    Map<String, Long> getApprovalStatusStatistics();
    
    // 新增统计方法
    Map<String, Long> getTechnologyStackStatistics();
    Map<String, Long> getSalaryRangeStatistics();
    Map<String, Object> getSatisfactionStatistics();
    Map<String, Object> getEmploymentTrendAnalysis();
    List<Map<String, Object>> getTopTechnologyStacks(int limit);
    List<Map<String, Object>> getTopIndustries(int limit);
    
    // 搜索方法
    List<InternshipEmploymentModel> search(String keyword);
    List<InternshipEmploymentModel> searchByStudentName(String studentName);
    List<InternshipEmploymentModel> searchByCompany(String company);
    List<InternshipEmploymentModel> searchByPosition(String position);
} 