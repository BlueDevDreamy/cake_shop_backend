package com.andjela.diplomski.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public ResponseEntity<String> homeController(){
        String res = "Welcome To E-Commerce System";
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
