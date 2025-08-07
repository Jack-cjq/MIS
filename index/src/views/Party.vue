<template>
  <div class="party-layout">
    <div class="top-bar">
      <el-button type="primary" size="large" class="stats-btn" @click="showStatsDialog = true">党内统计</el-button>
    </div>
    <el-row :gutter="24">
      <!-- 左侧：党员信息 -->
      <el-col :span="10">
        <el-card class="member-info-card">
          <div class="avatar-box">
            <div class="photo-container">
              <img v-if="memberInfo.avatar" :src="memberInfo.avatar" class="member-photo" />
              <el-empty v-else description="暂无照片" />
              <div class="upload-overlay" @click="triggerPhotoUpload">
                <el-icon><Upload /></el-icon>
                <span>上传证件照</span>
              </div>
            </div>
            <input
              type="file"
              ref="photoInput"
              style="display: none"
              accept="image/*"
              @change="handlePhotoChange"
            />
          </div>
          <el-descriptions column="1" border>
            <el-descriptions-item label="学号">{{ memberInfo.studentId }}</el-descriptions-item>
            <el-descriptions-item label="姓名">{{ memberInfo.name }}</el-descriptions-item>
            <el-descriptions-item label="性别">{{ memberInfo.gender }}</el-descriptions-item>
            <el-descriptions-item label="民族">{{ memberInfo.nation }}</el-descriptions-item>
            <el-descriptions-item label="出生日期">{{ memberInfo.birth }}</el-descriptions-item>
            <el-descriptions-item label="籍贯">{{ memberInfo.nativePlace }}</el-descriptions-item>
            <el-descriptions-item label="所在班级">{{ memberInfo.className }}</el-descriptions-item>
            <el-descriptions-item label="所在支部">{{ memberInfo.branch }}</el-descriptions-item>
            <el-descriptions-item label="入党时间">{{ memberInfo.joinDate }}</el-descriptions-item>
            <el-descriptions-item label="是否转正">{{ memberInfo.isRegular }}</el-descriptions-item>
            <el-descriptions-item label="志愿书编号">{{ memberInfo.applicationNo }}</el-descriptions-item>
            <el-descriptions-item label="党内职务">{{ memberInfo.position }}</el-descriptions-item>
            <el-descriptions-item label="奖惩情况">{{ memberInfo.rewardsPunishments }}</el-descriptions-item>
            <el-descriptions-item label="审核状态">
              <el-tag :type="getAuditStatusType(memberInfo.auditStatus)">
                {{ memberInfo.auditStatus || '未提交' }}
              </el-tag>
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
      <!-- 右侧：学习情况 -->
      <el-col :span="14">
        <el-row :gutter="16">
          <!-- 思想汇报 -->
          <el-col :span="12">
            <el-card class="info-card mb-16" header="思想汇报">
              <el-form :model="reportForm" label-width="100px">
                <el-form-item label="思想汇报标题">
                  <el-input v-model="reportForm.title" placeholder="请输入思想汇报标题" />
                </el-form-item>
                <el-form-item label="思想汇报内容">
                  <el-input v-model="reportForm.content" type="textarea" :rows="3" placeholder="请输入思想汇报内容" />
                </el-form-item>
                <el-form-item label="汇报日期">
                  <el-date-picker v-model="reportForm.reportDate" type="date" placeholder="选择日期" style="width: 100%" />
                </el-form-item>
                <el-form-item label="汇报类型">
                  <el-select v-model="reportForm.reportType" placeholder="请选择汇报类型" style="width: 100%">
                    <el-option label="定期汇报" value="定期" />
                    <el-option label="专项汇报" value="专项" />
                  </el-select>
                </el-form-item>
                <el-form-item label="备注">
                  <el-input v-model="reportForm.remark" type="textarea" :rows="2" placeholder="请输入备注" />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitReport" :loading="reportLoading">提交</el-button>
                </el-form-item>
              </el-form>
            </el-card>
          </el-col>
          <!-- 思政推文 -->
          <el-col :span="12">
            <el-card class="info-card mb-16" header="思政推文">
              <el-form :model="tweetForm" label-width="100px">
                <el-form-item label="推文标题">
                  <el-input v-model="tweetForm.title" placeholder="请输入推文标题" />
                </el-form-item>
                <el-form-item label="推文内容">
                  <el-input v-model="tweetForm.content" type="textarea" :rows="3" placeholder="请输入推文内容" />
                </el-form-item>
                <el-form-item label="推文日期">
                  <el-date-picker v-model="tweetForm.tweetDate" type="date" placeholder="选择日期" style="width: 100%" />
                </el-form-item>
                <el-form-item label="动态类型">
                  <el-select v-model="tweetForm.tweetType" placeholder="请选择动态类型" style="width: 100%">
                    <el-option label="理论学习" value="理论学习" />
                    <el-option label="实践活动" value="实践活动" />
                    <el-option label="心得体会" value="心得体会" />
                  </el-select>
                </el-form-item>
                <el-form-item label="备注">
                  <el-input v-model="tweetForm.remark" type="textarea" :rows="2" placeholder="请输入备注" />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitTweet" :loading="tweetLoading">提交</el-button>
                </el-form-item>
              </el-form>
            </el-card>
          </el-col>
          <!-- 微党课 -->
          <el-col :span="12">
            <el-card class="info-card mb-16" header="微党课">
              <el-form :model="courseForm" label-width="100px">
                <el-form-item label="课程标题">
                  <el-input v-model="courseForm.title" placeholder="请输入课程标题" />
                </el-form-item>
                <el-form-item label="课程描述">
                  <el-input v-model="courseForm.description" type="textarea" :rows="2" placeholder="请输入课程描述" />
                </el-form-item>
                <el-form-item label="课程日期">
                  <el-date-picker v-model="courseForm.courseDate" type="date" placeholder="选择日期" style="width: 100%" />
                </el-form-item>
                <el-form-item label="课程类型">
                  <el-select v-model="courseForm.courseType" placeholder="请选择课程类型" style="width: 100%">
                    <el-option label="理论课" value="理论课" />
                    <el-option label="实践课" value="实践课" />
                    <el-option label="讨论课" value="讨论课" />
                  </el-select>
                </el-form-item>
                <el-form-item label="授课教师">
                  <el-input v-model="courseForm.instructor" placeholder="请输入授课教师" />
                </el-form-item>
                <el-form-item label="上课地点">
                  <el-input v-model="courseForm.location" placeholder="请输入上课地点" />
                </el-form-item>
                <el-form-item label="课程时长">
                  <el-input v-model="courseForm.duration" placeholder="请输入课程时长（如：2小时）" />
                </el-form-item>
                <el-form-item label="课程材料">
                  <el-input v-model="courseForm.materials" type="textarea" :rows="2" placeholder="请输入课程材料" />
                </el-form-item>
                <el-form-item label="参与人员">
                  <el-input v-model="courseForm.participants" type="textarea" :rows="2" placeholder="请输入参与人员" />
                </el-form-item>
                <el-form-item label="备注">
                  <el-input v-model="courseForm.remark" type="textarea" :rows="2" placeholder="请输入备注" />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitCourse" :loading="courseLoading">提交</el-button>
                </el-form-item>
              </el-form>
            </el-card>
          </el-col>
          <!-- 志愿服务 -->
          <el-col :span="12">
            <el-card class="info-card" header="志愿服务">
              <el-form :model="serviceForm" label-width="100px">
                <el-form-item label="服务名称">
                  <el-input v-model="serviceForm.serviceName" placeholder="请输入服务名称" />
                </el-form-item>
                <el-form-item label="服务描述">
                  <el-input v-model="serviceForm.serviceDescription" type="textarea" :rows="2" placeholder="请输入服务描述" />
                </el-form-item>
                <el-form-item label="服务日期">
                  <el-date-picker v-model="serviceForm.serviceDate" type="date" placeholder="选择日期" style="width: 100%" />
                </el-form-item>
                <el-form-item label="服务类型">
                  <el-select v-model="serviceForm.serviceType" placeholder="请选择服务类型" style="width: 100%">
                    <el-option label="社区服务" value="社区服务" />
                    <el-option label="环保服务" value="环保服务" />
                    <el-option label="教育服务" value="教育服务" />
                    <el-option label="医疗健康" value="医疗健康" />
                    <el-option label="文化服务" value="文化服务" />
                  </el-select>
                </el-form-item>
                <el-form-item label="服务时长">
                  <el-input v-model="serviceForm.serviceHours" placeholder="请输入服务时长（如：2小时）" />
                </el-form-item>
                <el-form-item label="服务地点">
                  <el-input v-model="serviceForm.serviceLocation" placeholder="请输入服务地点" />
                </el-form-item>
                <el-form-item label="服务组织">
                  <el-input v-model="serviceForm.organization" placeholder="请输入服务组织" />
                </el-form-item>
                <el-form-item label="联系人">
                  <el-input v-model="serviceForm.contactPerson" placeholder="请输入联系人" />
                </el-form-item>
                <el-form-item label="联系电话">
                  <el-input v-model="serviceForm.contactPhone" placeholder="请输入联系电话" />
                </el-form-item>
                <el-form-item label="备注">
                  <el-input v-model="serviceForm.remark" type="textarea" :rows="2" placeholder="请输入备注" />
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitService" :loading="serviceLoading">提交</el-button>
                </el-form-item>
              </el-form>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-dialog v-model="previewVisible" title="图片预览" width="400px">
      <img :src="previewImage" alt="预览" style="width: 100%" />
    </el-dialog>
    <el-dialog v-model="showStatsDialog" title="党内统计" width="600px">
      <el-form :model="statsForm" label-width="110px">
        <el-form-item label="学号">
          <el-input v-model="statsForm.studentId" />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="statsForm.name" />
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="statsForm.gender" />
        </el-form-item>
        <el-form-item label="民族">
          <el-input v-model="statsForm.nation" />
        </el-form-item>
        <el-form-item label="出生日期">
          <el-date-picker v-model="statsForm.birth" type="date" placeholder="选择日期" style="width: 100%" />
        </el-form-item>
        <el-form-item label="籍贯">
          <el-input v-model="statsForm.nativePlace" />
        </el-form-item>
        <el-form-item label="学生类别">
          <el-input v-model="statsForm.studentType" />
        </el-form-item>
        <el-form-item label="已取得学历">
          <el-input v-model="statsForm.education" />
        </el-form-item>
        <el-form-item label="所在年级">
          <el-input v-model="statsForm.grade" />
        </el-form-item>
        <el-form-item label="专业班级">
          <el-input v-model="statsForm.class" />
        </el-form-item>
        <el-form-item label="所在支部">
          <el-input v-model="statsForm.branch" />
        </el-form-item>
        <el-form-item label="入党时间">
          <el-date-picker v-model="statsForm.joinDate" type="date" placeholder="选择日期" style="width: 100%" />
        </el-form-item>
        <el-form-item label="是否转正">
          <el-input v-model="statsForm.isRegular" />
        </el-form-item>
        <el-form-item label="党内外奖惩情况">
          <el-input v-model="statsForm.rewardsPunishments" />
        </el-form-item>
        <el-form-item label="党内职务">
          <el-input v-model="statsForm.position" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="statsForm.remark" type="textarea" :rows="2" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="showStatsDialog = false">取消</el-button>
        <el-button type="primary" @click="submitStats">提交</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, defineComponent, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus, Upload } from '@element-plus/icons-vue'
