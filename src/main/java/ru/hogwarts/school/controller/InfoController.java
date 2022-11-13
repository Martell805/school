package ru.hogwarts.school.controller;

import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class InfoController {
    @Value("${server.port}")
    private Integer port;

    @GetMapping("/getPort")
    public ResponseEntity<Integer> gerPort(){
        return ResponseEntity.ok(this.port);
    }
}
