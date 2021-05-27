package self.learning.edu.basma_online_Store.models;

import static self.learning.edu.basma_online_Store.models.Authority.*;

public enum Role {
	ROLE_USER(USER_AUTHORITIES), ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);

	private String[] authorities;

	Role(String... authorities) {
		this.authorities = authorities;
	}

	public String[] getAuthorities() {
		return authorities;
	}

}
