<template>
  <div class="internship-admin-page">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="statistics-cards">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon">
              <el-icon><Briefcase /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ statistics.totalCount || 0 }}</div>
              <div class="stat-label">总记录数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon internship">
              <el-icon><Calendar /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ statistics.internshipCount || 0 }}</div>
              <div class="stat-label">实习记录</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon employment">
              <el-icon><OfficeBuilding /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ statistics.employmentCount || 0 }}</div>
              <div class="stat-label">就业记录</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon pending">
              <el-icon><Clock /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ statistics.pendingApprovalCount || 0 }}</div>
              <div class="stat-label">待审批</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-card>
      <template #header>
        <div class="card-header">
          <span>实习就业管理</span>
          <el-button type="primary" @click="handleAdd">添加记录</el-button>
        </div>
      </template>

      <!-- 搜索栏 -->
      <div class="search-bar">
        <el-row :gutter="20">
          <el-col :span="4">
            <el-input
              v-model="searchForm.studentName"
              placeholder="请输入学生姓名"
              clearable
              @keyup.enter="handleSearch"
            />
          </el-col>
          <el-col :span="4">
            <el-input
              v-model="searchForm.company"
              placeholder="请输入单位名称"
              clearable
              @keyup.enter="handleSearch"
            />
          </el-col>
          <el-col :span="4">
            <el-select v-model="searchForm.type" placeholder="请选择类型" clearable>
              <el-option label="实习" value="实习" />
              <el-option label="就业" value="就业" />
            </el-select>
          </el-col>
          <el-col :span="4">
            <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
              <el-option label="在岗" value="在岗" />
              <el-option label="已结束" value="已结束" />
              <el-option label="待入职" value="待入职" />
              <el-option label="已离职" value="已离职" />
            </el-select>
          </el-col>
          <el-col :span="4">
            <el-select v-model="searchForm.approvalStatus" placeholder="请选择审批状态" clearable>
              <el-option label="待审批" value="待审批" />
              <el-option label="已通过" value="已通过" />
              <el-option label="已拒绝" value="已拒绝" />
            </el-select>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" @click="handleSearch">搜索</el-button>
            <el-button @click="handleReset">重置</el-button>
          </el-col>
        </el-row>
      </div>

      <!-- 实习就业列表 -->
      <el-table
        :data="internshipList"
        v-loading="loading"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="studentName" label="学生姓名" width="100" />
        <el-table-column prop="studentId" label="学号" width="120" />
        <el-table-column prop="type" label="类型" width="80">
          <template #default="scope">
            <el-tag :type="scope.row.type === '实习' ? 'warning' : 'success'">
              {{ scope.row.type }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="company" label="单位名称" width="150" />
        <el-table-column prop="position" label="职位" width="120" />
        <el-table-column prop="department" label="部门" width="120" />
        <el-table-column prop="workLocation" label="工作地点" width="120" />
        <el-table-column prop="salary" label="薪资" width="100" />
        <el-table-column prop="startDate" label="开始时间" width="120">
          <template #default="scope">
            {{ formatDate(scope.row.startDate) }}
          </template>
        </el-table-column>
        <el-table-column prop="endDate" label="结束时间" width="120">
          <template #default="scope">
            {{ formatDate(scope.row.endDate) }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.status)">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="approvalStatus" label="审批状态" width="100">
          <template #default="scope">
            <el-tag :type="getApprovalStatusType(scope.row.approvalStatus)">
              {{ scope.row.approvalStatus }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <div class="action-buttons">
              <!-- 查看按钮 -->
              <el-tooltip content="查看详情" placement="top">
                <el-button 
                  size="small" 
                  type="info" 
                  :icon="View"
                  circle
                  @click="handleView(scope.row)"
                />
              </el-tooltip>
              
              <!-- 编辑按钮 -->
              <el-tooltip content="编辑记录" placement="top">
                <el-button 
                  size="small" 
                  type="primary" 
                  :icon="Edit"
                  circle
                  @click="handleEdit(scope.row)"
                  :disabled="scope.row.approvalStatus === '已通过'"
                />
              </el-tooltip>
              
              <!-- 审批按钮 -->
              <el-tooltip content="审批记录" placement="top" v-if="scope.row.approvalStatus === '待审批'">
                <el-button 
                  size="small" 
                  type="success" 
                  :icon="Check"
                  circle
                  @click="handleApprove(scope.row)"
                />
              </el-tooltip>
              
              <!-- 删除按钮 -->
              <el-tooltip content="删除记录" placement="top">
                <el-button 
                  size="small" 
                  type="danger" 
                  :icon="Delete"
                  circle
                  @click="handleDelete(scope.row)"
                  :disabled="scope.row.approvalStatus === '已通过'"
                />
              </el-tooltip>
            </div>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          :current-page="pagination.currentPage"
          :page-size="pagination.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="900px"
      @close="handleDialogClose"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="120px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="学生姓名" prop="studentName">
              <el-input v-model="form.studentName" placeholder="请输入学生姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学号" prop="studentId">
              <el-input v-model="form.studentId" placeholder="请输入学号" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="类型" prop="type">
              <el-select v-model="form.type" placeholder="请选择类型">
                <el-option label="实习" value="实习" />
                <el-option label="就业" value="就业" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="单位名称" prop="company">
              <el-input v-model="form.company" placeholder="请输入单位名称" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="单位类型" prop="companyType">
              <el-select v-model="form.companyType" placeholder="请选择单位类型">
                <el-option label="国企" value="国企" />
                <el-option label="外企" value="外企" />
                <el-option label="民企" value="民企" />
                <el-option label="事业单位" value="事业单位" />
                <el-option label="其他" value="其他" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="行业" prop="industry">
              <el-select v-model="form.industry" placeholder="请选择行业">
                <el-option label="IT" value="IT" />
                <el-option label="金融" value="金融" />
                <el-option label="教育" value="教育" />
                <el-option label="制造业" value="制造业" />
                <el-option label="其他" value="其他" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="职位" prop="position">
              <el-input v-model="form.position" placeholder="请输入职位" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="部门" prop="department">
              <el-input v-model="form.department" placeholder="请输入部门" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="工作地点" prop="workLocation">
              <el-input v-model="form.workLocation" placeholder="请输入工作地点" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工作类型" prop="workType">
              <el-select v-model="form.workType" placeholder="请选择工作类型">
                <el-option label="全职" value="全职" />
                <el-option label="兼职" value="兼职" />
                <el-option label="实习" value="实习" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="薪资" prop="salary">
              <el-input v-model="form.salary" placeholder="请输入薪资" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="薪资类型" prop="salaryType">
              <el-select v-model="form.salaryType" placeholder="请选择薪资类型">
                <el-option label="月薪" value="月薪" />
                <el-option label="年薪" value="年薪" />
                <el-option label="时薪" value="时薪" />
                <el-option label="项目制" value="项目制" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="薪资范围" prop="salaryRange">
              <el-select v-model="form.salaryRange" placeholder="请选择薪资范围">
                <el-option label="3k以下" value="3k以下" />
                <el-option label="3k-5k" value="3k-5k" />
                <el-option label="5k-8k" value="5k-8k" />
                <el-option label="8k-12k" value="8k-12k" />
                <el-option label="12k-20k" value="12k-20k" />
                <el-option label="20k以上" value="20k以上" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公司规模" prop="companySize">
              <el-select v-model="form.companySize" placeholder="请选择公司规模">
                <el-option label="初创(1-50人)" value="初创(1-50人)" />
                <el-option label="中小型(50-500人)" value="中小型(50-500人)" />
                <el-option label="大型(500人以上)" value="大型(500人以上)" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="技术栈" prop="technologyStack">
              <el-select v-model="form.technologyStack" multiple placeholder="请选择技术栈">
                <el-option label="Java" value="Java" />
                <el-option label="Python" value="Python" />
                <el-option label="C++" value="C++" />
                <el-option label="JavaScript" value="JavaScript" />
                <el-option label="Go" value="Go" />
                <el-option label="Rust" value="Rust" />
                <el-option label="PHP" value="PHP" />
                <el-option label="C#" value="C#" />
                <el-option label="其他" value="其他" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="编程语言" prop="programmingLanguages">
              <el-input v-model="form.programmingLanguages" placeholder="请输入编程语言，多个用逗号分隔" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="框架" prop="frameworks">
              <el-select v-model="form.frameworks" multiple placeholder="请选择框架">
                <el-option label="Spring" value="Spring" />
                <el-option label="React" value="React" />
                <el-option label="Vue" value="Vue" />
                <el-option label="Angular" value="Angular" />
                <el-option label="Django" value="Django" />
                <el-option label="Flask" value="Flask" />
                <el-option label="Express" value="Express" />
                <el-option label="其他" value="其他" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据库" prop="databases">
              <el-select v-model="form.databases" multiple placeholder="请选择数据库">
                <el-option label="MySQL" value="MySQL" />
                <el-option label="PostgreSQL" value="PostgreSQL" />
                <el-option label="MongoDB" value="MongoDB" />
                <el-option label="Redis" value="Redis" />
                <el-option label="Oracle" value="Oracle" />
                <el-option label="SQL Server" value="SQL Server" />
                <el-option label="其他" value="其他" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="云平台" prop="cloudPlatforms">
              <el-select v-model="form.cloudPlatforms" multiple placeholder="请选择云平台">
                <el-option label="AWS" value="AWS" />
                <el-option label="Azure" value="Azure" />
                <el-option label="阿里云" value="阿里云" />
                <el-option label="腾讯云" value="腾讯云" />
                <el-option label="华为云" value="华为云" />
                <el-option label="其他" value="其他" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="开发工具" prop="developmentTools">
              <el-select v-model="form.developmentTools" multiple placeholder="请选择开发工具">
                <el-option label="Git" value="Git" />
                <el-option label="Docker" value="Docker" />
                <el-option label="Kubernetes" value="Kubernetes" />
                <el-option label="Jenkins" value="Jenkins" />
                <el-option label="Maven" value="Maven" />
                <el-option label="Gradle" value="Gradle" />
                <el-option label="其他" value="其他" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="项目类型" prop="projectType">
              <el-select v-model="form.projectType" placeholder="请选择项目类型">
                <el-option label="Web开发" value="Web开发" />
                <el-option label="移动开发" value="移动开发" />
                <el-option label="数据分析" value="数据分析" />
                <el-option label="人工智能" value="人工智能" />
                <el-option label="系统架构" value="系统架构" />
                <el-option label="运维" value="运维" />
                <el-option label="测试" value="测试" />
                <el-option label="其他" value="其他" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="分类" prop="category">
              <el-select v-model="form.category" placeholder="请选择分类">
                <el-option label="暑期实习" value="暑期实习" />
                <el-option label="毕业实习" value="毕业实习" />
                <el-option label="正式就业" value="正式就业" />
                <el-option label="创业" value="创业" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startDate">
              <el-date-picker
                v-model="form.startDate"
                type="date"
                placeholder="请选择开始时间"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束时间" prop="endDate">
              <el-date-picker
                v-model="form.endDate"
                type="date"
                placeholder="请选择结束时间"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态">
                <el-option label="在岗" value="在岗" />
                <el-option label="已结束" value="已结束" />
                <el-option label="待入职" value="待入职" />
                <el-option label="已离职" value="已离职" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="合同类型" prop="contractType">
              <el-select v-model="form.contractType" placeholder="请选择合同类型">
                <el-option label="正式" value="正式" />
                <el-option label="实习" value="实习" />
                <el-option label="临时" value="临时" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="福利待遇" prop="benefits">
          <el-input
            v-model="form.benefits"
            type="textarea"
            :rows="3"
            placeholder="请输入福利待遇"
          />
        </el-form-item>

        <el-form-item label="工作描述" prop="jobDescription">
          <el-input
            v-model="form.jobDescription"
            type="textarea"
            :rows="3"
            placeholder="请输入工作描述"
          />
        </el-form-item>

        <el-form-item label="工作职责" prop="responsibilities">
          <el-input
            v-model="form.responsibilities"
            type="textarea"
            :rows="3"
            placeholder="请输入工作职责"
          />
        </el-form-item>

        <el-form-item label="工作成果" prop="achievements">
          <el-input
            v-model="form.achievements"
            type="textarea"
            :rows="3"
            placeholder="请输入工作成果"
          />
        </el-form-item>

        <el-form-item label="学习收获" prop="learningOutcomes">
          <el-input
            v-model="form.learningOutcomes"
            type="textarea"
            :rows="3"
            placeholder="请输入学习收获"
          />
        </el-form-item>

        <el-form-item label="所需技能" prop="skills">
          <el-input
            v-model="form.skills"
            type="textarea"
            :rows="3"
            placeholder="请输入所需技能"
          />
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="联系人" prop="contactPerson">
              <el-input v-model="form.contactPerson" placeholder="请输入联系人" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系电话" prop="contactPhone">
              <el-input v-model="form.contactPhone" placeholder="请输入联系电话" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系邮箱" prop="contactEmail">
              <el-input v-model="form.contactEmail" placeholder="请输入联系邮箱" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="导师姓名" prop="supervisorName">
              <el-input v-model="form.supervisorName" placeholder="请输入导师姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="导师联系方式" prop="supervisorContact">
              <el-input v-model="form.supervisorContact" placeholder="请输入导师联系方式" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="学生评价" prop="studentEvaluation">
              <el-input
                v-model="form.studentEvaluation"
                type="textarea"
                :rows="3"
                placeholder="请输入学生评价"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="企业评价" prop="companyEvaluation">
              <el-input
                v-model="form.companyEvaluation"
                type="textarea"
                :rows="3"
                placeholder="请输入企业评价"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="导师评价" prop="supervisorEvaluation">
              <el-input
                v-model="form.supervisorEvaluation"
                type="textarea"
                :rows="3"
                placeholder="请输入导师评价"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="满意度评分" prop="satisfactionScore">
              <el-rate
                v-model="form.satisfactionScore"
                :max="10"
                show-score
                score-template="{value}分"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            :rows="3"
            placeholder="请输入备注"
          />
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 查看详情对话框 -->
    <el-dialog
      v-model="viewDialogVisible"
      title="实习就业详情"
      width="800px"
    >
      <el-descriptions :column="2" border>
        <el-descriptions-item label="学生姓名">{{ viewData.studentName }}</el-descriptions-item>
        <el-descriptions-item label="学号">{{ viewData.studentId }}</el-descriptions-item>
        <el-descriptions-item label="类型">{{ viewData.type }}</el-descriptions-item>
        <el-descriptions-item label="单位名称">{{ viewData.company }}</el-descriptions-item>
        <el-descriptions-item label="单位类型">{{ viewData.companyType }}</el-descriptions-item>
        <el-descriptions-item label="行业">{{ viewData.industry }}</el-descriptions-item>
        <el-descriptions-item label="职位">{{ viewData.position }}</el-descriptions-item>
        <el-descriptions-item label="部门">{{ viewData.department }}</el-descriptions-item>
        <el-descriptions-item label="工作地点">{{ viewData.workLocation }}</el-descriptions-item>
        <el-descriptions-item label="工作类型">{{ viewData.workType }}</el-descriptions-item>
        <el-descriptions-item label="薪资">{{ viewData.salary }}</el-descriptions-item>
        <el-descriptions-item label="薪资类型">{{ viewData.salaryType }}</el-descriptions-item>
        <el-descriptions-item label="开始时间">{{ formatDate(viewData.startDate) }}</el-descriptions-item>
        <el-descriptions-item label="结束时间">{{ formatDate(viewData.endDate) }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ viewData.status }}</el-descriptions-item>
        <el-descriptions-item label="合同类型">{{ viewData.contractType }}</el-descriptions-item>
        <el-descriptions-item label="审批状态">{{ viewData.approvalStatus }}</el-descriptions-item>
        <el-descriptions-item label="审批备注">{{ viewData.approvalRemark }}</el-descriptions-item>
        <el-descriptions-item label="联系人">{{ viewData.contactPerson }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ viewData.contactPhone }}</el-descriptions-item>
        <el-descriptions-item label="联系邮箱">{{ viewData.contactEmail }}</el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ formatDate(viewData.createTime) }}</el-descriptions-item>
      </el-descriptions>

      <el-divider content-position="left">详细信息</el-divider>
      
      <el-row :gutter="20">
        <el-col :span="12">
          <h4>福利待遇</h4>
          <p>{{ viewData.benefits || '暂无' }}</p>
        </el-col>
        <el-col :span="12">
          <h4>所需技能</h4>
          <p>{{ viewData.skills || '暂无' }}</p>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="24">
          <h4>工作描述</h4>
          <p>{{ viewData.jobDescription || '暂无' }}</p>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="24">
          <h4>备注</h4>
          <p>{{ viewData.remark || '暂无' }}</p>
        </el-col>
      </el-row>
    </el-dialog>

    <!-- 审批对话框 -->
    <el-dialog
      v-model="approvalDialogVisible"
      title="审批实习就业记录"
      width="500px"
    >
      <el-form
        ref="approvalFormRef"
        :model="approvalForm"
        :rules="approvalRules"
        label-width="100px"
      >
        <el-form-item label="审批结果" prop="result">
          <el-radio-group v-model="approvalForm.result">
            <el-radio label="approve">通过</el-radio>
            <el-radio label="reject">拒绝</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="审批备注" prop="remark">
          <el-input
            v-model="approvalForm.remark"
            type="textarea"
            :rows="4"
            placeholder="请输入审批备注"
          />
        </el-form-item>
      </el-form>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="approvalDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleApprovalSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
