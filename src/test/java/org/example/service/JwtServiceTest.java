package org.example.service;

import org.example.Service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGenerateToken() {
        String token = jwtService.generateToken("testuser", "USER");
        assertNotNull(token);
    }

    @Test
    public void testExtractUsername() {
        String token = jwtService.generateToken("testuser", "USER");
        String username = jwtService.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    public void testExtractExpiration() {
        String token = jwtService.generateToken("testuser", "USER");
        Date expiration = jwtService.extractExpiration(token);
        assertNotNull(expiration);
    }

    @Test
    public void testValidateToken() {
        String token = jwtService.generateToken("testuser", "USER");
        boolean isValid = jwtService.validateToken(token, "testuser");
        assertTrue(isValid);
    }

    @Test
    public void testValidateTokenWithInvalidUsername() {
        String token = jwtService.generateToken("testuser", "USER");
        boolean isValid = jwtService.validateToken(token, "invaliduser");
        assertFalse(isValid);
    }

    @Test
    public void testValidateTokenWithExpiredToken() {
        String token = jwtService.generateToken("testuser", "USER");
        // Simulate token expiration by setting the expiration time to a past date
        jwtService.extractAllClaims(token).setExpiration(new Date(System.currentTimeMillis() - 1000));
        boolean isValid = jwtService.validateToken(token, "testuser");
        assertFalse(isValid);
    }
}
