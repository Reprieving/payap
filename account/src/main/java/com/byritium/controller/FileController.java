package com.byritium.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("file")
public class FileController {
    @RequestMapping("load")
    public ResponseEntity<StreamingResponseBody> file() {
        File file = new File("D:\\r.xlsx");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(outputStream -> {
                    try (InputStream inputStream = new FileInputStream(file)) {
                        StreamUtils.copy(inputStream, outputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }
}
