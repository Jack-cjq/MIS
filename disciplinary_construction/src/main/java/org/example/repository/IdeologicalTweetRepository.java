package org.example.repository;

import org.example.model.IdeologicalTweetModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IdeologicalTweetRepository extends MongoRepository<IdeologicalTweetModel, String> {
    List<IdeologicalTweetModel> findByStudentId(String studentId);
    List<IdeologicalTweetModel> findByAuditStatus(String auditStatus);
    List<IdeologicalTweetModel> findByStudentIdAndAuditStatus(String studentId, String auditStatus);
    List<IdeologicalTweetModel> findByTweetType(String tweetType);
    List<IdeologicalTweetModel> findByStatus(String status);
    List<IdeologicalTweetModel> findByTitleContaining(String title);
    List<IdeologicalTweetModel> findByContentContaining(String content);
    List<IdeologicalTweetModel> findByStudentNameContaining(String studentName);
} 