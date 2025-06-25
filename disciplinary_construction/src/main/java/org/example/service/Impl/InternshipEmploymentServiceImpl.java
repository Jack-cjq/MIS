package org.example.service.Impl;

import org.example.model.InternshipEmploymentModel;
import org.example.repository.InternshipEmploymentRepository;
import org.example.service.InternshipEmploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InternshipEmploymentServiceImpl implements InternshipEmploymentService {
    @Autowired
    private InternshipEmploymentRepository repository;

    @Override
    public InternshipEmploymentModel add(InternshipEmploymentModel model) {
        return repository.save(model);
    }

    @Override
    public InternshipEmploymentModel update(InternshipEmploymentModel model) {
        return repository.save(model);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public InternshipEmploymentModel getById(String id) {
        Optional<InternshipEmploymentModel> optional = repository.findById(id);
        return optional.orElse(null);
    }

    @Override
    public List<InternshipEmploymentModel> getByUserId(String userId) {
        return repository.findByUserId(userId);
    }

    @Override
    public List<InternshipEmploymentModel> getAll() {
        return repository.findAll();
    }
} 