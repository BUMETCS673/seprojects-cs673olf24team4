package team4.teambuilder.controller;

import team4.teambuilder.model.User;
import team4.teambuilder.model.Group;
import team4.teambuilder.service.GroupService;
import team4.teambuilder.service.TeamAssignmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private TeamAssignmentService teamAssignmentService;

    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody Group group) {
        Group createdGroup = groupService.createGroup(group);
        return ResponseEntity.ok(createdGroup);
    }

    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        return groupService.getGroupById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{groupId}/assign-teams")
    public ResponseEntity<List<List<User>>> assignTeams(
            @PathVariable Long groupId, 
            @RequestParam int numberOfTeams) {
        List<List<User>> teams = teamAssignmentService.assignTeams(groupId, numberOfTeams);
        return ResponseEntity.ok(teams);
    }

    
}