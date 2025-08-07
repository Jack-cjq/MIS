# MIS - 学生信息管理系统

Hubei University of Technology Information Construction and Development Group

## 📋 项目概述

本项目是一个基于Spring Boot + Vue 3的全栈学生信息管理系统，采用前后端分离架构，实现了学生和管理员双角色权限管理。

## 🏗️ 技术架构

### 后端技术栈
- **Spring Boot 2.7.x** - 主框架
- **MongoDB** - 数据库
- **Redis** - 缓存
- **Spring Security** - 安全框架
- **JWT** - 无状态认证
- **Knife4j** - API文档
- **Maven** - 依赖管理

### 前端技术栈
- **Vue 3** - 前端框架
- **Element Plus** - UI组件库
- **Vue Router** - 路由管理
- **Vuex** - 状态管理
- **Axios** - HTTP客户端
- **Echarts** - 数据可视化

## 🔐 JWT Token认证系统

### 完整的JWT实现
本项目已完整实现JWT Token认证系统：

#### **后端JWT功能**
- ✅ **JWT工具类** - 完整的token生成、验证、解析功能
- ✅ **JWT过滤器** - 自动验证请求中的Bearer token
- ✅ **Token刷新** - 支持token自动刷新机制
- ✅ **安全配置** - Spring Security集成JWT认证
- ✅ **登录接口** - 返回JWT token而非完整用户信息

#### **前端JWT功能**
- ✅ **Token存储** - localStorage安全存储JWT token
- ✅ **请求拦截** - 自动添加Authorization头
- ✅ **响应拦截** - 401自动跳转登录页
- ✅ **Token刷新** - 自动刷新即将过期的token
- ✅ **路由守卫** - 基于JWT的路由权限控制

#### **安全特性**
- 🔒 **无状态认证** - 完全符合JWT无状态设计
- 🔒 **Token过期控制** - 24小时自动过期
- 🔒 **自动刷新** - 提前5分钟自动刷新token
- 🔒 **安全存储** - 本地仅存储token，用户信息动态获取
- 🔒 **权限验证** - 基于用户类型的路由权限控制

### 认证流程

1. **用户登录** → 后端验证凭据 → 生成JWT token → 返回token
2. **前端存储** → localStorage存储token → 设置过期时间
3. **API请求** → 请求拦截器添加Bearer token → 发送请求
4. **后端验证** → JWT过滤器验证token → 解析用户信息
5. **Token刷新** → 检测即将过期 → 自动刷新token
6. **权限控制** → 路由守卫验证 → 基于用户类型跳转

### API接口

#### 学生认证
- `POST /msi/student/login` - 学生登录
- `GET /msi/student/current-user` - 获取当前学生信息
- `POST /msi/student/refresh-token` - 刷新学生token

#### 管理员认证
- `POST /msi/admin/login` - 管理员登录
- `GET /msi/admin/current-user` - 获取当前管理员信息
- `POST /msi/admin/refresh-token` - 刷新管理员token

## 🔐 安全存储策略

### 最安全的技术方案
我们采用了当前最安全的无状态存储方案：

#### **localStorage** (本地存储)
```javascript
// 仅存储JWT认证令牌
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2ODViY2NhNDUyN2ExMzBhYTViZjg...",
  "token_expire": "1703123456789"
}
```

#### **内存存储** (临时存储)
```javascript
// 用户信息仅在内存中临时保存，页面刷新后重新获取
{
  "user": null // 不存储任何用户信息到本地
}
```

#### **完全无状态设计**
- ✅ **零本地存储** - 不存储任何用户信息到本地
- ✅ **JWT Token认证** - 使用JWT作为唯一身份标识
- ✅ **API动态获取** - 每次需要用户信息时从后端获取
- ✅ **页面刷新恢复** - 通过API自动恢复用户信息

#### **不存储的敏感信息**
- ❌ 用户ID
- ❌ 学号
- ❌ 姓名
- ❌ 邮箱地址
- ❌ 手机号码
- ❌ 家庭住址
- ❌ 身份证号
- ❌ 密码信息
- ❌ 其他任何个人隐私数据

### 安全优势
1. **零隐私泄露** - 本地不存储任何用户信息
2. **XSS完全防护** - 恶意脚本无法获取用户数据
3. **设备共享安全** - 设备共享时完全无隐私泄露
4. **JWT无状态认证** - 符合现代安全标准
5. **动态信息获取** - 敏感信息实时从后端获取

## 👥 角色权限管理

### 学生端功能
- **个人信息管理** - 查看和编辑个人基本信息
- **实习就业管理** - 申请和管理实习就业记录
- **科创竞赛管理** - 参与和记录竞赛信息
- **校友管理** - 查看校友信息
- **党员管理** - 党员相关信息管理
- **日常管理** - 日常事务管理

