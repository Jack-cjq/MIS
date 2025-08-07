<template>
  <div class="party-add">
    <div class="page-header">
      <h2>添加党员信息</h2>
    </div>

    <el-card class="form-card">
      <el-form
        ref="memberFormRef"
        :model="memberForm"
        :rules="memberFormRules"
        label-width="120px"
      >
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="学号" prop="studentId">
              <el-input v-model="memberForm.studentId" placeholder="请输入学号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="memberForm.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="memberForm.gender" placeholder="请选择性别" style="width: 100%">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="民族" prop="nation">
              <el-input v-model="memberForm.nation" placeholder="请输入民族" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="出生日期" prop="birth">
              <el-date-picker
                v-model="memberForm.birth"
                type="date"
                placeholder="选择出生日期"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="籍贯" prop="nativePlace">
              <el-input v-model="memberForm.nativePlace" placeholder="请输入籍贯" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="班级" prop="className">
              <el-input v-model="memberForm.className" placeholder="请输入班级" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="党支部" prop="branch">
              <el-select v-model="memberForm.branch" placeholder="请选择党支部" style="width: 100%">
                <el-option label="计算机学院党支部" value="计算机学院党支部" />
                <el-option label="软件学院党支部" value="软件学院党支部" />
                <el-option label="信息学院党支部" value="信息学院党支部" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="入党时间" prop="joinDate">
              <el-date-picker
                v-model="memberForm.joinDate"
                type="date"
                placeholder="选择入党时间"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否正式党员" prop="isRegular">
              <el-select v-model="memberForm.isRegular" placeholder="请选择党员状态" style="width: 100%">
                <el-option label="正式党员" value="是" />
                <el-option label="预备党员" value="否" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="志愿书编号" prop="applicationNo">
              <el-input v-model="memberForm.applicationNo" placeholder="请输入志愿书编号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="党内职务" prop="position">
              <el-input v-model="memberForm.position" placeholder="请输入党内职务" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="奖惩情况" prop="rewardsPunishments">
          <el-input
            v-model="memberForm.rewardsPunishments"
            type="textarea"
            :rows="3"
            placeholder="请输入党内外奖惩情况"
          />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="memberForm.remark"
            type="textarea"
            :rows="2"
            placeholder="请输入备注"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="submitLoading">
            提交
          </el-button>
          <el-button @click="handleReset">重置</el-button>
          <el-button @click="handleBack">返回</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { partyMemberApi } from '@/api/party'

const router = useRouter()

// 表单数据
const memberForm = reactive({
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
  remark: ''
})

// 表单验证规则
const memberFormRules = {
  studentId: [{ required: true, message: '请输入学号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  nation: [{ required: true, message: '请输入民族', trigger: 'blur' }],
  birth: [{ required: true, message: '请选择出生日期', trigger: 'change' }],
  nativePlace: [{ required: true, message: '请输入籍贯', trigger: 'blur' }],
  className: [{ required: true, message: '请输入班级', trigger: 'blur' }],
  branch: [{ required: true, message: '请选择党支部', trigger: 'change' }],
  joinDate: [{ required: true, message: '请选择入党时间', trigger: 'change' }],
  isRegular: [{ required: true, message: '请选择党员状态', trigger: 'change' }]
}

const memberFormRef = ref(null)
const submitLoading = ref(false)

// 提交表单
const handleSubmit = async () => {
  if (!memberFormRef.value) return
  
  try {
    await memberFormRef.value.validate()
    submitLoading.value = true
    
    await partyMemberApi.addPartyMember(memberForm)
    ElMessage.success('党员信息添加成功')
    router.push('/admin/party-list')
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error('提交失败')
  } finally {
    submitLoading.value = false
  }
}

// 重置表单
const handleReset = () => {
  if (memberFormRef.value) {
    memberFormRef.value.resetFields()
  }
}

// 返回列表
const handleBack = () => {
  router.push('/admin/party-list')
}
</script>

<style scoped>
.party-add {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;
}

.page-header h2 {
  margin: 0;
  color: #303133;
}

.form-card {
  max-width: 800px;
  margin: 0 auto;
}

:deep(.el-form-item) {
  margin-bottom: 20px;
}

:deep(.el-card__body) {
  padding: 30px;
}
</style>