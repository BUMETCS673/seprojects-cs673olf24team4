package team4.teambuilder;

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

    /*
        Default
        User("Alice", "alice@example.com", "Developer", Arrays.asList("Leader"));
        User("Bob", "bob@example.com", "Designer", Arrays.asList("Collaborator"));
        User("Charlie", "charlie@example.com", "Manager", Arrays.asList("Coordinator"));
     */

    @Test
    public void testUserCreation() {
        User newUser = new User("David", "david@example.com", "Developer", Arrays.asList("Leader"));
        User createdUser = userService.createUser(newUser);
        assertNotNull(createdUser.getId());
        assertEquals("David", createdUser.getName());
        assertEquals("david@example.com", createdUser.getEmail());
        assertEquals("Developer", createdUser.getRole());
        assertEquals(Arrays.asList("Leader"), createdUser.getAnswers());
    }

    @Test
    public void testSubmitAnswersForUser() {
        User user = userService.createUser(new User("Eric", "eric@example.com", "Designer", null));
        List<String> answers = Arrays.asList("Answer1", "Answer2", "Answer3");
        User updatedUser = userService.submitAnswers(user.getId(), answers);
        assertEquals(answers, updatedUser.getAnswers());
    }

    @Test
    public void testDeleteUser() {
        User user = userService.createUser(new User("Delete User", "delete@example.com", "Deleted", null));
        userService.deleteUser(user.getId());
        assertFalse(userRepository.findById(user.getId()).isPresent());
    }

    @Test
    public void testUserCountAfterCreation() {
        long initialCount = userRepository.count();
        User newUser = new User("Frank", "frank@example.com", "Manager", Arrays.asList("Coordinator"));
        userService.createUser(newUser);
        long finalCount = userRepository.count();
        assertEquals(initialCount + 1, finalCount);
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

        long groupUserCount = userRepository.findByGroupId(testGroup.getId()).size();
        List<List<User>> teams = teamAssignmentService.assignTeams(testGroup.getId(), 3);
        assertEquals(3, teams.size());
        assertTrue(teams.get(0).size() >= 1);
        assertTrue(teams.get(1).size() >= 1);
        assertTrue(teams.get(2).size() >= 1);
        assertEquals(groupUserCount, teams.get(0).size() + teams.get(1).size() + teams.get(2).size());
    }

}
