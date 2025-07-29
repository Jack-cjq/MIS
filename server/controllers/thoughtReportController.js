const ThoughtReport = require('../models/ThoughtReport');

// 新增思想汇报
exports.createReport = async (req, res) => {
  try {
    const { title, content } = req.body;
    const image = req.file ? `/uploads/${req.file.filename}` : null;

    const report = await ThoughtReport.create({
      userId: req.user._id,
      title,
      content,
      image
    });

    res.status(201).json(report);
  } catch (err) {
    res.status(500).json({ message: '提交失败', error: err });
  }
};

// 获取当前用户所有汇报
exports.getMyReports = async (req, res) => {
  try {
    const reports = await ThoughtReport.find({ userId: req.user._id });
    res.json(reports);
  } catch (err) {
    res.status(500).json({ message: '获取失败', error: err });
  }
};

// 删除思想汇报
exports.deleteReport = async (req, res) => {
  try {
    const deleted = await ThoughtReport.findByIdAndDelete(req.params.id);
    if (!deleted) return res.status(404).json({ message: '未找到汇报' });
    res.json({ message: '已删除' });
  } catch (err) {
    res.status(500).json({ message: '删除失败', error: err });
  }
};
