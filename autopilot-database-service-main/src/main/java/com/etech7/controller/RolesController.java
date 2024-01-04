package com.etech7.controller;

import com.etech7.entity.Roles;
import com.etech7.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{mspCustomDomain}/roles")
public class RolesController {

    @Autowired
    private RolesService rolesService;

    @PostMapping("/create")
    public ResponseEntity<Roles> createRole(@PathVariable String mspCustomDomain, @RequestBody Roles role) {
        if (role == null) {
        	//        if (mspCustomDomain == null || role == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(rolesService.createRole(role));
    }

    @GetMapping("/getById")
    public ResponseEntity<Roles> getRole(@RequestParam String id) {
        return rolesService.getRole(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/getByName")
    public ResponseEntity<Roles> getRoleByName(@RequestParam String name) {
        Roles role = rolesService.getRoleByName(name);
        if (role != null) {
            return ResponseEntity.ok(role);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Roles>> getAllRoles(@PathVariable String mspCustomDomain) {
        return ResponseEntity.ok(rolesService.getAllRoles(mspCustomDomain));
    }

    @GetMapping("/delete")
    public ResponseEntity<Roles> updateToDeleteRoleById(@RequestParam String id) {
        Roles updatedRole = rolesService.updateToDeleteRoleById(id);
        if (updatedRole != null) {
            return ResponseEntity.ok(updatedRole);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
