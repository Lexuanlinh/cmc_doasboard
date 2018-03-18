package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.model.Group;

public interface GroupRepository extends JpaRepository<Group, Integer>{
	
	@Query("select g from Group g where g.groupId = :groupId")
	public Group findById(@Param("groupId") int groupId);
	
	@Query("select g from Group g where g.groupName = :groupName")
	public Group findByName(@Param("groupName") String groupName);
	
	@Query(value = "SELECT g.group_id,g.created_on,g.group_desc,g.group_name,"
	    + "g.updated_on,g.group_rank, g.role_id FROM dashboard.groups AS g WHERE g.group_id IN (:groupId) ORDER BY group_rank DESC", nativeQuery = true)
	public List<Group> findMaxRank(@Param("groupId") List<Integer> groupIds);
}