import { partyMemberApi, thoughtReportApi, partyCourseApi, volunteerServiceApi } from '@/api/party'
import { useStore } from 'vuex'

// 定义组件名称
defineComponent({
  name: 'PartyView'
})

// 党员信息
const memberInfo = reactive({
  avatar: '',
  studentId: '',
  name: '',
  gender: '',
  nation: '',
  birth: '',
  nativePlace: '',
  className: '',
  branch: '',
  joinDate: '',
  isRegular: '',
  applicationNo: '',
  position: '',
  rewardsPunishments: '',
  auditStatus: ''
})

// 表单数据
const reportForm = reactive({
  title: '',
  content: '',
  reportDate: null,
  reportType: '',
  remark: ''
})

const tweetForm = reactive({
  title: '',
  content: '',
  tweetDate: null,
  tweetType: '',
  remark: ''
})

const courseForm = reactive({
  title: '',
  description: '',
  courseDate: null,
  courseType: '',
  instructor: '',
  location: '',
  duration: '',
  materials: '',
  participants: '',
  remark: ''
})

const serviceForm = reactive({
  serviceName: '',
  serviceDescription: '',
  serviceDate: null,
  serviceType: '',
  serviceHours: '',
  serviceLocation: '',
  organization: '',
  contactPerson: '',
  contactPhone: '',
  remark: ''
})

