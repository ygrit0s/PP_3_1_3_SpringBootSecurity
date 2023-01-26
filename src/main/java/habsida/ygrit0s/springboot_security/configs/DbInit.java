package habsida.ygrit0s.springboot_security.configs;

import org.springframework.stereotype.Component;
import habsida.ygrit0s.springboot_security.entity.*;
import habsida.ygrit0s.springboot_security.service.*;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class DbInit {
	private final UserService userService;
	private final RoleService roleService;

	public DbInit(UserService userService, RoleService roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}

	@PostConstruct
	public void initDbUsers() {

		if (roleService.roleList().isEmpty()) {
			roleService.addRole(new Role("ROLE_USER"));
			roleService.addRole(new Role("ROLE_ADMIN"));
		}

		if (userService.userList().isEmpty()) {
			User admin = new User();
			Set<Role> adminRoles = new HashSet<>();
			adminRoles.add(roleService.getRole(1L));
			adminRoles.add(roleService.getRole(2L));
			admin.setId(1L);
			admin.setName("Admin");
			admin.setSurname("A");
			admin.setAge((byte) 37);
			admin.setUsername("admin");
			admin.setPassword("admin");
			admin.setRoles(adminRoles);
			userService.updateUser(admin);

			User user = new User();
			Set<Role> userRoles = new HashSet<>();
			userRoles.add(roleService.getRole(1L));
			user.setId(2L);
			user.setName("User");
			user.setSurname("U");
			user.setAge((byte) 27);
			user.setUsername("user");
			user.setPassword("user");
			user.setRoles(userRoles);
			userService.updateUser(user);
		}
	}
}