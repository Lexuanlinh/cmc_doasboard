package com.cmc.dashboard.qms.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.qms.model.ProjectQms;
import com.cmc.dashboard.util.CustomValueUtil;

public interface ProjectQmsRepository extends JpaRepository<ProjectQms, Integer> {

	/**
	 * Get project's manager name
	 * 
	 * @param id:
	 *            id of project
	 * @return List<String>
	 * @author: DVNgoc
	 */
	@Query(value = "SELECT concat(u.lastname,\" \",u.firstname) AS projectManager FROM projects p\r\n"
			+ "INNER JOIN members m ON m.project_id = p.id INNER JOIN users u ON m.user_id = u.id\r\n"
			+ "INNER JOIN member_roles mr ON mr.member_id = m.id WHERE mr.role_id = " + CustomValueUtil.ROLE_PM + "\r\n"
			+ "  AND p.id = :id", nativeQuery = true)
	public List<String> getProjectManagerById(@Param("id") int id);

	/**
	 * Get project's start date
	 * 
	 * @param id:
	 *            id of project
	 * @return String
	 * @author: DVNgoc
	 */
	@Query(value = "SELECT CV.value FROM custom_values CV WHERE CV.custom_field_id=" + CustomValueUtil.START_DATE_ID
			+ " AND CV.customized_id = :id", nativeQuery = true)
	public String getProjectStartDateById(@Param("id") int id);

	/**
	 * Get project's end date
	 * 
	 * @param id:
	 *            id of project
	 * @return String
	 * @author: DVNgoc
	 */
	@Query(value = "SELECT CV.value FROM custom_values CV WHERE CV.custom_field_id=" + CustomValueUtil.END_DATE_ID
			+ " AND CV.customized_id = :id", nativeQuery = true)
	public String getProjectEndDateById(@Param("id") int id);

	/**
	 * Get project's delivery Unit
	 * 
	 * @param id:
	 *            id of project
	 * @return String
	 * @author: DVNgoc
	 */
	@Query(value = "SELECT CV.value FROM custom_values CV WHERE CV.custom_field_id=" + CustomValueUtil.DELIVERY_UNIT_ID
			+ " AND CV.customized_id = :id", nativeQuery = true)
	public String getProjectDeliveryUnitById(@Param("id") int id);

	/**
	 * Count project by type for each delivery unit
	 * Note: Count project for whole company
	 * @param startDate
	 * @param Date
	 * @return List<ProjectQms>
	 * @author: nvkhoa
	 */
	@Query(value = "SELECT TB1.delivery_unit, TB1.p_type ,count(*) as total FROM "
			+ "(SELECT P.id, P.name,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS start_date,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS end_date,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS delivery_unit,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND CV.customized_id = P.id) AS p_type\r\n"
			+ "FROM redmine_db.projects P WHERE P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n"
			+ ") TB1 WHERE (TB1.delivery_unit IN (:units) AND TB1.p_type IS NOT NULL) AND\r"
			+ " 	((TB1.start_date < :startDate\r\n" 
			+ "   AND TB1.end_date>:endDate)\r\n"
			+ "   OR (TB1.start_date >= :startDate\r\n" 
			+ "   AND TB1.end_date<=:endDate)\r\n"
			+ "   OR (TB1.start_date >= :startDate\r\n" 
			+ "   AND TB1.start_date <= :endDate\r\n"
			+ "   AND TB1.end_date>=:endDate)\r\n" 
			+ "   OR (TB1.start_date <= :startDate\r\n"
			+ "   AND TB1.end_date<=:endDate\r\n"
			+ "   AND TB1.end_date>=:startDate))\r\n"
			+ "	GROUP BY TB1.delivery_unit, TB1.p_type", nativeQuery = true)
	public Optional<List<Object>> getProjectByType(@Param("units") Set<String> units ,
													@Param("startDate") String startDate, @Param("endDate") String endDate);

