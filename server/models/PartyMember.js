const mongoose = require('mongoose');

const PartyMemberSchema = new mongoose.Schema({
  avatar: String,
  studentId: { type: String, required: true, unique: true },
  name: String,
  gender: String,
  nation: String,
  birth: String,
  nativePlace: String,
  class: String,
  branch: String,
  joinDate: String,
  isRegular: String,
  applicationNo: String,
  position: String,
  rewardsPunishments: String
}, { timestamps: true });

module.exports = mongoose.model('PartyMember', PartyMemberSchema);
