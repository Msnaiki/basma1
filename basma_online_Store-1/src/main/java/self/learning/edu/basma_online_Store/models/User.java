package self.learning.edu.basma_online_Store.models;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false)
	private Long user_id;

	private String user_key;
	private String firstname;
	private String lastname;
	private String phone;
	private String email;

	private String username;
	private String password;

	private String role;
	private boolean isActive;
	private String[] authorities;

	public User() {
	}

	public User(Long user_id, String user_key, String firstname, String lastname, String phone, String email,
			String username, String password, String role, boolean isActive, String[] authorities) {
		super();
		this.user_id = user_id;
		this.user_key = user_key;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = role;
		this.isActive = isActive;
		this.authorities = authorities;
	}

	public User(String user_key, String firstname, String lastname, String phone, String email, String username,
			String password, String role, boolean isActive, String[] authorities) {
		super();
		this.user_key = user_key;
		this.firstname = firstname;
		this.lastname = lastname;
		this.phone = phone;
		this.email = email;
		this.username = username;
		this.password = password;
		this.role = role;
		this.isActive = isActive;
		this.authorities = authorities;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getUser_key() {
		return user_key;
	}

	public void setUser_key(String user_key) {
		this.user_key = user_key;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String[] getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String[] authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_key=" + user_key + ", firstname=" + firstname + ", lastname="
				+ lastname + ", phone=" + phone + ", email=" + email + ", username=" + username + ", password="
				+ password + ", role=" + role + ", isActive=" + isActive + ", authorities="
				+ Arrays.toString(authorities) + "]";
	}

}