/* eslint-disable no-undef */
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useStore } from 'vuex'
import { 
  getInternshipList, 
  addInternship, 
  updateInternship, 
  deleteInternship,
  getInternshipById,
  approveInternship,
  rejectInternship,
  getInternshipStatistics
} from '@/api/internship'
import { Briefcase, Calendar, OfficeBuilding, Clock, View, Edit, Check, Delete } from '@element-plus/icons-vue'

const store = useStore()
const loading = ref(false)
const internshipList = ref([])
const selectedInternships = ref([])
const dialogVisible = ref(false)
const viewDialogVisible = ref(false)
const approvalDialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref()
const approvalFormRef = ref()
const viewData = ref({})
const statistics = ref({})

// 搜索表单
const searchForm = reactive({
  studentName: '',
  company: '',
  type: '',
  status: '',
  approvalStatus: ''
})

// 分页
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 表单
const form = reactive({
  id: '',
  userId: '',
  studentId: '',
  studentName: '',
  studentClass: '',
  major: '',
  grade: '',
  type: '',
  category: '',
  company: '',
  companyType: '',
  industry: '',
  companySize: '',
  companyLocation: '',
  position: '',
  department: '',
  workLocation: '',
  workType: '',
  salary: '',
  salaryType: '',
  salaryRange: '',
  startDate: '',
  endDate: '',
  duration: '',
  status: '',
  contractType: '',
  technologyStack: [],
  programmingLanguages: '',
  frameworks: [],
  databases: [],
  cloudPlatforms: [],
  developmentTools: [],
  projectType: '',
  benefits: '',
  jobDescription: '',
  responsibilities: '',
  achievements: '',
  learningOutcomes: '',
  skills: '',
  contactPerson: '',
  contactPhone: '',
  contactEmail: '',
  supervisorName: '',
  supervisorContact: '',
  studentEvaluation: '',
  companyEvaluation: '',
  supervisorEvaluation: '',
  satisfactionScore: 0,
  remark: '',
  attachments: '',
  tags: ''
})

