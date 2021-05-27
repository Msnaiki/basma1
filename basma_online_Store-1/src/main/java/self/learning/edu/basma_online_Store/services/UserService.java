package self.learning.edu.basma_online_Store.services;

import java.util.List;
import java.util.Optional;

import self.learning.edu.basma_online_Store.models.User;

public interface UserService {

	public User register(String firstname, String lastname, String phone, String email, String username);

	public List<User> listsOfTheUsers();

	User findUserByUsername(String username);

	User findUserByEmail(String email);

	public void delete(Long user_id);

	Optional getOneUser(Long user_id);

	User updateUser(String currentUser, String firstname, String lastname, String phone, String email, String username,
			String role);

}
