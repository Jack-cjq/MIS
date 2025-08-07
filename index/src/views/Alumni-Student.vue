/* eslint-disable vue/multi-word-component-names */
<template>
  <div class="alumni">
    <el-card style="margin-right: 250px;margin-left: 250px">
      <template #header>
        <div class="card-header">
          <span>校友信息</span>
          <div class="search-group">
            <el-button type="primary" :disabled="alumniData!==null" @click="insertAlumniDetail">新增校友信息</el-button>
            <el-button type="danger" :disabled="alumniData===null" @click="updateAlumniDetail(alumniData)">修改校友信息</el-button>
          </div>
        </div>
      </template>

      <el-descriptions border column="2" size="large" label-width="15%">
        <el-descriptions-item label="姓名">{{ form.name }}</el-descriptions-item>
        <el-descriptions-item label="学号">{{ form.studentId }}</el-descriptions-item>
        <el-descriptions-item label="入学年份" width="30%">{{ alumniData === null ? '未填写' : alumniData.enrollmentDate }}</el-descriptions-item>
        <el-descriptions-item label="毕业年份">{{ alumniData === null ? '未填写' : alumniData.graduationDate }}</el-descriptions-item>
        <el-descriptions-item label="就业地区">
          {{ alumniData === null ? '未填写': alumniData.workLocation.province + " " + alumniData.workLocation.city + " " + alumniData.workLocation.district}}
        </el-descriptions-item>
        <el-descriptions-item label="所在行业">{{ alumniData === null ? '未填写' : alumniData.workField }}</el-descriptions-item>
        <el-descriptions-item label="所在单位">{{ alumniData === null ? '未填写' : alumniData.workPlace }}</el-descriptions-item>
        <el-descriptions-item label="岗位类型">{{ alumniData === null ? '未填写' : alumniData.jobType }}</el-descriptions-item>
      </el-descriptions>
    </el-card>

    <!-- 新增信息对话框 -->
    <el-dialog v-model="showAlumniInsertDialog" @closed="onDialogClose" title="新增校友信息" width="800px">
      <el-form :model="form" :rules="formRules" ref="insertFormRef" label-width="100px">
        <el-form-item label="姓名">
          <el-input v-model="form.name" disabled/>
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="form.studentId" disabled/>
        </el-form-item>
        <el-form-item label="入学年份" prop="enrollmentDate">
          <el-date-picker v-model="form.enrollmentDate" type="date" placeholder="选择入学年份"/>
        </el-form-item>
        <el-form-item label="毕业年份" prop="graduationDate">
          <el-date-picker v-model="form.graduationDate" type="date" placeholder="选择毕业年份"/>
        </el-form-item>
        <el-form-item label="就业地区" prop="workLocation">
          <el-cascader :options="regionData" v-model="form.workLocation" placeholder="选择就业地区">
          </el-cascader>
        </el-form-item>
        <el-form-item label="所在行业" prop="workField">
          <el-input v-model="form.workField" type="textarea" :rows="3" placeholder="请输入所在行业"/>
        </el-form-item>
        <el-form-item label="所在单位" prop="workPlace">
          <el-input v-model="form.workPlace" type="textarea" :rows="3" placeholder="请输入所在单位"/>
        </el-form-item>
        <el-form-item label="岗位类型" prop="jobType">
          <el-input v-model="form.jobType" type="textarea" :rows="3" placeholder="请输入岗位类型"/>
        </el-form-item>

      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAlumniInsertDialog = false">取消</el-button>
          <el-button type="primary" @click="submitInsertAlumni">确认</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 修改信息对话框 -->
    <el-dialog v-model="showAlumniUpdateDialog" @closed="onDialogClose" title="修改校友信息" width="800px">
      <el-form :model="form" :rules="formRules" ref="updateFormRef" label-width="100px">
        <el-form-item label="姓名">
          <el-input v-model="form.name" disabled/>
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="form.studentId" disabled/>
        </el-form-item>
        <el-form-item label="入学年份" prop="enrollmentDate">
          <el-date-picker v-model="form.enrollmentDate" type="date" placeholder="选择入学年份"/>
        </el-form-item>
        <el-form-item label="毕业年份" prop="graduationDate">
          <el-date-picker v-model="form.graduationDate" type="date" placeholder="选择毕业年份"/>
        </el-form-item>
        <el-form-item label="就业地区" prop="workLocation">
          <el-cascader :options="regionData" v-model="form.workLocation" placeholder="选择就业地区">
          </el-cascader>
        </el-form-item>
        <el-form-item label="所在行业" prop="workField">
          <el-input v-model="form.workField" type="textarea" :rows="3" placeholder="请输入所在行业"/>
        </el-form-item>
        <el-form-item label="所在单位" prop="workPlace">
          <el-input v-model="form.workPlace" type="textarea" :rows="3" placeholder="请输入所在单位"/>
        </el-form-item>
        <el-form-item label="岗位类型" prop="jobType">
          <el-input v-model="form.jobType" type="textarea" :rows="3" placeholder="请输入岗位类型"/>
        </el-form-item>

      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAlumniUpdateDialog = false">取消</el-button>
          <el-button type="primary" @click="submitUpdateAlumni">确认</el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup>
