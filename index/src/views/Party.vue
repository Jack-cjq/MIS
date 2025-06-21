/* eslint-disable vue/multi-word-component-names */
<template>
  <div class="party">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>党员信息</span>
              <el-button type="primary" @click="showAddDialog = true">
                添加党员
              </el-button>
            </div>
          </template>
          
          <el-table :data="partyMembers" style="width: 100%">
            <el-table-column prop="name" label="姓名" width="100" />
            <el-table-column prop="studentId" label="学号" width="120" />
            <el-table-column prop="joinDate" label="入党时间" width="120" />
            <el-table-column prop="position" label="党内职务" width="120" />
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === '正式党员' ? 'success' : 'warning'">
                  {{ scope.row.status }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="phone" label="联系电话" width="120" />
            <el-table-column label="操作" width="200">
              <template #default="scope">
                <el-button size="small" @click="editMember(scope.row)">编辑</el-button>
                <el-button size="small" type="danger" @click="deleteMember(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>组织生活</span>
              <el-button type="primary" @click="showActivityDialog = true">
                添加活动
              </el-button>
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
              <div class="activity-actions">
                <el-button size="small" @click="viewActivity(activity)">查看详情</el-button>
                <el-button size="small" @click="joinActivity(activity)" v-if="activity.status === '报名中'">
                  报名参加
                </el-button>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 添加/编辑党员对话框 -->
    <el-dialog v-model="showAddDialog" :title="isEdit ? '编辑党员' : '添加党员'" width="500px">
      <el-form :model="memberForm" :rules="memberRules" ref="memberFormRef" label-width="100px">
        <el-form-item label="姓名" prop="name">
          <el-input v-model="memberForm.name" />
        </el-form-item>
        <el-form-item label="学号" prop="studentId">
          <el-input v-model="memberForm.studentId" />
        </el-form-item>
        <el-form-item label="入党时间" prop="joinDate">
          <el-date-picker
            v-model="memberForm.joinDate"
            type="date"
            placeholder="选择入党时间"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="党内职务" prop="position">
          <el-select v-model="memberForm.position" placeholder="请选择党内职务" style="width: 100%">
            <el-option label="支部书记" value="支部书记" />
            <el-option label="支部委员" value="支部委员" />
            <el-option label="普通党员" value="普通党员" />
          </el-select>
        </el-form-item>
        <el-form-item label="党员状态" prop="status">
          <el-radio-group v-model="memberForm.status">
            <el-radio label="正式党员">正式党员</el-radio>
            <el-radio label="预备党员">预备党员</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="memberForm.phone" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="memberForm.email" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAddDialog = false">取消</el-button>
          <el-button type="primary" @click="submitMember">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 添加组织生活对话框 -->
    <el-dialog v-model="showActivityDialog" title="添加组织生活" width="500px">
      <el-form :model="activityForm" :rules="activityRules" ref="activityFormRef" label-width="100px">
        <el-form-item label="活动标题" prop="title">
          <el-input v-model="activityForm.title" />
        </el-form-item>
        <el-form-item label="活动时间" prop="time">
          <el-date-picker
            v-model="activityForm.time"
            type="datetime"
            placeholder="选择活动时间"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="活动地点" prop="location">
          <el-input v-model="activityForm.location" />
        </el-form-item>
        <el-form-item label="活动类型" prop="type">
          <el-select v-model="activityForm.type" placeholder="请选择活动类型" style="width: 100%">
            <el-option label="支部大会" value="支部大会" />
            <el-option label="组织生活会" value="组织生活会" />
            <el-option label="主题党日" value="主题党日" />
            <el-option label="党课学习" value="党课学习" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="活动描述" prop="description">
          <el-input
            v-model="activityForm.description"
            type="textarea"
            :rows="4"
            placeholder="请输入活动描述"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showActivityDialog = false">取消</el-button>
          <el-button type="primary" @click="submitActivity">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getPartyMembers, addPartyMember, updatePartyMember, deletePartyMember, getPartyActivities, addPartyActivity, joinPartyActivity } from '@/api/party'

const showAddDialog = ref(false)
const showActivityDialog = ref(false)
const memberFormRef = ref()
const activityFormRef = ref()
const isEdit = ref(false)