// 加载状态
const reportLoading = ref(false)
const tweetLoading = ref(false)
const courseLoading = ref(false)
const serviceLoading = ref(false)

// 图片预览
const previewVisible = ref(false)
const previewImage = ref('')

// 获取审核状态类型
const getAuditStatusType = (status) => {
  switch (status) {
    case '待审核':
      return 'warning'
    case '通过':
      return 'success'
    case '拒绝':
      return 'danger'
    default:
      return 'info'
  }
}

// 获取党员信息
const getMemberInfo = async () => {
  try {
    // 从localStorage获取当前用户信息
    const token = localStorage.getItem('token')
    if (!token) {
      ElMessage.warning('请先登录')
      return
    }

    // 从store获取当前用户信息
    const store = useStore()
    const currentUser = store.state.user
    
    if (!currentUser || !currentUser.studentId) {
      ElMessage.warning('无法获取用户信息，请重新登录')
      return
    }

    const studentId = currentUser.studentId
    console.log('正在获取学生ID为', studentId, '的党员信息...')
    const response = await partyMemberApi.getStudentPartyMembers(studentId)
    
    if (response.data && response.data.length > 0) {
      const member = response.data[0]
      Object.assign(memberInfo, member)
      console.log('成功获取党员信息:', member)
    } else {
      console.log('该学生暂无党员信息，可能需要先添加党员信息')
      ElMessage.info('您暂无党员信息，请联系管理员添加')
    }
  } catch (error) {
    console.error('获取党员信息失败:', error)
    if (error.response && error.response.status === 401) {
      ElMessage.error('登录已过期，请重新登录')
      // 可以在这里跳转到登录页
    } else if (error.response && error.response.status === 404) {
      ElMessage.info('您暂无党员信息，请联系管理员添加')
    } else {
      ElMessage.error('获取党员信息失败，请稍后重试')
    }
  }
}

