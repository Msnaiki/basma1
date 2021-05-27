package self.learning.edu.basma_online_Store;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import self.learning.edu.basma_online_Store.models.User;
import self.learning.edu.basma_online_Store.repositories.UserRepository;
import self.learning.edu.basma_online_Store.services.impl.UserServiceImpl;

@SpringBootTest
class BasmaOnlineStore1ApplicationTests {

	@Autowired
	private UserServiceImpl service;

	@Autowired
	private UserRepository repository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testGetListUsers() throws Exception {
		List<User> _Lists = service.listsOfTheUsers();
		_Lists.stream().forEach(user -> {
			System.out.println(user.toString());
		});
	}

	@Test
	public void testDeleteItem() throws Exception {
		Long user_id = (long) 10;
		Optional user = service.getOneUser(user_id);

		if (user != null) {
			System.out.println(user.toString());
			repository.deleteById(user_id);
		} else {
			System.out.println("No user Found With The Given ID " + user_id);
		}
	}
}
