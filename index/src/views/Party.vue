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
            <el-descriptions-item label="所在班级">{{ memberInfo.class }}</el-descriptions-item>
            <el-descriptions-item label="所在支部">{{ memberInfo.branch }}</el-descriptions-item>
            <el-descriptions-item label="入党时间">{{ memberInfo.joinDate }}</el-descriptions-item>
            <el-descriptions-item label="是否转正">{{ memberInfo.isRegular }}</el-descriptions-item>
            <el-descriptions-item label="志愿书编号">{{ memberInfo.applicationNo }}</el-descriptions-item>
            <el-descriptions-item label="党内职务">{{ memberInfo.position }}</el-descriptions-item>
            <el-descriptions-item label="奖惩情况">{{ memberInfo.rewardsPunishments }}</el-descriptions-item>
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
                <el-form-item label="思想汇报题目">
                  <el-input v-model="reportForm.content" type="textarea" :rows="3" placeholder="请输入思想汇报内容" />
                </el-form-item>
                <el-form-item label="提交日期">
                  <el-date-picker v-model="reportForm.date" type="date" placeholder="选择日期" style="width: 100%" />
                </el-form-item>
                <el-form-item label="证明材料文本">
                  <el-input v-model="reportForm.proofText" type="textarea" :rows="2" placeholder="请输入证明材料文本" />
                </el-form-item>
                <el-form-item label="证明材料图片">
                  <el-upload
                    class="upload-demo"
                    action="#"
                    :file-list="reportForm.proofImages"
                    :auto-upload="false"
                    list-type="picture-card"
                    :on-preview="handlePreview"
                    :on-remove="(file, fileList) => handleRemove(file, fileList, 'reportForm')"
                    :on-change="(file, fileList) => handleChange(file, fileList, 'reportForm')"
                  >
                    <el-icon><Plus /></el-icon>
                  </el-upload>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitReport">提交</el-button>
                </el-form-item>
              </el-form>
            </el-card>
          </el-col>
          <!-- 思政推文 -->
          <el-col :span="12">
            <el-card class="info-card mb-16" header="思政推文">
              <el-form :model="tweetForm" label-width="100px">
                <el-form-item label="推文名称">
                  <el-input v-model="tweetForm.name" placeholder="请输入推文名称" />
                </el-form-item>
                <el-form-item label="推文日期">
                  <el-date-picker v-model="tweetForm.date" type="date" placeholder="选择日期" style="width: 100%" />
                </el-form-item>
                <el-form-item label="证明材料文本">
                  <el-input v-model="tweetForm.proofText" type="textarea" :rows="2" placeholder="请输入证明材料文本" />
                </el-form-item>
                <el-form-item label="证明材料图片">
                  <el-upload
                    class="upload-demo"
                    action="#"
                    :file-list="tweetForm.proofImages"
                    :auto-upload="false"
                    list-type="picture-card"
                    :on-preview="handlePreview"
                    :on-remove="(file, fileList) => handleRemove(file, fileList, 'tweetForm')"
                    :on-change="(file, fileList) => handleChange(file, fileList, 'tweetForm')"
                  >
                    <el-icon><Plus /></el-icon>
                  </el-upload>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitTweet">提交</el-button>
                </el-form-item>
              </el-form>
            </el-card>
          </el-col>
          <!-- 微党课 -->
          <el-col :span="12">
            <el-card class="info-card mb-16" header="微党课">
              <el-form :model="courseForm" label-width="100px">
                <el-form-item label="课程名称">
                  <el-input v-model="courseForm.name" placeholder="请输入课程名称" />
                </el-form-item>
                <el-form-item label="提交日期">
                  <el-date-picker v-model="courseForm.date" type="date" placeholder="选择日期" style="width: 100%" />
                </el-form-item>
                <el-form-item label="证明材料文本">
                  <el-input v-model="courseForm.proofText" type="textarea" :rows="2" placeholder="请输入证明材料文本" />
                </el-form-item>
                <el-form-item label="证明材料图片">
                  <el-upload
                    class="upload-demo"
                    action="#"
                    :file-list="courseForm.proofImages"
                    :auto-upload="false"
                    list-type="picture-card"
                    :on-preview="handlePreview"
                    :on-remove="(file, fileList) => handleRemove(file, fileList, 'courseForm')"
                    :on-change="(file, fileList) => handleChange(file, fileList, 'courseForm')"
                  >
                    <el-icon><Plus /></el-icon>
                  </el-upload>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitCourse">提交</el-button>
                </el-form-item>
              </el-form>
            </el-card>
          </el-col>
          <!-- 志愿服务 -->
          <el-col :span="12">
            <el-card class="info-card" header="志愿服务">
              <el-form :model="serviceForm" label-width="100px">
                <el-form-item label="活动名称">
                  <el-input v-model="serviceForm.name" placeholder="请输入活动名称" />
                </el-form-item>
                <el-form-item label="服务日期">
                  <el-date-picker v-model="serviceForm.date" type="date" placeholder="选择日期" style="width: 100%" />
                </el-form-item>
                <el-form-item label="服务时间">
                  <el-input v-model="serviceForm.time" placeholder="请输入服务时间（如：2小时）" />
                </el-form-item>
                <el-form-item label="证明材料文本">
                  <el-input v-model="serviceForm.proofText" type="textarea" :rows="2" placeholder="请输入证明材料文本" />
                </el-form-item>
                <el-form-item label="证明材料图片">
                  <el-upload
                    class="upload-demo"
                    action="#"
                    :file-list="serviceForm.proofImages"
                    :auto-upload="false"
                    list-type="picture-card"
                    :on-preview="handlePreview"
                    :on-remove="(file, fileList) => handleRemove(file, fileList, 'serviceForm')"
                    :on-change="(file, fileList) => handleChange(file, fileList, 'serviceForm')"
                  >
                    <el-icon><Plus /></el-icon>
                  </el-upload>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="submitService">提交</el-button>
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
import { ref, reactive, defineComponent } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus, Upload } from '@element-plus/icons-vue'

