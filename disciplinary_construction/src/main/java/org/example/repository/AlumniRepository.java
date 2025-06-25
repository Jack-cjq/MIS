package org.example.repository;

import org.example.model.AlumniModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumniRepository extends MongoRepository<AlumniModel, String> {

}
