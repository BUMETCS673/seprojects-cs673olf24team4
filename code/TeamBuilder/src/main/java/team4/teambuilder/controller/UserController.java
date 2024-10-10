package team4.teambuilder.controller;

import team4.teambuilder.model.User;
import team4.teambuilder.service.UserService;
import team4.teambuilder.service.AdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing User entities.
 * Provides endpoints for CRUD operations on users.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    /**
     * Retrieves all users. Requires admin authentication.
     *
     * @param adminUsername the admin username
     * @param adminPassword the admin password
     * @return a list of users if authentication is successful, otherwise returns a 401 Unauthorized response
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(
            @RequestParam String adminUsername,
            @RequestParam String adminPassword) {
        if (adminService.authenticateAdmin(adminUsername, adminPassword)) {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    /**
     * Retrieves a user by ID.
     *
     * @param id the ID of the user to retrieve
     * @return the user if found, otherwise returns a 404 Not Found response
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates a new user.
     *
     * @param user the user to create
     * @return the created user
     */
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    /**
     * Deletes a user by ID. Requires admin authentication.
     *
     * @param id the ID of the user to delete
     * @param adminUsername the admin username
     * @param adminPassword the admin password
     * @return a ResponseEntity with no content if successful, or a 401 Unauthorized if authentication fails
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable UUID id,
            @RequestParam String adminUsername,
            @RequestParam String adminPassword) {
        if (adminService.authenticateAdmin(adminUsername, adminPassword)) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Updates a user by ID. Requires admin authentication.
     *
     * @param id the ID of the user to update
     * @param userDetails the updated user details
     * @param adminUsername the admin username
     * @param adminPassword the admin password
     * @return the updated user if authentication is successful, otherwise returns a 401 Unauthorized response
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable UUID id,
            @RequestBody User userDetails,
            @RequestParam String adminUsername,
            @RequestParam String adminPassword) {
        if (adminService.authenticateAdmin(adminUsername, adminPassword)) {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * Submits answers for a user.
     *
     * @param user the user with answers
     * @return the saved user
     */
    @PostMapping("/submit-answers")
    public ResponseEntity<User> submitAnswers(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.ok(savedUser);
    }

    /**
     * Assigns a user to a group.
     *
     * @param userId the ID of the user to assign
     * @param groupId the ID of the group to assign the user to
     * @return the updated user
     */
    @PostMapping("/{userId}/assign-group/{groupId}")
    public ResponseEntity<User> assignUserToGroup(@PathVariable UUID userId, @PathVariable Long groupId) {
        User updatedUser = userService.assignUserToGroup(userId, groupId);
        return ResponseEntity.ok(updatedUser);
    }
}
