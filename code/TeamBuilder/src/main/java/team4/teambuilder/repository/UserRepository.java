package team4.teambuilder.repository;

import team4.teambuilder.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}