package team4.teambuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import team4.teambuilder.model.User;
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
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
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
                .content(objectMapper.writeValueAsString(createdUser)))
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
        mockMvc.perform(delete("/api/users/" + createdUser.getId()))
                .andExpect(status().isOk());

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

        // Assign teams
        mockMvc.perform(post("/api/groups/" + createdGroup.getId() + "/assign-teams")
                .param("numberOfTeams", "2")
                .param("adminUsername", "admin")
                .param("adminPassword", "password123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
    

}