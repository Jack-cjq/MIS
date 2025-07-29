/* eslint-disable vue/multi-word-component-names */
<template>
  <div class="alumni">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>校友信息</span>
              <div class="search-group">
                <el-input
                  v-model="searchKeyword"
                  placeholder="搜索校友姓名或公司"
                  style="width: 200px; margin-right: 10px"
                  clearable
                  @input="handleSearch"
                >
                  <template #prefix>
                    <el-icon><Search /></el-icon>
                  </template>
                </el-input>
                <el-select v-model="filterYear" placeholder="毕业年份" style="width: 120px">
                  <el-option label="全部年份" value="" />
                  <el-option label="2023年" value="2023" />
                  <el-option label="2022年" value="2022" />
                  <el-option label="2021年" value="2021" />
                  <el-option label="2020年" value="2020" />
                </el-select>
              </div>
            </div>
          </template>
          
          <div class="alumni-list">
            <div
              v-for="alumni in filteredAlumni"
              :key="alumni.id"
              class="alumni-item"
            >
              <div class="alumni-avatar">
                <el-avatar :size="60" :src="alumni.avatar">
                  {{ alumni.name.charAt(0) }}
                </el-avatar>
              </div>
              <div class="alumni-info">
                <div class="alumni-header">
                  <h3 class="alumni-name">{{ alumni.name }}</h3>
                  <el-tag :type="alumni.gender === '男' ? 'primary' : 'success'">
                    {{ alumni.gender }}
                  </el-tag>
                </div>
                <div class="alumni-details">
                  <div class="detail-item">
                    <span class="label">毕业年份：</span>
                    <span class="value">{{ alumni.graduationYear }}年</span>
                  </div>
                  <div class="detail-item">
                    <span class="label">专业：</span>
                    <span class="value">{{ alumni.major }}</span>
                  </div>
                  <div class="detail-item">
                    <span class="label">现工作单位：</span>
                    <span class="value">{{ alumni.company }}</span>
                  </div>
                  <div class="detail-item">
                    <span class="label">职位：</span>
                    <span class="value">{{ alumni.position }}</span>
                  </div>
                </div>
                <div class="alumni-contact">
                  <el-button type="primary" size="small" @click="contactAlumni(alumni)">
                    联系校友
                  </el-button>
                  <el-button size="small" @click="viewProfile(alumni)">
                    查看详情
                  </el-button>
                </div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>校友活动</span>
            </div>
          </template>
          
          <div class="activity-list">
            <div
              v-for="activity in activities"
              :key="activity.id"
              class="activity-item"
            >
              <div class="activity-header">
                <span class="activity-title">{{ activity.title }}</span>
                <el-tag :type="getActivityStatusType(activity.status)" size="small">
                  {{ activity.status }}
                </el-tag>
              </div>
              <div class="activity-time">{{ activity.time }}</div>
              <div class="activity-location">{{ activity.location }}</div>
              <div class="activity-description">{{ activity.description }}</div>
              <el-button 
                type="primary" 
                size="small" 
                @click="joinActivity(activity)"
                :disabled="activity.status !== '报名中'"
              >
                {{ activity.status === '报名中' ? '立即报名' : '活动结束' }}
              </el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 联系校友对话框 -->
    <el-dialog v-model="showContactDialog" title="联系校友" width="500px">
      <el-form :model="contactForm" :rules="contactRules" ref="contactFormRef" label-width="100px">
        <el-form-item label="校友姓名">
          <el-input v-model="contactForm.alumniName" disabled />
        </el-form-item>
        <el-form-item label="联系方式" prop="contactMethod">
          <el-radio-group v-model="contactForm.contactMethod">
            <el-radio label="邮箱">邮箱</el-radio>
            <el-radio label="微信">微信</el-radio>
            <el-radio label="电话">电话</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="联系内容" prop="content">
          <el-input
            v-model="contactForm.content"
            type="textarea"
            :rows="4"
            placeholder="请输入联系内容"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showContactDialog = false">取消</el-button>
          <el-button type="primary" @click="submitContact">发送联系</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 校友详情对话框 -->
    <el-dialog v-model="showProfileDialog" title="校友详情" width="600px">
      <div v-if="selectedAlumni" class="alumni-profile">
        <div class="profile-header">
          <el-avatar :size="80" :src="selectedAlumni.avatar">
            {{ selectedAlumni.name.charAt(0) }}
          </el-avatar>
          <div class="profile-info">
            <h2>{{ selectedAlumni.name }}</h2>
            <p>{{ selectedAlumni.position }} @ {{ selectedAlumni.company }}</p>
          </div>
        </div>
        <el-divider />
        <div class="profile-details">
          <div class="detail-row">
            <span class="detail-label">毕业年份：</span>
            <span class="detail-value">{{ selectedAlumni.graduationYear }}年</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">专业：</span>
            <span class="detail-value">{{ selectedAlumni.major }}</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">工作单位：</span>
            <span class="detail-value">{{ selectedAlumni.company }}</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">职位：</span>
            <span class="detail-value">{{ selectedAlumni.position }}</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">工作地点：</span>
            <span class="detail-value">{{ selectedAlumni.location }}</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">个人简介：</span>
            <span class="detail-value">{{ selectedAlumni.bio }}</span>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Search } from '@element-plus/icons-vue'
import { getAlumniList, getAlumniActivities, joinAlumniActivity } from '@/api/alumni'

const showContactDialog = ref(false)
const showProfileDialog = ref(false)
const contactFormRef = ref()
const searchKeyword = ref('')
const filterYear = ref('')
const selectedAlumni = ref(null)

