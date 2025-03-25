package org.example.controller;

import java.util.Arrays;
import java.util.Optional;

import org.example.Service.UserService;
import org.example.model.User;
import org.example.model.UserType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;
    private UserType userType;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        userType = new UserType();
        userType.setId(1L);
        userType.setUserType("MANAGER");

        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setPasswordHash("password");
        user.setUserType(userType);
    }

    @Test
    public void testGetAllUsers() throws Exception {
        when(userService.findAllUsers()).thenReturn(Arrays.asList(user));

        mockMvc.perform(get("/api/users")
                .requestAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testuser"));
    }

    @Test
    public void testGetUserById() throws Exception {
        when(userService.findUserById(any(Long.class))).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/1")
                .requestAttr("user", user))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testRegisterUser() throws Exception {
        when(userService.registerUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testuser\", \"passwordHash\": \"password\", \"userType\": {\"id\": 1}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        when(userService.updateUser(any(Long.class), any(User.class))).thenReturn(Optional.of(user));

        // Create a user with MANAGER privileges (userType.getId() == 1)
        User managerUser = new User();
        managerUser.setId(1L);
        managerUser.setUsername("manageruser");
        managerUser.setUserType(userType); // Already set up as MANAGER in setUp()

        mockMvc.perform(put("/api/users/1")
                .requestAttr("user", managerUser) // Use manager user for authorization
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"updateduser\", \"passwordHash\": \"newpassword\", \"userType\": {\"id\": 1}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        when(userService.deleteUser(any(Long.class))).thenReturn(true);

        mockMvc.perform(delete("/api/users/1")
                .requestAttr("user", user))
                .andExpect(status().isNoContent());
    }
}
