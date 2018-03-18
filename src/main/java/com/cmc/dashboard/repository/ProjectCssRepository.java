/**
 * DashboardSystem - com.cmc.dashboard.repository
 */
package com.cmc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.dto.CssChartDTO;
import com.cmc.dashboard.dto.CssTimeDto;
import com.cmc.dashboard.dto.ProjectCssUtilizationDto;
import com.cmc.dashboard.model.ProjectCss;
import com.cmc.dashboard.util.CustomValueUtil;

/**
 * @author: DVNgoc
 * @Date: Dec 15, 2017
 */
public interface ProjectCssRepository extends JpaRepository<ProjectCss, Integer> {

	/**
	 * Get total project css of company
	 * 
	 * @param month
	 * @param year
	 * @return CssChartDTO
	 * @author: ngocdv
	 */
	@Query("SELECT NEW com.cmc.dashboard.dto.CssChartDTO('" + CustomValueUtil.COMPANY
			+ "' as du_name,AVG(P.scoreValue) as average_css)" + " FROM ProjectCss AS P\r\n"
			+ "WHERE (YEAR(P.startDate) < :year" + " AND YEAR(P.endDate) >:year)\r\n" + "  OR (YEAR(P.startDate)< :year"
			+ " AND YEAR(P.endDate) =:year\r\n" + "      AND MONTH(P.endDate) >= :month)\r\n"
			+ "  OR (YEAR(P.startDate)= :year\r\n" + "      AND YEAR(P.endDate) >:year\r\n"
			+ "      AND MONTH(P.startDate) <= :month)\r\n" + "  OR (YEAR(P.startDate)= :year\r\n"
			+ "      AND YEAR(P.endDate) =:year\r\n" + "      AND MONTH(P.startDate) <= :month\r\n"
			+ "      AND MONTH(P.endDate) >= :month)")
	public CssChartDTO getTotalAvgCSS(@Param("month") int month, @Param("year") int year);

	/**
	 * get list project id of project which has css for the given time period
	 * 
	 * @param deliveryUnit:
	 *            delivery units are selected for get css points in the given time
	 *            period
	 * @param month:
	 *            month of the given time period
	 * @param year:
	 *            year of the given time period
	 * @return List<Integer>
	 * @author: DVNgoc
	 */
	@Query("SELECT DISTINCT P.projectId FROM ProjectCss AS P\r\n" + "WHERE P.deliveryUnit LIKE %:deliveryUnit% "
			+ "AND ((YEAR(P.startDate) < :year\r\n" + "AND YEAR(P.endDate) >:year) OR (YEAR(P.startDate)< :year\r\n"
			+ "AND YEAR(P.endDate) =:year AND MONTH(P.endDate) >= :month)\r\n"
			+ "OR (YEAR(P.startDate)= :year AND YEAR(P.endDate) >:year\r\n"
			+ "AND MONTH(P.startDate) <= :month) OR (YEAR(P.startDate)= :year\r\n"
			+ "AND YEAR(P.endDate) =:year AND MONTH(P.startDate) <= :month AND MONTH(P.endDate) >= :month))")
	public List<Integer> getListProjectId(@Param("deliveryUnit") String deliveryUnit, @Param("month") int month,
			@Param("year") int year);

	/**
	 * get times and value of getting css points in the given time period
	 * 
	 * @param projectId:
	 *            selected project
	 * @param month:month
	 *            of the given time period
	 * @param year:
	 *            year of the given time period
	 * @return List<CssTimeDto>
	 * @author: DVNgoc
	 */
	@Query(value = "SELECT NEW com.cmc.dashboard.dto.CssTimeDto(P.time as time, P.scoreValue as values)"
			+ "FROM ProjectCss AS P WHERE P.projectId = :projectId AND ((YEAR(P.startDate) < :year) "
			+ "OR (YEAR(P.startDate)= :year AND MONTH(P.startDate) <= :month))")
	public List<CssTimeDto> getCssTimeByProjectId(@Param("projectId") int projectId, @Param("month") int month,
			@Param("year") int year);

