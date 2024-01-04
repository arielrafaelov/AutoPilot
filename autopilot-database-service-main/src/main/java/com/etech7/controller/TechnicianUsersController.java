package com.etech7.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.etech7.dto.UserSignInRequest;
import com.etech7.entity.TechnicianUsers;
import com.etech7.service.TechnicianUsersService;

@RestController
@RequestMapping("/{mspCustomDomain}/technicianUsers")
public class TechnicianUsersController {

    @Autowired
    private TechnicianUsersService technicianUsersService;

    @GetMapping("/activeTechnicians")
    public ResponseEntity<List<TechnicianUsers>> getActiveTechniciansByMspCustomDomain(
            @PathVariable String mspCustomDomain) {
        if (mspCustomDomain == null) {
            return ResponseEntity.badRequest().build();
        }
        List<TechnicianUsers> activeTechnicians = technicianUsersService
                .getActiveTechniciansByMspCustomDomain(mspCustomDomain);
        return ResponseEntity.ok(activeTechnicians);
    }

    @GetMapping("/getById")
    public ResponseEntity<TechnicianUsers> getClientByIdAndMspCustomDomain(
            @PathVariable String mspCustomDomain,
            @RequestParam String id) {
        if (mspCustomDomain == null || id == null) {
            return ResponseEntity.badRequest().build();
        }
        TechnicianUsers user = technicianUsersService.getTechnicianUserByIdAndMspCustomDomain(id, mspCustomDomain);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/getByEmail")
    public ResponseEntity<TechnicianUsers> getTechnician(
            @PathVariable String mspCustomDomain,
            @RequestParam String email) {
        if (mspCustomDomain == null || email == null) {
            return ResponseEntity.badRequest().build();
        }
        TechnicianUsers technicianUser = technicianUsersService.getTechnician(email, mspCustomDomain);
        return ResponseEntity.ok(technicianUser);
    }

    @GetMapping("/delete")
    public TechnicianUsers get(@RequestParam(required = true) String id) {
        return technicianUsersService.updateToDeleteUsersById(id);

    }

    @PostMapping("/add")
    public ResponseEntity<TechnicianUsers> addTechnicianUser(
            @PathVariable String mspCustomDomain,
            @RequestBody TechnicianUsers technicianUser) {
        if (mspCustomDomain == null || technicianUser == null) {
            return ResponseEntity.badRequest().build();
        }
        TechnicianUsers addedTechnicianUser = technicianUsersService.addTechnicianUser(mspCustomDomain, technicianUser);
        return ResponseEntity.ok(addedTechnicianUser);
    }

    // @PostMapping("/verifyPassword")
    // public ResponseEntity<Boolean> verifyPassword(
    // @PathVariable String mspCustomDomain,
    // @RequestParam String email,
    // @RequestParam String password) {
    // if (mspCustomDomain == null || email == null || password == null) {
    // return ResponseEntity.badRequest().build();
    // }
    // boolean isVerified = technicianUsersService.verifyPassword(email,
    // mspCustomDomain, password);
    // return ResponseEntity.ok(isVerified);
    // }

    @PostMapping("/verifyPassword")
    public ResponseEntity<?> verifyPasswordAndGetTechUser(
            @PathVariable String mspCustomDomain,
            @RequestParam String email,
            @RequestParam String password) {
        if (mspCustomDomain == null || email == null || password == null) {
            return ResponseEntity.badRequest().build();
        }

        TechnicianUsers technicianUser = technicianUsersService.verifyPasswordAndGetTechUser(email, mspCustomDomain,
                password);
        if (technicianUser != null) {
            return ResponseEntity.ok(technicianUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<TechnicianUsers> editTechnicianUser(
            @PathVariable String mspCustomDomain,
            @RequestBody TechnicianUsers technicianUser) {
        if (mspCustomDomain == null || technicianUser == null) {
            return ResponseEntity.badRequest().build();
        }
        TechnicianUsers editedTechnicianUser = technicianUsersService.editTechnicianUser(technicianUser,
                mspCustomDomain);
        return ResponseEntity.ok(editedTechnicianUser);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(
            @PathVariable String mspCustomDomain,
            @RequestBody UserSignInRequest req) {
        if (mspCustomDomain == null || req == null) {
            return ResponseEntity.badRequest().build();
        }
        TechnicianUsers technician = technicianUsersService.getTechnician(req.getEmailId(), mspCustomDomain);
        if (technician == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
        } else {
            boolean isResetSuccessful = technicianUsersService.resetPassword(technician, req.getPassword(),
                    req.getNewPassword());
            if (isResetSuccessful) {
                return ResponseEntity.ok("Password reset successful.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid old password.");
            }
        }
    }
}
