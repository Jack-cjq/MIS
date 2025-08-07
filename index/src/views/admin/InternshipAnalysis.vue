<template>
  <div class="internship-analysis-page">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="statistics-cards">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon">
              <el-icon><DataAnalysis /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ statistics.totalCount || 0 }}</div>
              <div class="stat-label">总记录数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon internship">
              <el-icon><Calendar /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ statistics.internshipCount || 0 }}</div>
              <div class="stat-label">实习记录</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon employment">
              <el-icon><OfficeBuilding /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ statistics.employmentCount || 0 }}</div>
              <div class="stat-label">就业记录</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon satisfaction">
              <el-icon><Star /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ satisfactionStats.averageScore?.toFixed(1) || 0 }}</div>
              <div class="stat-label">平均满意度</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" class="chart-section">
      <!-- 行业分布 -->
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>行业分布</span>
            </div>
          </template>
          <div class="chart-container">
            <div ref="industryChart" style="height: 300px;"></div>
          </div>
        </el-card>
      </el-col>

      <!-- 技术栈分布 -->
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>热门技术栈</span>
            </div>
          </template>
          <div class="chart-container">
            <div ref="techStackChart" style="height: 300px;"></div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="chart-section">
      <!-- 薪资分布 -->
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>薪资分布</span>
            </div>
          </template>
          <div class="chart-container">
            <div ref="salaryChart" style="height: 300px;"></div>
          </div>
        </el-card>
      </el-col>

      <!-- 就业趋势 -->
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>就业趋势</span>
            </div>
          </template>
          <div class="chart-container">
            <div ref="trendChart" style="height: 300px;"></div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 详细统计表格 -->
    <el-row :gutter="20" class="table-section">
      <el-col :span="24">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>详细统计</span>
              <el-button type="primary" @click="exportStatistics">导出统计报告</el-button>
            </div>
          </template>
          
          <el-tabs v-model="activeTab" type="border-card">
            <!-- 公司类型统计 -->
            <el-tab-pane label="公司类型统计" name="companyType">
              <el-table :data="companyTypeStats" style="width: 100%">
                <el-table-column prop="type" label="公司类型" />
                <el-table-column prop="count" label="数量" />
                <el-table-column prop="percentage" label="占比">
                  <template #default="scope">
                    {{ scope.row.percentage }}%
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>

            <!-- 技术栈统计 -->
            <el-tab-pane label="技术栈统计" name="techStack">
              <el-table :data="techStackStats" style="width: 100%">
                <el-table-column prop="technology" label="技术栈" />
                <el-table-column prop="count" label="使用次数" />
                <el-table-column prop="percentage" label="占比">
                  <template #default="scope">
                    {{ scope.row.percentage }}%
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>

            <!-- 薪资范围统计 -->
            <el-tab-pane label="薪资范围统计" name="salaryRange">
              <el-table :data="salaryRangeStats" style="width: 100%">
                <el-table-column prop="range" label="薪资范围" />
                <el-table-column prop="count" label="人数" />
                <el-table-column prop="percentage" label="占比">
                  <template #default="scope">
                    {{ scope.row.percentage }}%
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>

            <!-- 满意度统计 -->
            <el-tab-pane label="满意度统计" name="satisfaction">
              <el-descriptions :column="2" border>
                <el-descriptions-item label="平均满意度">
                  {{ satisfactionStats.averageScore?.toFixed(1) || 0 }}分
                </el-descriptions-item>
                <el-descriptions-item label="最高评分">
                  {{ satisfactionStats.maxScore || 0 }}分
                </el-descriptions-item>
                <el-descriptions-item label="最低评分">
                  {{ satisfactionStats.minScore || 0 }}分
                </el-descriptions-item>
                <el-descriptions-item label="评价总数">
                  {{ satisfactionStats.totalEvaluations || 0 }}人
                </el-descriptions-item>
              </el-descriptions>
            </el-tab-pane>
          </el-tabs>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { 
  getInternshipStatistics,
  getTechnologyStackStatistics,
  getSalaryRangeStatistics,
  getSatisfactionStatistics,
  getEmploymentTrendAnalysis,
  getTopTechnologyStacks,
  getTopIndustries,
  exportStatisticsReport
} from '@/api/internship'
import { DataAnalysis, Calendar, OfficeBuilding, Star } from '@element-plus/icons-vue'
import * as echarts from 'echarts'

const statistics = ref({})
const satisfactionStats = ref({})
const companyTypeStats = ref([])
const techStackStats = ref([])
const salaryRangeStats = ref([])
const activeTab = ref('companyType')

// 图表引用
const industryChart = ref()
const techStackChart = ref()
const salaryChart = ref()
const trendChart = ref()

