<template>
  <div class="competition">
    <!-- 统计卡片 -->
    <div class="stats-container">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-card class="stats-card">
            <div class="stats-content">
              <el-icon class="stats-icon competition-icon"><Trophy /></el-icon>
              <div class="stats-info">
                <div class="stats-number">{{ stats.competitions }}</div>
                <div class="stats-label">学科竞赛</div>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stats-card">
            <div class="stats-content">
              <el-icon class="stats-icon paper-icon"><Document /></el-icon>
              <div class="stats-info">
                <div class="stats-number">{{ stats.papers }}</div>
                <div class="stats-label">发表文章</div>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stats-card">
            <div class="stats-content">
              <el-icon class="stats-icon patent-icon"><Operation /></el-icon>
              <div class="stats-info">
                <div class="stats-number">{{ stats.patents }}</div>
                <div class="stats-label">专利</div>
              </div>
            </div>
          </el-card>
        </el-col>
        <el-col :span="6">
          <el-card class="stats-card">
            <div class="stats-content">
              <el-icon class="stats-icon project-icon"><Files /></el-icon>
              <div class="stats-info">
                <div class="stats-number">{{ stats.projects }}</div>
                <div class="stats-label">项目</div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <!-- 主要内容 -->
    <el-card class="main-card">
      <el-tabs v-model="activeTab" @tab-change="handleTabChange">
        <!-- 学科竞赛标签页 -->
        <el-tab-pane label="学科竞赛" name="competition">
          <div class="tab-content">
            <div class="action-bar">
              <el-button type="primary" @click="openCompetitionDialog()">
                <el-icon><Plus /></el-icon>
                添加竞赛
              </el-button>
            </div>
            <el-table :data="competitionList" style="width: 100%" v-loading="loading">
              <el-table-column prop="competitionName" label="竞赛名称" />
              <el-table-column prop="competitionLevel" label="竞赛级别" width="120" />
              <el-table-column prop="awardLevel" label="获奖等级" width="120" />
              <el-table-column prop="ranking" label="排名" width="80" />
              <el-table-column prop="awardDate" label="获奖时间" width="120" />
              <el-table-column prop="auditStatus" label="审核状态" width="100">
                <template #default="scope">
                  <el-tag :type="getStatusType(scope.row.auditStatus)">
                    {{ scope.row.auditStatus || '待审核' }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="250">
                <template #default="scope">
                  <el-button size="small" @click="viewDetail(scope.row, 'competition')">详情</el-button>
                  <el-button size="small" type="primary" @click="editItem(scope.row, 'competition')">编辑</el-button>
                  <el-button size="small" type="danger" @click="deleteItem(scope.row.id, 'competition')">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>

        <!-- 发表文章标签页 -->
        <el-tab-pane label="发表文章" name="paper">
          <div class="tab-content">
            <div class="action-bar">
              <el-button type="primary" @click="openPaperDialog()">
                <el-icon><Plus /></el-icon>
                添加文章
              </el-button>
            </div>
            <el-table :data="paperList" style="width: 100%" v-loading="loading">
              <el-table-column prop="paperTitle" label="论文题目" />
              <el-table-column prop="journalName" label="会议/期刊名称" width="150" />
              <el-table-column prop="journalLevel" label="会议/期刊级别" width="100" />
              <el-table-column prop="authorRank" label="作者排名" width="80" />
              <el-table-column prop="publicationStatus" label="发表状态" width="100" />
              <el-table-column prop="auditStatus" label="审核状态" width="100">
                <template #default="scope">
                  <el-tag :type="getStatusType(scope.row.auditStatus)">
                    {{ scope.row.auditStatus || '待审核' }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="250">
                <template #default="scope">
                  <el-button size="small" @click="viewDetail(scope.row, 'paper')">详情</el-button>
                  <el-button size="small" type="primary" @click="editItem(scope.row, 'paper')">编辑</el-button>
                  <el-button size="small" type="danger" @click="deleteItem(scope.row.id, 'paper')">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>

        <!-- 专利标签页 -->
        <el-tab-pane label="专利" name="patent">
          <div class="tab-content">
            <div class="action-bar">
              <el-button type="primary" @click="openPatentDialog()">
                <el-icon><Plus /></el-icon>
                添加专利
              </el-button>
            </div>
            <el-table :data="patentList" style="width: 100%" v-loading="loading">
              <el-table-column prop="patentTitle" label="专利名称" />
              <el-table-column prop="patentType" label="专利类型" width="120" />
              <el-table-column prop="patentNumber" label="专利号" width="150" />
              <el-table-column prop="inventorRank" label="发明人排名" width="100" />
              <el-table-column prop="patentStatus" label="专利状态" width="100" />
              <el-table-column prop="auditStatus" label="审核状态" width="100">
                <template #default="scope">
                  <el-tag :type="getStatusType(scope.row.auditStatus)">
                    {{ scope.row.auditStatus || '待审核' }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="250">
                <template #default="scope">
                  <el-button size="small" @click="viewDetail(scope.row, 'patent')">详情</el-button>
                  <el-button size="small" type="primary" @click="editItem(scope.row, 'patent')">编辑</el-button>
                  <el-button size="small" type="danger" @click="deleteItem(scope.row.id, 'patent')">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>

        <!-- 项目标签页 -->
        <el-tab-pane label="项目" name="project">
          <div class="tab-content">
            <div class="action-bar">
              <el-button type="primary" @click="openProjectDialog()">
                <el-icon><Plus /></el-icon>
                添加项目
              </el-button>
            </div>
            <el-table :data="projectList" style="width: 100%" v-loading="loading">
              <el-table-column prop="projectName" label="项目名称" />
              <el-table-column prop="projectLevel" label="项目级别" width="120" />
              <el-table-column prop="ranking" label="排名" width="80" />
              <el-table-column prop="startDate" label="开始时间" width="120" />
              <el-table-column prop="endDate" label="结束时间" width="120" />
              <el-table-column prop="auditStatus" label="审核状态" width="100">
                <template #default="scope">
                  <el-tag :type="getStatusType(scope.row.auditStatus)">
                    {{ scope.row.auditStatus || '待审核' }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="250">
                <template #default="scope">
                  <el-button size="small" @click="viewDetail(scope.row, 'project')">详情</el-button>
                  <el-button size="small" type="primary" @click="editItem(scope.row, 'project')">编辑</el-button>
                  <el-button size="small" type="danger" @click="deleteItem(scope.row.id, 'project')">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-card>

    <!-- 学科竞赛对话框 -->
    <el-dialog v-model="competitionDialog" :title="dialogTitle" width="600px">
      <el-form :model="competitionForm" :rules="competitionRules" ref="competitionFormRef" label-width="120px">
        <el-form-item label="竞赛名称" prop="competitionName">
          <el-autocomplete
              v-model="competitionForm.competitionName"
              :fetch-suggestions="queryCompetitions"
              placeholder="请输入竞赛名称关键词"
              style="width: 100%"
              :trigger-on-focus="false"
              @select="handleCompetitionSelect"
              @input="handleCompetitionInput"
              clearable
          >
            <template #default="{ item }">
              <div class="competition-item">
                <div class="competition-name">{{ item.value }}</div>
                <div class="competition-level">{{ item.level }}</div>
              </div>
            </template>
          </el-autocomplete>
        </el-form-item>

        <el-form-item label="竞赛级别" prop="competitionLevel">
          <el-select v-model="competitionForm.competitionLevel" placeholder="请选择竞赛级别" style="width: 100%">
            <el-option label="重大赛事" value="重大赛事" />
            <el-option label="重点赛事" value="重点赛事" />
            <el-option label="一般赛事" value="一般赛事" />
          </el-select>
        </el-form-item>

        <el-form-item label="获奖等级" prop="awardLevel">
          <el-select v-model="competitionForm.awardLevel" placeholder="请选择获奖等级" style="width: 100%">
            <el-option label="国家级特等奖" value="国家级特等奖" />
            <el-option label="国家级一等奖" value="国家级一等奖" />
            <el-option label="国家级二等奖" value="国家级二等奖" />
            <el-option label="国家级三等奖" value="国家级三等奖" />
            <el-option label="省级特等奖" value="省级特等奖" />
            <el-option label="省级一等奖" value="省级一等奖" />
            <el-option label="省级二等奖" value="省级二等奖" />
            <el-option label="省级三等奖" value="省级三等奖" />
            <el-option label="市级一等奖" value="市级一等奖" />
            <el-option label="市级二等奖" value="市级二等奖" />
            <el-option label="市级三等奖" value="市级三等奖" />
            <el-option label="校级一等奖" value="校级一等奖" />
            <el-option label="校级二等奖" value="校级二等奖" />
            <el-option label="校级三等奖" value="校级三等奖" />
          </el-select>
        </el-form-item>

        <el-form-item label="排名" prop="ranking">
          <el-input-number
              v-model="competitionForm.ranking"
              :min="1"
              :max="999"
              placeholder="请输入排名"
              style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="获奖时间" prop="awardDate">
          <el-date-picker
              v-model="competitionForm.awardDate"
              type="date"
              placeholder="选择获奖时间"
              style="width: 100%"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
          />
        </el-form-item>

        <el-form-item label="团队成员">
          <el-input
              v-model="competitionForm.teamMembers"
              placeholder="多个成员用逗号分隔，如：张三,李四,王五"
              type="textarea"
              :rows="2"
          />
        </el-form-item>

        <el-form-item label="指导老师" prop="instructorName">
          <el-input v-model="competitionForm.instructorName" placeholder="请输入指导老师姓名" />
        </el-form-item>

        <el-form-item label="证明材料">
          <el-upload
              class="upload-demo"
              :action="uploadUrl"
              :on-success="(response, file, fileList) => handleUploadSuccess(response, file, fileList, 'competition')"
              :on-error="handleUploadError"
              :before-upload="beforeUpload"
              :file-list="competitionFileList"
              multiple
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <template #tip>
              <div class="el-upload__tip">
                支持 jpg/png/pdf/doc/docx 格式，单个文件大小不超过 10MB
              </div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="competitionDialog = false">取消</el-button>
        <el-button type="primary" @click="submitCompetition" :loading="submitLoading">确定</el-button>
      </template>
    </el-dialog>

    <!-- 论文对话框 -->
    <el-dialog v-model="paperDialog" :title="dialogTitle" width="600px">
      <el-form :model="paperForm" :rules="paperRules" ref="paperFormRef" label-width="120px">
        <el-form-item label="论文题目" prop="paperTitle">
          <el-input v-model="paperForm.paperTitle" placeholder="请输入论文题目" />
        </el-form-item>
        <el-form-item label="会议/期刊名称" prop="journalName">
          <el-input v-model="paperForm.journalName" placeholder="请输入会议/期刊名称" />
        </el-form-item>
        <el-form-item label="会议/期刊级别" prop="journalLevel">
          <el-select v-model="paperForm.journalLevel" placeholder="请选择会议/期刊级别" style="width: 100%">
            <el-option label="CCFA会议" value="CCFA会议" />
            <el-option label="SCI、SSCI（一区）" value="SCI、SSCI（一区）" />
            <el-option label="SCI、SSCI（二区）/CCFB会议" value="SCI、SSCI（二区）/CCFB会议" />
            <el-option label="SCI、SSCI（三区、四区）" value="SCI、SSCI（三区、四区）" />
            <el-option label="南大核心" value="南大核心" />
            <el-option label="北大核心/ISTP会议" value="北大核心/ISTP会议" />
            <el-option label="EI会议/湖北工业大学学报" value="EI会议/湖北工业大学学报" />
          </el-select>
        </el-form-item>
        <el-form-item label="发表状态" prop="publicationStatus">
          <el-select v-model="paperForm.publicationStatus" placeholder="请选择发表状态" style="width: 100%">
            <el-option label="已见刊" value="已见刊" />
            <el-option label="已接收" value="已接收" />
            <el-option label="审稿中" value="审稿中" />
          </el-select>
        </el-form-item>
        <el-form-item label="见刊时间" prop="publicationDate" v-if="paperForm.publicationStatus === '已见刊'">
          <el-date-picker
              v-model="paperForm.publicationDate"
              type="date"
              placeholder="选择见刊时间"
              style="width: 100%"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="接收时间" prop="acceptanceDate" v-if="paperForm.publicationStatus === '已接收'">
          <el-date-picker
              v-model="paperForm.acceptanceDate"
              type="date"
              placeholder="选择接收时间"
              style="width: 100%"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="作者排名" prop="authorRank">
          <el-input-number
              v-model="paperForm.authorRank"
              :min="1"
              :max="99"
              placeholder="请输入作者排名"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="合作作者" prop="coAuthors">
          <el-input v-model="paperForm.coAuthors" placeholder="多个作者用逗号分隔" />
        </el-form-item>
        <el-form-item label="DOI号" prop="doi">
          <el-input v-model="paperForm.doi" placeholder="请输入DOI号（可选）" />
        </el-form-item>

        <el-form-item label="证明材料">
          <el-upload
              class="upload-demo"
              :action="uploadUrl"
              :on-success="(response, file, fileList) => handleUploadSuccess(response, file, fileList, 'paper')"
              :on-error="handleUploadError"
              :before-upload="beforeUpload"
              :file-list="paperFileList"
              multiple
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <template #tip>
              <div class="el-upload__tip">
                支持 jpg/png/pdf/doc/docx 格式，单个文件大小不超过 10MB
              </div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="paperDialog = false">取消</el-button>
        <el-button type="primary" @click="submitPaper" :loading="submitLoading">确定</el-button>
      </template>
    </el-dialog>

    <!-- 专利对话框 -->
    <el-dialog v-model="patentDialog" :title="dialogTitle" width="600px">
      <el-form :model="patentForm" :rules="patentRules" ref="patentFormRef" label-width="120px">
        <el-form-item label="专利名称" prop="patentTitle">
          <el-input v-model="patentForm.patentTitle" placeholder="请输入专利名称" />
        </el-form-item>
        <el-form-item label="专利类型" prop="patentType">
          <el-select v-model="patentForm.patentType" placeholder="请选择专利类型" style="width: 100%">
            <el-option label="发明专利" value="发明专利" />
            <el-option label="实用新型专利" value="实用新型专利" />
            <el-option label="外观设计专利" value="外观设计专利" />
            <el-option label="软件著作权" value="软件著作权" />
          </el-select>
        </el-form-item>
        <el-form-item label="专利号" prop="patentNumber">
          <el-input v-model="patentForm.patentNumber" placeholder="请输入专利号" />
        </el-form-item>
        <el-form-item label="专利状态" prop="patentStatus">
          <el-select v-model="patentForm.patentStatus" placeholder="请选择专利状态" style="width: 100%">
            <el-option label="已授权" value="已授权" />
            <el-option label="已受理" value="已受理" />
            <el-option label="申请中" value="申请中" />
          </el-select>
        </el-form-item>
        <el-form-item label="授权时间" prop="authorizationDate" v-if="patentForm.patentStatus === '已授权'">
          <el-date-picker
              v-model="patentForm.authorizationDate"
              type="date"
              placeholder="选择授权时间"
              style="width: 100%"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="受理时间" prop="applicationDate" v-if="patentForm.patentStatus === '已受理'">
          <el-date-picker
              v-model="patentForm.applicationDate"
              type="date"
              placeholder="选择受理时间"
              style="width: 100%"
              format="YYYY-MM-DD"
              value-format="YYYY-MM-DD"
          />
        </el-form-item>
        <el-form-item label="发明人排名" prop="inventorRank">
          <el-input-number
              v-model="patentForm.inventorRank"
              :min="1"
              :max="99"
              placeholder="请输入发明人排名"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="合作发明人" prop="coInventors">
          <el-input v-model="patentForm.coInventors" placeholder="多个发明人用逗号分隔" />
        </el-form-item>

        <el-form-item label="证明材料">
          <el-upload
              class="upload-demo"
              :action="uploadUrl"
              :on-success="(response, file, fileList) => handleUploadSuccess(response, file, fileList, 'patent')"
              :on-error="handleUploadError"
              :before-upload="beforeUpload"
              :file-list="patentFileList"
              multiple
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <template #tip>
              <div class="el-upload__tip">
                支持 jpg/png/pdf/doc/docx 格式，单个文件大小不超过 10MB
              </div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="patentDialog = false">取消</el-button>
        <el-button type="primary" @click="submitPatent" :loading="submitLoading">确定</el-button>
      </template>
    </el-dialog>

    <!-- 项目对话框 -->
    <el-dialog v-model="projectDialog" :title="dialogTitle" width="600px">
      <el-form :model="projectForm" :rules="projectRules" ref="projectFormRef" label-width="120px">
        <el-form-item label="项目名称" prop="projectName">
          <el-input v-model="projectForm.projectName" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="项目级别" prop="projectLevel">
          <el-select v-model="projectForm.projectLevel" placeholder="请选择项目级别" style="width: 100%">
            <el-option label="校（市）级" value="校（市）级" />
            <el-option label="省（部）级" value="省（部）级" />
            <el-option label="国家级" value="国家级" />
          </el-select>
        </el-form-item>
        <el-form-item label="排名" prop="ranking">
          <el-input-number
              v-model="projectForm.ranking"
              :min="1"
              :max="999"
              placeholder="请输入排名"
              style="width: 100%"
          />
        </el-form-item>
        <el-form-item label="项目时间" required>
          <el-col :span="11">
            <el-form-item prop="startDate">
              <el-date-picker
                  v-model="projectForm.startDate"
                  type="date"
                  placeholder="开始时间"
                  style="width: 100%"
                  format="YYYY-MM-DD"
                  value-format="YYYY-MM-DD"
              />
            </el-form-item>
          </el-col>
          <el-col :span="2" style="text-align: center">
            <span style="color: #606266">至</span>
          </el-col>
          <el-col :span="11">
            <el-form-item prop="endDate">
              <el-date-picker
                  v-model="projectForm.endDate"
                  type="date"
                  placeholder="结束时间"
                  style="width: 100%"
                  format="YYYY-MM-DD"
                  value-format="YYYY-MM-DD"
              />
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="团队成员" prop="teamMembers">
          <el-input v-model="projectForm.teamMembers" placeholder="多个成员用逗号分隔" />
        </el-form-item>
        <el-form-item label="指导老师" prop="instructorName">
          <el-input v-model="projectForm.instructorName" placeholder="请输入指导老师姓名" />
        </el-form-item>

        <el-form-item label="证明材料">
          <el-upload
              class="upload-demo"
              :action="uploadUrl"
              :on-success="(response, file, fileList) => handleUploadSuccess(response, file, fileList, 'project')"
              :on-error="handleUploadError"
              :before-upload="beforeUpload"
              :file-list="projectFileList"
              multiple
          >
            <el-button size="small" type="primary">点击上传</el-button>
            <template #tip>
              <div class="el-upload__tip">
                支持 jpg/png/pdf/doc/docx 格式，单个文件大小不超过 10MB
              </div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="projectDialog = false">取消</el-button>
        <el-button type="primary" @click="submitProject" :loading="submitLoading">确定</el-button>
      </template>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog v-model="detailDialog" title="详情信息" width="700px">
      <div v-if="currentDetail">
        <el-descriptions :column="2" border>
          <!-- 竞赛详情 -->
          <template v-if="detailType === 'competition'">
            <el-descriptions-item label="竞赛名称">{{ currentDetail.competitionName }}</el-descriptions-item>
            <el-descriptions-item label="竞赛级别">{{ currentDetail.competitionLevel }}</el-descriptions-item>
            <el-descriptions-item label="获奖等级">{{ currentDetail.awardLevel }}</el-descriptions-item>
            <el-descriptions-item label="排名">{{ currentDetail.ranking }}</el-descriptions-item>
            <el-descriptions-item label="获奖时间">{{ currentDetail.awardDate }}</el-descriptions-item>
            <el-descriptions-item label="指导老师">{{ currentDetail.instructorName }}</el-descriptions-item>
            <el-descriptions-item label="团队成员" :span="2">{{ currentDetail.teamMembers }}</el-descriptions-item>
          </template>

          <!-- 论文详情 -->
          <template v-if="detailType === 'paper'">
            <el-descriptions-item label="论文题目" :span="2">{{ currentDetail.paperTitle }}</el-descriptions-item>
            <el-descriptions-item label="会议/期刊名称">{{ currentDetail.journalName }}</el-descriptions-item>
            <el-descriptions-item label="会议/期刊级别">{{ currentDetail.journalLevel }}</el-descriptions-item>
            <el-descriptions-item label="作者排名">{{ currentDetail.authorRank }}</el-descriptions-item>
            <el-descriptions-item label="发表状态">{{ currentDetail.publicationStatus }}</el-descriptions-item>
            <el-descriptions-item label="见刊时间" v-if="currentDetail.publicationDate">{{ currentDetail.publicationDate }}</el-descriptions-item>
            <el-descriptions-item label="接收时间" v-if="currentDetail.acceptanceDate">{{ currentDetail.acceptanceDate }}</el-descriptions-item>
            <el-descriptions-item label="DOI号" v-if="currentDetail.doi">{{ currentDetail.doi }}</el-descriptions-item>
            <el-descriptions-item label="合作作者" :span="2">{{ currentDetail.coAuthors }}</el-descriptions-item>
          </template>

          <!-- 专利详情 -->
          <template v-if="detailType === 'patent'">
            <el-descriptions-item label="专利名称" :span="2">{{ currentDetail.patentTitle }}</el-descriptions-item>
            <el-descriptions-item label="专利类型">{{ currentDetail.patentType }}</el-descriptions-item>
            <el-descriptions-item label="专利号">{{ currentDetail.patentNumber }}</el-descriptions-item>
            <el-descriptions-item label="发明人排名">{{ currentDetail.inventorRank }}</el-descriptions-item>
            <el-descriptions-item label="专利状态">{{ currentDetail.patentStatus }}</el-descriptions-item>
            <el-descriptions-item label="授权时间" v-if="currentDetail.authorizationDate">{{ currentDetail.authorizationDate }}</el-descriptions-item>
            <el-descriptions-item label="受理时间" v-if="currentDetail.applicationDate">{{ currentDetail.applicationDate }}</el-descriptions-item>
            <el-descriptions-item label="合作发明人" :span="2">{{ currentDetail.coInventors }}</el-descriptions-item>
          </template>

          <!-- 项目详情 -->
          <template v-if="detailType === 'project'">
            <el-descriptions-item label="项目名称" :span="2">{{ currentDetail.projectName }}</el-descriptions-item>
            <el-descriptions-item label="项目级别">{{ currentDetail.projectLevel }}</el-descriptions-item>
            <el-descriptions-item label="排名">{{ currentDetail.ranking }}</el-descriptions-item>
            <el-descriptions-item label="开始时间">{{ currentDetail.startDate }}</el-descriptions-item>
            <el-descriptions-item label="结束时间">{{ currentDetail.endDate }}</el-descriptions-item>
            <el-descriptions-item label="指导老师">{{ currentDetail.instructorName }}</el-descriptions-item>
            <el-descriptions-item label="团队成员" :span="2">{{ currentDetail.teamMembers }}</el-descriptions-item>
          </template>

          <!-- 通用审核信息 -->
          <el-descriptions-item label="审核状态">
            <el-tag :type="getStatusType(currentDetail.auditStatus)">
              {{ currentDetail.auditStatus || '待审核' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="审核意见" v-if="currentDetail.auditComment">{{ currentDetail.auditComment }}</el-descriptions-item>
          <el-descriptions-item label="创建时间">{{ formatDateTime(currentDetail.createTime) }}</el-descriptions-item>
          <el-descriptions-item label="更新时间" v-if="currentDetail.updateTime">{{ formatDateTime(currentDetail.updateTime) }}</el-descriptions-item>
        </el-descriptions>

        <!-- 附件信息 -->
        <div v-if="currentDetail.attachments" style="margin-top: 20px;">
          <h4>证明材料</h4>
          <div class="attachment-list">
            <el-link
                v-for="(file, index) in parseAttachments(currentDetail.attachments)"
                :key="index"
                :href="getFileUrl(file.url)"
                target="_blank"
                type="primary"
                style="margin-right: 10px; margin-bottom: 10px;"
            >
              {{ file.name }}
            </el-link>
          </div>
        </div>
      </div>

      <template #footer>
        <el-button @click="detailDialog = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Trophy, Document, Operation, Files, Plus } from '@element-plus/icons-vue'
import {
  addCompetition, updateCompetition, deleteCompetition, getStudentCompetitions,
  addPaper, updatePaper, deletePaper, getStudentPapers,
  addPatent, updatePatent, deletePatent, getStudentPatents,
  addProject, updateProject, deleteProject, getStudentProjects,
  searchCompetitions, getAllActiveCompetitions
} from '@/api/competition'

// 响应式数据
const activeTab = ref('competition')
const loading = ref(false)
const submitLoading = ref(false)

// 详情对话框相关
const detailDialog = ref(false)
const currentDetail = ref(null)
const detailType = ref('')

// 文件上传相关
const uploadUrl = 'http://localhost:1010/SCSE@hbut/msi/upload'
const competitionFileList = ref([])
const paperFileList = ref([])
const patentFileList = ref([])
const projectFileList = ref([])

// 竞赛字典数据
const competitionDict = ref([])

// 统计数据
const stats = ref({
  competitions: 0,
  papers: 0,
  patents: 0,
  projects: 0
})

// 表格数据
const competitionList = ref([])
const paperList = ref([])
const patentList = ref([])
const projectList = ref([])

// 对话框状态
const competitionDialog = ref(false)
const paperDialog = ref(false)
const patentDialog = ref(false)
const projectDialog = ref(false)

// 表单引用
const competitionFormRef = ref()
const paperFormRef = ref()
const patentFormRef = ref()
const projectFormRef = ref()

// 编辑状态
const isEdit = ref(false)
const currentEditId = ref('')

const dialogTitle = computed(() => {
  if (isEdit.value) {
    return {
      competition: '编辑学科竞赛',
      paper: '编辑发表文章',
      patent: '编辑专利',
      project: '编辑项目'
    }[activeTab.value]
  } else {
    return {
      competition: '添加学科竞赛',
      paper: '添加发表文章',
      patent: '添加专利',
      project: '添加项目'
    }[activeTab.value]
  }
})

// 表单数据
const competitionForm = reactive({
  studentId: '2021001',
  studentName: '张三',
  competitionName: '',
  competitionLevel: '',
  awardLevel: '',
  ranking: null,
  awardDate: '',
  teamMembers: '',
  instructorName: '',
  attachments: ''
})

const paperForm = reactive({
  studentId: '2021001',
  studentName: '张三',
  paperTitle: '',
  journalName: '',
  journalLevel: '',
  publicationStatus: '',
  publicationDate: '',
  acceptanceDate: '',
  authorRank: null,
  coAuthors: '',
  doi: '',
  attachments: ''
})

const patentForm = reactive({
  studentId: '2021001',
  studentName: '张三',
  patentTitle: '',
  patentType: '',
  patentNumber: '',
  patentStatus: '',
  authorizationDate: '',
  applicationDate: '',
  inventorRank: null,
  coInventors: '',
  attachments: ''
})

const projectForm = reactive({
  studentId: '2021001',
  studentName: '张三',
  projectName: '',
  projectLevel: '',
  ranking: null,
  startDate: '',
  endDate: '',
  teamMembers: '',
  instructorName: '',
  attachments: ''
})

// 表单验证规则
const competitionRules = {
  competitionName: [{ required: true, message: '请输入竞赛名称', trigger: 'blur' }],
  competitionLevel: [{ required: true, message: '请选择竞赛级别', trigger: 'change' }],
  awardLevel: [{ required: true, message: '请选择获奖等级', trigger: 'change' }],
  ranking: [{ required: true, message: '请输入排名', trigger: 'blur' }],
  awardDate: [{ required: true, message: '请选择获奖时间', trigger: 'change' }]
}

const paperRules = {
  paperTitle: [{ required: true, message: '请输入论文题目', trigger: 'blur' }],
  journalName: [{ required: true, message: '请输入会议/期刊名称', trigger: 'blur' }],
  journalLevel: [{ required: true, message: '请选择会议/期刊级别', trigger: 'change' }],
  publicationStatus: [{ required: true, message: '请选择发表状态', trigger: 'change' }],
  authorRank: [{ required: true, message: '请输入作者排名', trigger: 'blur' }]
}

const patentRules = {
  patentTitle: [{ required: true, message: '请输入专利名称', trigger: 'blur' }],
  patentType: [{ required: true, message: '请选择专利类型', trigger: 'change' }],
  patentStatus: [{ required: true, message: '请选择专利状态', trigger: 'change' }],
  inventorRank: [{ required: true, message: '请输入发明人排名', trigger: 'blur' }]
}

const projectRules = {
  projectName: [{ required: true, message: '请输入项目名称', trigger: 'blur' }],
  projectLevel: [{ required: true, message: '请选择项目级别', trigger: 'change' }],
  ranking: [{ required: true, message: '请输入排名', trigger: 'blur' }],
  startDate: [{ required: true, message: '请选择开始时间', trigger: 'change' }],
  endDate: [{ required: true, message: '请选择结束时间', trigger: 'change' }]
}

// 文件上传相关方法
const beforeUpload = (file) => {
  const allowedTypes = [
    'image/jpeg', 'image/png', 'image/jpg',
    'application/pdf',
    'application/msword',
    'application/vnd.openxmlformats-officedocument.wordprocessingml.document'
  ]
  const isAllowedType = allowedTypes.includes(file.type)
  const isLimitSize = file.size / 1024 / 1024 < 10

  if (!isAllowedType) {
    ElMessage.error('只能上传 jpg/png/pdf/doc/docx 格式的文件!')
    return false
  }
  if (!isLimitSize) {
    ElMessage.error('文件大小不能超过 10MB!')
    return false
  }
  return true
}

const handleUploadSuccess = (response, file, fileList, type = 'competition') => {
  console.log('文件上传成功:', response)

  if (response.code === 200 && response.data) {
    ElMessage.success(`文件 ${file.name} 上传成功`)

    // 处理返回的文件URL（现在是简单的字符串数组）
    let attachments = []
    if (Array.isArray(response.data)) {
      // 多个文件
      attachments = response.data.map(url => ({
        name: file.name,
        url: url
      }))
    } else {
      // 单个文件
      attachments = [{
        name: file.name,
        url: response.data
      }]
    }

    const attachmentString = JSON.stringify(attachments)

    switch (type) {
      case 'competition':
        competitionForm.attachments = attachmentString
        break
      case 'paper':
        paperForm.attachments = attachmentString
        break
      case 'patent':
        patentForm.attachments = attachmentString
        break
      case 'project':
        projectForm.attachments = attachmentString
        break
    }
  } else {
    ElMessage.error('上传失败: ' + (response.message || '未知错误'))
  }
}

const handleUploadError = (error, file, fileList) => {
  console.error('文件上传失败:', error)
  ElMessage.error(`文件 ${file.name} 上传失败`)
}

// 加载数据方法
const loadData = async () => {
  loading.value = true
  try {
    const studentId = '2021001'

    const [competitions, papers, patents, projects] = await Promise.all([
      getStudentCompetitions(studentId),
      getStudentPapers(studentId),
      getStudentPatents(studentId),
      getStudentProjects(studentId)
    ])

    competitionList.value = competitions.data || []
    paperList.value = papers.data || []
    patentList.value = patents.data || []
    projectList.value = projects.data || []

    stats.value = {
      competitions: competitionList.value.length,
      papers: paperList.value.length,
      patents: patentList.value.length,
      projects: projectList.value.length
    }
  } catch (error) {
    console.error('加载数据失败:', error)
    ElMessage.error('加载数据失败')
  } finally {
    loading.value = false
  }
}

// 加载竞赛字典
const loadCompetitionDict = async () => {
  try {
    const response = await getAllActiveCompetitions()
    if (response && response.data) {
      competitionDict.value = response.data
    } else {
      competitionDict.value = []
    }
  } catch (error) {
    console.error('加载竞赛字典失败:', error)
    ElMessage.error('加载竞赛字典失败')
    competitionDict.value = []
  }
}

// 竞赛名称自动完成搜索
const queryCompetitions = (queryString, cb) => {
  if (!queryString || queryString.trim() === '') {
    cb([])
    return
  }

  const keyword = queryString.toLowerCase().trim()
  const results = competitionDict.value
      .filter(item =>
          item.competitionName &&
          item.competitionName.toLowerCase().includes(keyword)
      )
      .map(item => ({
        value: item.competitionName,
        level: item.competitionLevel,
        serialNumber: item.serialNumber
      }))
      .slice(0, 10)

  cb(results)
}

// 选择竞赛时自动填入级别
const handleCompetitionSelect = (item) => {
  competitionForm.competitionLevel = item.level
  ElMessage.success(`已选择: ${item.value} (${item.level})`)
}

// 输入竞赛名称时的处理
const handleCompetitionInput = (value) => {
  if (!value || value.trim() === '') {
    competitionForm.competitionLevel = ''
  }
}

// 详情查看方法
const viewDetail = (item, type) => {
  currentDetail.value = item
  detailType.value = type
  detailDialog.value = true
}

// 格式化时间
const formatDateTime = (dateTime) => {
  if (!dateTime) return ''
  return new Date(dateTime).toLocaleString()
}

// 解析附件字符串
const parseAttachments = (attachments) => {
  if (!attachments) return []
  try {
    return JSON.parse(attachments)
  } catch {
    return []
  }
}

// 获取文件完整URL
const getFileUrl = (url) => {
  if (url.startsWith('http')) {
    return url
  }
  return `http://localhost:1010/SCSE@hbut${url}`
}

// 获取状态标签类型
const getStatusType = (status) => {
  const statusMap = {
    '已通过': 'success',
    '待审核': 'warning',
    '已拒绝': 'danger'
  }
  return statusMap[status] || 'warning'
}

// 打开对话框方法
const openCompetitionDialog = () => {
  resetCompetitionForm()
  isEdit.value = false
  currentEditId.value = ''
  competitionDialog.value = true
}

const openPaperDialog = () => {
  resetPaperForm()
  isEdit.value = false
  currentEditId.value = ''
  paperDialog.value = true
}

const openPatentDialog = () => {
  resetPatentForm()
  isEdit.value = false
  currentEditId.value = ''
  patentDialog.value = true
}

const openProjectDialog = () => {
  resetProjectForm()
  isEdit.value = false
  currentEditId.value = ''
  projectDialog.value = true
}

// 编辑项目
const editItem = (item, type) => {
  isEdit.value = true
  currentEditId.value = item.id

  switch (type) {
    case 'competition':
      Object.assign(competitionForm, item)
      competitionDialog.value = true
      break
    case 'paper':
      Object.assign(paperForm, item)
      paperDialog.value = true
      break
    case 'patent':
      Object.assign(patentForm, item)
      patentDialog.value = true
      break
    case 'project':
      Object.assign(projectForm, item)
      projectDialog.value = true
      break
  }
}

// 删除项目
const deleteItem = async (id, type) => {
  try {
    await ElMessageBox.confirm('确定要删除这条记录吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })

    switch (type) {
      case 'competition':
        await deleteCompetition(id)
        break
      case 'paper':
        await deletePaper(id)
        break
      case 'patent':
        await deletePatent(id)
        break
      case 'project':
        await deleteProject(id)
        break
    }

    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

// 提交表单方法
const submitCompetition = async () => {
  try {
    await competitionFormRef.value.validate()

    submitLoading.value = true
    const data = { ...competitionForm }

    if (isEdit.value) {
      data.id = currentEditId.value
      await updateCompetition(data)
      ElMessage.success('更新成功')
    } else {
      await addCompetition(data)
      ElMessage.success('添加成功')
    }

    competitionDialog.value = false
    loadData()
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error('提交失败: ' + (error.message || '未知错误'))
  } finally {
    submitLoading.value = false
  }
}

const submitPaper = async () => {
  try {
    await paperFormRef.value.validate()

    submitLoading.value = true
    const data = { ...paperForm }

    if (isEdit.value) {
      data.id = currentEditId.value
      await updatePaper(data)
      ElMessage.success('更新成功')
    } else {
      await addPaper(data)
      ElMessage.success('添加成功')
    }

    paperDialog.value = false
    loadData()
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error('提交失败: ' + (error.message || '未知错误'))
  } finally {
    submitLoading.value = false
  }
}

const submitPatent = async () => {
  try {
    await patentFormRef.value.validate()

    submitLoading.value = true
    const data = { ...patentForm }

    if (isEdit.value) {
      data.id = currentEditId.value
      await updatePatent(data)
      ElMessage.success('更新成功')
    } else {
      await addPatent(data)
      ElMessage.success('添加成功')
    }

    patentDialog.value = false
    loadData()
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error('提交失败: ' + (error.message || '未知错误'))
  } finally {
    submitLoading.value = false
  }
}

const submitProject = async () => {
  try {
    await projectFormRef.value.validate()

    submitLoading.value = true
    const data = { ...projectForm }

    if (isEdit.value) {
      data.id = currentEditId.value
      await updateProject(data)
      ElMessage.success('更新成功')
    } else {
      await addProject(data)
      ElMessage.success('添加成功')
    }

    projectDialog.value = false
    loadData()
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error('提交失败: ' + (error.message || '未知错误'))
  } finally {
    submitLoading.value = false
  }
}

// 重置表单方法
const resetCompetitionForm = () => {
  Object.assign(competitionForm, {
    studentId: '2021001',
    studentName: '张三',
    competitionName: '',
    competitionLevel: '',
    awardLevel: '',
    ranking: null,
    awardDate: '',
    teamMembers: '',
    instructorName: '',
    attachments: ''
  })
  competitionFileList.value = []
}

const resetPaperForm = () => {
  Object.assign(paperForm, {
    studentId: '2021001',
    studentName: '张三',
    paperTitle: '',
    journalName: '',
    journalLevel: '',
    publicationStatus: '',
    publicationDate: '',
    acceptanceDate: '',
    authorRank: null,
    coAuthors: '',
    doi: '',
    attachments: ''
  })
  paperFileList.value = []
}

const resetPatentForm = () => {
  Object.assign(patentForm, {
    studentId: '2021001',
    studentName: '张三',
    patentTitle: '',
    patentType: '',
    patentNumber: '',
    patentStatus: '',
    authorizationDate: '',
    applicationDate: '',
    inventorRank: null,
    coInventors: '',
    attachments: ''
  })
  patentFileList.value = []
}

const resetProjectForm = () => {
  Object.assign(projectForm, {
    studentId: '2021001',
    studentName: '张三',
    projectName: '',
    projectLevel: '',
    ranking: null,
    startDate: '',
    endDate: '',
    teamMembers: '',
    instructorName: '',
    attachments: ''
  })
  projectFileList.value = []
}

// 标签页切换
const handleTabChange = (tabName) => {
  activeTab.value = tabName
}

// 初始化
onMounted(async () => {
  await loadCompetitionDict()
  await loadData()
})
</script>

<style scoped>
.competition {
  padding: 20px;
}

.stats-container {
  margin-bottom: 20px;
}

.stats-card {
  height: 100px;
}

.stats-content {
  display: flex;
  align-items: center;
  height: 100%;
}

.stats-icon {
  font-size: 40px;
  margin-right: 15px;
}

.competition-icon {
  color: #67C23A;
}

.paper-icon {
  color: #409EFF;
}

.patent-icon {
  color: #E6A23C;
}

.project-icon {
  color: #F56C6C;
}

.stats-info {
  flex: 1;
}

.stats-number {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
  line-height: 1;
}

.stats-label {
  font-size: 14px;
  color: #909399;
  margin-top: 5px;
}

.main-card {
  min-height: 600px;
}

.tab-content {
  padding: 20px 0;
}

.action-bar {
  margin-bottom: 20px;
}

.attachment-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.upload-demo {
  width: 100%;
}

.competition-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 0;
}

.competition-name {
  font-weight: 500;
  color: #303133;
  flex: 1;
}

.competition-level {
  font-size: 12px;
  color: #909399;
  background: #f0f2f5;
  padding: 2px 8px;
  border-radius: 4px;
  margin-left: 10px;
}
</style>