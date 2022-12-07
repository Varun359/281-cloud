package com.cmpe281.team12.ccrs.resource;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import com.cmpe281.team12.ccrs.model.User;
import com.cmpe281.team12.ccrs.model.payload.JwtResponse;
import com.cmpe281.team12.ccrs.model.payload.LoginRequest;
import com.cmpe281.team12.ccrs.model.payload.MessageResponse;
import com.cmpe281.team12.ccrs.model.payload.SignupRequest;
import com.cmpe281.team12.ccrs.repository.RoleRepository;
import com.cmpe281.team12.ccrs.repository.UserRepository;
import com.cmpe281.team12.ccrs.security.jwt.JwtUtils;
import com.cmpe281.team12.ccrs.service.AuthService;
import com.cmpe281.team12.ccrs.service.UserDetailsImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    JwtUtils jwtUtils;

    private AuthService authService;

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @ApiOperation(value = "Authenticates a login to ensure it is valid",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticateUser(@ApiParam(value = "Object to hold username and password to login", required = true)
                                              @Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    @ApiOperation(value = "Registers a new user (customer, business user, admin)",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@ApiParam(value = "Object to hold required fields to sign up a new user", required = true)
                                          @Valid @RequestBody SignupRequest signUpRequest) {

        try {
            authService.registerUser(signUpRequest);
        } catch (Exception e) {
            String message = e.getMessage();
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse(message));
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/user")
    @ApiOperation(value = "Gets the core user record for a specified user ID (customer, business user, admin)",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
}