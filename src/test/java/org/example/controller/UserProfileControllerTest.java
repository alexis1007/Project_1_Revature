package org.example.controller;

import org.example.Service.UserProfileService;
import org.example.model.User;
import org.example.model.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserProfileController.class)
public class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserProfileService userProfileService;

    @Mock
    private User mockUser;

    @InjectMocks
    private UserProfileController userProfileController;

    private UserProfile userProfile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileController).build();

        userProfile = new UserProfile();
        userProfile.setId(1L);
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");
        userProfile.setPhoneNumber("1234567890");
        userProfile.setCreditScore(700);

        when(mockUser.getUserType().getId()).thenReturn(1L);
        when(mockUser.getId()).thenReturn(1L);
    }

    @Test
    public void testGetAllUserProfiles() throws Exception {
        List<UserProfile> userProfiles = Arrays.asList(userProfile);
        when(userProfileService.findAllUserProfiles()).thenReturn(userProfiles);

        mockMvc.perform(get("/api/user-profiles")
                .requestAttr("user", mockUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("John"));
    }

    @Test
    public void testGetUserProfileById() throws Exception {
        when(userProfileService.findUserProfileById(anyLong())).thenReturn(Optional.of(userProfile));

        mockMvc.perform(get("/api/user-profiles/1")
                .requestAttr("user", mockUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    public void testRegisterUserProfile() throws Exception {
        when(userProfileService.registerUserProfile(any(UserProfile.class))).thenReturn(userProfile);

        mockMvc.perform(post("/api/user-profiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"phoneNumber\": \"1234567890\", \"creditScore\": 700}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    public void testUpdateUserProfile() throws Exception {
        when(userProfileService.updateUserProfile(anyLong(), any(UserProfile.class))).thenReturn(Optional.of(userProfile));

        mockMvc.perform(put("/api/user-profiles/1")
                .requestAttr("user", mockUser)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\": \"Jane\", \"lastName\": \"Smith\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    public void testDeleteUserProfile() throws Exception {
        when(userProfileService.deleteUserProfile(anyLong())).thenReturn(true);

        mockMvc.perform(delete("/api/user-profiles/1")
                .requestAttr("user", mockUser))
                .andExpect(status().isNoContent());
    }
}
