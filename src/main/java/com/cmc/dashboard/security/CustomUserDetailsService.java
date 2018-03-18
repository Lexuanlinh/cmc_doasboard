
package com.cmc.dashboard.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.model.RolePermission;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.service.UserService;

/**
 * 
 * @author longl
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
	private final UserQmsRepository userQmsRepository;

	@Autowired
	UserService userService;

	@Autowired
	public CustomUserDetailsService(UserQmsRepository userQmsRepository) {
	  log.info("2---CustomUserDetailsService --- public void init(FilterConfig filterConfig)- ");
		this.userQmsRepository = userQmsRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	  log.info("2---CustomUserDetailsService init --- CustomUserDetailsService- ");
		QmsUser account = null;
		account = userQmsRepository.findUserByLogin(username);

		if (account == null) {
			throw new UsernameNotFoundException("User " + username + " not found.");
		}
		
		User user = findByUserName(account.getLogin());
		
		if(user == null) {
			throw new UsernameNotFoundException("User " + username + " not found.");
		}
		Collection<GrantedAuthority> grantedAuthorities = getAuthorities(user);
	
		org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(
				account.getLogin(), buildPasswordWithSalt(account.getHashed_password(), account.getSalt()), true, true,
				true, true, grantedAuthorities);

		return userDetails;
	}

	/**
	 * Cộng chuỗi salt vào cùng với pwd truyền sang
	 *
	 * @param pwd
	 * @param salt
	 * @return
	 */
	private String buildPasswordWithSalt(String pwd, String salt) {
	  log.info("2---buildPasswordWithSalt ");
		return pwd + " " + salt;
	}

	private final Collection<GrantedAuthority> getAuthorities(User user) {
	  
	  log.info("2---Collection<GrantedAuthority> getAuthorities(User user) ");
		final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		List<RolePermission> lstPermission = user.getGroup().getRole().getRolePermissions();
		if (lstPermission != null)
			for (RolePermission item : lstPermission) {
				if (item.getEnable() == 1) {
					authorities.add(new SimpleGrantedAuthority(item.getPermission().getPermissionName()));
				}
			}
		return authorities;
	}

	/**
	 * Get user info from dashboard
	 * 
	 * @param userName
	 * @return
	 */
	private User findByUserName(String userName) {
	  log.info("2---findByUserName ");
		User user = userService.findByUserName(userName);
		return user;
	}

}
