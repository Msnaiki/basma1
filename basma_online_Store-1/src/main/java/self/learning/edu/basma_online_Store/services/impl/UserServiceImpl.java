package self.learning.edu.basma_online_Store.services.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import self.learning.edu.basma_online_Store.models.Role;
import self.learning.edu.basma_online_Store.models.User;
import self.learning.edu.basma_online_Store.models.UserPrincipal;
import self.learning.edu.basma_online_Store.repositories.UserRepository;
import self.learning.edu.basma_online_Store.services.UserService;

@Service
@Transactional
@Qualifier(value = "UserDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserRepository user_Repository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository user_Repository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.user_Repository = user_Repository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = user_Repository.findUserByUsername(username);
		if (user == null) {
			LOGGER.error("no user found based on the given username : " + username);
			throw new UsernameNotFoundException("no user found based on the given username : " + username);
		} else {
			user_Repository.save(user);
			UserPrincipal principal = new UserPrincipal(user);
			LOGGER.error("Returning user based on the given username : " + username);
			return principal;
		}
	}

	@Override
	public User register(String firstname, String lastname, String phone, String email, String username) {

		User user = new User();
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setPhone(phone);
		user.setEmail(email);
		user.setUsername(username);
		user.setUser_key(generateKey());
		user.setActive(true);
		user.setRole(Role.ROLE_USER.name());
		user.setAuthorities(Role.ROLE_USER.getAuthorities());
		String password = generatePassword();
		user.setPassword(encryptPassword(password));

		user_Repository.save(user);
		LOGGER.info("new Password For The New Account :" + password);

		return user;
	}

	private String encryptPassword(String password) {
		return bCryptPasswordEncoder.encode(password);
	}

	private String generateKey() {
		return RandomStringUtils.randomNumeric(10);
	}

	private String generatePassword() {
		return RandomStringUtils.randomAlphanumeric(10);
	}

	@Override
	public List<User> listsOfTheUsers() {
		List<User> lists = user_Repository.findAll();
		return lists;
	}

	@Override
	public User findUserByEmail(String email) {
		return user_Repository.findUserByEmail(email);
	}

	@Override
	public User findUserByUsername(String username) {
		return user_Repository.findUserByUsername(username);
	}

	@Override
	public void delete(Long user_id) {
		user_Repository.deleteById(user_id);
	}

	@Override
	public User updateUser(String currentUser, String firstname, String lastname, String phone, String email,
			String username, String role) {
		User user__ = user_Repository.findUserByUsername(currentUser);
		if (user__ != null) {
			user__.setFirstname(firstname);
			user__.setLastname(lastname);
			user__.setPhone(phone);
			user__.setEmail(email);
			user__.setUsername(username);
			user__.setUser_key(generateKey());
			user__.setActive(true);
			user__.setRole(getRoleEnumName(role).name());
			user__.setAuthorities(getRoleEnumName(role).getAuthorities());
			user_Repository.save(user__);
		}
		return user__;
	}

	private Role getRoleEnumName(String role) {
		return Role.valueOf(role.toUpperCase());
	}

	@Override
	public Optional getOneUser(Long user_id) {
		Optional<User> user = user_Repository.findById(user_id);
		return user;
	}

}
