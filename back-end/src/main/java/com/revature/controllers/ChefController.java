package com.revature.controllers;

import com.revature.models.Chef;
import com.revature.aspects.annotations.NoAuthIn;
import com.revature.services.ChefService;
import com.revature.utilities.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/chef")
public class ChefController {
    @Autowired
    private ChefService chefService;

    @PostMapping
    @NoAuthIn
    public ResponseEntity<Chef> addChef(@RequestBody Chef chef, @RequestHeader("password") String password) {
        chef.setC_id(SecurityUtil.getId());
        chef.setPasskey(SecurityUtil.hashPassword(password));
        int i = chefService.addChef(chef);
        if (i > 0) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", SecurityUtil.generateToken(chef));
            return new ResponseEntity<>(chef, headers, HttpStatus.CREATED);
        } else {
            if (chef.isValid() || password == null || password.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @GetMapping("/{username}")
    @NoAuthIn
    public ResponseEntity<Chef> getChefByUsername(@PathVariable("username") String username, @RequestHeader("password") String password) {
        if (password == null || password.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } else {
            Chef chef = chefService.getChefByUsername(username);
            if (chef == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            } else {
                if (!SecurityUtil.isPassword(password, chef.getPasskey())) {
                    return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
                } else {
                    HttpHeaders headers = new HttpHeaders();
                    headers.add("Authorization", SecurityUtil.generateToken(chef));
                    return new ResponseEntity<>(chef, headers, HttpStatus.OK);
                }
            }
        }
    }

    @GetMapping("/id={id}")
    public ResponseEntity<Chef> getChefById(@PathVariable("id") int id) {
        Chef chef = chefService.getChefById(id);
        if (chef == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", SecurityUtil.generateToken(chef));
            return new ResponseEntity<>(chef, headers, HttpStatus.OK);
        }
    }
}