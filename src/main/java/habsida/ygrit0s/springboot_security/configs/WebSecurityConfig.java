package habsida.ygrit0s.springboot_security.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final SuccessUserHandler successUserHandler;

	public WebSecurityConfig(SuccessUserHandler successUserHandler) {
		this.successUserHandler = successUserHandler;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
					.antMatchers("/", "/login").permitAll()
					.antMatchers("/admin/**").hasRole("ADMIN")
					.antMatchers("/user/**").hasAnyRole("ADMIN", "USER")
					.anyRequest().authenticated()
					.and()
				.formLogin().successHandler(successUserHandler)
					.permitAll();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}