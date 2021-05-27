package self.learning.edu.basma_online_Store.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import self.learning.edu.basma_online_Store.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findUserByUsername(String username);

	User findUserByEmail(String email);
}
