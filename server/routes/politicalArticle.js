const express = require('express');
const router = express.Router();

// 定义路由
router.get('/', (req, res) => {
  res.send('政治文章接口');
});

module.exports = router; // ✅ 直接导出 router，而不是对象
