<template>
  <div class="alumni-add">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>添加校友</span>
        </div>
      </template>

      <el-form ref="formRef" :model="form" :rules="formRules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" />
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
            <el-form-item label="入学年份" prop="enrollmentDate">
              <el-date-picker v-model="form.enrollmentDate" type="date" placeholder="选择入学年份"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="毕业年份" prop="graduationDate">
              <el-date-picker v-model="form.graduationDate" type="date" placeholder="选择毕业年份"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="就业地区" prop="workLocation">
          <el-cascader :options="regionData" v-model="form.workLocation" placeholder="选择就业地区"/>
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

        <el-form-item>
          <el-button type="primary" @click="handleSubmit">保存</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import {dayjs, ElMessage} from 'element-plus'
import {CodeToText, regionData} from "@/utils/china-area-data";
import {insertAlumniByAdmin} from "@/api/alumni";

const formRef = ref()
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
  name: [
    {required: true, message: '请输入姓名', trigger: 'blur'}
  ],
  studentId: [
    {required: true, message: '请输入学号', trigger: 'blur'}
  ],
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

const handleSubmit  = async () => {
  try {
    await formRef.value.validate();
  } catch (fields) {
    console.log('error submit!', fields)
    return
  }

  if (form.enrollmentDate > form.graduationDate) {
    ElMessage.warning('毕业年份不能小于入学年份！')
    return;
  }

  let data = {
    student: {
      'studentId': form.studentId,
      'name': form.name,
    },
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
    let response = await insertAlumniByAdmin(data);
    if (response.code === 200) {
      ElMessage.success('添加成功')
    } else {
      ElMessage.error(response.message)
    }
  } catch (error) {
    ElMessage.error('添加失败')
  }
}

const handleReset = () => {
  formRef.value?.resetFields()
}
</script>

<style scoped>
.alumni-add {
  padding: 20px;
}

.card-header {
  font-weight: 500;
  color: #303133;
}
</style> 