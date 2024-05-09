package com.apiusers.controller;

import com.apiusers.record.request.UserRequestRecord;
import com.apiusers.record.response.ResponseMessageRecord;
import com.apiusers.record.response.UserResponseRecord;
import com.apiusers.service.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UserResponseRecord>> findAllWithPagination(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "orderBy", defaultValue = "id") String orderBy,
            @RequestParam(name = "order", defaultValue = "desc") String order
    ){
        return new ResponseEntity<>(this.service.findAllWithPagination(page, size, orderBy, order), HttpStatus.OK);
    }

    @GetMapping( value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserResponseRecord>> findAll(@RequestParam(
            name = "roleId",
            defaultValue = "0",
            required = false
    ) Long roleId) {
        return new ResponseEntity<>(this.service.findAll(roleId), HttpStatus.OK);
    }

    @GetMapping( value =  "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseRecord> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.service.findById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessageRecord> create(@Valid @RequestBody UserRequestRecord data) {
        return new ResponseEntity<>(this.service.create(data), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessageRecord> update(@PathVariable("id") Long id, @Valid @RequestBody UserRequestRecord data) {
        return new ResponseEntity<>(this.service.update(id, data), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseMessageRecord> delete(@PathVariable("id") Long id) {
        return new ResponseEntity<>(this.service.delete(id), HttpStatus.OK);
    }
}