// 定义组件名称
defineComponent({
  name: 'PartyView'
})

// 党员信息
const memberInfo = reactive({
  avatar: '',
  studentId: '20230001',
  name: '张三',
  gender: '男',
  nation: '汉族',
  birth: '2000-01-01',
  nativePlace: '江苏南京',
  class: '软件工程1班',
  branch: '计算机学院党支部',
  joinDate: '2022-06-01',
  isRegular: '是',
  applicationNo: 'ZYS2022001',
  position: '组织委员',
  rewardsPunishments: '无'
})

// 表单数据
const reportForm = reactive({
  content: '',
  date: null,
  proofText: '',
  proofImages: []
})

const tweetForm = reactive({
  name: '',
  date: null,
  proofText: '',
  proofImages: []
})

const courseForm = reactive({
  name: '',
  date: null,
  proofText: '',
  proofImages: []
})

const serviceForm = reactive({
  name: '',
  date: null,
  time: '',
  proofText: '',
  proofImages: []
})

// 图片预览
const previewVisible = ref(false)
const previewImage = ref('')

// 处理函数
const handlePreview = (file) => {
  previewImage.value = file.url || file.preview || ''
  previewVisible.value = true
}

const handleRemove = (file, fileList, formKey) => {
  // 实现文件移除逻辑
  switch (formKey) {
    case 'reportForm':
      reportForm.proofImages = fileList
      break
    case 'tweetForm':
      tweetForm.proofImages = fileList
      break
    case 'courseForm':
      courseForm.proofImages = fileList
      break
    case 'serviceForm':
      serviceForm.proofImages = fileList
      break
  }
}

const handleChange = (file, fileList, formKey) => {
  switch (formKey) {
    case 'reportForm':
      reportForm.proofImages = fileList
      break
    case 'tweetForm':
      tweetForm.proofImages = fileList
      break
    case 'courseForm':
      courseForm.proofImages = fileList
      break
    case 'serviceForm':
      serviceForm.proofImages = fileList
      break
  }
}

// 提交处理函数
const submitReport = async () => {
  try {
    // TODO: 实现API调用
    ElMessage.success('思想汇报已提交')
  } catch (error) {
    ElMessage.error('提交失败')
  }
}

const submitTweet = async () => {
  try {
    // TODO: 实现API调用
    ElMessage.success('思政推文已提交')
  } catch (error) {
    ElMessage.error('提交失败')
  }
}

const submitCourse = async () => {
  try {
    // TODO: 实现API调用
    ElMessage.success('微党课已提交')
  } catch (error) {
    ElMessage.error('提交失败')
  }
}

const submitService = async () => {
  try {
    // TODO: 实现API调用
    ElMessage.success('志愿服务已提交')
  } catch (error) {
    ElMessage.error('提交失败')
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
