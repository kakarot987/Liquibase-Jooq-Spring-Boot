package com.springBootliquibasejooq.service;

import com.springBootliquibasejooq.ModelRepository;
import org.springframework.stereotype.Service;

@Service
public class ModelService {

    private final ModelRepository modelRepository;

    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    public void addModel(int id, String name, String gender, String blood) {
        // Propagate database errors so the controller can return a suitable status.
        modelRepository.addSaiyan(id, name, gender, blood);
    }
}
