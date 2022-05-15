package com.springBootliquibasejooq.controller;

import co.db.jooq.tables.daos.SaiyansDao;
import co.db.jooq.tables.pojos.Saiyans;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springBootliquibasejooq.ModelRepository;
import com.springBootliquibasejooq.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
@RestController
public class ModelController {

    @Autowired
    ModelService modelService;

    @Autowired
    ModelRepository modelRepository;

    @RequestMapping("/addModel")
    public String addModel(@RequestBody Saiyans model){
        return modelService.addModel(model.getSaiyanId(),model.getSaiyanName(),
                model.getSaiyanGender(),model.getSaiyanBlood());
    }

    @RequestMapping ("getModels")
    public List<Saiyans> getModels(){
        //Optional<ModelRecord> msd = modelRepository.selectFrom();
    return modelRepository.selectAllSaiyans();
    }
}
