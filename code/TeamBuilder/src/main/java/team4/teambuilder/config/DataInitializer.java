package team4.teambuilder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import team4.teambuilder.model.User;
import team4.teambuilder.repository.UserRepository;
import team4.teambuilder.model.Admin;
import team4.teambuilder.repository.AdminRepository;

import java.util.Arrays;

@Configuration
public class DataInitializer {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {
            User user1 = new User("Alice Adams", "alice@example.com", "Developer",
                    Arrays.asList("Leader", "Work under pressure", "Direct communication", "Frontend development", "Team meetings"));

            User user2 = new User("Bob Baker", "bob@example.com", "Designer",
                    Arrays.asList("Collaborator", "Structured approach", "Visual communication", "UI/UX design", "Individual work"));

            User user3 = new User("Charlie Clark", "charlie@example.com", "Manager",
                    Arrays.asList("Coordinator", "Proactive planning", "Diplomatic communication", "Project management", "Mix of both"));

            userRepository.saveAll(Arrays.asList(user1, user2, user3));

            // initial admin account
            Admin admin = new Admin("admin", "password123");
            adminRepository.save(admin);
        };
    }
}
