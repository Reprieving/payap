package com.byritium.controller;

import com.byritium.constance.ResponseCode;
import com.byritium.dto.ResponseBody;
import com.byritium.entity.AccountAuth;
import com.byritium.exception.BusinessException;
import com.byritium.respository.EntityRepository;
import com.byritium.service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.SocketUtils;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("entity")
public class EntityController {

    @Autowired
    private EntityService entityService;

    @RequestMapping("create")
    public Object create() {
        Map<String, Object> map = new HashMap<>();

        return map;
    }

    @RequestMapping("deposit")
    public ResponseEntity<?> deposit(String goodsId) {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseBody.build().data(null));
    }

    @RequestMapping("withdraw")
    public ResponseEntity<?> withdraw() {
        return ResponseEntity.status(HttpStatus.OK).body(ResponseBody.build().data(null));
    }

    @RequestMapping("recorded")
    public ResponseEntity<?> recorded() {


        return ResponseEntity.status(HttpStatus.OK).body(ResponseBody.build().data(null));
    }

    @RequestMapping("payment")
    public ResponseEntity<?> payment() {


        return ResponseEntity.status(HttpStatus.OK).body(ResponseBody.build().data(null));
    }

}
