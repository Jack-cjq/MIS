const IdeologicalTweet = require('../models/IdeologicalTweet');

exports.createTweet = async (req, res) => {
  try {
    const { title, content } = req.body;
    const image = req.file ? `/uploads/${req.file.filename}` : null;

    const tweet = await IdeologicalTweet.create({
      userId: req.user._id,
      title,
      content,
      image
    });

    res.status(201).json(tweet);
  } catch (err) {
    res.status(500).json({ message: '发布失败', error: err });
  }
};

exports.getAllTweets = async (req, res) => {
  try {
    const tweets = await IdeologicalTweet.find();
    res.json(tweets);
  } catch (err) {
    res.status(500).json({ message: '获取失败', error: err });
  }
};

exports.deleteTweet = async (req, res) => {
  try {
    const deleted = await IdeologicalTweet.findByIdAndDelete(req.params.id);
    if (!deleted) return res.status(404).json({ message: '未找到推文' });
    res.json({ message: '已删除' });
  } catch (err) {
    res.status(500).json({ message: '删除失败', error: err });
  }
};
