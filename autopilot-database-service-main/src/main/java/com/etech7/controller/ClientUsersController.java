package com.etech7.controller;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
import com.etech7.entity.ClientUsers;
import com.etech7.entity.Conversations;
import com.etech7.service.ClientUsersService;

@RestController
@RequestMapping("/{mspCustomDomain}/clientUsers")
public class ClientUsersController {

    @Autowired
    private ClientUsersService clientUsersService;

    @GetMapping("/getById")
    public ResponseEntity<ClientUsers> getClientByIdAndMspCustomDomain(
            @PathVariable String mspCustomDomain,
            @RequestParam String id) {
        if (mspCustomDomain == null || id == null) {
            return ResponseEntity.badRequest().build();
        }
        ClientUsers clientUser = clientUsersService.getClientByIdAndMspCustomDomain(id, mspCustomDomain);
        return ResponseEntity.ok(clientUser);
    }
    

	@GetMapping("/delete")
	public ClientUsers get(@RequestParam(required = true) String id) {
		return clientUsersService.updateToDeleteUsersById(id);

	}

    @GetMapping("/getByEmail")
    public ResponseEntity<ClientUsers> getClient(
            @PathVariable String mspCustomDomain,
            @RequestParam String email) {
        if (mspCustomDomain == null || email == null) {
            return ResponseEntity.badRequest().build();
        }
        ClientUsers clientUser = clientUsersService.getClient(email, mspCustomDomain);
        return ResponseEntity.ok(clientUser);
    }

    @PostMapping("/add")
    public ResponseEntity<ClientUsers> addClientUser(
            @PathVariable String mspCustomDomain,
            @RequestBody ClientUsers clientUser) {
        if (mspCustomDomain == null || clientUser == null) {
            return ResponseEntity.badRequest().build();
        }
        ClientUsers addedClientUser = clientUsersService.addClientUser(clientUser);
        return ResponseEntity.ok(addedClientUser);
    }

    @PostMapping("/verifyPassword")
    public ResponseEntity<Boolean> verifyPassword(
            @PathVariable String mspCustomDomain,
            @RequestParam String email,
            @RequestParam String password) {
        if (mspCustomDomain == null || email == null || password == null) {
            return ResponseEntity.badRequest().build();
        }
        boolean isVerified = clientUsersService.verifyPassword(email, mspCustomDomain, password);
        return ResponseEntity.ok(isVerified);
    }

    @PutMapping("/edit")
    public ResponseEntity<ClientUsers> editClientUser(
            @PathVariable String mspCustomDomain,
            @RequestBody ClientUsers clientUser) {
        if (mspCustomDomain == null || clientUser == null) {
            return ResponseEntity.badRequest().build();
        }
        ClientUsers editedClientUser = clientUsersService.editClientUser(clientUser, mspCustomDomain);
        return ResponseEntity.ok(editedClientUser);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(
            @PathVariable String mspCustomDomain,
            @RequestBody UserSignInRequest req) {
        if (mspCustomDomain == null || req == null) {
            return ResponseEntity.badRequest().build();
        }
        ClientUsers client = clientUsersService.getClient(req.getEmailId(), mspCustomDomain);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found.");
        } else {
            boolean isResetSuccessful = clientUsersService.resetPassword(client, req.getPassword(), req.getNewPassword());
            if (isResetSuccessful) {
                return ResponseEntity.ok("Password reset successful.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid old password.");
            }
        }
    }

    @PostMapping("/createAccount")
    public ResponseEntity<String> createAccount(
            @PathVariable String mspCustomDomain,
            @RequestBody ClientUsers clientUser) {
        if (mspCustomDomain == null || clientUser == null) {
            return ResponseEntity.badRequest().build();
        }
        ClientUsers existingClientUser = clientUsersService.getClient(clientUser.getEmail(), mspCustomDomain);
        if (existingClientUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists.");
        }
        clientUsersService.addClientUser(clientUser);
        return ResponseEntity.ok("User created successfully");
    }
       	
//    @GetMapping("/signup")
//    public ResponseEntity<?> signupProcess(
//            @PathVariable String mspCustomDomain,
//            @RequestParam String emailId) {
//        if (mspCustomDomain == null || emailId == null) {
//            return ResponseEntity.badRequest().build();
//        }
//        ClientUsers client = clientUsersService.getClient(emailId, mspCustomDomain);
//        if (Objects.isNull(client)) {
//            // Assuming cws.getclientUsers(emailId) is now refactored to clientUsersService.getUsersByEmail(emailId)
//           ClientUsers users = clientUsersService.getClient(emailId,mspCustomDomain);
//            if (users != null && !users.isEmpty()) {
//                return ResponseEntity.ok(users);
//            } else {
//                // Assuming a constructor with these arguments exists
//                return ResponseEntity.ok(Collections.singletonList(new ClientUsers(emailId, 19298, "Catchall")));
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists. Please sign in.");
//        }
//    }

    @PostMapping("/signin")
    public ResponseEntity<?> signinProcess(
            @PathVariable String mspCustomDomain,
            @RequestBody UserSignInRequest req) {
        if (mspCustomDomain == null || req == null) {
            return ResponseEntity.badRequest().build();
        }
        ClientUsers client = clientUsersService.getClient(req.getEmailId(), mspCustomDomain);
        if (Objects.isNull(client)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found. Please sign up.");
        } else {
            if (clientUsersService.verifyPassword(req.getEmailId(), mspCustomDomain, req.getPassword())) {
                return ResponseEntity.ok(client);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }
        }
    }
}
