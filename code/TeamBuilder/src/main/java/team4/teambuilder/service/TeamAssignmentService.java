package team4.teambuilder.service;

import team4.teambuilder.model.User;
import team4.teambuilder.model.Group;
import team4.teambuilder.repository.UserRepository;
import team4.teambuilder.repository.GroupRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

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
    @Transactional
    public List<List<User>> assignTeams(Long groupId, int numberOfTeams) {
        List<User> groupUsers = userRepository.findByGroupId(groupId);
        List<List<User>> teams = new ArrayList<>();

        // Initialize teams
        for (int i = 0; i < numberOfTeams; i++) {
            teams.add(new ArrayList<>());
        }

        // Calculate scores and sort users
        List<User> sortedUsers = groupUsers.stream()
            .sorted(Comparator.comparingDouble(this::calculateUserScore).reversed())
            .collect(Collectors.toList());

        // Assign users to teams using round-robin approach
        for (int i = 0; i < sortedUsers.size(); i++) {
            teams.get(i % numberOfTeams).add(sortedUsers.get(i));
        }


        return teams;
    }

    private double calculateUserScore(User user) {
        List<String> answers = user.getAnswers();
        if (answers == null || answers.isEmpty()) {
            return 0.0;
        }

        // Simple scoring: sum of answer lengths
        return answers.stream()
            .mapToDouble(answer -> answer != null ? answer.length() : 0)
            .sum();
    }
}
