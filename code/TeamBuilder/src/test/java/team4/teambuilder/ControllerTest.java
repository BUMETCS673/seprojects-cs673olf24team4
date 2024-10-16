package team4.teambuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import team4.teambuilder.model.User;
import team4.teambuilder.model.Admin;
import team4.teambuilder.service.AdminService;
import team4.teambuilder.repository.AdminRepository;
import team4.teambuilder.model.Group;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminService adminService;

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "password123";

    @BeforeEach
    public void setUp() {
        // Clear existing data
        adminRepository.deleteAll();

        // Create a test admin
        Admin admin = new Admin();
        admin.setUsername(ADMIN_USERNAME);
        admin.setPassword(ADMIN_PASSWORD);
        adminService.createAdmin(admin);
    }

    //UserAPI Test

    @Test
    public void testCreateUser() throws Exception {
        User user = new User("Test User", "test@example.com", "Tester", Arrays.asList("Testing", "Automation"));

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.role").value("Tester"))
                .andExpect(jsonPath("$.answers[0]").value("Testing"))
                .andExpect(jsonPath("$.answers[1]").value("Automation"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                .param("adminUsername", ADMIN_USERNAME)
                .param("adminPassword", ADMIN_PASSWORD))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetAllUsersUnauthorized() throws Exception {
        mockMvc.perform(get("/api/users")
                .param("adminUsername", "wronguser")
                .param("adminPassword", "wrongpass"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetUserById() throws Exception {
        // First, create a user
        User user = new User("Test User", "test@example.com", "Tester", Arrays.asList("Testing", "Automation"));
        String response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        User createdUser = objectMapper.readValue(response, User.class);

        // Then, get the user by ID
        mockMvc.perform(get("/api/users/" + createdUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        // First, create a user
        User user = new User("Test User", "test@example.com", "Tester", Arrays.asList("Testing", "Automation"));
        String response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        User createdUser = objectMapper.readValue(response, User.class);

        // Update the user
        createdUser.setName("Updated User");
        createdUser.setEmail("updated@example.com");

        mockMvc.perform(put("/api/users/" + createdUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createdUser))
                .param("adminUsername", ADMIN_USERNAME)
                .param("adminPassword", ADMIN_PASSWORD))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated User"))
                .andExpect(jsonPath("$.email").value("updated@example.com"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        // First, create a user
        User user = new User("Test User", "test@example.com", "Tester", Arrays.asList("Testing", "Automation"));
        String response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        User createdUser = objectMapper.readValue(response, User.class);

        // Delete the user
        mockMvc.perform(delete("/api/users/" + createdUser.getId())
                .param("adminUsername", ADMIN_USERNAME)
                .param("adminPassword", ADMIN_PASSWORD))
                .andExpect(status().isNoContent());

        // Verify the user is deleted
        mockMvc.perform(get("/api/users/" + createdUser.getId()))
                .andExpect(status().isNotFound());
    }


    //Group API Test

    @Test
    public void testCreateGroup() throws Exception {
        Group group = new Group("Test Group");

        mockMvc.perform(post("/api/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(group)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Group"));
    }

    @Test
    public void testGetAllGroups() throws Exception {
        mockMvc.perform(get("/api/groups"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetGroupById() throws Exception {
        // First, create a group
        Group group = new Group("Test Group");
        String response = mockMvc.perform(post("/api/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(group)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Group createdGroup = objectMapper.readValue(response, Group.class);

        // Then, get the group by ID
        mockMvc.perform(get("/api/groups/" + createdGroup.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Group"));
    }

    @Test
    public void testAssignTeams() throws Exception {
        // First, create a group
        Group group = new Group("Test Group");
        String response = mockMvc.perform(post("/api/groups")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(group)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Group createdGroup = objectMapper.readValue(response, Group.class);

        // Create some users and assign them to the group
        User user1 = new User("Alice", "alice@example.com", "Developer", Arrays.asList("Java", "Spring"));
        User user2 = new User("Bob", "bob@example.com", "Designer", Arrays.asList("UI/UX", "Figma"));
        user1.setGroup(createdGroup);
        user2.setGroup(createdGroup);
        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user1))).andExpect(status().isOk());
        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user2))).andExpect(status().isOk());

        // Assign teams
        mockMvc.perform(post("/api/groups/" + createdGroup.getId() + "/assign-teams")
                .param("numberOfTeams", "2")
                .param("adminUsername", "admin")
                .param("adminPassword", "password123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].teamNumber").exists())
                .andExpect(jsonPath("$[0].members").isArray())
                .andExpect(jsonPath("$[1].teamNumber").exists())
                .andExpect(jsonPath("$[1].members").isArray());
    }
    
    @Test
    public void testGetTeamsByGroupId() throws Exception {
        // First, create a group
        Group group = new Group("Test Group");
        String groupResponse = mockMvc.perform(post("/api/groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(group)))
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();

        Group createdGroup = objectMapper.readValue(groupResponse, Group.class);

        // Create some users and assign them to the group
        User user1 = new User("Alice", "alice@example.com", "Developer", Arrays.asList("Java", "Spring"));
        User user2 = new User("Bob", "bob@example.com", "Designer", Arrays.asList("UI/UX", "Figma"));
        user1.setGroup(createdGroup);
        user2.setGroup(createdGroup);
        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user1))).andExpect(status().isOk());
        mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(user2))).andExpect(status().isOk());

        // Assign teams
        mockMvc.perform(post("/api/groups/" + createdGroup.getId() + "/assign-teams")
                .param("numberOfTeams", "2")
                .param("adminUsername", "admin")
                .param("adminPassword", "password123"))
                .andExpect(status().isOk());

        // Get teams for the group
        mockMvc.perform(get("/api/groups/" + createdGroup.getId() + "/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].teamNumber").exists())
                .andExpect(jsonPath("$[0].members").isArray())
                .andExpect(jsonPath("$[1].teamNumber").exists())
                .andExpect(jsonPath("$[1].members").isArray());
    }

}