// 审批表单
const approvalForm = reactive({
  id: '',
  result: 'approve',
  remark: ''
})

// 表单验证规则
const rules = {
  studentName: [
    { required: true, message: '请输入学生姓名', trigger: 'blur' }
  ],
  studentId: [
    { required: true, message: '请输入学号', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请选择类型', trigger: 'change' }
  ],
  company: [
    { required: true, message: '请输入单位名称', trigger: 'blur' }
  ],
  position: [
    { required: true, message: '请输入职位', trigger: 'blur' }
  ],
  startDate: [
    { required: true, message: '请选择开始时间', trigger: 'change' }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ]
}

// 审批表单验证规则
const approvalRules = {
  result: [
    { required: true, message: '请选择审批结果', trigger: 'change' }
  ],
  remark: [
    { required: true, message: '请输入审批备注', trigger: 'blur' }
  ]
}

// 获取当前用户信息
const currentUser = computed(() => store.state.user)

// 加载实习就业列表
const loadInternshipList = async () => {
  loading.value = true
  try {
    const res = await getInternshipList()
    if (res.code === 200) {
      internshipList.value = res.data || []
      pagination.total = internshipList.value.length
      // console.log('加载的实习就业列表:', internshipList.value)
      // if (internshipList.value.length > 0) {
      //   // console.log('第一条记录的ID:', internshipList.value[0].id)
      // }
    }
  } catch (error) {
    ElMessage.error('加载实习就业列表失败')
  } finally {
    loading.value = false
  }
}

// 加载统计信息
const loadStatistics = async () => {
  try {
    const res = await getInternshipStatistics()
    if (res.code === 200) {
      statistics.value = res.data || {}
    }
  } catch (error) {
    ElMessage.error('加载统计信息失败')
  }
}

// 搜索
const handleSearch = () => {
  pagination.currentPage = 1
  loadInternshipList()
}

// 重置搜索
const handleReset = () => {
  Object.assign(searchForm, {
    studentName: '',
    company: '',
    type: '',
    status: '',
    approvalStatus: ''
  })
  handleSearch()
}

// 选择变化
const handleSelectionChange = (selection) => {
  selectedInternships.value = selection
}

// 添加记录
const handleAdd = () => {
  dialogTitle.value = '添加实习就业记录'
  Object.assign(form, {
    id: '',
    userId: '',
    studentId: '',
    studentName: '',
    studentClass: '',
    major: '',
    grade: '',
    type: '',
    category: '',
    company: '',
    companyType: '',
    industry: '',
    companySize: '',
    companyLocation: '',
    position: '',
    department: '',
    workLocation: '',
    workType: '',
    salary: '',
    salaryType: '',
    salaryRange: '',
    startDate: '',
    endDate: '',
    duration: '',
    status: '待入职',
    contractType: '',
    technologyStack: [],
    programmingLanguages: '',
    frameworks: [],
    databases: [],
    cloudPlatforms: [],
    developmentTools: [],
    projectType: '',
    benefits: '',
    jobDescription: '',
    responsibilities: '',
    achievements: '',
    learningOutcomes: '',
    skills: '',
    contactPerson: '',
    contactPhone: '',
    contactEmail: '',
    supervisorName: '',
    supervisorContact: '',
    studentEvaluation: '',
    companyEvaluation: '',
    supervisorEvaluation: '',
    satisfactionScore: 0,
    remark: '',
    attachments: '',
    tags: ''
  })
  dialogVisible.value = true
}

// 编辑记录
const handleEdit = (row) => {
  dialogTitle.value = '编辑实习就业记录'
  Object.assign(form, row)
  dialogVisible.value = true
}

// 查看详情
const handleView = async (row) => {
  try {
    // console.log('查看详情，行数据:', row)
    // console.log('ID:', row.id)
    // console.log('_id:', row._id)
    
    // 尝试获取ID，可能是id或_id
    const recordId = row.id || row._id
    // console.log('使用的ID:', recordId)
    
    if (!recordId) {
      ElMessage.error('记录ID不存在')
      return
    }
    
    const res = await getInternshipById(recordId)
    if (res.code === 200) {
      viewData.value = res.data
      viewDialogVisible.value = true
    } else {
      ElMessage.error(res.msg || '获取详情失败')
    }
  } catch (error) {
    console.error('查看详情错误:', error)
    ElMessage.error('获取详情失败')
  }
}

// 审批记录
const handleApprove = (row) => {
  approvalForm.id = row.id
  approvalForm.result = 'approve'
  approvalForm.remark = ''
  approvalDialogVisible.value = true
}

// 删除记录
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除该记录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const res = await deleteInternship(row.id)
    if (res.code === 200) {
      ElMessage.success('删除成功')
      loadInternshipList()
      loadStatistics()
    } else {
      ElMessage.error(res.msg || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 提交表单
const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    
    let res
    if (form.id) {
      res = await updateInternship(form)
    } else {
      res = await addInternship(form)
    }
    
    if (res.code === 200) {
      ElMessage.success(form.id ? '更新成功' : '添加成功')
      dialogVisible.value = false
      loadInternshipList()
      loadStatistics()
    } else {
      ElMessage.error(res.msg || '操作失败')
    }
  } catch (error) {
    ElMessage.error('表单验证失败')
  }
}

// 提交审批
const handleApprovalSubmit = async () => {
  try {
    await approvalFormRef.value.validate()
    
    // console.log('审批表单数据:', approvalForm)
    // console.log('当前用户:', currentUser.value)
    
    let res
    if (approvalForm.result === 'approve') {
      res = await approveInternship(approvalForm.id, approvalForm.remark, currentUser.value.username)
    } else {
      res = await rejectInternship(approvalForm.id, approvalForm.remark, currentUser.value.username)
    }
    
    if (res.code === 200) {
      ElMessage.success('审批成功')
      approvalDialogVisible.value = false
      loadInternshipList()
      loadStatistics()
    } else {
      ElMessage.error(res.msg || '审批失败')
    }
  } catch (error) {
    console.error('审批错误:', error)
    ElMessage.error('审批失败: ' + error.message)
  }
}

// 对话框关闭
const handleDialogClose = () => {
  formRef.value?.resetFields()
}

// 分页大小变化
const handleSizeChange = (size) => {
  pagination.pageSize = size
  loadInternshipList()
}

// 当前页变化
const handleCurrentChange = (page) => {
  pagination.currentPage = page
  loadInternshipList()
}

// 格式化日期
const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString()
}

