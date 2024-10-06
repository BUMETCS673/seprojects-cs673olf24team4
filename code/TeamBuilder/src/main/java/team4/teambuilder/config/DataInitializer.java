package team4.teambuilder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import team4.teambuilder.model.User;
import team4.teambuilder.model.Group;
import team4.teambuilder.repository.UserRepository;
import team4.teambuilder.repository.GroupRepository;
import team4.teambuilder.model.Admin;
import team4.teambuilder.repository.AdminRepository;

import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;
    
    @Autowired
    private AdminRepository adminRepository;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            // Create a test group
            Group testGroup = new Group("Test Group");
            testGroup = groupRepository.save(testGroup);

            User user1 = new User("Alice", "alice@example.com", "Team Leader", 
                Arrays.asList("Leader", "Problem solver", "AWS", "Python", "Jira"));

            User user2 = new User("Bob", "bob@example.com", "Requirements Leader", 
                Arrays.asList("Collaborator", "UI/UX", "Figma", "Creative"));

            User user3 = new User("Charlie", "charlie@example.com", "Design and Implementation Leader", 
                Arrays.asList("Coordinator", "Agile", "Scrum master", "Communicator", "Problem solver", "AWS"));

            User user4 = new User("David", "david@example.com", "Design and Implementation Leader", 
                Arrays.asList("Team player", "Python", "Machine Learning", "Docker", "Analytical", "Python"));

            User user5 = new User("Eve", "eve@example.com", "Quality Assurance Leader", 
                Arrays.asList("Detail-oriented", "QA", "Automation", "Selenium"));

            User user6 = new User("Frank", "frank@example.com", "Configuration Leader", 
                Arrays.asList("Problem solver", "Kubernetes", "Jenkins", "Monitoring"));

            User user7 = new User("Grace", "grace@example.com", "Security Leader", 
                Arrays.asList("Analytical", "Python", "SQL", "Statistics"));

            User user8 = new User("Henry", "henry@example.com", "Design and Implementation Leader", 
                Arrays.asList("JavaScript", "React", "CSS", "Responsive design"));

            
            List<User> users = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8);
            for (User user : users) {
                user.setGroup(testGroup);
                userRepository.save(user);
            }

            // initial admin account
            Admin admin = new Admin("admin", "password123");
            adminRepository.save(admin);
        };
    }
}