package fr.formation.jwtsecuredserver.dtos;

/**
 * A projection of a {@code CustomUser} for user info.
 */
public interface CustomUserInfoDto {

    Long getId();

    String getUsername();

    String getFirstname();

    String getLastname();
}
