package com.cmc.dashboard.repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cmc.dashboard.dto.ResourceDTO;
import com.cmc.dashboard.dto.ResourcePlanDTO;
import com.cmc.dashboard.dto.UserPlanTimeDTO;
import com.cmc.dashboard.model.UserPlan;

@Repository
public interface UserPlanRepository extends JpaRepository<UserPlan, Integer> {
	
	/**
	 * get userplan by projectid.
	 * 
	 * @param projectId
	 * @return List<PlanResourceDTO>
	 * @author: Hoai-Nam
	 */
	@Query(value="FROM UserPlan dp"
			+ " WHERE dp.projectId = :projectId")
	public List<UserPlan>getAllUserEachProject(@Param("projectId")int projectId) throws SQLException;

	/**
	 * Get list resource plan by userID and projectID
	 * @param userId
	 * @param projectId
	 * @return
	 * @throws SQLException List<ResourceDTO> 
	 * @author: Hoai-Nam
	 */
	@Query(value = "SELECT new com.cmc.dashboard.dto.ResourceDTO(USER_PLAN.userPlanId,USER_PLAN.userId,USER_PLAN.projectId,USER_PLAN.fromDate,USER_PLAN.toDate,USER_PLAN.manDay, USER_PLAN.effortPerDay,USER_PLAN.isOverloaded) "
			+ " FROM UserPlan USER_PLAN"
			+ " WHERE USER_PLAN.userId = :userId")
	public List<ResourceDTO> getAllResourcePlanByUser(@Param("userId") int userId) throws SQLException;
	
	//TODO
	@Query(value="SELECT du.user_plan_id,\r\n" + 
			"       du.is_overloaded\r\n" + 
			"FROM dashboard.user_plan du\r\n" + 
			"WHERE :fromDate <=du.to_date\r\n" + 
			"  AND :toDate >= du.from_date\r\n" + 
			"  AND du.user_id = :userId",nativeQuery=true)
	public List<Object> getListObjectAfterDeletePlan(@Param("fromDate")Date fromDate,@Param("toDate")Date toDate,@Param("userId")int userId);
	
	/**
	 * Get Resource by search's name and delivery Unit
	 * 
	 * @param name
	 * @param duName
	 * @param pageable
	 * @return List<ResourceUtilizationDTO>
	 * @author: DVNgoc
	 */
	@Query(value = "SELECT TB1.id\r\n" + 
			"FROM\r\n" + 
			"  (SELECT DISTINCT u.id,\r\n" + 
			"                   concat(u.lastname,' ',u.firstname) AS full_name,\r\n" + 
			"                   u.login\r\n" + 
			"   FROM dashboard.user_plan AS up\r\n" + 
			"   INNER JOIN dashboard.user_plan_detail upd ON upd.user_plan_id = up.user_plan_id\r\n" + 
			"   INNER JOIN redmine_db.users AS u ON u.id = up.user_id\r\n" + 
			"   WHERE upd.res_delivery_unit LIKE %:duName% ) TB1\r\n" + 
			"WHERE login LIKE %:name%\r\n" + 
			"  OR TB1.full_name LIKE %:name% \n#pageable\n",
			countQuery = "SELECT count(*)\r\n" + 
					"FROM\r\n" + 
					"  (SELECT DISTINCT u.id,\r\n" + 
					"                   concat(u.lastname,' ',u.firstname) AS full_name,\r\n" + 
					"                   u.login\r\n" + 
					"   FROM dashboard.user_plan AS up\r\n" + 
					"   INNER JOIN dashboard.user_plan_detail upd ON upd.user_plan_id = up.user_plan_id\r\n" + 
					"   INNER JOIN redmine_db.users AS u ON u.id = up.user_id\r\n" + 
					"   WHERE upd.res_delivery_unit LIKE %:duName% ) TB1\r\n" + 
					"WHERE login LIKE %:name%\r\n" + 
					"  OR TB1.full_name LIKE %:name%", nativeQuery = true)
	public Page<Integer> getResourceIdByNameAndDU(@Param("name") String name, @Param("duName") String duName,
			Pageable pageable);
	
	
	//update plan
	@Query(value = "SELECT new com.cmc.dashboard.dto.UserPlanTimeDTO(dup.userPlanId,dup.fromDate,"
			+ "dup.toDate,dup.manDay)"
			+ " FROM UserPlan dup"
			+ " WHERE dup.userId = :userId")
			//+ " AND project_id = 11;")
	public List<UserPlanTimeDTO> getListUserPlanByUserId(@Param("userId")int userId);
	
	
	@Query(value = "SELECT new com.cmc.dashboard.dto.ResourceDTO(USER_PLAN.userPlanId,USER_PLAN.userId,USER_PLAN.projectId,USER_PLAN.fromDate,USER_PLAN.toDate,USER_PLAN.manDay, USER_PLAN.effortPerDay,USER_PLAN.isOverloaded) "
			+ " FROM UserPlan USER_PLAN"
			+ " WHERE USER_PLAN.projectId = :projectId AND USER_PLAN.userId = :userId")
	public List<ResourceDTO> getResourcePlanByUser(@Param("userId") int userId, @Param("projectId") int projectId) throws SQLException;
	
	/**
	 * get list resource detail.
	 * 
	 * @param userId
	 * @param month
	 * @param year
	 * @return List<ResourceDetailDTO>
	 * @author: Hoai-Nam
	 */
	@Query(value = "SELECT new com.cmc.dashboard.dto.ResourcePlanDTO(up.projectId,ROUND(SUM(upd.manDay),1)) FROM UserPlan up"
			+ " INNER JOIN up.userPlanDetails upd"
			+ " WHERE up.userId = :userId AND upd.planMonth = :planMonth"
			+ " AND upd.deliveryUnit LIKE %:deliveryUnit%"
			+ " GROUP BY up.projectId")
	public List<ResourcePlanDTO> getResourceDetailWithDeleveryUnit(@Param("userId")int userId,@Param("planMonth") String planMonth,@Param("deliveryUnit")String deliveryUnit);
	
	@Query(value = "SELECT new com.cmc.dashboard.dto.ResourcePlanDTO(up.projectId,ROUND(SUM(upd.manDay),1)) FROM UserPlan up"
			+ " INNER JOIN up.userPlanDetails upd"
			+ " WHERE up.userId = :userId AND upd.planMonth = :planMonth"
			+ " GROUP BY up.projectId"
			)
	public List<ResourcePlanDTO> getResourceDetail(@Param("userId")int userId,@Param("planMonth") String planMonth);
	
	
	/**
	 * Count number user in userplan table.
	 * @param userId
	 * @return Integer 
	 * @author: Hoai-Nam
	 */
	@Query(value="SELECT count(*)\r\n" + 
			"FROM dashboard.user_plan du\r\n" + 
			"WHERE du.user_id =:userId",nativeQuery=true)
	public Integer countAllUserPlaned(@Param("userId")int userId);
	
}