import {onMounted, reactive, ref} from 'vue'
import {Search} from '@element-plus/icons-vue'
import {updateAlumni, getMyAlumniInfo, insertAlumni} from '@/api/alumni'
import {CodeToText, regionData, TextToCode} from '@/utils/china-area-data.js'
import {dayjs, ElMessage} from "element-plus";
import {useStore} from "vuex";

let store = useStore()

const showAlumniInsertDialog = ref(false)
const showAlumniUpdateDialog = ref(false)
const insertFormRef = ref()
const updateFormRef = ref()

const form = reactive({
  studentId: '',
  name: '',
  enrollmentDate: null,
  graduationDate: null,
  workLocation: [],
  workField: '',
  workPlace: '',
  jobType: '',
})

const formRules = reactive({
  enrollmentDate: [
    {required: true, message: '请选择入学年份', trigger: 'change'},
  ],
  graduationDate: [
    {required: true, message: '请输入毕业年份', trigger: 'change'}
  ],
  workLocation: [
    {required: true, message: '请选择就业地区', trigger: 'change'}
  ],
  workField: [
    {required: true, message: '请输入所在行业', trigger: 'change'}
  ],
  workPlace: [
    {required: true, message: '请输入所在单位', trigger: 'change'}
  ],
  jobType: [
    {required: true, message: '请输入岗位类型', trigger: 'change'}
  ]
})

const alumniData= ref(null)

const loadData = async () => {
  form.studentId = store.getters.user.studentId
  form.name = store.getters.user.name
  try {
    let response = await getMyAlumniInfo();
    alumniData.value = response.data
  } catch (error) {
    console.error('加载数据失败:', error)
  }
}

const insertAlumniDetail = () => {
  showAlumniInsertDialog.value = true
}

const updateAlumniDetail = (alumni) => {
  form.studentId = alumni.studentId
  form.enrollmentDate = new Date(alumni.enrollmentDate)
  form.graduationDate = new Date(alumni.graduationDate)
  form.workLocation = [
    TextToCode[alumni.workLocation.province].code,
    TextToCode[alumni.workLocation.province][alumni.workLocation.city].code,
    TextToCode[alumni.workLocation.province][alumni.workLocation.city][alumni.workLocation.district].code,
  ]
  form.workField = alumni.workField
  form.workPlace = alumni.workPlace
  form.jobType = alumni.jobType
  showAlumniUpdateDialog.value = true
}

const submitUpdateAlumni = async () => {
  try {
    await updateFormRef.value.validate();
  } catch (fields) {
    console.log('error submit!', fields)
    return
  }

  if (form.enrollmentDate > form.graduationDate) {
    ElMessage.warning('毕业年份不能小于入学年份！')
    return;
  }

  let data = {
    studentId: form.studentId,
    name: form.name,
    enrollmentDate: dayjs(form.enrollmentDate).format('YYYY-MM-DD'),
    graduationDate: dayjs(form.graduationDate).format('YYYY-MM-DD'),
    workLocation: {
      'province': CodeToText[form.workLocation[0]],
      'city': CodeToText[form.workLocation[1]],
      'district': CodeToText[form.workLocation[2]]
    },
    workField: form.workField,
    workPlace: form.workPlace,
    jobType: form.jobType,
  }

  try {
    let response = await updateAlumni(data);
    if (response.code === 200) {
      ElMessage.success('修改成功')
    } else {
      ElMessage.error(response.message)
    }
    showAlumniUpdateDialog.value = false
  } catch (error) {
    ElMessage.error('修改失败')
  }
}

const submitInsertAlumni = async () => {
  try {
    await insertFormRef.value.validate();
  } catch (fields) {
    console.log('error submit!', fields)
    return
  }

  if (form.enrollmentDate > form.graduationDate) {
    ElMessage.warning('毕业年份不能小于入学年份！')
    return;
  }

  let data = {
    studentId: form.studentId,
    name: form.name,
    enrollmentDate: dayjs(form.enrollmentDate).format('YYYY-MM-DD'),
    graduationDate: dayjs(form.graduationDate).format('YYYY-MM-DD'),
    workLocation: {
      'province': CodeToText[form.workLocation[0]],
      'city': CodeToText[form.workLocation[1]],
      'district': CodeToText[form.workLocation[2]]
    },
    workField: form.workField,
    workPlace: form.workPlace,
    jobType: form.jobType,
  }

  try {
    let response = await insertAlumni(data);
    if (response.code === 200) {
      ElMessage.success('添加成功')
    } else {
      ElMessage.error(response.message)
    }
    showAlumniInsertDialog.value = false
  } catch (error) {
    ElMessage.error('添加失败')
  }
}

const onDialogClose = () => {
  form.name = ''
  form.studentId = ''
  form.enrollmentDate = null
  form.graduationDate = null
  form.workLocation = []
  form.workField = ''
  form.workPlace = ''
  form.jobType = ''
  loadData()
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
  font-size: 25px;
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
  width: 200px;
  flex-shrink: 0;
  font-size: 15px;
}

.value {
  color: #606266;
  font-size: 15px;
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