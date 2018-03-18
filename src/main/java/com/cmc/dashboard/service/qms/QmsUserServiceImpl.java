package com.cmc.dashboard.service.qms;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.UserUtilizationDTO;
import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.GroupRepository;
import com.cmc.dashboard.security.CustomUserDetailsService;

@Service
public class QmsUserServiceImpl implements QmsUserService {
	
	@Autowired
	UserQmsRepository qmsUserRepository;

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	UserQmsRepository userQmsRepository;

	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Override
	public UserUtilizationDTO loadInfo(String username) throws SQLException {
		UserUtilizationDTO userInfo = null;

		QmsUser qmsUser = qmsUserRepository.findUserByLogin(username);

		if(qmsUser == null) {
			throw new UsernameNotFoundException("User not found exception");
		}
		
		try {
			List<Integer> listIds = userQmsRepository.getGroupIdByQmsUserId(qmsUser.getId());
			if (listIds.size() != 0) {
				List<Group> groups = groupRepository.findMaxRank(listIds);
				if (!groups.isEmpty() && groups != null) {
					userInfo = new UserUtilizationDTO();
					userInfo.setId(qmsUser.getId());
					userInfo.setLogin(qmsUser.getLogin());
					userInfo.setFirstname(qmsUser.getFirstname());
					userInfo.setLastname(qmsUser.getLastname());
					userInfo.setRole(groups.get(0).getGroupName());
				}
			}
			return userInfo;
		} catch (EntityNotFoundException e) {
			return userInfo;
		}
		
	}
}
