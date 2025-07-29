const express = require('express');
const router = express.Router();
const {
  createService,
  getAllServices,
  deleteService
} = require('../controllers/volunteerServiceController');
const { protect } = require('../middleware/authMiddleware');
const upload = require('../middleware/upload');

router.post('/', protect, upload.single('image'), createService);
router.get('/', protect, getAllServices);
router.delete('/:id', protect, deleteService);

module.exports = router;
