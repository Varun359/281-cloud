package com.cmpe281.team12.ccrs.model.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class JwtResponse {

    @JsonProperty
    @ApiModelProperty(value = "JWT Token")
    private String token;

    @JsonProperty
    @ApiModelProperty(value = "Type")
    private String type = "Basic";

    @JsonProperty
    @ApiModelProperty(value = "ID")
    private Long id;

    @JsonProperty
    @ApiModelProperty(value = "Username")
    private String username;

    @JsonProperty
    @ApiModelProperty(value = "Email Address")
    private String email;

    @JsonProperty
    @ApiModelProperty(value = "Roles")
    private List<String> roles;

    @JsonCreator
    public JwtResponse(String token, Long id, String username, String email, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}