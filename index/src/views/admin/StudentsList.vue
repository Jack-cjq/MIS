<template>
  <div class="students-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>学生管理</span>
          <el-button type="primary" @click="handleAdd">添加学生</el-button>
        </div>
      </template>
      
      <!-- 搜索栏 -->
      <div class="search-bar">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-input
              v-model="searchForm.name"
              placeholder="请输入学生姓名"
              clearable
              @keyup.enter="handleSearch"
            />
          </el-col>
          <el-col :span="6">
            <el-input
              v-model="searchForm.studentId"
              placeholder="请输入学号"
              clearable
              @keyup.enter="handleSearch"
            />
          </el-col>
          <el-col :span="6">
            <el-select v-model="searchForm.major" placeholder="请选择专业" clearable>
              <el-option label="计算机科学与技术" value="计算机科学与技术" />
              <el-option label="软件工程" value="软件工程" />
              <el-option label="信息安全" value="信息安全" />
            </el-select>
          </el-col>
          <el-col :span="6">
            <el-button type="primary" @click="handleSearch">搜索</el-button>
            <el-button @click="handleReset">重置</el-button>
          </el-col>
        </el-row>
      </div>
      
      <!-- 学生列表 -->
      <el-table
        :data="students"
        v-loading="loading"
        style="width: 100%"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="studentId" label="学号" width="120" />
        <el-table-column prop="name" label="姓名" width="100" />
        <el-table-column prop="gender" label="性别" width="60" />
        <el-table-column prop="age" label="年龄" width="60" />
        <el-table-column prop="major" label="专业" width="150" />
        <el-table-column prop="grade" label="年级" width="100" />
        <el-table-column prop="className" label="班级" width="120" />
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="email" label="邮箱" width="200" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="scope">
            <el-tag :type="scope.row.status === '在读' ? 'success' : 'info'">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          :current-page="pagination.currentPage"
          :page-size="pagination.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
    
    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="800px"
      @close="handleDialogClose"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="学号" prop="studentId">
              <el-input v-model="form.studentId" placeholder="请输入学号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择性别">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年龄" prop="age">
              <el-input-number v-model="form.age" :min="0" :max="100" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="专业" prop="major">
              <el-select v-model="form.major" placeholder="请选择专业">
                <el-option label="计算机科学与技术" value="计算机科学与技术" />
                <el-option label="软件工程" value="软件工程" />
                <el-option label="信息安全" value="信息安全" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="年级" prop="grade">
              <el-input v-model="form.grade" placeholder="请输入年级" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="班级" prop="className">
              <el-input v-model="form.className" placeholder="请输入班级" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="form.email" placeholder="请输入邮箱" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态">
                <el-option label="在读" value="在读" />
                <el-option label="毕业" value="毕业" />
                <el-option label="休学" value="休学" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="密码" prop="password" v-if="!form.id">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { getStudentList, createStudent, updateStudent, deleteStudent } from '@/api/student'

const loading = ref(false)
const students = ref([])
const selectedStudents = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref()

// 搜索表单
const searchForm = reactive({
  name: '',
  studentId: '',
  major: ''
})

// 分页
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 表单
const form = reactive({
  id: '',
  studentId: '',
  name: '',
  gender: '',
  age: 0,
  major: '',
  grade: '',
  className: '',
  phone: '',
  email: '',
  status: '',
  password: ''
})

// 表单验证规则
const rules = {
  studentId: [
    { required: true, message: '请输入学号', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  major: [
    { required: true, message: '请选择专业', trigger: 'change' }
  ],
  grade: [
    { required: true, message: '请输入年级', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

// 加载学生列表
const loadStudents = async () => {
  loading.value = true
  try {
    const res = await getStudentList()
    if (res.code === 200) {
      students.value = res.data || []
      pagination.total = students.value.length
    }
  } catch (error) {
    ElMessage.error('加载学生列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  pagination.currentPage = 1
  loadStudents()
}

// 重置搜索
const handleReset = () => {
  Object.assign(searchForm, {
    name: '',
    studentId: '',
    major: ''
  })
  handleSearch()
}

// 选择变化
const handleSelectionChange = (selection) => {
  selectedStudents.value = selection
}

// 添加学生
const handleAdd = () => {
  dialogTitle.value = '添加学生'
  Object.assign(form, {
    id: '',
    studentId: '',
    name: '',
    gender: '',
    age: 0,
    major: '',
    grade: '',
    className: '',
    phone: '',
    email: '',
    status: '在读',
    password: ''
  })
  dialogVisible.value = true
}

// 编辑学生
const handleEdit = (row) => {
  dialogTitle.value = '编辑学生'
  Object.assign(form, row)
  dialogVisible.value = true
}

// 删除学生
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除该学生吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const res = await deleteStudent(row.id)
    if (res.code === 200) {
      ElMessage.success('删除成功')
      loadStudents()
    } else {
      ElMessage.error(res.msg || '删除失败')
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

// 提交表单
const handleSubmit = async () => {
  try {
    await formRef.value.validate()
    
    let res
    if (form.id) {
      res = await updateStudent(form)
    } else {
      res = await createStudent(form)
    }
    
    if (res.code === 200) {
      ElMessage.success(form.id ? '更新成功' : '添加成功')
      dialogVisible.value = false
      loadStudents()
    } else {
      ElMessage.error(res.msg || '操作失败')
    }
  } catch (error) {
    ElMessage.error('表单验证失败')
  }
}

// 对话框关闭
const handleDialogClose = () => {
  formRef.value?.resetFields()
}

// 分页大小变化
const handleSizeChange = (size) => {
  pagination.pageSize = size
  loadStudents()
}

// 当前页变化
const handleCurrentChange = (page) => {
  pagination.currentPage = page
  loadStudents()
}

onMounted(() => {
  loadStudents()
})
</script>

<style scoped>
.students-list {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.search-bar {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  text-align: right;
}

.dialog-footer {
  text-align: right;
}
</style> 