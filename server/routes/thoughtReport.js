const express = require('express');
const router = express.Router();
const {
  createReport,
  getMyReports,
  deleteReport
} = require('../controllers/thoughtReportController');
const { protect } = require('../middleware/authMiddleware');
const upload = require('../middleware/upload');

router.post('/', protect, upload.single('image'), createReport);
router.get('/', protect, getMyReports);
router.delete('/:id', protect, deleteReport);

module.exports = router;
