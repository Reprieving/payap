package com.byritium.controller;

import com.byritium.respository.CoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("fund")
public class FundController {

    @RequestMapping("in")
    public ResponseEntity<?> in() {
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }


}