// 加载统计数据
const loadStatistics = async () => {
  try {
    // 加载基础统计
    const statsRes = await getInternshipStatistics()
    if (statsRes.code === 200) {
      statistics.value = statsRes.data
    }

    // 加载满意度统计
    const satisfactionRes = await getSatisfactionStatistics()
    if (satisfactionRes.code === 200) {
      satisfactionStats.value = satisfactionRes.data
    }

    // 加载技术栈统计
    const techStackRes = await getTechnologyStackStatistics()
    if (techStackRes.code === 200) {
      const techData = techStackRes.data
      techStackStats.value = Object.entries(techData).map(([tech, count]) => ({
        technology: tech,
        count: count,
        percentage: ((count / Object.values(techData).reduce((a, b) => a + b, 0)) * 100).toFixed(1)
      }))
    }

    // 加载薪资范围统计
    const salaryRes = await getSalaryRangeStatistics()
    if (salaryRes.code === 200) {
      const salaryData = salaryRes.data
      salaryRangeStats.value = Object.entries(salaryData).map(([range, count]) => ({
        range: range,
        count: count,
        percentage: ((count / Object.values(salaryData).reduce((a, b) => a + b, 0)) * 100).toFixed(1)
      }))
    }

    // 加载公司类型统计
    if (statistics.value.companyTypeStatistics) {
      const companyData = statistics.value.companyTypeStatistics
      companyTypeStats.value = Object.entries(companyData).map(([type, count]) => ({
        type: type,
        count: count,
        percentage: ((count / Object.values(companyData).reduce((a, b) => a + b, 0)) * 100).toFixed(1)
      }))
    }

    // 初始化图表
    initCharts()
  } catch (error) {
    ElMessage.error('加载统计数据失败')
  }
}

// 初始化图表
const initCharts = () => {
  // 行业分布饼图
  if (statistics.value.industryStatistics) {
    const industryData = Object.entries(statistics.value.industryStatistics).map(([name, value]) => ({
      name,
      value
    }))
    
    const industryChartInstance = echarts.init(industryChart.value)
    industryChartInstance.setOption({
      title: {
        text: '行业分布',
        left: 'center'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}: {c} ({d}%)'
      },
      series: [
        {
          name: '行业',
          type: 'pie',
          radius: '50%',
          data: industryData,
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    })
  }

  // 技术栈柱状图
  if (techStackStats.value.length > 0) {
    const techData = techStackStats.value.slice(0, 10) // 显示前10个
    const techChartInstance = echarts.init(techStackChart.value)
    techChartInstance.setOption({
      title: {
        text: '热门技术栈',
        left: 'center'
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      xAxis: {
        type: 'category',
        data: techData.map(item => item.technology),
        axisLabel: {
          rotate: 45
        }
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: '使用次数',
          type: 'bar',
          data: techData.map(item => item.count)
        }
      ]
    })
  }

  // 薪资分布柱状图
  if (salaryRangeStats.value.length > 0) {
    const salaryChartInstance = echarts.init(salaryChart.value)
    salaryChartInstance.setOption({
      title: {
        text: '薪资分布',
        left: 'center'
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      xAxis: {
        type: 'category',
        data: salaryRangeStats.value.map(item => item.range)
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: '人数',
          type: 'bar',
          data: salaryRangeStats.value.map(item => item.count)
        }
      ]
    })
  }

  // 就业趋势折线图
  loadTrendChart()
}

// 加载趋势图
const loadTrendChart = async () => {
  try {
    const trendRes = await getEmploymentTrendAnalysis()
    if (trendRes.code === 200) {
      const trendData = trendRes.data
      const monthlyData = trendData.monthlyStatistics || {}
      
      const trendChartInstance = echarts.init(trendChart.value)
      trendChartInstance.setOption({
        title: {
          text: '就业趋势',
          left: 'center'
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: Object.keys(monthlyData).sort()
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '就业人数',
            type: 'line',
            data: Object.keys(monthlyData).sort().map(key => monthlyData[key])
          }
        ]
      })
    }
  } catch (error) {
    console.error('加载趋势数据失败:', error)
  }
}

// 导出统计报告
const exportStatistics = async () => {
  try {
    const res = await exportStatisticsReport()
    if (res.data) {
      const blob = new Blob([res.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' })
      const url = window.URL.createObjectURL(blob)
      const link = document.createElement('a')
      link.href = url
      link.download = '实习就业统计报告.xlsx'
      link.click()
      window.URL.revokeObjectURL(url)
      ElMessage.success('导出成功')
    }
  } catch (error) {
    ElMessage.error('导出失败')
  }
}

onMounted(() => {
  loadStatistics()
})
</script>

<style scoped>
.internship-analysis-page {
  padding: 20px;
}

.statistics-cards {
  margin-bottom: 20px;
}

.stat-card {
  display: flex;
  align-items: center;
  padding: 10px;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
  font-size: 24px;
  color: white;
}

.stat-icon:not(.internship):not(.employment):not(.satisfaction) {
  background-color: #409eff;
}

.stat-icon.internship {
  background-color: #e6a23c;
}

.stat-icon.employment {
  background-color: #67c23a;
}

.stat-icon.satisfaction {
  background-color: #f56c6c;
}

.stat-content {
  flex: 1;
}

.stat-number {
  font-size: 24px;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  color: #666;
}

.chart-section {
  margin-bottom: 20px;
}

.chart-container {
  padding: 10px;
}

.table-section {
  margin-bottom: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style> 