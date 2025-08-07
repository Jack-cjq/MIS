/* eslint-disable vue/multi-word-component-names */
<template>
  <div class="competition">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>科创竞赛</span>
              <el-select v-model="filterType" placeholder="筛选类型" style="width: 150px">
                <el-option label="全部" value="" />
                <el-option label="数学建模" value="数学建模" />
                <el-option label="程序设计" value="程序设计" />
                <el-option label="创新创业" value="创新创业" />
                <el-option label="其他" value="其他" />
              </el-select>
            </div>
          </template>
          
          <div class="competition-list">
            <div
              v-for="competition in filteredCompetitions"
              :key="competition.id"
              class="competition-item"
            >
              <div class="competition-header">
                <h3 class="competition-title">{{ competition.title }}</h3>
                <el-tag :type="getCompetitionStatusType(competition.status)">
                  {{ competition.status }}
                </el-tag>
              </div>
              <div class="competition-info">
                <div class="competition-type">{{ competition.type }}</div>
                <div class="competition-time">
                  报名时间：{{ competition.registrationStart }} 至 {{ competition.registrationEnd }}
                </div>
                <div class="competition-time">
                  比赛时间：{{ competition.competitionStart }} 至 {{ competition.competitionEnd }}
                </div>
              </div>
              <div class="competition-description">{{ competition.description }}</div>
              <div class="competition-footer">
                <div class="competition-prizes">
                  <span class="prize-label">奖项设置：</span>
                  <span class="prize-content">{{ competition.prizes }}</span>
                </div>
                <el-button 
                  type="primary" 
                  @click="registerCompetition(competition)"
                  :disabled="competition.status !== '报名中'"
                >
                  {{ competition.status === '报名中' ? '立即报名' : '报名结束' }}
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
              <span>我的参赛</span>
            </div>
          </template>
          
          <div class="my-competitions">
            <div
              v-for="myComp in myCompetitions"
              :key="myComp.id"
              class="my-competition-item"
            >
              <div class="my-competition-header">
                <span class="my-competition-title">{{ myComp.title }}</span>
                <el-tag :type="getMyCompetitionStatusType(myComp.status)" size="small">
                  {{ myComp.status }}
                </el-tag>
              </div>
              <div class="my-competition-time">报名时间：{{ myComp.registerTime }}</div>
              <div class="my-competition-result" v-if="myComp.result">
                比赛结果：{{ myComp.result }}
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 报名对话框 -->
    <el-dialog v-model="showRegisterDialog" title="竞赛报名" width="500px">
      <el-form :model="registerForm" :rules="registerRules" ref="registerFormRef" label-width="100px">
        <el-form-item label="竞赛名称">
          <el-input v-model="registerForm.competitionTitle" disabled />
        </el-form-item>
        <el-form-item label="参赛类型" prop="participantType">
          <el-radio-group v-model="registerForm.participantType">
            <el-radio label="个人">个人参赛</el-radio>
            <el-radio label="团队">团队参赛</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="团队名称" v-if="registerForm.participantType === '团队'" prop="teamName">
          <el-input v-model="registerForm.teamName" placeholder="请输入团队名称" />
        </el-form-item>
        <el-form-item label="团队成员" v-if="registerForm.participantType === '团队'" prop="teamMembers">
          <el-input
            v-model="registerForm.teamMembers"
            type="textarea"
            :rows="3"
            placeholder="请输入团队成员姓名，用逗号分隔"
          />
        </el-form-item>
        <el-form-item label="指导教师" prop="advisor">
          <el-input v-model="registerForm.advisor" placeholder="请输入指导教师姓名" />
        </el-form-item>
        <el-form-item label="联系方式" prop="contact">
          <el-input v-model="registerForm.contact" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="参赛宣言" prop="declaration">
          <el-input
            v-model="registerForm.declaration"
            type="textarea"
            :rows="3"
            placeholder="请输入参赛宣言"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showRegisterDialog = false">取消</el-button>
          <el-button type="primary" @click="submitRegistration">提交报名</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Trophy, Document, Operation, Files, Plus } from '@element-plus/icons-vue'
import {
  addCompetition, updateCompetition, deleteCompetition, getMyCompetitions,
  addPaper, updatePaper, deletePaper, getMyPapers,
  addPatent, updatePatent, deletePatent, getMyPatents,
  addProject, updateProject, deleteProject, getMyProjects,
  searchCompetitions, getAllActiveCompetitions, registerForCompetition
} from '@/api/competition'

const showRegisterDialog = ref(false)
const registerFormRef = ref()
const filterType = ref('')

const registerForm = reactive({
  competitionTitle: '',
  participantType: '个人',
  teamName: '',
  teamMembers: '',
  advisor: '',
  contact: '',
  declaration: ''
})

