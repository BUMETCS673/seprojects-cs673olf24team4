package team4.teambuilder.service;

import team4.teambuilder.model.User;
import team4.teambuilder.repository.UserRepository;
import team4.teambuilder.util.KeywordWeights;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.Map;

/**
 * Service for team assignment operations.
 * Algorithm:
 * 1. Group users by role
 * 2. Sort each role by the user's score (descending)
 * 3. Assign users to teams, prioritizes role distribution by processing roles in order of their frequency.
 */
@Service
public class TeamAssignmentService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Assigns teams to a group.
     *
     * @param groupId       the ID of the group to assign teams to
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

        // Group users by role
        Map<String, List<User>> usersByRole = groupUsers.stream()
                .collect(Collectors.groupingBy(User::getRole));

        // Sort roles by the number of users in each role (descending)
        List<String> sortedRoles = usersByRole.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().size(), e1.getValue().size()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        // Assign users to teams, prioritizes role
        int teamIndex = 0;
        boolean ascending = true;
        for (String role : sortedRoles) {
            List<User> usersInRole = usersByRole.get(role);
            usersInRole.sort(Comparator.comparingInt(this::calculateUserScore).reversed());

            for (User user : usersInRole) {
                teams.get(teamIndex).add(user);
                // Order ex. 0, 1, 2, 3, 4, 4, 3, 2, 1, 0
                if (ascending) {
                    teamIndex++;
                    if (teamIndex == numberOfTeams - 1) {
                        ascending = false;
                    }
                } else {
                    teamIndex--;
                    if (teamIndex == 0) {
                        ascending = true;
                    }
                }
            }
        }

        return teams;
    }

    /**
     * Calculates the user's score based on their answers.
     *
     * @param user the user to calculate the score for
     * @return the user's score
     */
    private int calculateUserScore(User user) {
        List<String> answers = user.getAnswers();
        if (answers == null || answers.isEmpty()) {
            return 0;
        }

        return answers.stream()
                .mapToInt(answer -> {
                    if (answer == null) return 0;
                    String lowerCaseAnswer = answer.toLowerCase();
                    return KeywordWeights.WEIGHTS.entrySet().stream()
                            .filter(entry -> lowerCaseAnswer.contains(entry.getKey()))
                            .mapToInt(Map.Entry::getValue)
                            .sum();
                })
                .sum();
    }
}
