const PartyCourse = require('../models/PartyCourse');

exports.createCourse = async (req, res) => {
  try {
    const { title, description } = req.body;
    const resource = req.file ? `/uploads/${req.file.filename}` : null;

    const course = await PartyCourse.create({
      userId: req.user._id,
      title,
      description,
      resource
    });

    res.status(201).json(course);
  } catch (err) {
    res.status(500).json({ message: '创建失败', error: err });
  }
};

exports.getAllCourses = async (req, res) => {
  try {
    const courses = await PartyCourse.find();
    res.json(courses);
  } catch (err) {
    res.status(500).json({ message: '获取失败', error: err });
  }
};

exports.deleteCourse = async (req, res) => {
  try {
    const deleted = await PartyCourse.findByIdAndDelete(req.params.id);
    if (!deleted) return res.status(404).json({ message: '未找到课程' });
    res.json({ message: '已删除' });
  } catch (err) {
    res.status(500).json({ message: '删除失败', error: err });
  }
};
