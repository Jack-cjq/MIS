package org.example.repository;

import org.example.model.StudentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface StudentRepository extends MongoRepository<StudentModel, String> {
    //自动支持：findAll(),findById(),save(),delete()

    //自定义查询方法（根据方法名自动解析）
    List<StudentModel> findByName(String name);

    //使用@Query注解编写复杂查询
    @Query("{'age' : {$gte: ?0, $lte: ?1}")
    List<StudentModel> findByAgeRange(int minAge, int maxAge);
}