### 管理员端功能
- **全局数据管理** - 管理所有学生信息
- **实习就业审核** - 审核学生实习就业申请
- **统计分析** - 查看系统统计数据
- **用户管理** - 管理学生和管理员账户
- **系统监控** - 监控系统运行状态

## 💼 实习就业模块完整功能

### 学生端功能

#### 1. 实习就业申请
- **基本信息填写**
  - 实习/就业类型选择
  - 公司信息录入
  - 职位描述
  - 薪资待遇
  - 工作时间

- **技术栈记录**
  - 编程语言
  - 开发框架
  - 数据库技术
  - 云平台
  - 开发工具

- **附件上传**
  - 实习协议
  - 相关证书
  - 项目文档

#### 2. 记录管理
- **查看申请状态** - 实时查看审核进度
- **编辑申请信息** - 修改未审核的申请
- **删除申请** - 删除不需要的记录
- **详情查看** - 查看完整的申请详情

#### 3. 搜索筛选
- **多条件搜索** - 按公司、职位、状态等筛选
- **时间范围筛选** - 按申请时间筛选
- **状态筛选** - 按审核状态筛选

### 管理员端功能

#### 1. 全局管理
- **查看所有申请** - 浏览所有学生的实习就业记录
- **批量操作** - 批量审核、导出等操作
- **高级搜索** - 多维度搜索和筛选

#### 2. 审核功能
- **审核申请** - 审核学生提交的实习就业申请
- **状态管理** - 设置审核状态（待审核/通过/拒绝）
- **审核意见** - 添加审核意见和反馈

#### 3. 统计分析
- **数据统计** - 实习就业数据统计
- **趋势分析** - 就业趋势图表展示
- **分类统计** - 按专业、年级等分类统计

#### 4. 数据导出
- **Excel导出** - 导出实习就业数据
- **报表生成** - 生成统计报表
- **数据备份** - 数据备份功能

### 数据模型

#### 实习就业记录字段
```javascript
{
  // 基本信息
  id: "记录ID",
  userId: "用户ID",
  studentId: "学号",
  studentName: "学生姓名",
  studentClass: "班级",
  major: "专业",
  grade: "年级",
  
  // 实习就业信息
  type: "类型(实习/就业)",
  category: "类别",
  company: "公司名称",
  companyType: "公司类型",
  industry: "行业",
  companySize: "公司规模",
  companyLocation: "公司地址",
  position: "职位",
  department: "部门",
  workLocation: "工作地点",
  workType: "工作类型",
  
  // 薪资信息
  salary: "薪资",
  salaryType: "薪资类型",
  salaryRange: "薪资范围",
  
  // 时间信息
  startDate: "开始日期",
  endDate: "结束日期",
  duration: "持续时间",
  
  // 状态信息
  status: "状态",
  contractType: "合同类型",
  approvalStatus: "审核状态",
  
  // 技术信息
  technologyStack: ["技术栈"],
  programmingLanguages: "编程语言",
  frameworks: ["框架"],
  databases: ["数据库"],
  cloudPlatforms: ["云平台"],
  developmentTools: ["开发工具"],
  
  // 详细描述
  projectType: "项目类型",
  benefits: "福利待遇",
  jobDescription: "工作描述",
  responsibilities: "工作职责",
  achievements: "工作成果",
  learningOutcomes: "学习收获",
  skills: "技能提升",
  
  // 联系信息
  contactPerson: "联系人",
  contactPhone: "联系电话",
  contactEmail: "联系邮箱",
  supervisorName: "导师姓名",
  supervisorContact: "导师联系方式",
  
  // 评价信息
  studentEvaluation: "学生评价",
  companyEvaluation: "公司评价",
  supervisorEvaluation: "导师评价",
  satisfactionScore: "满意度评分",
  
  // 其他信息
  remark: "备注",
  attachments: "附件",
  tags: "标签",
  
  // 时间戳
  createTime: "创建时间",
  updateTime: "更新时间"
}
```

## 🚀 快速开始

### 环境要求
- JDK 8+
- Node.js 16+
- MongoDB 4.4+
- Redis 6.0+

### 后端启动
```bash
cd disciplinary_construction
mvn spring-boot:run
```

### 前端启动
```bash
cd index
npm install
npm run serve
```

### 访问地址
- **前端**: http://localhost:8080
- **后端API**: http://localhost:1010
- **API文档**: http://localhost:1010/doc.html

## 📝 开发说明

### JWT配置
JWT相关配置在 `application.yml` 中：
```yaml
jwt:
  secret: your-secret-key-must-be-at-least-256-bits-long-for-production-use
  expiration: 86400000  # 24小时
  refresh-threshold: 300000  # 5分钟刷新阈值
```

### 安全建议
1. **生产环境** - 修改JWT密钥为强密钥
2. **HTTPS** - 生产环境必须使用HTTPS
3. **密钥管理** - 使用环境变量管理JWT密钥
4. **日志记录** - 记录认证相关操作日志

## 📄 许可证

本项目采用 MIT 许可证。
