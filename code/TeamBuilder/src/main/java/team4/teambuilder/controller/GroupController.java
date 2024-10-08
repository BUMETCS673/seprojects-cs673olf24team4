package team4.teambuilder.controller;

import team4.teambuilder.model.User;
import team4.teambuilder.model.Group;
import team4.teambuilder.service.AdminService;
import team4.teambuilder.service.GroupService;
import team4.teambuilder.service.TeamAssignmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * REST controller for managing Group entities.
 * Provides endpoints for CRUD operations on groups.
 */
@RestController
@RequestMapping("/api/groups")
public class GroupController {

    /**
     * Service for group operations.
     */
    @Autowired
    private GroupService groupService;

    /**
     * Service for team assignment operations.
     */
    @Autowired
    private TeamAssignmentService teamAssignmentService;

    /**
     * Creates a new group.
     *
     * @param group the group to create
     * @return the created group
     */
    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group createdGroup = groupService.createGroup(group);
        return ResponseEntity.ok(createdGroup);
    }

    /**
     * Retrieves all groups.
     *
     * @return a list of groups
     */
    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    /**
     * Retrieves a group by ID.
     *
     * @param id the ID of the group to retrieve
     * @return the group if found, otherwise returns a 404 Not Found response
     */
    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Service for admin operations.
     */
    @Autowired
    private AdminService adminService;

    /**
     * Assigns teams to a group.
     *
     * @param groupId the ID of the group to assign teams to
     * @param numberOfTeams the number of teams to assign
     * @param adminUsername the username of the admin
     * @param adminPassword the password of the admin
     * @return the assigned teams
     */
    @PostMapping("/{groupId}/assign-teams")
    public ResponseEntity<?> assignTeams(
            @PathVariable Long groupId, 
            @RequestParam int numberOfTeams,
            @RequestParam String adminUsername,
            @RequestParam String adminPassword) {
        if (!adminService.authenticateAdmin(adminUsername, adminPassword)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Admin authentication failed");
        }
        List<List<User>> teams = teamAssignmentService.assignTeams(groupId, numberOfTeams);
        return ResponseEntity.ok(teams);
    }

    
}