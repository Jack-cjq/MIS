const VolunteerService = require('../models/VolunteerService');

exports.createService = async (req, res) => {
  try {
    const { title, description, time, location } = req.body;
    const image = req.file ? `/uploads/${req.file.filename}` : null;

    const service = await VolunteerService.create({
      userId: req.user._id,
      title,
      description,
      time,
      location,
      image
    });

    res.status(201).json(service);
  } catch (err) {
    res.status(500).json({ message: '创建失败', error: err });
  }
};

exports.getAllServices = async (req, res) => {
  try {
    const services = await VolunteerService.find();
    res.json(services);
  } catch (err) {
    res.status(500).json({ message: '获取失败', error: err });
  }
};

exports.deleteService = async (req, res) => {
  try {
    const deleted = await VolunteerService.findByIdAndDelete(req.params.id);
    if (!deleted) return res.status(404).json({ message: '未找到活动' });
    res.json({ message: '已删除' });
  } catch (err) {
    res.status(500).json({ message: '删除失败', error: err });
  }
};