// 提交处理函数
const submitReport = async () => {
  if (!reportForm.title || !reportForm.content || !reportForm.reportDate || !reportForm.reportType) {
    ElMessage.warning('请填写完整的思想汇报信息')
    return
  }
  
  reportLoading.value = true
  try {
    const data = {
      title: reportForm.title,
      content: reportForm.content,
      reportDate: reportForm.reportDate,
      reportType: reportForm.reportType,
      remark: reportForm.remark
    }
    await thoughtReportApi.addThoughtReport(data)
    ElMessage.success('思想汇报已提交')
    // 清空表单
    Object.assign(reportForm, {
      title: '',
      content: '',
      reportDate: null,
      reportType: '',
      remark: ''
    })
  } catch (error) {
    console.error('提交思想汇报失败:', error)
    ElMessage.error('提交失败')
  } finally {
    reportLoading.value = false
  }
}

const submitTweet = async () => {
  if (!tweetForm.title || !tweetForm.content || !tweetForm.tweetDate || !tweetForm.tweetType) {
    ElMessage.warning('请填写完整的思政推文信息')
    return
  }
  
  tweetLoading.value = true
  try {
    const data = {
      title: tweetForm.title,
      content: tweetForm.content,
      tweetDate: tweetForm.tweetDate,
      tweetType: tweetForm.tweetType,
      remark: tweetForm.remark
    }
    await thoughtReportApi.addThoughtReport(data)
    ElMessage.success('思政推文已提交')
    // 清空表单
    Object.assign(tweetForm, {
      title: '',
      content: '',
      tweetDate: null,
      tweetType: '',
      remark: ''
    })
  } catch (error) {
    console.error('提交思政推文失败:', error)
    ElMessage.error('提交失败')
  } finally {
    tweetLoading.value = false
  }
}

const submitCourse = async () => {
  if (!courseForm.title || !courseForm.description || !courseForm.courseDate || !courseForm.courseType) {
    ElMessage.warning('请填写完整的党课信息')
    return
  }
  
  courseLoading.value = true
  try {
    const data = {
      title: courseForm.title,
      description: courseForm.description,
      courseDate: courseForm.courseDate,
      courseType: courseForm.courseType,
      instructor: courseForm.instructor,
      location: courseForm.location,
      duration: courseForm.duration,
      materials: courseForm.materials,
      participants: courseForm.participants,
      remark: courseForm.remark
    }
    await partyCourseApi.addPartyCourse(data)
    ElMessage.success('微党课已提交')
    // 清空表单
    Object.assign(courseForm, {
      title: '',
      description: '',
      courseDate: null,
      courseType: '',
      instructor: '',
      location: '',
      duration: '',
      materials: '',
      participants: '',
      remark: ''
    })
  } catch (error) {
    console.error('提交微党课失败:', error)
    ElMessage.error('提交失败')
  } finally {
    courseLoading.value = false
  }
}

