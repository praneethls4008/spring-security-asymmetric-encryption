package com.learning.spring_security_asymmetric_encryption;

import com.learning.spring_security_asymmetric_encryption.role.Role;
import com.learning.spring_security_asymmetric_encryption.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class SpringSecurityAsymmetricEncryptionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityAsymmetricEncryptionApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(final RoleRepository roleRepository){
		return args -> {
			final Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
			if(userRole.isEmpty()){
				final Role role = new Role();
				role.setName("ROLE_USER");
				role.setCreatedBy("CommandLineRunner");
				roleRepository.save(role);
			}
		};
	}

}
