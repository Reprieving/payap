package com.byritium.controller;

import com.byritium.dto.ResponseBody;
import com.byritium.respository.CoreRepository;
import com.byritium.respository.EntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("entity")
public class CoreController {

    @Autowired
    private CoreRepository coreRepository;

    @RequestMapping("register")
    public ResponseEntity<?> create() {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