const contactForm = reactive({
  alumniName: '',
  contactMethod: '邮箱',
  content: ''
})

const contactRules = {
  contactMethod: [
    { required: true, message: '请选择联系方式', trigger: 'change' }
  ],
  content: [
    { required: true, message: '请输入联系内容', trigger: 'blur' }
  ]
}

const alumniList = ref([])
const activities = ref([])

const filteredAlumni = computed(() => {
  let filtered = alumniList.value
  
  if (searchKeyword.value) {
    filtered = filtered.filter(alumni => 
      alumni.name.includes(searchKeyword.value) ||
      alumni.company.includes(searchKeyword.value)
    )
  }
  
  if (filterYear.value) {
    filtered = filtered.filter(alumni => 
      alumni.graduationYear.toString() === filterYear.value
    )
  }
  
  return filtered
})

const loadData = async () => {
  try {
    const [alumniData, activitiesData] = await Promise.all([
      getAlumniList(),
      getAlumniActivities()
    ])
    alumniList.value = alumniData
    activities.value = activitiesData
  } catch (error) {
    console.error('加载数据失败:', error)
    // 使用模拟数据
    alumniList.value = [
      {
        id: 1,
        name: '张三',
        gender: '男',
        graduationYear: 2023,
        major: '计算机科学与技术',
        company: '腾讯科技',
        position: '前端开发工程师',
        location: '深圳',
        avatar: '',
        bio: '热爱编程，专注于前端技术开发，有丰富的Vue.js和React开发经验。'
      },
      {
        id: 2,
        name: '李四',
        gender: '女',
        graduationYear: 2022,
        major: '软件工程',
        company: '阿里巴巴',
        position: '产品经理',
        location: '杭州',
        avatar: '',
        bio: '专注于用户体验设计，有多个成功产品的设计经验。'
      }
    ]
    activities.value = [
      {
        id: 1,
        title: '校友返校日',
        time: '2024-03-15',
        location: '学校大礼堂',
        description: '欢迎各位校友返校，共叙同窗情谊',
        status: '报名中'
      },
      {
        id: 2,
        title: '职业发展分享会',
        time: '2024-04-01',
        location: '线上会议',
        description: '邀请优秀校友分享职业发展经验',
        status: '报名中'
      }
    ]
  }
}

const contactAlumni = (alumni) => {
  contactForm.alumniName = alumni.name
  showContactDialog.value = true
}

const viewProfile = (alumni) => {
  selectedAlumni.value = alumni
  showProfileDialog.value = true
}

const submitContact = async () => {
  try {
    await contactFormRef.value.validate()
    // 这里应该调用实际的API
    ElMessage.success('联系请求已发送')
    showContactDialog.value = false
    Object.assign(contactForm, {
      alumniName: '',
      contactMethod: '邮箱',
      content: ''
    })
  } catch (error) {
    console.error('发送失败:', error)
    ElMessage.error('发送失败')
  }
}

const joinActivity = async (activity) => {
  try {
    await joinAlumniActivity(activity.id)
    ElMessage.success('报名成功')
    loadData()
  } catch (error) {
    console.error('报名失败:', error)
    ElMessage.error('报名失败')
  }
}

const handleSearch = () => {
  // 搜索功能已通过计算属性实现
}

const getActivityStatusType = (status) => {
  const statusMap = {
    '报名中': 'success',
    '进行中': 'primary',
    '已结束': 'info'
  }
  return statusMap[status] || 'info'
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.alumni {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-group {
  display: flex;
  align-items: center;
}

.alumni-list {
  max-height: 600px;
  overflow-y: auto;
}

.alumni-item {
  display: flex;
  padding: 16px;
  border: 1px solid #e6e6e6;
  border-radius: 8px;
  margin-bottom: 16px;
}

.alumni-avatar {
  margin-right: 16px;
}

.alumni-info {
  flex: 1;
}

.alumni-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.alumni-name {
  margin: 0;
  color: #303133;
  font-size: 16px;
}

.alumni-details {
  margin-bottom: 12px;
}

.detail-item {
  display: flex;
  margin-bottom: 4px;
}

.label {
  color: #909399;
  width: 80px;
  flex-shrink: 0;
}

.value {
  color: #606266;
}

.alumni-contact {
  display: flex;
  gap: 8px;
}

.activity-list {
  max-height: 400px;
  overflow-y: auto;
}

.activity-item {
  padding: 12px;
  border: 1px solid #e6e6e6;
  border-radius: 4px;
  margin-bottom: 12px;
}

.activity-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.activity-title {
  font-weight: bold;
  color: #303133;
}

.activity-time {
  color: #606266;
  font-size: 14px;
  margin-bottom: 4px;
}

.activity-location {
  color: #909399;
  font-size: 14px;
  margin-bottom: 8px;
}

.activity-description {
  color: #606266;
  margin-bottom: 12px;
  line-height: 1.5;
}

.alumni-profile {
  padding: 20px;
}

.profile-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.profile-info {
  margin-left: 20px;
}

.profile-info h2 {
  margin: 0 0 8px 0;
  color: #303133;
}

.profile-info p {
  margin: 0;
  color: #606266;
}

.profile-details {
  padding: 20px 0;
}

.detail-row {
  display: flex;
  margin-bottom: 12px;
}

.detail-label {
  color: #909399;
  width: 100px;
  flex-shrink: 0;
}

.detail-value {
  color: #606266;
  flex: 1;
}
</style> 