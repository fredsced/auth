package fr.formation.jwtsecuredserver.dtos;

import java.util.Set;

import fr.formation.jwtsecuredserver.entities.Role;

/**
 * A projection of a {@code CustomUser} for authentication.
 */
public interface CustomUserAuthDto {

    Long getId();

    String getUsername();

    String getPassword();

    Set<Role> getRoles();

    boolean isEnabled();

    boolean isAccountNonExpired();

    boolean isAccountNonLocked();

    boolean isCredentialsNonExpired();
}
