package com.cmc.dashboard.controller.rest;

import com.cmc.dashboard.dto.RestResponse;
import com.cmc.dashboard.dto.UserUtilizationDTO;
import com.cmc.dashboard.service.PermissionService;
import com.cmc.dashboard.service.qms.QmsUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class QmsUserController {

    @Autowired
    private QmsUserService qmsUserService;

    @Autowired
    private PermissionService permissionService;

 @Autowired
    private ConsumerTokenServices consumerTokenServices;

     @Autowired
     private HttpSession httpSession;

    /**
     * Login by user
     *
     * @author: LVLong
     */
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @ResponseBody
    private ResponseEntity<?> getUserInfo() {
        // Get user from Principal
        Object princaipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (princaipal instanceof UserDetails) {
            username = ((UserDetails) princaipal).getUsername();
        } else {
            username = String.valueOf(princaipal);
        }

        UserUtilizationDTO userInfo;
        try {
            userInfo = qmsUserService.loadInfo(username);

            if (userInfo == null) {
                return new ResponseEntity<>(RestResponse.error(HttpStatus.NOT_FOUND, "User not found"),
                        HttpStatus.NOT_FOUND);
            }

            // Add permissions to user info object
            Collection<? extends GrantedAuthority> auths = SecurityContextHolder.getContext().getAuthentication()
                    .getAuthorities();
            List<String> permissions = new ArrayList<>();

            if (auths != null && !auths.isEmpty()) {
                auths.forEach(auth -> permissions.add(auth.getAuthority()));
            }
            userInfo.setAllPermissions(permissionService.getAllPermission());
            userInfo.setPermissions(permissions);

        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(RestResponse.errorSQL(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(RestResponse.success(userInfo), HttpStatus.OK);
    }

    @RequestMapping(value = "/oauth/logout", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.substring(7, authHeader.length()).trim();
            boolean isRevoked = consumerTokenServices.revokeToken(tokenValue);
            if (isRevoked) {
                httpSession.invalidate();
                return new ResponseEntity<>(RestResponse.success("Logout successful"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(RestResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot logout user"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(RestResponse.error(HttpStatus.BAD_REQUEST, "Token not found in request"), HttpStatus.BAD_REQUEST);
        }
    }

}
