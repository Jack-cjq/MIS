const mongoose = require('mongoose');

const VolunteerServiceSchema = new mongoose.Schema({
  userId: { type: mongoose.Schema.Types.ObjectId, ref: 'User' },
  title: String,
  description: String,
  image: String,
  time: String,
  location: String,
  createdAt: { type: Date, default: Date.now }
});

module.exports = mongoose.model('VolunteerService', VolunteerServiceSchema);
