/* eslint-disable vue/multi-word-component-names */
<template>
  <div class="internship">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>实习岗位</span>
              <el-input
                v-model="searchKeyword"
                placeholder="搜索岗位"
                style="width: 200px"
                clearable
                @input="handleSearch"
              >
                <template #prefix>
                  <el-icon><Search /></el-icon>
                </template>
              </el-input>
            </div>
          </template>
          
          <div class="job-list">
            <div
              v-for="job in filteredJobs"
              :key="job.id"
              class="job-item"
            >
              <div class="job-header">
                <h3 class="job-title">{{ job.title }}</h3>
                <el-tag :type="job.type === '实习' ? 'primary' : 'success'">
                  {{ job.type }}
                </el-tag>
              </div>
              <div class="job-company">{{ job.company }}</div>
              <div class="job-info">
                <span class="job-location">{{ job.location }}</span>
                <span class="job-salary">{{ job.salary }}</span>
              </div>
              <div class="job-description">{{ job.description }}</div>
              <div class="job-footer">
                <div class="job-tags">
                  <el-tag
                    v-for="tag in job.tags"
                    :key="tag"
                    size="small"
                    style="margin-right: 8px"
                  >
                    {{ tag }}
                  </el-tag>
                </div>
                <el-button type="primary" @click="applyJob(job)">
                  申请
                </el-button>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>我的申请</span>
            </div>
          </template>
          
          <div class="application-list">
            <div
              v-for="application in applications"
              :key="application.id"
              class="application-item"
            >
              <div class="application-header">
                <span class="application-title">{{ application.jobTitle }}</span>
                <el-tag :type="getApplicationStatusType(application.status)" size="small">
                  {{ application.status }}
                </el-tag>
              </div>
              <div class="application-company">{{ application.company }}</div>
              <div class="application-time">申请时间：{{ application.applyTime }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 申请对话框 -->
    <el-dialog v-model="showApplyDialog" title="申请岗位" width="500px">
      <el-form :model="applyForm" :rules="applyRules" ref="applyFormRef" label-width="100px">
        <el-form-item label="岗位名称">
          <el-input v-model="applyForm.jobTitle" disabled />
        </el-form-item>
        <el-form-item label="公司名称">
          <el-input v-model="applyForm.company" disabled />
        </el-form-item>
        <el-form-item label="个人简介" prop="introduction">
          <el-input
            v-model="applyForm.introduction"
            type="textarea"
            :rows="4"
            placeholder="请简要介绍自己的技能和经验"
          />
        </el-form-item>
        <el-form-item label="期望薪资" prop="expectedSalary">
          <el-input v-model="applyForm.expectedSalary" placeholder="请输入期望薪资" />
        </el-form-item>
        <el-form-item label="可到岗时间" prop="availableTime">
          <el-date-picker
            v-model="applyForm.availableTime"
            type="date"
            placeholder="选择可到岗时间"
            style="width: 100%"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showApplyDialog = false">取消</el-button>
          <el-button type="primary" @click="submitApplication">提交申请</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Search } from '@element-plus/icons-vue'
import { getJobList, applyForJob, getApplications } from '@/api/internship'

const showApplyDialog = ref(false)
const applyFormRef = ref()
const searchKeyword = ref('')

const applyForm = reactive({
  jobTitle: '',
  company: '',
  introduction: '',
  expectedSalary: '',
  availableTime: ''
})

const applyRules = {
  introduction: [
    { required: true, message: '请输入个人简介', trigger: 'blur' }
  ],
  expectedSalary: [
    { required: true, message: '请输入期望薪资', trigger: 'blur' }
  ],
  availableTime: [
    { required: true, message: '请选择可到岗时间', trigger: 'change' }
  ]
}

const jobList = ref([])
const applications = ref([])

const filteredJobs = computed(() => {
  if (!searchKeyword.value) return jobList.value
  return jobList.value.filter(job => 
    job.title.includes(searchKeyword.value) ||
    job.company.includes(searchKeyword.value) ||
    job.description.includes(searchKeyword.value)
  )
})

const loadData = async () => {
  try {
    const [jobsData, applicationsData] = await Promise.all([
      getJobList(),
      getApplications()
    ])
    jobList.value = jobsData
    applications.value = applicationsData
  } catch (error) {
    console.error('加载数据失败:', error)
    // 使用模拟数据
    jobList.value = [
      {
        id: 1,
        title: '前端开发实习生',
        company: '腾讯科技',
        location: '深圳',
        salary: '4000-6000元/月',
        type: '实习',
        description: '负责公司产品的前端开发工作，参与产品设计和开发流程。',
        tags: ['Vue.js', 'JavaScript', 'HTML/CSS']
      },
      {
        id: 2,
        title: 'Java开发工程师',
        company: '阿里巴巴',
        location: '杭州',
        salary: '8000-12000元/月',
        type: '全职',
        description: '负责企业级应用系统的开发，参与系统架构设计。',
        tags: ['Java', 'Spring Boot', 'MySQL']
      }
    ]
    applications.value = [
      {
        id: 1,
        jobTitle: '前端开发实习生',
        company: '腾讯科技',
        status: '待处理',
        applyTime: '2024-01-15'
      }
    ]
  }
}

const applyJob = (job) => {
  applyForm.jobTitle = job.title
  applyForm.company = job.company
  showApplyDialog.value = true
}

const submitApplication = async () => {
  try {
    await applyFormRef.value.validate()
    await applyForJob(applyForm)
    ElMessage.success('申请提交成功')
    showApplyDialog.value = false
    Object.assign(applyForm, {
      jobTitle: '',
      company: '',
      introduction: '',
      expectedSalary: '',
      availableTime: ''
    })
    loadData()
  } catch (error) {
    console.error('申请失败:', error)
    ElMessage.error('申请失败')
  }
}

const handleSearch = () => {
  // 搜索功能已通过计算属性实现
}

const getApplicationStatusType = (status) => {
  const statusMap = {
    '待处理': 'warning',
    '已通过': 'success',
    '已拒绝': 'danger'
  }
  return statusMap[status] || 'info'
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.internship {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.job-list {
  max-height: 600px;
  overflow-y: auto;
}

.job-item {
  padding: 16px;
  border: 1px solid #e6e6e6;
  border-radius: 8px;
  margin-bottom: 16px;
}

.job-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.job-title {
  margin: 0;
  color: #303133;
  font-size: 16px;
}

.job-company {
  color: #606266;
  margin-bottom: 8px;
}

.job-info {
  display: flex;
  gap: 16px;
  margin-bottom: 8px;
}

.job-location,
.job-salary {
  color: #909399;
  font-size: 14px;
}

.job-description {
  color: #606266;
  margin-bottom: 12px;
  line-height: 1.5;
}

.job-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.application-list {
  max-height: 400px;
  overflow-y: auto;
}

.application-item {
  padding: 12px;
  border: 1px solid #e6e6e6;
  border-radius: 4px;
  margin-bottom: 12px;
}

.application-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.application-title {
  font-weight: bold;
  color: #303133;
}

.application-company {
  color: #606266;
  margin-bottom: 4px;
}

.application-time {
  font-size: 12px;
  color: #909399;
}
</style> 