package team4.teambuilder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team4.teambuilder.model.Admin;
import team4.teambuilder.service.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // POST endpoint to authenticate admin credentials
    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticateAdmin(@RequestBody Admin request) {
        boolean isAuthenticated = adminService.authenticateAdmin(request.getUsername(), request.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Authenticated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
