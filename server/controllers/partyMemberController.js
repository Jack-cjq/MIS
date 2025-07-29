const PartyMember = require('../models/PartyMember');

// 获取所有党员
exports.getAllMembers = async (req, res) => {
  try {
    const members = await PartyMember.find();
    res.json(members);
  } catch (err) {
    res.status(500).json({ message: '获取失败', error: err });
  }
};

// 根据 ID 获取党员
exports.getMemberById = async (req, res) => {
  try {
    const member = await PartyMember.findById(req.params.id);
    if (!member) return res.status(404).json({ message: '找不到党员' });
    res.json(member);
  } catch (err) {
    res.status(500).json({ message: '获取失败', error: err });
  }
};

// 添加党员
exports.createMember = async (req, res) => {
  try {
    const exists = await PartyMember.findOne({ studentId: req.body.studentId });
    if (exists) return res.status(400).json({ message: '该学号已存在' });

    const newMember = await PartyMember.create(req.body);
    res.status(201).json(newMember);
  } catch (err) {
    res.status(500).json({ message: '创建失败', error: err });
  }
};

// 更新党员
exports.updateMember = async (req, res) => {
  try {
    const updated = await PartyMember.findByIdAndUpdate(req.params.id, req.body, { new: true });
    if (!updated) return res.status(404).json({ message: '找不到党员' });
    res.json(updated);
  } catch (err) {
    res.status(500).json({ message: '更新失败', error: err });
  }
};

// 删除党员
exports.deleteMember = async (req, res) => {
  try {
    const deleted = await PartyMember.findByIdAndDelete(req.params.id);
    if (!deleted) return res.status(404).json({ message: '找不到党员' });
    res.json({ message: '已删除' });
  } catch (err) {
    res.status(500).json({ message: '删除失败', error: err });
  }
};
