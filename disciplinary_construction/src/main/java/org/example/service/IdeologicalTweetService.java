package org.example.service;

import org.example.model.IdeologicalTweetModel;
import java.util.List;

public interface IdeologicalTweetService {
    // 学生操作
    IdeologicalTweetModel addIdeologicalTweet(IdeologicalTweetModel ideologicalTweet);
    IdeologicalTweetModel updateIdeologicalTweet(IdeologicalTweetModel ideologicalTweet);
    void deleteIdeologicalTweet(String id);
    List<IdeologicalTweetModel> getStudentIdeologicalTweets(String studentId);
    IdeologicalTweetModel getIdeologicalTweetById(String id);

    // 管理员操作
    List<IdeologicalTweetModel> getPendingIdeologicalTweets();
    IdeologicalTweetModel auditIdeologicalTweet(String id, String auditStatus, String auditComment, String auditorId);
    List<IdeologicalTweetModel> getAllIdeologicalTweets();
    List<IdeologicalTweetModel> searchIdeologicalTweets(String keyword);
    List<IdeologicalTweetModel> getIdeologicalTweetsByType(String tweetType);
    List<IdeologicalTweetModel> getIdeologicalTweetsByStatus(String status);
} 