const memberForm = reactive({
  id: '',
  name: '',
  studentId: '',
  joinDate: '',
  position: '',
  status: '正式党员',
  phone: '',
  email: ''
})

const memberRules = {
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' }
  ],
  studentId: [
    { required: true, message: '请输入学号', trigger: 'blur' }
  ],
  joinDate: [
    { required: true, message: '请选择入党时间', trigger: 'change' }
  ],
  position: [
    { required: true, message: '请选择党内职务', trigger: 'change' }
  ],
  status: [
    { required: true, message: '请选择党员状态', trigger: 'change' }
  ],
  phone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ]
}

const activityForm = reactive({
  title: '',
  time: '',
  location: '',
  type: '',
  description: ''
})

const activityRules = {
  title: [
    { required: true, message: '请输入活动标题', trigger: 'blur' }
  ],
  time: [
    { required: true, message: '请选择活动时间', trigger: 'change' }
  ],
  location: [
    { required: true, message: '请输入活动地点', trigger: 'blur' }
  ],
  type: [
    { required: true, message: '请选择活动类型', trigger: 'change' }
  ],
  description: [
    { required: true, message: '请输入活动描述', trigger: 'blur' }
  ]
}

const partyMembers = ref([])
const activities = ref([])

const loadData = async () => {
  try {
    const [membersData, activitiesData] = await Promise.all([
      getPartyMembers(),
      getPartyActivities()
    ])
    partyMembers.value = membersData
    activities.value = activitiesData
  } catch (error) {
    console.error('加载数据失败:', error)
    // 使用模拟数据
    partyMembers.value = [
      {
        id: 1,
        name: '张三',
        studentId: '2021001',
        joinDate: '2022-06-15',
        position: '支部书记',
        status: '正式党员',
        phone: '13800138000',
        email: 'zhangsan@example.com'
      },
      {
        id: 2,
        name: '李四',
        studentId: '2021002',
        joinDate: '2023-03-20',
        position: '普通党员',
        status: '预备党员',
        phone: '13800138001',
        email: 'lisi@example.com'
      }
    ]
    activities.value = [
      {
        id: 1,
        title: '支部大会',
        time: '2024-01-20 14:00',
        location: '会议室A',
        type: '支部大会',
        description: '讨论支部工作计划',
        status: '报名中'
      },
      {
        id: 2,
        title: '主题党日活动',
        time: '2024-01-25 09:00',
        location: '党员活动室',
        type: '主题党日',
        description: '学习党的理论知识',
        status: '进行中'
      }
    ]
  }
}

const editMember = (member) => {
  isEdit.value = true
  Object.assign(memberForm, member)
  showAddDialog.value = true
}

const submitMember = async () => {
  try {
    await memberFormRef.value.validate()
    if (isEdit.value) {
      await updatePartyMember(memberForm)
      ElMessage.success('更新成功')
    } else {
      await addPartyMember(memberForm)
      ElMessage.success('添加成功')
    }
    showAddDialog.value = false
    Object.assign(memberForm, {
      id: '',
      name: '',
      studentId: '',
      joinDate: '',
      position: '',
      status: '正式党员',
      phone: '',
      email: ''
    })
    isEdit.value = false
    loadData()
  } catch (error) {
    console.error('操作失败:', error)
    ElMessage.error('操作失败')
  }
}

const deleteMember = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这个党员吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await deletePartyMember(id)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

const submitActivity = async () => {
  try {
    await activityFormRef.value.validate()
    await addPartyActivity(activityForm)
    ElMessage.success('添加成功')
    showActivityDialog.value = false
    Object.assign(activityForm, {
      title: '',
      time: '',
      location: '',
      type: '',
      description: ''
    })
    loadData()
  } catch (error) {
    console.error('添加失败:', error)
    ElMessage.error('添加失败')
  }
}

const viewActivity = (activity) => {
  // 查看活动详情
  console.log('查看活动:', activity)
}

const joinActivity = async (activity) => {
  try {
    await joinPartyActivity(activity.id)
    ElMessage.success('报名成功')
    loadData()
  } catch (error) {
    console.error('报名失败:', error)
    ElMessage.error('报名失败')
  }
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
.party {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
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

.activity-actions {
  display: flex;
  gap: 8px;
}
</style> 