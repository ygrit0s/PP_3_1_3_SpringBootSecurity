package habsida.ygrit0s.springboot_security.service;

import habsida.ygrit0s.springboot_security.entity.*;
import habsida.ygrit0s.springboot_security.repository.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public List<User> userList() {
		return userRepository.findAll();
	}

	public User getUser(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isEmpty()) {
			throw new UsernameNotFoundException(String.format("User with id %s not found", id));
		} else {
			return user.get();
		}
	}
	
	public boolean addUser(User user) {
		User userDB = userRepository.findByUsername(user.getUsername());
		if (userDB != null) {
			return false;
		}
		//user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return true;
	}

	public void updateUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	public boolean removeUser(Long id) {
		if (userRepository.findById(id).isPresent()) {
			userRepository.deleteById(id);
			return true;
		}
		return false;
	}

	public User getByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = getByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("User with username '%s' not found", username));
		} else {
			return user;
		}
	}
}
