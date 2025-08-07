package org.example.service.Impl;

import org.example.model.IdeologicalTweetModel;
import org.example.repository.IdeologicalTweetRepository;
import org.example.service.IdeologicalTweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IdeologicalTweetServiceImpl implements IdeologicalTweetService {

    @Autowired
    private IdeologicalTweetRepository ideologicalTweetRepository;

    @Override
    public IdeologicalTweetModel addIdeologicalTweet(IdeologicalTweetModel ideologicalTweet) {
        ideologicalTweet.setCreateTime(LocalDateTime.now());
        ideologicalTweet.setUpdateTime(LocalDateTime.now());
        ideologicalTweet.setAuditStatus("待审核");
        return ideologicalTweetRepository.save(ideologicalTweet);
    }

    @Override
    public IdeologicalTweetModel updateIdeologicalTweet(IdeologicalTweetModel ideologicalTweet) {
        ideologicalTweet.setUpdateTime(LocalDateTime.now());
        return ideologicalTweetRepository.save(ideologicalTweet);
    }

    @Override
    public void deleteIdeologicalTweet(String id) {
        ideologicalTweetRepository.deleteById(id);
    }

    @Override
    public List<IdeologicalTweetModel> getStudentIdeologicalTweets(String studentId) {
        return ideologicalTweetRepository.findByStudentId(studentId);
    }

    @Override
    public IdeologicalTweetModel getIdeologicalTweetById(String id) {
        return ideologicalTweetRepository.findById(id).orElse(null);
    }

    @Override
    public List<IdeologicalTweetModel> getPendingIdeologicalTweets() {
        return ideologicalTweetRepository.findByAuditStatus("待审核");
    }

    @Override
    public IdeologicalTweetModel auditIdeologicalTweet(String id, String auditStatus, String auditComment, String auditorId) {
        IdeologicalTweetModel ideologicalTweet = ideologicalTweetRepository.findById(id).orElse(null);
        if (ideologicalTweet != null) {
            ideologicalTweet.setAuditStatus(auditStatus);
            ideologicalTweet.setAuditComment(auditComment);
            ideologicalTweet.setAuditorId(auditorId);
            ideologicalTweet.setAuditTime(LocalDateTime.now());
            return ideologicalTweetRepository.save(ideologicalTweet);
        }
        return null;
    }

    @Override
    public List<IdeologicalTweetModel> getAllIdeologicalTweets() {
        return ideologicalTweetRepository.findAll();
    }

    @Override
    public List<IdeologicalTweetModel> searchIdeologicalTweets(String keyword) {
        List<IdeologicalTweetModel> byTitle = ideologicalTweetRepository.findByTitleContaining(keyword);
        List<IdeologicalTweetModel> byContent = ideologicalTweetRepository.findByContentContaining(keyword);
        List<IdeologicalTweetModel> byStudentName = ideologicalTweetRepository.findByStudentNameContaining(keyword);
        
        byTitle.addAll(byContent);
        byTitle.addAll(byStudentName);
        return byTitle;
    }

    @Override
    public List<IdeologicalTweetModel> getIdeologicalTweetsByType(String tweetType) {
        return ideologicalTweetRepository.findByTweetType(tweetType);
    }

    @Override
    public List<IdeologicalTweetModel> getIdeologicalTweetsByStatus(String status) {
        return ideologicalTweetRepository.findByStatus(status);
    }
} 