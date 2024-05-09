package com.apiusers.controller;

import com.apiusers.record.response.PhoneCodeResponseRecord;
import com.apiusers.service.IPhoneCodeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/codes")
@AllArgsConstructor
public class PhoneCodeController {

    private final IPhoneCodeService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PhoneCodeResponseRecord>> findAll() {
        return new ResponseEntity<>(this.service.findAll(), HttpStatus.OK);
    }

}
