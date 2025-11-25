package com.newsmoa.app.config;

import org.springframework.context.annotation.Configuration;

	@Configuration
	@EnableWebSecurity
	public class SecurityConfig extends WebSecurityConfigurerAdapter {
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	            .authorizeRequests()
	                .antMatchers("/login", "/register").permitAll()
	                .anyRequest().authenticated()
	            .and()
	            .formLogin()
	                .loginPage("/login")
	                .defaultSuccessUrl("/home")
	                .permitAll()
	            .and()
	            .logout()
	                .permitAll();
	    }
}