	/**
	 * List of project by type for delivery unit
	 * 
	 * @param du
	 * @param projectType
	 * @param startDate
	 * @param Date
	 * @return List<ProjectQms>
	 * @author: nvkhoa
	 */
	@Query(value = "SELECT TB1.id, TB1.name, TB1.status FROM "
			+ "(SELECT P.id, P.name, P.status,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS start_date,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS end_date,\r\n"
			+ "	   (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.MAN_DAY_ID+"\r AND CV.customized_id = P.id) AS man_day,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND CV.customized_id = P.id) AS p_type,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS delivery_unit\r\n"
			+ "FROM redmine_db.projects P WHERE P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n"
			+ ") TB1 WHERE (TB1.delivery_unit LIKE :du AND TB1.p_type IN (:projectType))\r\n"
			+ "		   AND ((TB1.start_date < :startDate\r\n" 
			+ "               AND TB1.end_date>:endDate)\r\n"
			+ "          OR (TB1.start_date >= :startDate\r\n" 
			+ "              AND TB1.end_date<=:endDate)\r\n"
			+ "          OR (TB1.start_date >= :startDate\r\n" 
			+ "              AND TB1.start_date <= :endDate\r\n"
			+ "              AND TB1.end_date>=:endDate)\r\n" 
			+ "          OR (TB1.start_date <= :startDate\r\n"
			+ "              AND TB1.end_date<=:endDate\r\n"
			+ "              AND TB1.end_date>=:startDate));", nativeQuery = true)
	public Optional<List<ProjectQms>> getListProjectByType(@Param("du") String du, @Param("projectType") List<String> projectType,
			@Param("startDate") String startDate, @Param("endDate") String endDate);

	/**
	 * Get list of project by Delivery unit
	 * @param projectType
	 * @param startDate
	 * @param endate
	 * @return Optional<List<ProjectQms>> 
	 * @author: NVKhoa
	 */
	@Query(value = "SELECT TB1.id, TB1.name, TB1.status FROM "
			+ "(SELECT P.id, P.name, P.status,\r\n"
			+ "    (SELECT CV.value FROM custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS start_date,\r\n"
			+ "    (SELECT CV.value FROM custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS end_date,\r\n"
			+ "	   (SELECT CV.value FROM custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.MAN_DAY_ID+"\r AND CV.customized_id = P.id) AS man_day,\r\n"
			+ "    (SELECT CV.value FROM custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND CV.customized_id = P.id) AS p_type,\r\n"
			+ "    (SELECT CV.value FROM custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS delivery_unit\r\n"
			+ "FROM projects P WHERE P.id NOT IN (SELECT PS.parent_id FROM projects PS WHERE PS.parent_id IS NOT NULL)\r\n"
			+ ") TB1 WHERE (TB1.delivery_unit IS NOT NULL AND TB1.p_type IN (:projectType))\r\n"
			+ "		   AND ((TB1.start_date < :startDate\r\n" 
			+ "               AND TB1.end_date>:endDate)\r\n"
			+ "          OR (TB1.start_date >= :startDate\r\n" 
			+ "              AND TB1.end_date<=:endDate)\r\n"
			+ "          OR (TB1.start_date >= :startDate\r\n" 
			+ "              AND TB1.start_date <= :endDate\r\n"
			+ "              AND TB1.end_date>=:endDate)\r\n" 
			+ "          OR (TB1.start_date <= :startDate\r\n"
			+ "              AND TB1.end_date<=:endDate\r\n"
			+ "              AND TB1.end_date>=:startDate));", nativeQuery = true)
	public Optional<List<ProjectQms>> getProjectByAllDu(@Param("projectType") List<String> projectType,
			@Param("startDate") String startDate, @Param("endDate") String endate);

	/**
	 * Get all project by user who is incharge
	 * 
	 * @param roleName
	 * @param userId
	 * @return List<ProjectQms>
	 * @author: nvkhoa
	 */

	@Query(value = "SELECT TB1.id, TB1.name, TB1.status FROM "
			+ "	   (SELECT P.id, P.name, P.status,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS start_date,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS end_date,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS delivery_unit\r\n"
			+ "FROM redmine_db.projects P \r\n" + "INNER JOIN members M ON M.project_id = P.id\r\n"
			+ "INNER JOIN redmine_db.users U ON M.user_id = U.id\r\n" + "INNER JOIN redmine_db.member_roles MR ON MR.member_id = M.id\r\n"
			+ "WHERE U.id = :userId AND MR.role_id =" + CustomValueUtil.ROLE_PM + "\r\n"
			+ " AND P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n"
			+ ") TB1 WHERE (TB1.delivery_unit IS NOT NULL)\r\n"
			+ "AND ((TB1.start_date < :startDate\r\n" 
			+ "          AND TB1.end_date>:endDate)\r\n"
			+ "          OR (TB1.start_date >= :startDate\r\n" 
			+ "              AND TB1.end_date<=:endDate)\r\n"
			+ "          OR (TB1.start_date >= :startDate\r\n" 
			+ "              AND TB1.start_date <= :endDate\r\n"
			+ "              AND TB1.end_date>=:endDate)\r\n" 
			+ "          OR (TB1.start_date <= :startDate\r\n"
			+ "              AND TB1.end_date<=:endDate\r\n" 
			+ "              AND TB1.end_date>=:startDate))\r\n"
			+ "ORDER BY TB1.name \n#pageable\n", 
			countQuery = "SELECT count(*) FROM ("
					+ "SELECT P.id, P.name,\r\n"
					+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS start_date,\r\n"
					+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS end_date,\r\n"
					+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS delivery_unit\r\n"
					+ "FROM redmine_db.projects P \r\n" + "INNER JOIN redmine_db.members M ON M.project_id = P.id\r\n"
					+ "INNER JOIN redmine_db.users U ON M.user_id = U.id\r\n"
					+ "INNER JOIN redmine_db.member_roles MR ON MR.member_id = M.id\r\n" + "WHERE U.id = :userId AND MR.role_id ="
					+ CustomValueUtil.ROLE_PM + "\r"
					+ " AND P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n"
					+ ") TB1 WHERE (TB1.delivery_unit IS NOT NULL)\r\n"
					+ "AND ((TB1.start_date < :startDate\r\n" 
					+ "               AND TB1.end_date>:endDate)\r\n"
					+ "          OR (TB1.start_date >= :startDate\r\n" 
					+ "              AND TB1.end_date<=:endDate)\r\n"
					+ "          OR (TB1.start_date >= :startDate\r\n"
					+ "              AND TB1.start_date <= :endDate\r\n"
					+ "              AND TB1.end_date>=:endDate)\r\n" 
					+ "          OR (TB1.start_date <= :startDate\r\n"
					+ "              AND TB1.end_date<=:endDate\r\n"
					+ "              AND TB1.end_date>=:startDate))\r\n", nativeQuery = true)
	public Page<ProjectQms> getProjectListByUser(@Param("userId") int userId, @Param("startDate") String startDate,
			@Param("endDate") String endDate, Pageable pageable);

	/**
	 * Get all project
	 * 
	 * @return List<ProjectQms>
	 * @author: nvkhoa
	 */

	@Query(value = "SELECT TB1.id, TB1.name, TB1.status FROM "
			+ "(SELECT P.id, P.name, P.status,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS start_date,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS end_date,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS delivery_unit\r\n"
			+ "FROM redmine_db.projects P \r\n"
			+ " WHERE P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n"	
			+ ") TB1 WHERE (TB1.delivery_unit IS NOT NULL)\r\n"
			+ "	 AND ((TB1.start_date < :startDate\r\n" 
			+ "		  AND TB1.end_date>:endDate)\r\n"
			+ "	 OR (TB1.start_date >= :startDate\r\n" 
			+ "	     AND TB1.end_date<=:endDate)\r\n"
			+ "	 OR (TB1.start_date >= :startDate\r\n" 
			+ "	 AND TB1.start_date <= :endDate\r\n"
			+ "	     AND TB1.end_date>=:endDate)  \r\n" 
			+ "	 OR (TB1.start_date <= :endDate\r\n"
			+ "		 AND TB1.end_date<=:endDate\r\n" 
			+ "		 AND TB1.end_date>=:startDate))\r\n"
			+ "         ORDER BY id \n#pageable\n", 
		countQuery = "SELECT count(*) FROM (SELECT \r\n"
				+ "	P.id, P.name,\r\n"
				+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS start_date,\r\n"
				+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS end_date,\r\n"
				+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS delivery_unit\r\n"
				+ "FROM projects P \r\n"
				+ " WHERE P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n"
				+ ") TB1 WHERE (TB1.delivery_unit IS NOT NULL)\r\n "
				+ "  AND ((TB1.start_date < :startDate\r\n" 
				+ "		  AND TB1.end_date>:endDate)\r\n"
				+ "	 OR (TB1.start_date >= :startDate\r\n" 
				+ "	     AND TB1.end_date<=:endDate)\r\n"
				+ "	 OR (TB1.start_date >= :startDate\r\n" 
				+ "	 AND TB1.start_date <= :endDate\r\n"
				+ "	     AND TB1.end_date>=:endDate)  \r\n" 
				+ "	 OR (TB1.start_date <= :endDate\r\n"
				+ "		 AND TB1.end_date<=:endDate\r\n"
				+ "		 AND TB1.end_date>=:startDate))\r\n", nativeQuery = true)
	public Page<ProjectQms> getAllProject(@Param("startDate") String startDate, @Param("endDate") String endDate,
			Pageable pageable);

	/**
	 * Get project by delivery unit
	 * 
	 * @param duName
	 * @return List<ProjectQms>
	 * @author: nvkhoa
	 */
	@Query(value = "SELECT TB1.id, TB1.name, TB1.status FROM "
			+ "(SELECT P.id, P.name, P.status,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS start_date,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS end_date,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS delivery_unit,\r\n"
			+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND CV.customized_id = P.id) AS p_type\r\n"
			+ "FROM redmine_db.projects P WHERE P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n"
			+ ") TB1 WHERE "
			+ " (TB1.delivery_unit =:duName AND TB1.p_type IS NOT NULL) AND\r"
			+ " 			((TB1.start_date < :startDate\r\n" 
			+ "               AND TB1.end_date>:endDate)\r\n"
			+ "          OR (TB1.start_date >= :startDate\r\n" 
			+ "              AND TB1.end_date<=:endDate)\r\n"
			+ "          OR (TB1.start_date >= :startDate\r\n" 
			+ "              AND TB1.start_date <= :endDate\r\n"
			+ "              AND TB1.end_date>=:endDate)\r\n" 
			+ "          OR (TB1.start_date <= :startDate\r\n"
			+ "              AND TB1.end_date<=:endDate\r\n" 
			+ "              AND TB1.end_date>=:startDate))\r\n"
			+ "ORDER BY TB1.id \n#pageable\n", countQuery = "SELECT count(*) FROM (SELECT \r\n"
					+ "	P.id, P.name,\r\n"
					+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.START_DATE_ID+"\r AND CV.customized_id = P.id) AS start_date,\r\n"
					+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.END_DATE_ID+"\r AND CV.customized_id = P.id) AS end_date,\r\n"
					+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.DELIVERY_UNIT_ID+"\r AND CV.customized_id = P.id) AS delivery_unit,\r\n"
					+ "    (SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id="+CustomValueUtil.PROJECT_TYPE_ID+"\r AND CV.customized_id = P.id) AS p_type\r\n"
					+ "FROM redmine_db.projects P WHERE P.id NOT IN (SELECT PS.parent_id FROM redmine_db.projects PS WHERE PS.parent_id IS NOT NULL)\r\n"
					+ "AND P.status <> " + CustomValueUtil.STATUS_CLOSE + "\r\n" + ") TB1 WHERE "
					+ " (TB1.delivery_unit = :duName AND TB1.p_type IS NOT NULL) AND\r"
					+ " 			((TB1.start_date < :startDate\r\n" 
					+ "               AND TB1.end_date>:endDate)\r\n"
					+ "          OR (TB1.start_date >= :startDate\r\n" 
					+ "              AND TB1.end_date<=:endDate)\r\n"
					+ "          OR (TB1.start_date >= :startDate\r\n"
					+ "              AND TB1.start_date <= :endDate\r\n"
					+ "              AND TB1.end_date>=:endDate)\r\n" 
					+ "          OR (TB1.start_date <= :startDate\r\n"
					+ "              AND TB1.end_date<=:endDate\r\n"
					+ "              AND TB1.end_date>=:startDate))\r\n", nativeQuery = true)
	public Page<ProjectQms> getProjectByDeliveryUnit(@Param("duName") String duName,
			@Param("startDate") String startDate, @Param("endDate") String endDate, Pageable pageble);

	@Query(value = "SELECT p.id, p.name, p.status FROM projects p WHERE p.id =:projectId", nativeQuery = true)
	public ProjectQms findOne(@Param("projectId") int projectId);
	
	@Query(value = "SELECT CV.value FROM custom_values CV WHERE CV.custom_field_id=38 GROUP BY CV.value", nativeQuery = true)
	public List<String> getListDeliveryUnit();
}
