const mongoose = require('mongoose');

const IdeologicalTweetSchema = new mongoose.Schema({
  userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
  title: String,
  content: String,
  image: String,
  createdAt: { type: Date, default: Date.now }
});

module.exports = mongoose.model('IdeologicalTweet', IdeologicalTweetSchema);
