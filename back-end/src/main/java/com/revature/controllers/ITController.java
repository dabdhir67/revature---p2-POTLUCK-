package com.revature.controllers;

import com.revature.models.Chef;
import com.revature.aspects.annotations.NoAuthIn;
import com.revature.utilities.SecurityUtil;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/init")
public class ITController {

    @PostMapping
    @NoAuthIn
    public String getToken(@RequestBody Chef chef, @RequestHeader Integer id, @RequestHeader String passkey) {
        chef.setC_id(id);
        chef.setPasskey(passkey);
        return SecurityUtil.generateToken(chef);
    }
}