// 获取状态类型
const getStatusType = (status) => {
  const statusMap = {
    '在岗': 'success',
    '已结束': 'info',
    '待入职': 'warning',
    '已离职': 'danger'
  }
  return statusMap[status] || 'info'
}

// 获取审批状态类型
const getApprovalStatusType = (approvalStatus) => {
  const statusMap = {
    '待审批': 'warning',
    '已通过': 'success',
    '已拒绝': 'danger'
  }
  return statusMap[approvalStatus] || 'info'
}

onMounted(() => {
  loadInternshipList()
  loadStatistics()
})
</script>

<style scoped>
.internship-admin-page {
  padding: 20px;
}

.statistics-cards {
  margin-bottom: 20px;
}

.stat-card {
  display: flex;
  align-items: center;
  padding: 10px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
  font-size: 24px;
  color: white;
}

.stat-icon:not(.internship):not(.employment):not(.pending) {
  background-color: #409eff;
}

.stat-icon.internship {
  background-color: #e6a23c;
}

.stat-icon.employment {
  background-color: #67c23a;
}

.stat-icon.pending {
  background-color: #f56c6c;
}

.stat-content {
  flex: 1;
}

.stat-number {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  color: #666;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-bar {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  text-align: right;
}

.dialog-footer {
  text-align: right;
}

h4 {
  margin: 10px 0 5px 0;
  color: #333;
}

p {
  margin: 0;
  color: #666;
  line-height: 1.5;
}

.action-buttons {
  display: flex;
  gap: 8px;
  justify-content: center;
  align-items: center;
}

.action-buttons .el-button {
  margin: 0;
  transition: all 0.3s ease;
}

.action-buttons .el-button:hover {
  transform: scale(1.1);
}

.action-buttons .el-button:disabled {
  opacity: 0.5;
  cursor: not-allowed;
  transform: none;
}
</style> 