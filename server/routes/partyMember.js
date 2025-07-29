const express = require('express');
const router = express.Router();
const {
  getAllMembers,
  getMemberById,
  createMember,
  updateMember,
  deleteMember
} = require('../controllers/partyMemberController');
const { protect } = require('../middleware/authMiddleware');

// 所有接口需登录
router.get('/', protect, getAllMembers);
router.get('/:id', protect, getMemberById);
router.post('/', protect, createMember);
router.put('/:id', protect, updateMember);
router.delete('/:id', protect, deleteMember);

module.exports = router;
