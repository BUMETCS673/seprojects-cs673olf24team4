package team4.teambuilder.h2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamAssignmentService {

    @Autowired
    private UserRepository userRepository;

    public List<List<User>> assignTeams(int numberOfTeams) {
        List<User> allUsers = userRepository.findAll();
        List<List<User>> teams = new ArrayList<>();

        // Initialize teams
        for (int i = 0; i < numberOfTeams; i++) {
            teams.add(new ArrayList<>());
        }

        // simple logic to assign team
        for (int i = 0; i < allUsers.size(); i++) {
            teams.get(i % numberOfTeams).add(allUsers.get(i));
        }

        return teams;
    }
}
