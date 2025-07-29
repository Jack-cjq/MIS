const express = require('express');
const router = express.Router();
const { createCourse, getAllCourses, deleteCourse } = require('../controllers/partyCourseController');
const { protect } = require('../middleware/authMiddleware');
const upload = require('../middleware/upload');

router.post('/', protect, upload.single('resource'), createCourse);
router.get('/', protect, getAllCourses);
router.delete('/:id', protect, deleteCourse);

module.exports = router;
