package org.example.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.example.model.AlumniModel;
import org.example.repository.AlumniRepository;
import org.example.service.AlumniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class AlumniServiceImpl implements AlumniService {
    @Autowired
    private AlumniRepository alumniRepository;

    public AlumniServiceImpl() {
        log.info("AlumniServiceImpl已创建");
    }

    @Override
    public void insertAlumni(AlumniModel alumniModel) {

    }

    @Override
    public void updateAlumni(AlumniModel alumniModel) {

    }

    @Override
    public AlumniModel searchAlumniByStudentId(String studentId) {
        return null;
    }

    @Override
    public List<AlumniModel> searchAlumniByName(String name) {
        return Collections.emptyList();
    }

    @Override
    public List<AlumniModel> findAllAlumni() {
        return Collections.emptyList();
    }
}
