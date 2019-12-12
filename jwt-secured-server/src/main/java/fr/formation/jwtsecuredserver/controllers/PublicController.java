package fr.formation.jwtsecuredserver.controllers;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.jwtsecuredserver.config.ResourceServerConfig;

/**
 * @see ResourceServerConfig#configure(HttpSecurity)
 */
@RestController
@RequestMapping("/public")
public class PublicController {

    /**
     * Accessible for anyone even anonymous.
     *
     * @return "Hello anyone!"
     */
    @GetMapping("/hello")
    public String hello() {
	return "Hello anyone!";
    }
}
