package org.example.repository;

import org.bson.Document;
import org.example.model.AlumniModel;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumniRepository extends MongoRepository<AlumniModel, String> {
    AlumniModel findAlumniByStudentId(String studentId);

    @Aggregation(pipeline = {
            "{ '$lookup': { 'from': 'students', 'localField': 'studentId', 'foreignField': 'studentId', 'as': 'student' } }",
            "{ '$unwind': { 'path': '$student', 'preserveNullAndEmptyArrays': true } }",
            "{ '$match': { '$or': [ " +
                    "{ 'studentId': { $regex: ?0, $options: 'i' } }, " +
                    "{ 'student.name': { $regex: ?0, $options: 'i' } }, " +
                    "{ 'workPlace': { $regex: ?0, $options: 'i' } } " +
                    "] } }",
            "{ '$skip': ?1 }",
            "{ '$limit': ?2 }"
    })
    List<AlumniModel> searchAlumniList(String searchValue, Integer skip, Integer limit);

    @Aggregation(pipeline = {
            "{ '$lookup': { 'from': 'students', 'localField': 'studentId', 'foreignField': 'studentId', 'as': 'student' } }",
            "{ '$unwind': { 'path': '$student', 'preserveNullAndEmptyArrays': true } }",
            "{ '$match': { '$or': [ " +
                    "{ 'studentId': { $regex: ?0, $options: 'i' } }, " +
                    "{ 'student.name': { $regex: ?0, $options: 'i' } }, " +
                    "{ 'workPlace': { $regex: ?0, $options: 'i' } } " +
                    "] } }",
            "{ '$count': 'total' }"
    })
    Long getTotalCount(String searchValue);
}
