package com.cmc.dashboard.qms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cmc.dashboard.qms.model.MemberQms;
import com.cmc.dashboard.util.CustomValueUtil;

public interface MemberQmsRepository extends JpaRepository<MemberQms, Integer>{
	
	/**
	 * TODO description
	 * @return List<MemberQms> 
	 * @author: nvkhoa
	 */
	@Query(value = "SELECT M.id, M.user_id, M.project_id FROM members M \r" +
			"JOIN member_roles MR ON MR.member_id = M.id\r" +
			"WHERE MR.role_id = " + CustomValueUtil.ROLE_PM, nativeQuery = true)
	public List<MemberQms> getAllMemberRolePM();
}
