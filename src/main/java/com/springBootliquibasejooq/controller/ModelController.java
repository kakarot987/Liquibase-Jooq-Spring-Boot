package com.springBootliquibasejooq.controller;

import co.db.jooq.tables.pojos.Saiyans;
import com.springBootliquibasejooq.ModelRepository;
import com.springBootliquibasejooq.service.ModelService;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.SQLStateClass;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ModelController {

    private final ModelService modelService;
    private final ModelRepository modelRepository;

    public ModelController(ModelService modelService, ModelRepository modelRepository) {
        this.modelService = modelService;
        this.modelRepository = modelRepository;
    }

    @PostMapping("/addModel")
    public ResponseEntity<Void> addModel(@RequestBody Saiyans model) {
        if (model.getSaiyanId() == null || model.getSaiyanId() <= 0
                || model.getSaiyanName() == null || model.getSaiyanName().isBlank()
                || model.getSaiyanName().length() > 20) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valid id and name are required");
        }
        try {
            modelService.addModel(model.getSaiyanId(), model.getSaiyanName(),
                    model.getSaiyanGender(), model.getSaiyanBlood());
        } catch (DataAccessException e) {
            if (e.sqlStateClass() == SQLStateClass.C23_INTEGRITY_CONSTRAINT_VIOLATION) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Duplicate or invalid row", e);
            }
            throw e;
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/getModels")
    public List<Saiyans> getModels() {
        return modelRepository.selectAllSaiyans();
    }
}
