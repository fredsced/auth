package fr.formation.jwtsecuredserver.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import fr.formation.jwtsecuredserver.dtos.CustomUserInfoDto;

public interface CustomUserDetailsService extends UserDetailsService {

    CustomUserInfoDto getCurrentUserInfo(Long id);
}
