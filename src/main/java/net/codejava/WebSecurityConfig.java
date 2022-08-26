package net.codejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder.jdbcAuthentication()
			.dataSource(dataSource)
			.passwordEncoder(NoOpPasswordEncoder.getInstance())
			.usersByUsernameQuery("select email, password,active from users where email=?")
			.authoritiesByUsernameQuery("select email, roles from users where email=?")
			;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			.antMatchers("/", "index", "/css/", "/js/").permitAll()
			.antMatchers("/student/**").hasAnyRole("STUDENT", "ADMIN")
			.antMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest()
				.authenticated()
			.and()
			.formLogin()
				.loginPage("/login").permitAll()
				.defaultSuccessUrl("/index", true)
				.passwordParameter("password")
				.usernameParameter("username")
			.and()
			.logout().logoutUrl("/logout")
			.clearAuthentication(true)
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID")
			.logoutSuccessUrl("/login");
	}

}



