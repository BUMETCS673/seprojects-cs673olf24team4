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

        // Assign users to the test group
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.setGroup(testGroup);
            userRepository.save(user);
        }

        List<List<User>> teams = teamAssignmentService.assignTeams(testGroup.getId(), 2);
        assertEquals(2, teams.size());
        assertEquals(3, teams.get(0).size());
        assertEquals(2, teams.get(1).size());
    }

}
