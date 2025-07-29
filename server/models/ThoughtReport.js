const mongoose = require('mongoose');

const ThoughtReportSchema = new mongoose.Schema({
  userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
  title: String,
  content: String,
  image: String, // 存储图片 URL 或路径
  submittedAt: { type: Date, default: Date.now }
}, { timestamps: true });

module.exports = mongoose.model('ThoughtReport', ThoughtReportSchema);
