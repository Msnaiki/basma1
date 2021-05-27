package self.learning.edu.basma_online_Store.controller;

import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import self.learning.edu.basma_online_Store.models.HttpResponse;
import self.learning.edu.basma_online_Store.models.User;
import self.learning.edu.basma_online_Store.models.UserPrincipal;
import self.learning.edu.basma_online_Store.services.impl.UserServiceImpl;
import self.learning.edu.basma_online_Store.util.JWTokenProvider;

@RestController
public class Default {

	@Autowired
	private UserServiceImpl service;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTokenProvider jwtTokenProvider;

	@GetMapping(value = "/")
	public String greeting() {
		return "";
	}

	@PostMapping(value = "/register")
	public ResponseEntity<User> registrationProcess(@RequestBody User user) {
		User register = service.register(user.getFirstname(), user.getLastname(), user.getPhone(), user.getEmail(),
				user.getUsername());
		return new ResponseEntity<User>(register, HttpStatus.OK);

	}

	@PostMapping(value = "/login")
	public ResponseEntity<User> login(@RequestBody User user) {
		authenticate(user.getUsername(), user.getPassword());
		User loggedUser = service.findUserByUsername(user.getUsername());
		UserPrincipal principal = new UserPrincipal(loggedUser);
		HttpHeaders jwtHeader = getJwtHeader(principal);
		return new ResponseEntity<>(loggedUser, jwtHeader, OK);
	}

	@GetMapping(value = "/list")
	public ResponseEntity<List<User>> getListUsers() {
		List<User> lists = service.listsOfTheUsers();
		return new ResponseEntity<List<User>>(lists, OK);
	}

	@PostMapping(value = "/update")
	
	public ResponseEntity<User> updateUser(@RequestParam("currentUsername") String currentUsername,
			@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname,
			@RequestParam("phone") String phone, @RequestParam("email") String email,
			@RequestParam("username") String username, @RequestParam("isActive") String isActive,
			@RequestParam("role") String role) {

		User u_user = service.updateUser(currentUsername, firstname, lastname, phone, email, username, role);
		return new ResponseEntity<User>(u_user, OK);
	}

	@DeleteMapping(value = "/delete/{id}")
	@PreAuthorize("hasAnyAuthority('user:delete')")
	public ResponseEntity<HttpResponse> deleteUser(@PathVariable(value = "id") long id) throws IOException {
		service.delete(id);
		return response(HttpStatus.NO_CONTENT, "User Deleted Successfully");

	}

	private void authenticate(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}

	private HttpHeaders getJwtHeader(UserPrincipal principal) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Jwt-Token", jwtTokenProvider.generateJWToken(principal));
		return headers;
	}

	private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
		return new ResponseEntity<HttpResponse>(new HttpResponse(httpStatus.value(), httpStatus,
				httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase()), httpStatus);
	}
}
