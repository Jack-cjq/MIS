package org.example.controller;

import org.example.model.InternshipEmploymentModel;
import org.example.service.InternshipEmploymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/msi/internship-employment")
public class InternshipEmploymentController {
    @Autowired
    private InternshipEmploymentService service;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> add(@RequestBody InternshipEmploymentModel model) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", service.add(model));
        result.put("msg", "success");
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> update(@RequestBody InternshipEmploymentModel model) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", service.update(model));
        result.put("msg", "success");
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable String id) {
        service.delete(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", null);
        result.put("msg", "success");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable String id) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", service.getById(id));
        result.put("msg", "success");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getByUserId(@PathVariable String userId) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", service.getByUserId(userId));
        result.put("msg", "success");
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAll() {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("data", service.getAll());
        result.put("msg", "success");
        return ResponseEntity.ok(result);
    }
} 