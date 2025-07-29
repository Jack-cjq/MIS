const express = require('express');
const router = express.Router();
const {
  createTweet,
  getAllTweets,
  deleteTweet
} = require('../controllers/ideologicalTweetController');
const { protect } = require('../middleware/authMiddleware');
const upload = require('../middleware/upload');

router.post('/', protect, upload.single('image'), createTweet);
router.get('/', protect, getAllTweets);
router.delete('/:id', protect, deleteTweet);

module.exports = router;
