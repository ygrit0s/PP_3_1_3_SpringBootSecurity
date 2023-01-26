package habsida.ygrit0s.springboot_security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "user")
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "The field cannot be empty")
	@Size(min = 2, max = 31, message = "Name should be between 2 and 31 characters")
	private String name;

	@Size(min = 2, max = 31, message = "Name should be between 2 and 31 characters")
	private String surname;

	@Min(value = 0, message = "Age should be >= 0")
	@Max(value = 127, message = "Age should be < 128")
	private Byte age;

	@Column(unique = true)
	@NotEmpty(message = "Username cannot be empty")
	@Size(min = 4, max = 15, message = "Name should be between 4 and 15 latin characters")
	private String username;

	@NotEmpty(message = "The field cannot be empty")
	@Size(min = 4, message = "Password should be greater then 4 symbols")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles;
	public User() {
	}

	public User(String name, String surname, Byte age, String username, String password, Set<Role> roles) {
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public Byte getAge() {
		return age;
	}
	
	public void setAge(Byte age) {
		this.age = age;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String toString() {
		return  """
                
                User  [ ID =        %s,
                        Name =      %s,
                        Surname = 	%s,
                        Age =       %s,
                        Username = 	%s,
                        Password =	%s,
                        Roles =		%s ]
                """
				.formatted(id, name, surname, age, username, password, roles);
	}
}
