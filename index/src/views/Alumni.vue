/* eslint-disable vue/multi-word-component-names */
<template>
  <div class="alumni">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>校友信息</span>
          <div class="search-group">
            <el-input v-model="searchValue" placeholder="搜索校友姓名、学号或公司"
                      @keyup.enter="handleSearch()"
                      style="width: 250px; margin-right: 10px"
                      clearable>
              <template #prefix>
                <el-icon>
                  <Search/>
                </el-icon>
              </template>
            </el-input>
            <el-date-picker v-model="searchYear" type="year" placeholder="毕业年份"
                            style="width: 120px;margin-right: 10px"/>
            <el-button type="danger" @click="handleSearch()">搜索</el-button>
          </div>
        </div>
      </template>
      <el-table :data="alumniList" stripe style="width: 100%" table-layout="auto">
        <el-table-column prop="student.name" label="姓名" width="180"/>
        <el-table-column prop="studentId" label="学号" width="180"/>
        <el-table-column label="性别" width="180">
          <template #default="scope">
            <el-tag :type="scope.row.student.gender === '男' ? 'primary' : 'danger'" effect="light">
              {{ scope.row.student.gender }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="student.major" label="专业"/>
        <el-table-column prop="enrollmentDate" label="入学年份">
          <template #default="scope">
            <el-tag type="primary">{{ scope.row.enrollmentDate }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="graduationDate" label="毕业年份">
          <template #default="scope">
            <el-tag type="success">{{ scope.row.graduationDate }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="就业地区">
          <template #default="scope">
            {{
              scope.row.workLocation.province + " " + scope.row.workLocation.city + " " + scope.row.workLocation.district
            }}
          </template>
        </el-table-column>
        <el-table-column prop="workField" label="所在行业"/>
        <el-table-column prop="workPlace" label="所在单位"/>
        <el-table-column prop="jobType" label="岗位类型"/>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button type="primary" @click="showAlumniDetail(scope.row)">校友详情</el-button>
            <el-button type="warning" @click="updateAlumniDetail(scope.row)">修改信息</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
          style="display: flex; justify-content: center; margin-top: 30px;"
          v-model:current-page="pageData.currentPage"
          v-model:page-size="pageData.pageSize"
          :page-sizes="[10, 20, 35, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="pageData.totalCount"
          @size-change="loadData()"
          @current-change="loadData()"
      />
    </el-card>
    <!-- 校友详情对话框 -->
    <el-dialog v-model="showAlumniDetailDialog" title="校友详情" width="1200px">
      <el-descriptions border>
        <el-descriptions-item :rowspan="2" :width="140" label="照片" align="center">
          <el-image style="width: 100px; height: 100px"
                    src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"/>
        </el-descriptions-item>
        <el-descriptions-item label="姓名">{{ selectedAlumni.student.name }}</el-descriptions-item>
        <el-descriptions-item label="学号">{{ selectedAlumni.studentId }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ selectedAlumni.student.gender }}</el-descriptions-item>
        <el-descriptions-item label="民族">{{ selectedAlumni.student.nation }}</el-descriptions-item>
        <el-descriptions-item label="政治面貌">{{ selectedAlumni.student.politicalStatus }}</el-descriptions-item>
        <el-descriptions-item label="籍贯">{{ selectedAlumni.student.nativePlace }}</el-descriptions-item>
        <el-descriptions-item label="学制类型">{{ selectedAlumni.student.educationType }}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{ selectedAlumni.student.email }}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{ selectedAlumni.student.phone }}</el-descriptions-item>
        <el-descriptions-item label="专业">{{ selectedAlumni.student.major }}</el-descriptions-item>
        <el-descriptions-item label="年级">{{ selectedAlumni.student.grade }}</el-descriptions-item>
        <el-descriptions-item label="班级">{{ selectedAlumni.student.className }}</el-descriptions-item>
        <el-descriptions-item label="导师姓名">{{ selectedAlumni.student.supervisor }}</el-descriptions-item>
        <el-descriptions-item label="研究方向">{{ selectedAlumni.student.researchDirection }}</el-descriptions-item>
        <el-descriptions-item label="学籍状态">
          <el-tag v-if="selectedAlumni.student.status==='在读'" type="success">{{ selectedAlumni.student.status }}
          </el-tag>
          <el-tag v-else-if="selectedAlumni.student.status==='毕业'" type="primary">
            {{ selectedAlumni.student.status }}
          </el-tag>
          <el-tag v-else-if="selectedAlumni.student.status==='休学'" type="danger">{{ selectedAlumni.student.status }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="现住址">{{ selectedAlumni.student.address }}</el-descriptions-item>

        <el-descriptions-item label="入学年份">{{ selectedAlumni.enrollmentDate }}</el-descriptions-item>
        <el-descriptions-item label="毕业年份">{{ selectedAlumni.graduationDate }}</el-descriptions-item>
        <el-descriptions-item label="就业地区">{{
            selectedAlumni.workLocation.province + " " + selectedAlumni.workLocation.city + " " + selectedAlumni.workLocation.district
          }}
        </el-descriptions-item>
        <el-descriptions-item label="所在行业">{{ selectedAlumni.workField }}</el-descriptions-item>
        <el-descriptions-item label="所在单位">{{ selectedAlumni.workPlace }}</el-descriptions-item>
        <el-descriptions-item label="岗位类型">{{ selectedAlumni.jobType }}</el-descriptions-item>
      </el-descriptions>

      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showAlumniDetailDialog = false">取消</el-button>
          <el-button type="primary" @click="showAlumniDetailDialog = false">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 修改信息对话框 -->
    <el-dialog v-model="showAlumniUpdateDialog" @closed="onUpdateAlumniDialogClose" title="修改校友信息" width="800px">
      <el-form :model="updateForm" :rules="formRules" ref="updateFormRef" label-width="100px">
        <el-form-item label="校友姓名">
          <el-input v-model="updateForm.name" disabled/>
        </el-form-item>
        <el-form-item label="学号">
          <el-input v-model="updateForm.studentId" disabled/>
        </el-form-item>
        <el-form-item label="入学年份" prop="enrollmentDate">
          <el-date-picker v-model="updateForm.enrollmentDate" type="date" placeholder="选择入学年份"/>
        </el-form-item>
        <el-form-item label="毕业年份" prop="graduationDate">
          <el-date-picker v-model="updateForm.graduationDate" type="date" placeholder="选择毕业年份"/>
        </el-form-item>
        <el-form-item label="就业地区" prop="workLocation">
          <el-cascader :options="regionData" v-model="updateForm.workLocation" placeholder="选择就业地区">
          </el-cascader>
        </el-form-item>
        <el-form-item label="所在行业" prop="workField">
          <el-input v-model="updateForm.workField" type="textarea" :rows="3" placeholder="请输入所在行业"/>
        </el-form-item>
        <el-form-item label="所在单位" prop="workPlace">
          <el-input v-model="updateForm.workPlace" type="textarea" :rows="3" placeholder="请输入所在单位"/>
        </el-form-item>
        <el-form-item label="岗位类型" prop="jobType">
          <el-input v-model="updateForm.jobType" type="textarea" :rows="3" placeholder="请输入岗位类型"/>
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
import {getAlumniList, getTotalCount, updateAlumni} from '@/api/alumni'
import {CodeToText, regionData, TextToCode} from '@/utils/china-area-data.js'
import {dayjs, ElMessage} from "element-plus";

const showAlumniDetailDialog = ref(false)
const showAlumniUpdateDialog = ref(false)
const updateFormRef = ref()
const searchValue = ref('')
const searchYear = ref(null)
const selectedAlumni = ref(null)

const pageData = reactive({
  currentPage: 1,
  pageSize: 10,
  totalCount: 0,
})

const updateForm = reactive({
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

const alumniList = ref([])

const loadData = async () => {
  try {
    const [alumniData, totalCount] = await Promise.all([
      getAlumniList(searchValue.value, searchYear.value === null ? '' : searchYear.value.getFullYear(), pageData.currentPage, pageData.pageSize),
      getTotalCount(searchValue.value, searchYear.value === null ? '' : searchYear.value.getFullYear())
    ])
    alumniList.value = alumniData.data
    pageData.totalCount = totalCount.data
  } catch (error) {
    console.error('加载数据失败:', error)
  }
}

//弹出校友详细信息弹窗
const showAlumniDetail = (alumni) => {
  selectedAlumni.value = alumni
  showAlumniDetailDialog.value = true
}

const updateAlumniDetail = (alumni) => {
  updateForm.name = alumni.student.name
  updateForm.studentId = alumni.studentId
  updateForm.enrollmentDate = new Date(alumni.enrollmentDate)
  updateForm.graduationDate = new Date(alumni.graduationDate)
  updateForm.workLocation = [
    TextToCode[alumni.workLocation.province].code,
    TextToCode[alumni.workLocation.province][alumni.workLocation.city].code,
    TextToCode[alumni.workLocation.province][alumni.workLocation.city][alumni.workLocation.district].code,
  ]
  updateForm.workField = alumni.workField
  updateForm.workPlace = alumni.workPlace
  updateForm.jobType = alumni.jobType
  showAlumniUpdateDialog.value = true
}

const submitUpdateAlumni = async () => {
  try {
    await updateFormRef.value.validate();
  } catch (fields) {
    console.log('error submit!', fields)
    return
  }

  if (updateForm.enrollmentDate > updateForm.graduationDate) {
    ElMessage.warning('毕业年份不能小于入学年份！')
    return;
  }

  let data = {
    studentId: updateForm.studentId,
    name: updateForm.name,
    enrollmentDate: dayjs(updateForm.enrollmentDate).format('YYYY-MM-DD'),
    graduationDate: dayjs(updateForm.graduationDate).format('YYYY-MM-DD'),
    workLocation: {
      'province': CodeToText[updateForm.workLocation[0]],
      'city': CodeToText[updateForm.workLocation[1]],
      'district': CodeToText[updateForm.workLocation[2]]
    },
    workField: updateForm.workField,
    workPlace: updateForm.workPlace,
    jobType: updateForm.jobType,
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

const onUpdateAlumniDialogClose = () => {
  updateForm.name = ''
  updateForm.studentId = ''
  updateForm.enrollmentDate = null
  updateForm.graduationDate = null
  updateForm.workLocation = []
  updateForm.workField = ''
  updateForm.workPlace = ''
  updateForm.jobType = ''
  loadData()
}

const handleSearch = () => {
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