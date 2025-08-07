package org.example.service.Impl;

import org.example.model.InternshipEmploymentModel;
import org.example.repository.InternshipEmploymentRepository;
import org.example.service.InternshipEmploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 实习就业服务实现类
 * 提供实习就业相关的业务逻辑处理
 * 包含统计分析、数据查询等功能
 */
@Service
public class InternshipEmploymentServiceImpl implements InternshipEmploymentService {
    @Autowired
    private InternshipEmploymentRepository repository;

    @Override
    public InternshipEmploymentModel add(InternshipEmploymentModel model) {
        // 设置创建时间和更新时间
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        model.setApprovalStatus("待审批");
        model.setIsDeleted(false);
        
        // 确保ID为空，让MongoDB自动生成
        model.setId(null);
        
        // 计算持续时间
        if (model.getStartDate() != null && model.getEndDate() != null) {
            long diffInMillies = model.getEndDate().getTime() - model.getStartDate().getTime();
            long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);
            model.setDuration(diffInDays + "天");
        }
        
        // 保存并返回包含ID的记录
        InternshipEmploymentModel savedModel = repository.save(model);
        System.out.println("新创建的记录ID: " + savedModel.getId());
        return savedModel;
    }

    @Override
    public InternshipEmploymentModel update(InternshipEmploymentModel model) {
        model.setUpdateTime(new Date());
        
        // 重新计算持续时间
        if (model.getStartDate() != null && model.getEndDate() != null) {
            long diffInMillies = model.getEndDate().getTime() - model.getStartDate().getTime();
            long diffInDays = diffInMillies / (24 * 60 * 60 * 1000);
            model.setDuration(diffInDays + "天");
        }
        
        return repository.save(model);
    }

    @Override
    public void delete(String id) {
        // 软删除，设置删除标志
        Optional<InternshipEmploymentModel> optional = repository.findById(id);
        if (optional.isPresent()) {
            InternshipEmploymentModel model = optional.get();
            model.setIsDeleted(true);
            model.setUpdateTime(new Date());
            repository.save(model);
        }
    }

    @Override
    public InternshipEmploymentModel getById(String id) {
        Optional<InternshipEmploymentModel> optional = repository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<InternshipEmploymentModel> getByUserId(String userId) {
        return repository.findByUserIdAndIsDeletedFalse(userId);
    }

    @Override
    public List<InternshipEmploymentModel> getAll() {
        return repository.findByIsDeletedFalse();
    }

    @Override
    public List<InternshipEmploymentModel> getByStudentId(String studentId) {
        return repository.findByStudentIdAndIsDeletedFalse(studentId);
    }

    @Override
    public List<InternshipEmploymentModel> getByStatus(String status) {
        return repository.findByStatusAndIsDeletedFalse(status);
    }

    @Override
    public List<InternshipEmploymentModel> getByApprovalStatus(String approvalStatus) {
        return repository.findByApprovalStatusAndIsDeletedFalse(approvalStatus);
    }

    @Override
    public List<InternshipEmploymentModel> getByType(String type) {
        return repository.findByTypeAndIsDeletedFalse(type);
    }

    @Override
    public List<InternshipEmploymentModel> getByCompanyType(String companyType) {
        return repository.findByCompanyTypeAndIsDeletedFalse(companyType);
    }

    @Override
    public List<InternshipEmploymentModel> getByIndustry(String industry) {
        return repository.findByIndustryAndIsDeletedFalse(industry);
    }

    @Override
    public List<InternshipEmploymentModel> getByWorkType(String workType) {
        return repository.findByWorkTypeAndIsDeletedFalse(workType);
    }

    @Override
    public List<InternshipEmploymentModel> getByDateRange(Date startDate, Date endDate) {
        return repository.findByStartDateBetweenAndIsDeletedFalse(startDate, endDate);
    }

    @Override
    public List<InternshipEmploymentModel> getByStudentIdAndStatus(String studentId, String status) {
        return repository.findByStudentIdAndStatusAndIsDeletedFalse(studentId, status);
    }

    @Override
    public List<InternshipEmploymentModel> getByStudentIdAndApprovalStatus(String studentId, String approvalStatus) {
        return repository.findByStudentIdAndApprovalStatusAndIsDeletedFalse(studentId, approvalStatus);
    }

    @Override
    public List<InternshipEmploymentModel> getByApprovalStatusAndType(String approvalStatus, String type) {
        return repository.findByApprovalStatusAndTypeAndIsDeletedFalse(approvalStatus, type);
    }

    @Override
    public InternshipEmploymentModel approve(String id, String approvalRemark, String approvalBy) {
        Optional<InternshipEmploymentModel> optional = repository.findById(id);
        if (optional.isPresent()) {
            InternshipEmploymentModel model = optional.get();
            model.setApprovalStatus("已通过");
            model.setApprovalRemark(approvalRemark);
            model.setApprovalBy(approvalBy);
            model.setApprovalTime(new Date());
            model.setUpdateTime(new Date());
            return repository.save(model);
        }
        return null;
    }

    @Override
    public InternshipEmploymentModel reject(String id, String approvalRemark, String approvalBy) {
        Optional<InternshipEmploymentModel> optional = repository.findById(id);
        if (optional.isPresent()) {
            InternshipEmploymentModel model = optional.get();
            model.setApprovalStatus("已拒绝");
            model.setApprovalRemark(approvalRemark);
            model.setApprovalBy(approvalBy);
            model.setApprovalTime(new Date());
            model.setUpdateTime(new Date());
            return repository.save(model);
        }
        return null;
    }

    @Override
    public Map<String, Object> getStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        List<InternshipEmploymentModel> allRecords = repository.findByIsDeletedFalse();
        
        // 基础统计
        statistics.put("totalCount", allRecords.size());
        statistics.put("internshipCount", allRecords.stream().filter(r -> "实习".equals(r.getType())).count());
        statistics.put("employmentCount", allRecords.stream().filter(r -> "就业".equals(r.getType())).count());
        statistics.put("pendingApprovalCount", allRecords.stream().filter(r -> "待审批".equals(r.getApprovalStatus())).count());
        statistics.put("approvedCount", allRecords.stream().filter(r -> "已通过".equals(r.getApprovalStatus())).count());
        statistics.put("rejectedCount", allRecords.stream().filter(r -> "已拒绝".equals(r.getApprovalStatus())).count());
        
        // 按状态统计
        Map<String, Long> statusStats = allRecords.stream()
                .collect(Collectors.groupingBy(
                        InternshipEmploymentModel::getStatus,
                        Collectors.counting()
                ));
        statistics.put("statusStatistics", statusStats);
        
        // 按行业统计
        Map<String, Long> industryStats = allRecords.stream()
                .collect(Collectors.groupingBy(
                        InternshipEmploymentModel::getIndustry,
                        Collectors.counting()
                ));
        statistics.put("industryStatistics", industryStats);
        
        // 按公司类型统计
        Map<String, Long> companyTypeStats = allRecords.stream()
                .collect(Collectors.groupingBy(
                        InternshipEmploymentModel::getCompanyType,
                        Collectors.counting()
                ));
        statistics.put("companyTypeStatistics", companyTypeStats);
        
        // 按技术栈统计
        Map<String, Long> techStackStats = allRecords.stream()
                .filter(r -> r.getTechnologyStack() != null)
                .flatMap(r -> Arrays.stream(r.getTechnologyStack().split(",")))
                .map(String::trim)
                .collect(Collectors.groupingBy(
                        tech -> tech,
                        Collectors.counting()
                ));
        statistics.put("technologyStackStatistics", techStackStats);
        
        // 按薪资范围统计
        Map<String, Long> salaryRangeStats = allRecords.stream()
                .filter(r -> r.getSalaryRange() != null)
                .collect(Collectors.groupingBy(
                        InternshipEmploymentModel::getSalaryRange,
                        Collectors.counting()
                ));
        statistics.put("salaryRangeStatistics", salaryRangeStats);
        
        // 按年级统计
        Map<String, Long> gradeStats = allRecords.stream()
                .filter(r -> r.getGrade() != null)
                .collect(Collectors.groupingBy(
                        InternshipEmploymentModel::getGrade,
                        Collectors.counting()
                ));
        statistics.put("gradeStatistics", gradeStats);
        
        // 按专业统计
        Map<String, Long> majorStats = allRecords.stream()
                .filter(r -> r.getMajor() != null)
                .collect(Collectors.groupingBy(
                        InternshipEmploymentModel::getMajor,
                        Collectors.counting()
                ));
        statistics.put("majorStatistics", majorStats);
        
        return statistics;
    }

    @Override
    public Map<String, Long> getStatusStatistics() {
        List<InternshipEmploymentModel> allRecords = repository.findByIsDeletedFalse();
        return allRecords.stream()
                .collect(Collectors.groupingBy(
                        InternshipEmploymentModel::getStatus,
                        Collectors.counting()
                ));
    }

    @Override
    public Map<String, Long> getIndustryStatistics() {
        List<InternshipEmploymentModel> allRecords = repository.findByIsDeletedFalse();
        return allRecords.stream()
                .collect(Collectors.groupingBy(
                        InternshipEmploymentModel::getIndustry,
                        Collectors.counting()
                ));
    }

    @Override
    public Map<String, Long> getCompanyTypeStatistics() {
        List<InternshipEmploymentModel> allRecords = repository.findByIsDeletedFalse();
        return allRecords.stream()
                .collect(Collectors.groupingBy(
                        InternshipEmploymentModel::getCompanyType,
                        Collectors.counting()
                ));
    }

    @Override
    public Map<String, Long> getApprovalStatusStatistics() {
        List<InternshipEmploymentModel> allRecords = repository.findByIsDeletedFalse();
        return allRecords.stream()
                .collect(Collectors.groupingBy(
                        InternshipEmploymentModel::getApprovalStatus,
                        Collectors.counting()
                ));
    }

    @Override
    public List<InternshipEmploymentModel> search(String keyword) {
        List<InternshipEmploymentModel> allRecords = repository.findByIsDeletedFalse();
        return allRecords.stream()
                .filter(record -> 
                    (record.getStudentName() != null && record.getStudentName().contains(keyword)) ||
                    (record.getCompany() != null && record.getCompany().contains(keyword)) ||
                    (record.getPosition() != null && record.getPosition().contains(keyword)) ||
                    (record.getTechnologyStack() != null && record.getTechnologyStack().contains(keyword))
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<InternshipEmploymentModel> searchByStudentName(String studentName) {
        return repository.findByStudentNameContaining(studentName);
    }

    @Override
    public List<InternshipEmploymentModel> searchByCompany(String company) {
        return repository.findByCompanyContaining(company);
    }

    @Override
    public List<InternshipEmploymentModel> searchByPosition(String position) {
        return repository.findByPositionContaining(position);
    }

    // 新增方法：获取技术栈统计
    @Override
    public Map<String, Long> getTechnologyStackStatistics() {
        List<InternshipEmploymentModel> allRecords = repository.findByIsDeletedFalse();
        return allRecords.stream()
                .filter(r -> r.getTechnologyStack() != null)
                .flatMap(r -> Arrays.stream(r.getTechnologyStack().split(",")))
                .map(String::trim)
                .collect(Collectors.groupingBy(
                        tech -> tech,
                        Collectors.counting()
                ));
    }

    // 新增方法：获取薪资范围统计
    @Override
    public Map<String, Long> getSalaryRangeStatistics() {
        List<InternshipEmploymentModel> allRecords = repository.findByIsDeletedFalse();
        return allRecords.stream()
                .filter(r -> r.getSalaryRange() != null)
                .collect(Collectors.groupingBy(
                        InternshipEmploymentModel::getSalaryRange,
                        Collectors.counting()
                ));
    }

    // 新增方法：获取满意度统计
    @Override
    public Map<String, Object> getSatisfactionStatistics() {
        List<InternshipEmploymentModel> allRecords = repository.findByIsDeletedFalse();
        List<Integer> scores = allRecords.stream()
                .filter(r -> r.getSatisfactionScore() != null)
                .map(InternshipEmploymentModel::getSatisfactionScore)
                .collect(Collectors.toList());
        
        Map<String, Object> stats = new HashMap<>();
        if (!scores.isEmpty()) {
            stats.put("averageScore", scores.stream().mapToInt(Integer::intValue).average().orElse(0.0));
            stats.put("maxScore", scores.stream().mapToInt(Integer::intValue).max().orElse(0));
            stats.put("minScore", scores.stream().mapToInt(Integer::intValue).min().orElse(0));
            stats.put("totalEvaluations", scores.size());
        }
        
        return stats;
    }

    // 新增方法：获取就业趋势分析
    @Override
    public Map<String, Object> getEmploymentTrendAnalysis() {
        List<InternshipEmploymentModel> allRecords = repository.findByIsDeletedFalse();
        
        // 按月份统计
        Map<String, Long> monthlyStats = allRecords.stream()
                .filter(r -> r.getCreateTime() != null)
                .collect(Collectors.groupingBy(
                        r -> {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(r.getCreateTime());
                            return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1);
                        },
                        Collectors.counting()
                ));
        
        // 按季度统计
        Map<String, Long> quarterlyStats = allRecords.stream()
                .filter(r -> r.getCreateTime() != null)
                .collect(Collectors.groupingBy(
                        r -> {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(r.getCreateTime());
                            int quarter = (cal.get(Calendar.MONTH) / 3) + 1;
                            return cal.get(Calendar.YEAR) + "Q" + quarter;
                        },
                        Collectors.counting()
                ));
        
        Map<String, Object> trendAnalysis = new HashMap<>();
        trendAnalysis.put("monthlyStatistics", monthlyStats);
        trendAnalysis.put("quarterlyStatistics", quarterlyStats);
        
        return trendAnalysis;
    }

    // 新增方法：获取热门技术栈
    @Override
    public List<Map<String, Object>> getTopTechnologyStacks(int limit) {
        Map<String, Long> techStackStats = getTechnologyStackStatistics();
        return techStackStats.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("technology", entry.getKey());
                    result.put("count", entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }

    // 新增方法：获取热门行业
    @Override
    public List<Map<String, Object>> getTopIndustries(int limit) {
        Map<String, Long> industryStats = getIndustryStatistics();
        return industryStats.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(limit)
                .map(entry -> {
                    Map<String, Object> result = new HashMap<>();
                    result.put("industry", entry.getKey());
                    result.put("count", entry.getValue());
                    return result;
                })
                .collect(Collectors.toList());
    }
} 