package team4.teambuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team4.teambuilder.model.User;
import team4.teambuilder.model.Group;
import team4.teambuilder.repository.UserRepository;
import team4.teambuilder.repository.GroupRepository;
import team4.teambuilder.service.TeamAssignmentService;
import team4.teambuilder.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.Comparator;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeamBuilderApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private TeamAssignmentService teamAssignmentService;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void setUp() {
        // Clear all existing users and groups
        userRepository.deleteAll();
        groupRepository.deleteAll();

        // Create new test users
        userService.createUser(new User("Alice", "alice@example.com", "Developer", Arrays.asList("Leader", "Java")));
        userService.createUser(new User("Bob", "bob@example.com", "Designer", Arrays.asList("Collaborator", "UI/UX")));
        userService.createUser(new User("Charlie", "charlie@example.com", "Manager", Arrays.asList("Coordinator", "Agile")));
        userService.createUser(new User("David", "david@example.com", "Developer", Arrays.asList("Team player", "Python")));
        userService.createUser(new User("Eve", "eve@example.com", "Tester", Arrays.asList("Detail-oriented", "QA")));
    }

    @Test
    public void testUserCreation() {
        User newUser = new User("Frank", "frank@example.com", "Developer", Arrays.asList("Problem solver", "JavaScript"));
        User createdUser = userService.createUser(newUser);
        assertNotNull(createdUser.getId());
        assertEquals("Frank", createdUser.getName());
        assertEquals("frank@example.com", createdUser.getEmail());
        assertEquals("Developer", createdUser.getRole());
        assertEquals(Arrays.asList("Problem solver", "JavaScript"), createdUser.getAnswers());
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = userService.getAllUsers();
        assertEquals(5, users.size());
    }

    @Test
    public void testUpdateUser() {
        User user = userService.getAllUsers().get(0);
        user.setRole("Senior Developer");
        user.setAnswers(Arrays.asList("Leader", "Java", "Spring"));
        User updatedUser = userService.updateUser(user.getId(), user);
        assertEquals("Senior Developer", updatedUser.getRole());
        assertEquals(Arrays.asList("Leader", "Java", "Spring"), updatedUser.getAnswers());
    }

    @Test
    public void testDeleteUser() {
        User user = userService.getAllUsers().get(0);
        userService.deleteUser(user.getId());
        assertEquals(4, userService.getAllUsers().size());
    }

    @Test
    public void testTeamAssignment() {
        // Create a test group
        Group testGroup = new Group("Test Group");
        testGroup = groupRepository.save(testGroup);

        // Create users with specific answers to test the scoring system
        User user1 = new User("Alice", "alice@example.com", "Team Leader", Arrays.asList("Leader", "Problem solver", "AWS", "Python", "Jira"));
        User user2 = new User("Bob", "bob@example.com", "Requirements Leader", Arrays.asList("Collaborator", "UI/UX", "Figma", "Creative"));
        User user3 = new User("Charlie", "charlie@example.com", "Design and Implementation Leader", Arrays.asList("Coordinator", "Agile", "Scrum master", "Communicator", "Problem solver", "AWS"));
        User user4 = new User("David", "david@example.com", "Design and Implementation Leader", Arrays.asList("Team player", "Python", "Machine Learning", "Docker", "Analytical", "Python"));
        User user5 = new User("Eve", "eve@example.com", "Quality Assurance Leader", Arrays.asList("Detail-oriented", "QA", "Automation", "Selenium"));
        User user6 = new User("Frank", "frank@example.com", "Configuration Leader", Arrays.asList("Problem solver", "Kubernetes", "Jenkins", "Monitoring"));
        User user7 = new User("Grace", "grace@example.com", "Security Leader", Arrays.asList("Analytical", "Python", "SQL", "Statistics"));
        User user8 = new User("Henry", "henry@example.com", "Design and Implementation Leader", Arrays.asList("JavaScript", "React", "CSS", "Responsive design"));

        List<User> users = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8);

        for (User user : users) {
            user.setGroup(testGroup);
            userRepository.save(user);
        }

        List<List<User>> teams = teamAssignmentService.assignTeams(testGroup.getId(), 2);

        // Assertions
        assertEquals(2, teams.size(), "There should be 2 teams");

        int totalUsers = users.size();
        int expectedMinTeamSize = totalUsers / 2;
        int expectedMaxTeamSize = (totalUsers + 1) / 2;

        System.out.println("Total users: " + totalUsers);
        System.out.println("Expected min team size: " + expectedMinTeamSize);
        System.out.println("Expected max team size: " + expectedMaxTeamSize);
        System.out.println("Actual team sizes: " + teams.get(0).size() + ", " + teams.get(1).size());

        assertTrue(teams.get(0).size() >= expectedMinTeamSize && teams.get(0).size() <= expectedMaxTeamSize,
                "Team 1 size should be between " + expectedMinTeamSize + " and " + expectedMaxTeamSize);
        assertTrue(teams.get(1).size() >= expectedMinTeamSize && teams.get(1).size() <= expectedMaxTeamSize,
                "Team 2 size should be between " + expectedMinTeamSize + " and " + expectedMaxTeamSize);
        assertEquals(totalUsers, teams.get(0).size() + teams.get(1).size(),
                "The sum of team sizes should equal the total number of users");

        // Check if roles are distributed across teams
        Set<String> team1Roles = teams.get(0).stream().map(User::getRole).collect(Collectors.toSet());
        Set<String> team2Roles = teams.get(1).stream().map(User::getRole).collect(Collectors.toSet());

        assertTrue(team1Roles.size() >= 3, "Team 1 should have at least 3 different roles");
        assertTrue(team2Roles.size() >= 3, "Team 2 should have at least 3 different roles");

        // Check if developers are in different teams
        assertTrue(
                teams.get(0).stream().anyMatch(u -> u.getRole().contains("Design and Implementation Leader")) &&
                        teams.get(1).stream().anyMatch(u -> u.getRole().contains("Design and Implementation Leader")),
                "Both teams should have at least one Design and Implementation Leader"
        );

        // Check if roles with multiple users are evenly distributed
        Map<String, Long> team1RoleCounts = teams.get(0).stream()
                .collect(Collectors.groupingBy(User::getRole, Collectors.counting()));
        Map<String, Long> team2RoleCounts = teams.get(1).stream()
                .collect(Collectors.groupingBy(User::getRole, Collectors.counting()));

        for (String role : team1RoleCounts.keySet()) {
            if (team1RoleCounts.get(role) > 1) {
                assertTrue(team2RoleCounts.containsKey(role),
                        "Role " + role + " should be present in both teams when there are multiple users with that role");
            }
        }

        // Check if high-scoring users are well-distributed
        List<String> highScoringUserNames = Arrays.asList("David", "Charlie");

        long team1HighScorers = teams.get(0).stream()
                .filter(u -> highScoringUserNames.contains(u.getName()))
                .count();
        long team2HighScorers = teams.get(1).stream()
                .filter(u -> highScoringUserNames.contains(u.getName()))
                .count();

        assertTrue(team1HighScorers > 0 && team2HighScorers > 0,
                "High-scoring users should be distributed across teams");
        assertTrue(Math.abs(team1HighScorers - team2HighScorers) <= 1,
                "High-scoring users should be evenly distributed");

    }

}
