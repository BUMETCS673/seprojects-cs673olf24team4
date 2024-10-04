package team4.teambuilder.service;

import team4.teambuilder.model.User;
import team4.teambuilder.model.Group;
import team4.teambuilder.repository.UserRepository;
import team4.teambuilder.repository.GroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for team assignment operations.
 */
@Service
public class TeamAssignmentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    /**
     * Assigns teams to a group.
     *
     * @param groupId the ID of the group to assign teams to
     * @param numberOfTeams the number of teams to assign
     * @return the assigned teams
     */
    public List<List<User>> assignTeams(Long groupId, int numberOfTeams) {
        List<User> groupUsers = userRepository.findByGroupId(groupId);
        List<List<User>> teams = new ArrayList<>();

        // Initialize teams
        for (int i = 0; i < numberOfTeams; i++) {
            teams.add(new ArrayList<>());
        }

        // Simple logic to assign teams
        for (int i = 0; i < groupUsers.size(); i++) {
            teams.get(i % numberOfTeams).add(groupUsers.get(i));
        }

        return teams;
    }
}