	/**
	 * get max of getting css points of projects
	 * 
	 * @param delivery_unit:
	 *            delivery units are selected for get css points in the given time
	 *            period
	 * @param month:month
	 *            of the given time period
	 * @param year:
	 *            year of the given time period
	 * @return Integer
	 * @author: DVNgoc
	 */
	@Query(value = "SELECT max(T.times) as col_0_0 FROM (SELECT project_id, count(P.project_id) AS times\r\n"
			+ "   FROM dashboard.project_css P\r\n"
			+ "WHERE P.delivery_unit LIKE %:delivery_unit%  AND ((YEAR(P.start_date) < :year\r\n"
			+ "	AND YEAR(P.end_date) >:year) OR (YEAR(P.start_date)< :year\r\n"
			+ "	AND YEAR(P.end_date) =:year AND MONTH(P.end_date) >= :month)\r\n"
			+ "	OR (YEAR(P.start_date)= :year AND YEAR(P.end_date) >:year\r\n"
			+ "	AND MONTH(P.start_date) <= :month) OR (YEAR(P.start_date)= :year\r\n"
			+ "	AND YEAR(P.end_date) =:year AND MONTH(P.start_date) <= :month\r\n"
			+ "	AND MONTH(P.end_date) >= :month)) GROUP BY project_id) AS T", nativeQuery = true)
	public Integer getMaxOfCssValueNumber(@Param("delivery_unit") String delivery_unit, @Param("month") int month,
			@Param("year") int year);

	/**
	 * Get list css value of selected project
	 * 
	 * @param projectId:
	 *            the selected project's id
	 * @return List<ProjectCssUtilizationDto>
	 * @author: DVNgoc
	 */
	@Query("SELECT new com.cmc.dashboard.dto.ProjectCssUtilizationDto(p.projectCssId, p.projectId,"
			+ " p.projectName, p.scoreValue, p.time, p.startDate, p.endDate, p.deliveryUnit) "
			+ "FROM ProjectCss p WHERE p.projectId = :projectId")
	public List<ProjectCssUtilizationDto> getListCssbyProjectId(@Param("projectId") int projectId);

	@Query("SELECT p FROM ProjectCss p\r\n" + "WHERE p.time = :time\r\n" + "  AND p.projectId = :projectId")
	public ProjectCss getLastestProjectCssByProjectId(@Param("time") int time, @Param("projectId") int projectId);

	/**
	 * Get all Project Delivery Unit have CSS
	 * @return List<String> 
	 * @author: DVNgoc
	 */
	@Query("SELECT DISTINCT P.deliveryUnit FROM ProjectCss P")
	public List<String> getAllDeliveryUnitCSS();

	/**
	 * Get AVG of CSS each delivery unit
	 * @param month
	 * @param year
	 * @param deliveryUnit
	 * @return Double 
	 * @author: DVNgoc
	 */
	@Query(value = "SELECT avg(avg_score) FROM (SELECT project_id,\r\n"
			+ "          delivery_unit, avg(score_value) AS avg_score\r\n"
			+ "   FROM dashboard.project_css P WHERE (year(P.start_date) < :year\r\n"
			+ "          AND year(P.end_date) > :year) OR (year(P.start_date) < :year\r\n"
			+ "         AND year(P.end_date) = :year AND month(P.end_date)>=:month)\r\n"
			+ "     OR (YEAR(P.start_date)= :year AND year(P.end_date) > :year\r\n"
			+ "         AND month(P.start_date)<= :month) OR (year(P.start_date) = :year\r\n"
			+ "         AND year(P.end_date) = :year AND month(P.start_date) <= :month\r\n"
			+ "         AND month(P.end_date)>=:month) GROUP BY project_id) TB\r\n"
			+ "WHERE TB.delivery_unit LIKE %:deliveryUnit%", nativeQuery = true)
	public Double getAvgCssByDeliveryUnit(@Param("month") int month, @Param("year") int year,
			@Param("deliveryUnit") String deliveryUnit);

}
