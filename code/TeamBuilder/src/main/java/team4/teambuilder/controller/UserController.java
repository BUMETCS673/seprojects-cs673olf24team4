package team4.teambuilder.controller;

import team4.teambuilder.model.User;
import team4.teambuilder.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    /**
     * Retrieves all users.
     *
     * @return a list of users
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
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
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    /**
     * Updates a user by ID.
     *
     * @param id the ID of the user to update
     * @param userDetails the updated user details
     * @return the updated user
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
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