const registerRules = {
  participantType: [
    { required: true, message: '请选择参赛类型', trigger: 'change' }
  ],
  teamName: [
    { required: true, message: '请输入团队名称', trigger: 'blur' }
  ],
  teamMembers: [
    { required: true, message: '请输入团队成员', trigger: 'blur' }
  ],
  advisor: [
    { required: true, message: '请输入指导教师', trigger: 'blur' }
  ],
  contact: [
    { required: true, message: '请输入联系方式', trigger: 'blur' }
  ],
  declaration: [
    { required: true, message: '请输入参赛宣言', trigger: 'blur' }
  ]
}

const competitionList = ref([])
const myCompetitions = ref([])

const filteredCompetitions = computed(() => {
  if (!filterType.value) return competitionList.value
  return competitionList.value.filter(comp => comp.type === filterType.value)
})

const loadData = async () => {
  try {
    const [competitions, papers, patents, projects] = await Promise.all([
      getMyCompetitions(),
      getMyPapers(),
      getMyPatents(),
      getMyProjects()
    ])
    competitionList.value = competitions
    myCompetitions.value = competitions
  } catch (error) {
    console.error('加载数据失败:', error)
    // 使用模拟数据
    competitionList.value = [
      {
        id: 1,
        title: '全国大学生数学建模竞赛',
        type: '数学建模',
        status: '报名中',
        registrationStart: '2024-01-01',
        registrationEnd: '2024-02-01',
        competitionStart: '2024-03-01',
        competitionEnd: '2024-03-03',
        description: '全国大学生数学建模竞赛是全国高校规模最大的基础性学科竞赛，也是世界上规模最大的数学建模竞赛。',
        prizes: '一等奖、二等奖、三等奖'
      },
      {
        id: 2,
        title: 'ACM程序设计竞赛',
        type: '程序设计',
        status: '报名中',
        registrationStart: '2024-01-15',
        registrationEnd: '2024-02-15',
        competitionStart: '2024-04-01',
        competitionEnd: '2024-04-01',
        description: 'ACM国际大学生程序设计竞赛是一项旨在展示大学生创新能力、团队精神和在压力下编写程序、分析和解决问题能力的年度竞赛。',
        prizes: '金奖、银奖、铜奖'
      }
    ]
    myCompetitions.value = [
      {
        id: 1,
        title: '全国大学生数学建模竞赛',
        status: '已报名',
        registerTime: '2024-01-10',
        result: ''
      }
    ]
  }
}

const registerCompetition = (competition) => {
  registerForm.competitionTitle = competition.title
  showRegisterDialog.value = true
}

const submitRegistration = async () => {
  try {
    await registerFormRef.value.validate()
    await registerForCompetition(registerForm)
    ElMessage.success('报名成功')
    showRegisterDialog.value = false
    Object.assign(registerForm, {
      competitionTitle: '',
      participantType: '个人',
      teamName: '',
      teamMembers: '',
      advisor: '',
      contact: '',
      declaration: ''
    })
    loadData()
  } catch (error) {
    console.error('报名失败:', error)
    ElMessage.error('报名失败')
  }
}

const getCompetitionStatusType = (status) => {
  const statusMap = {
    '报名中': 'success',
    '报名结束': 'warning',
    '进行中': 'primary',
    '已结束': 'info'
  }
  return statusMap[status] || 'info'
}

const getMyCompetitionStatusType = (status) => {
  const statusMap = {
    '已报名': 'warning',
    '进行中': 'primary',
    '已完成': 'success'
  }
  return statusMap[status] || 'info'
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.competition {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.competition-list {
  max-height: 600px;
  overflow-y: auto;
}

.competition-item {
  padding: 16px;
  border: 1px solid #e6e6e6;
  border-radius: 8px;
  margin-bottom: 16px;
}

.competition-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.competition-title {
  margin: 0;
  color: #303133;
  font-size: 16px;
}

.competition-info {
  margin-bottom: 12px;
}

.competition-type {
  color: #409EFF;
  font-weight: bold;
  margin-bottom: 4px;
}

.competition-time {
  color: #909399;
  font-size: 14px;
  margin-bottom: 2px;
}

.competition-description {
  color: #606266;
  margin-bottom: 12px;
  line-height: 1.5;
}

.competition-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.competition-prizes {
  flex: 1;
}

.prize-label {
  color: #909399;
  font-size: 14px;
}

.prize-content {
  color: #606266;
  font-size: 14px;
}

.my-competitions {
  max-height: 400px;
  overflow-y: auto;
}

.my-competition-item {
  padding: 12px;
  border: 1px solid #e6e6e6;
  border-radius: 4px;
  margin-bottom: 12px;
}

.my-competition-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.my-competition-title {
  font-weight: bold;
  color: #303133;
}

.my-competition-time {
  color: #606266;
  font-size: 14px;
  margin-bottom: 4px;
}

.my-competition-result {
  color: #409EFF;
  font-size: 14px;
  font-weight: bold;
}
</style> 