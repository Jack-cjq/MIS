const mongoose = require('mongoose');

const PartyCourseSchema = new mongoose.Schema({
  userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
  title: String,
  description: String,
  resource: String, // 上传的课件或视频路径
  createdAt: { type: Date, default: Date.now }
});

module.exports = mongoose.model('PartyCourse', PartyCourseSchema);
