/**
 *
 */
package com.cmc.dashboard.dto;

import com.cmc.dashboard.util.MethodUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author nahung
 */

public class UserUtilizationDTO {
    private Integer id;
    private String login;
    private String firstname;
    private String lastname;
    private boolean admin;
    private int status;
    private String role;
    private String currentTime = MethodUtil.getDateNow();
    private List<String> permissions;
    private List<String> allPermissions;

    @JsonProperty(value = "all_permission")
    public List<String> getAllPermissions() {
        return allPermissions;
    }

    public void setAllPermissions(List<String> all_permissions) {
        this.allPermissions = all_permissions;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public UserUtilizationDTO() {
        super();
    }

    public UserUtilizationDTO(Integer id, String login, String firstname, String lastname, boolean admin, int status,
                              String role, List<String> permissions) {
        super();
        this.id = id;
        this.login = login;
        this.firstname = firstname;
        this.lastname = lastname;
        this.admin = admin;
        this.status = status;
        this.role = role;
        this.setPermissions(permissions);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

}
