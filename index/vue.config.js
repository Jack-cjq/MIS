const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: [],
  
  // 添加这个配置来修复进度插件问题
  chainWebpack: config => {
    // 删除有问题的进度插件
    config.plugins.delete('progress')
  },
  
  devServer: {
    port: 3000,
    open: true,
    host: 'localhost'
  }
})