const submitService = async () => {
  if (!serviceForm.serviceName || !serviceForm.serviceDescription || !serviceForm.serviceDate || !serviceForm.serviceType) {
    ElMessage.warning('请填写完整的志愿服务信息')
    return
  }
  
  serviceLoading.value = true
  try {
    const data = {
      serviceName: serviceForm.serviceName,
      serviceDescription: serviceForm.serviceDescription,
      serviceDate: serviceForm.serviceDate,
      serviceType: serviceForm.serviceType,
      serviceHours: serviceForm.serviceHours,
      serviceLocation: serviceForm.serviceLocation,
      organization: serviceForm.organization,
      contactPerson: serviceForm.contactPerson,
      contactPhone: serviceForm.contactPhone,
      remark: serviceForm.remark
    }
    await volunteerServiceApi.addVolunteerService(data)
    ElMessage.success('志愿服务已提交')
    // 清空表单
    Object.assign(serviceForm, {
      serviceName: '',
      serviceDescription: '',
      serviceDate: null,
      serviceType: '',
      serviceHours: '',
      serviceLocation: '',
      organization: '',
      contactPerson: '',
      contactPhone: '',
      remark: ''
    })
  } catch (error) {
    console.error('提交志愿服务失败:', error)
    ElMessage.error('提交失败')
  } finally {
    serviceLoading.value = false
  }
}

const photoInput = ref(null)

const triggerPhotoUpload = () => {
  photoInput.value.click()
}

const handlePhotoChange = (event) => {
  const file = event.target.files[0]
  if (file) {
    if (file.size > 2 * 1024 * 1024) {
      ElMessage.warning('照片大小不能超过2MB')
      return
    }
    
    const reader = new FileReader()
    reader.onload = (e) => {
      memberInfo.avatar = e.target.result
      ElMessage.success('照片上传成功')
    }
    reader.readAsDataURL(file)
  }
}

const showStatsDialog = ref(false)
const statsForm = reactive({
  studentId: '',
  name: '',
  gender: '',
  nation: '',
  birth: '',
  nativePlace: '',
  studentType: '',
  education: '',
  grade: '',
  class: '',
  branch: '',
  joinDate: '',
  isRegular: '',
  rewardsPunishments: '',
  position: '',
  remark: ''
})

const submitStats = () => {
  ElMessage.success('党内统计信息已提交（模拟）')
  showStatsDialog.value = false
}

// 组件挂载时获取党员信息
onMounted(() => {
  getMemberInfo()
})
</script>

<style scoped>
.party-layout {
  padding: 24px;
}
.member-info-card {
  height: 856px; /* 420px * 2 + 16px(间距) = 右侧两行卡片的总高度 */
  display: flex;
  flex-direction: column;
}
.member-info-card :deep(.el-card__body) {
  flex: 1;
  overflow-y: auto;
  background-color: #fff;
}
.member-info-card :deep(.el-descriptions__body) {
  background-color: #fff;
}
.member-info-card :deep(.el-descriptions__label) {
  width: 100px;
  font-weight: bold;
}
.avatar-box {
  display: flex;
  justify-content: center;
  margin: 16px 0;
}
.photo-container {
  position: relative;
  width: 240px;
  height: 320px;
  border: 1px dashed #d9d9d9;
  border-radius: 4px;
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: #fafafa;
}

.member-photo {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.upload-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: white;
  opacity: 0;
  transition: opacity 0.3s;
  cursor: pointer;
}

.upload-overlay:hover {
  opacity: 1;
}

.upload-overlay .el-icon {
  font-size: 24px;
  margin-bottom: 8px;
}
.mb-16 {
  margin-bottom: 16px;
}
/* 右侧卡片样式优化 */
.el-card :deep(.el-card__body) {
  padding: 16px;
}
.el-form-item {
  margin-bottom: 14px;
}
:deep(.el-upload--picture-card) {
  --el-upload-picture-card-size: 100px;
}

.info-card {
  height: 420px; /* 保持右侧卡片高度不变 */
  display: flex;
  flex-direction: column;
}

.info-card :deep(.el-card__body) {
  flex: 1;
  overflow-y: auto;
  padding: 12px;
}

.info-card :deep(.el-form) {
  height: 100%;
}

.info-card :deep(.el-form-item) {
  margin-bottom: 12px;
}

.info-card :deep(.el-form-item__label) {
  font-weight: normal;
  white-space: nowrap;
}

.info-card :deep(.el-textarea__inner) {
  font-size: 14px;
}

.info-card :deep(.el-upload--picture-card) {
  --el-upload-picture-card-size: 80px;
}

.info-card :deep(.el-upload-list--picture-card) {
  --el-upload-list-picture-card-size: 80px;
}

.top-bar {
  margin-bottom: 16px;
  display: flex;
  justify-content: flex-start;
}

.stats-btn {
  font-size: 18px;
  font-weight: bold;
  padding: 10px 36px;
  letter-spacing: 2px;
}
</style>
