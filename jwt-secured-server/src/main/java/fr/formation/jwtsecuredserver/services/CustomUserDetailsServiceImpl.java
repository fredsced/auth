package fr.formation.jwtsecuredserver.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.formation.jwtsecuredserver.config.CustomUserDetails;
import fr.formation.jwtsecuredserver.config.ResourceNotFoundException;
import fr.formation.jwtsecuredserver.dtos.CustomUserAuthDto;
import fr.formation.jwtsecuredserver.dtos.CustomUserInfoDto;
import fr.formation.jwtsecuredserver.repositories.CustomUserJpaRepository;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

    private final CustomUserJpaRepository repo;

    protected CustomUserDetailsServiceImpl(CustomUserJpaRepository repo) {
	this.repo = repo;
    }

    // Throws UsernameNotFoundException (Spring contract)
    @Override
    public UserDetails loadUserByUsername(String username)
	    throws UsernameNotFoundException {
	CustomUserAuthDto user = repo.findByUsername(username)
		.orElseThrow(() -> new UsernameNotFoundException(
			"no user found with username: " + username));
	return new CustomUserDetails(user);
    }

    // Throws ResourceNotFoundException (restful practice)
    @Override
    public CustomUserInfoDto getCurrentUserInfo(Long id) {
	return repo.getById(id).orElseThrow(
		() -> new ResourceNotFoundException("with id:" + id));
    }
}
