<template>
  <div class="admins-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>管理员管理</span>
          <el-button type="primary" @click="handleAdd">添加管理员</el-button>
        </div>
      </template>
      
      <!-- 管理员列表 -->
      <el-table
        :data="admins"
        v-loading="loading"
        style="width: 100%"
      >
        <el-table-column prop="adminId" label="管理员ID" width="120" />
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="realName" label="真实姓名" width="120" />
        <el-table-column prop="email" label="邮箱" width="200" />
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="role" label="角色" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.role === 'super_admin' ? 'danger' : 'primary'">
              {{ scope.row.role === 'super_admin' ? '超级管理员' : '普通管理员' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="department" label="部门" width="150" />
        <el-table-column prop="position" label="职位" width="120" />
        <el-table-column prop="isActive" label="状态" width="80">
          <template #default="scope">
            <el-tag :type="scope.row.isActive ? 'success' : 'info'">
              {{ scope.row.isActive ? '激活' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="lastLoginTime" label="最后登录" width="160">
          <template #default="scope">
            {{ formatTime(scope.row.lastLoginTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button 
              size="small" 
              :type="scope.row.isActive ? 'warning' : 'success'"
              @click="handleToggleStatus(scope.row)"
            >
              {{ scope.row.isActive ? '禁用' : '激活' }}
            </el-button>
            <el-button 
              size="small" 
              type="danger" 
              @click="handleDelete(scope.row)"
              :disabled="scope.row.role === 'super_admin'"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- 添加/编辑对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="600px"
      @close="handleDialogClose"
    >
      <el-form
        ref="formRef"
        :model="form"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" :disabled="!!form.id" />
        </el-form-item>
        
        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="form.realName" />
        </el-form-item>
        
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" />
        </el-form-item>
        
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="form.phone" />
        </el-form-item>
        
        <el-form-item label="角色" prop="role">
          <el-select v-model="form.role" :disabled="!!form.id">
            <el-option label="超级管理员" value="super_admin" />
            <el-option label="普通管理员" value="admin" />
          </el-select>
        </el-form-item>
        
        <el-form-item label="部门" prop="department">
          <el-input v-model="form.department" />
        </el-form-item>
        
        <el-form-item label="职位" prop="position">
          <el-input v-model="form.position" />
        </el-form-item>
        
        <el-form-item label="密码" prop="password" v-if="!form.id">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" />
        </el-form-item>
        
        <el-form-item label="备注" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            :rows="3"
            placeholder="请输入备注信息"
          />
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
import { 
  getAdminList, 
  createAdmin, 
  updateAdmin, 
  deleteAdmin, 
  updateAdminStatus 
} from '@/api/admin'

const loading = ref(false)
const admins = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref()

// 表单
const form = reactive({
  id: '',
  username: '',
  realName: '',
  email: '',
  phone: '',
  role: '',
  department: '',
  position: '',
  password: '',
  remark: ''
})

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  realName: [
    { required: true, message: '请输入真实姓名', trigger: 'blur' }
  ],
  email: [
    { required: true, message: '请输入邮箱', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号格式', trigger: 'blur' }
  ],
  role: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

// 格式化时间
const formatTime = (time) => {
  if (!time) return '-'
  const date = new Date(time)
  return date.toLocaleString()
}

// 加载管理员列表
const loadAdmins = async () => {
  loading.value = true
  try {
    const res = await getAdminList()
    if (res.code === 200) {
      admins.value = res.data || []
    }
  } catch (error) {
    ElMessage.error('加载管理员列表失败')
  } finally {
    loading.value = false
  }
}

// 添加管理员
const handleAdd = () => {
  dialogTitle.value = '添加管理员'
  Object.assign(form, {
    id: '',
    username: '',
    realName: '',
    email: '',
    phone: '',
    role: '',
    department: '',
    position: '',
    password: '',
    remark: ''
  })
  dialogVisible.value = true
}

// 编辑管理员
const handleEdit = (row) => {
  dialogTitle.value = '编辑管理员'
  Object.assign(form, row)
  dialogVisible.value = true
}

// 切换状态
const handleToggleStatus = async (row) => {
  try {
    const action = row.isActive ? '禁用' : '激活'
    await ElMessageBox.confirm(`确定要${action}该管理员吗？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const res = await updateAdminStatus(row.adminId, !row.isActive)
    if (res.code === 200) {
      ElMessage.success(`${action}成功`)
      loadAdmins()
    } else {
      ElMessage.error(res.msg || `${action}失败`)
    }
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('操作失败')
    }
  }
}

// 删除管理员
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确定要删除该管理员吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    
    const res = await deleteAdmin(row.adminId)
    if (res.code === 200) {
      ElMessage.success('删除成功')
      loadAdmins()
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
      res = await updateAdmin(form)
    } else {
      res = await createAdmin(form)
    }
    
    if (res.code === 200) {
      ElMessage.success(form.id ? '更新成功' : '添加成功')
      dialogVisible.value = false
      loadAdmins()
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

onMounted(() => {
  loadAdmins()
})
</script>

<style scoped>
.admins-management {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.dialog-footer {
  text-align: right;
}
</style> 