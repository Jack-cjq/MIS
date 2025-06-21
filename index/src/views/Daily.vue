/* eslint-disable vue/multi-word-component-names */
<template>
  <div class="daily">
    <el-row :gutter="20">
      <el-col :span="16">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>考勤记录</span>
              <el-button type="primary" @click="showAttendanceDialog = true">
                签到
              </el-button>
            </div>
          </template>
          
          <el-table :data="attendanceRecords" style="width: 100%">
            <el-table-column prop="date" label="日期" width="120" />
            <el-table-column prop="time" label="时间" width="100" />
            <el-table-column prop="type" label="类型" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.type === '签到' ? 'success' : 'warning'">
                  {{ scope.row.type }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" width="100">
              <template #default="scope">
                <el-tag :type="scope.row.status === '正常' ? 'success' : 'danger'">
                  {{ scope.row.status }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="remark" label="备注" />
          </el-table>
        </el-card>
      </el-col>
      
      <el-col :span="8">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>请假申请</span>
              <el-button type="primary" @click="showLeaveDialog = true">
                申请请假
              </el-button>
            </div>
          </template>
          
          <div class="leave-list">
            <div
              v-for="leave in leaveRecords"
              :key="leave.id"
              class="leave-item"
            >
              <div class="leave-header">
                <span class="leave-type">{{ leave.type }}</span>
                <el-tag :type="getLeaveStatusType(leave.status)" size="small">
                  {{ leave.status }}
                </el-tag>
              </div>
              <div class="leave-time">{{ leave.startDate }} 至 {{ leave.endDate }}</div>
              <div class="leave-reason">{{ leave.reason }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 签到对话框 -->
    <el-dialog v-model="showAttendanceDialog" title="签到" width="400px">
      <el-form :model="attendanceForm" label-width="80px">
        <el-form-item label="签到类型">
          <el-radio-group v-model="attendanceForm.type">
            <el-radio label="签到">签到</el-radio>
            <el-radio label="签退">签退</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="attendanceForm.remark" type="textarea" :rows="3" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAttendanceDialog = false">取消</el-button>
          <el-button type="primary" @click="submitAttendance">确认</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 请假申请对话框 -->
    <el-dialog v-model="showLeaveDialog" title="请假申请" width="500px">
      <el-form :model="leaveForm" :rules="leaveRules" ref="leaveFormRef" label-width="100px">
        <el-form-item label="请假类型" prop="type">
          <el-select v-model="leaveForm.type" placeholder="请选择请假类型">
            <el-option label="事假" value="事假" />
            <el-option label="病假" value="病假" />
            <el-option label="其他" value="其他" />
          </el-select>
        </el-form-item>
        <el-form-item label="请假时间" prop="dateRange">
          <el-date-picker
            v-model="leaveForm.dateRange"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="请假原因" prop="reason">
          <el-input v-model="leaveForm.reason" type="textarea" :rows="4" />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showLeaveDialog = false">取消</el-button>
          <el-button type="primary" @click="submitLeave">提交申请</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { getAttendanceRecords, submitAttendanceRecord, getLeaveRecords, submitLeaveApplication } from '@/api/daily'

const showAttendanceDialog = ref(false)
const showLeaveDialog = ref(false)
const leaveFormRef = ref()

const attendanceForm = reactive({
  type: '签到',
  remark: ''
})

const leaveForm = reactive({
  type: '',
  dateRange: [],
  reason: ''
})

const leaveRules = {
  type: [
    { required: true, message: '请选择请假类型', trigger: 'change' }
  ],
  dateRange: [
    { required: true, message: '请选择请假时间', trigger: 'change' }
  ],
  reason: [
    { required: true, message: '请输入请假原因', trigger: 'blur' }
  ]
}

const attendanceRecords = ref([])
const leaveRecords = ref([])

const loadData = async () => {
  try {
    const [attendanceData, leaveData] = await Promise.all([
      getAttendanceRecords(),
      getLeaveRecords()
    ])
    attendanceRecords.value = attendanceData
    leaveRecords.value = leaveData
  } catch (error) {
    console.error('加载数据失败:', error)
    // 使用模拟数据
    attendanceRecords.value = [
      {
        date: '2024-01-15',
        time: '08:30',
        type: '签到',
        status: '正常',
        remark: ''
      },
      {
        date: '2024-01-15',
        time: '17:30',
        type: '签退',
        status: '正常',
        remark: ''
      }
    ]
    leaveRecords.value = [
      {
        id: 1,
        type: '事假',
        startDate: '2024-01-16',
        endDate: '2024-01-17',
        reason: '家中有事',
        status: '待审批'
      }
    ]
  }
}

const submitAttendance = async () => {
  try {
    await submitAttendanceRecord(attendanceForm)
    ElMessage.success('签到成功')
    showAttendanceDialog.value = false
    attendanceForm.remark = ''
    loadData()
  } catch (error) {
    console.error('签到失败:', error)
    ElMessage.error('签到失败')
  }
}

const submitLeave = async () => {
  try {
    await leaveFormRef.value.validate()
    const formData = {
      ...leaveForm,
      startDate: leaveForm.dateRange[0],
      endDate: leaveForm.dateRange[1]
    }
    await submitLeaveApplication(formData)
    ElMessage.success('请假申请提交成功')
    showLeaveDialog.value = false
    Object.assign(leaveForm, { type: '', dateRange: [], reason: '' })
    loadData()
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error('提交失败')
  }
}

const getLeaveStatusType = (status) => {
  const statusMap = {
    '待审批': 'warning',
    '已批准': 'success',
    '已拒绝': 'danger'
  }
  return statusMap[status] || 'info'
}

onMounted(() => {
  loadData()
})
</script>

<style scoped>
.daily {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.leave-list {
  max-height: 400px;
  overflow-y: auto;
}

.leave-item {
  padding: 12px;
  border: 1px solid #e6e6e6;
  border-radius: 4px;
  margin-bottom: 12px;
}

.leave-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 8px;
}

.leave-type {
  font-weight: bold;
  color: #303133;
}

.leave-time {
  font-size: 12px;
  color: #909399;
  margin-bottom: 4px;
}

.leave-reason {
  font-size: 14px;
  color: #606266;
}
</style> 