package com.cmc.dashboard.qms.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.util.CustomValueUtil;

public interface UserQmsRepository extends JpaRepository<QmsUser, Integer> {
	/**
	 * get role of user by list userid.
	 * 
	 * @param userId
	 * @return String
	 * @author: Hoai-Nam
	 */
	@Query(value = "SELECT REDMINE_ROLE.name FROM redmine_db.users REDMINE_USER \r\n"
			+ "INNER JOIN redmine_db.members REDMINE_MEMBER ON REDMINE_USER.id = REDMINE_MEMBER.user_id \r\n"
			+ "INNER JOIN redmine_db.member_roles REDMINE_MEMBER_ROLE ON REDMINE_MEMBER_ROLE.member_id = REDMINE_MEMBER.user_id\r\n"
			+ "INNER JOIN redmine_db.roles REDMINE_ROLE ON REDMINE_ROLE.id = REDMINE_MEMBER_ROLE.role_id\r\n"
			+ "WHERE REDMINE_USER.id = :userId LIMIT 1;", nativeQuery = true)
	public String getRoleByUserId(@Param("userId") int userId);

	/**
	 * get userplan by projectid.
	 * 
	 * @param projectId
	 * @return List<PlanResourceDTO>
	 * @author: Hoai-Nam
	 */
	@Query(value = "SELECT M.user_id," + " CONCAT(U.lastname, ' ', U.firstname) fullname,"
			+ "(SELECT 'Developer') ROLE\r\n" + "FROM redmine_db.members M\r\n"
			+ "INNER JOIN redmine_db.users U ON M.user_id = U.id\r\n"
			+ "WHERE M.project_id=:projectId", nativeQuery = true)
	public List<Object> getAllUserEachProject(@Param("projectId") int projectId) throws SQLException;

	/**
	 * get list user in charge project by project id.(02/01/2018)
	 * 
	 * @param projectId
	 * @return List<Integer>
	 * @author: Hoai-Nam
	 */
	@Query(value = "SELECT US.id FROM custom_values CV INNER JOIN projects P ON P.id = CV.customized_id"
			+ " INNER JOIN redmine_db.members MB ON P.id = MB.project_id"
			+ " INNER JOIN redmine_db.users US ON MB.user_id = US.id" + " WHERE CV.custom_field_id ="
			+ CustomValueUtil.DELIVERY_UNIT_ID + " AND P.id NOT IN (SELECT PS.parent_id FROM projects PS"
			+ " WHERE PS.parent_id IS NOT NULL)" + " AND P.id = :projectId", nativeQuery = true)
	public List<Integer> ltsUserIdInProject(@Param("projectId") int projectId) throws SQLException;

	/**
	 * Get list of user is Project manager
	 * 
	 * @return List<QmsUser>
	 * @author: NVKhoa
	 */
	@Query(value = "SELECT u.id, u.login, u.hashed_password, u.firstname, u.lastname, u.admin, u.status, u.salt \r"
			+ "FROM users u\r\n" + "INNER JOIN members m ON u.id = m.user_id\r\n"
			+ "INNER JOIN member_roles mr ON mr.member_id = m.id " + "WHERE  mr.role_id ="
			+ CustomValueUtil.ROLE_PM, nativeQuery = true)
	public List<QmsUser> getUserIsPm();

	@Query(value = "SELECT us.id, us.login, us.hashed_password, us.firstname, us.lastname, us.admin, us.status, us.salt\r\n"
			+ "FROM users us JOIN groups_users g on us.id = g.group_id\r\n" + "JOIN users u on u.id = g.user_id\r\n"
			+ "WHERE u.id = :id AND us.lastname LIKE '" + CustomValueUtil.DA + "%'", nativeQuery = true)
	public Optional<QmsUser> findGroup(@Param("id") int id);

	@Query(value = "SELECT id, login, firstname, lastname, admin, status, hashed_password, salt FROM users WHERE login =:username", nativeQuery = true)
	public QmsUser findUserByLogin(@Param("username") String username);

	/**
	 * Get all delivery unit
	 * 
	 * @return String
	 * @author: ngocdv
	 */
	@Query(value = "SELECT cf.possible_values FROM redmine_db.custom_fields cf WHERE id ="
			+ CustomValueUtil.USER_DELIVERY_UNIT_ID, nativeQuery = true)
	public String getAllUserDeliveryUnit();
	
	@Query(value = "SELECT CV.value AS duName\r\n" + 
			"FROM redmine_db.custom_values CV\r\n" + 
			"WHERE CV.custom_field_id=" +CustomValueUtil.DELIVERY_UNIT_USER_ID +
			"  AND CV.customized_id = :userId",nativeQuery = true)
	public String getDeliveryUnitByUserId(@Param("userId")int userId);

	/**
	 * Get group name of user from redmine
	 * 
	 * @param id
	 * @return String
	 * @author: ngocdv
	 */
	@Query(value = "SELECT g.lastname\r\n" + "FROM redmine_db.users u\r\n"
			+ "INNER JOIN redmine_db.groups_users gu ON u.id = gu.user_id\r\n"
			+ "INNER JOIN redmine_db.users g ON gu.group_id = g.id\r\n" + "WHERE u.id = :id\r\n"
			+ "AND g.lastname LIKE '" + CustomValueUtil.DA_PREFIX + "%'", nativeQuery = true)
	public String getGroupByUserId(@Param("id") int id);
	
	@Query(value = "select distinct us.id as group_id from users as u inner join groups_users as gu on u.id = gu.user_id inner join users as us on gu.group_id = us.id where u.id =:id", nativeQuery = true)
	public List<Integer> getGroupIdByQmsUserId(@Param("id") int id);
}
