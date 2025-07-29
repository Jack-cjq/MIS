const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const dotenv = require('dotenv');
const path = require('path');
const politicalArticleRouter = require('./routes/politicalArticle');

// 初始化
dotenv.config();
const app = express();
app.use(cors());
app.use(express.json());
app.use('/uploads', express.static(path.join(__dirname, 'uploads')));

// 数据库连接
mongoose.connect(process.env.MONGO_URI, {
  useNewUrlParser: true,
  useUnifiedTopology: true
}).then(() => console.log('✅ MongoDB connected'))
  .catch(err => console.error('❌ MongoDB connection error:', err));

// 路由
app.use('/api/auth', require('./routes/auth'));
app.use('/api/party-member', require('./routes/partyMember'));
app.use('/api/thought-report', require('./routes/thoughtReport'));
app.use('/api/article', politicalArticleRouter); 
app.use('/api/volunteer-service', require('./routes/volunteerService'));

// 启动
const PORT = process.env.PORT || 5000;
app.listen(PORT, () => {
  console.log(`🚀 Server running on http://localhost:${PORT}`);
});

// 让上传图片可通过 URL 访问
app.use('/uploads', express.static(path.join(__dirname, 'uploads')));

