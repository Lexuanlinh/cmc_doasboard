/**
 * DashboardSystem - com.cmc.dashboard.repository
 */
package com.cmc.dashboard.repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.model.UserPlanDetail;

/**
 * @author: DVNgoc
 * @Date: Dec 27, 2017
 */
public interface UserPlanDetailRepository extends JpaRepository<UserPlanDetail, Integer> {
	@Query(value = "SELECT  new com.cmc.dashboard.model.UserPlanDetail(upd.planMonth, SUM(upd.manDay) as manDay) FROM UserPlanDetail AS upd\r\n"
			+ "	INNER JOIN upd.userPlan up\r\n" + "	WHERE up.userId = :userId AND upd.resDeliveryUnit LIKE %:duName% GROUP BY upd.planMonth")
	public List<UserPlanDetail> getUserPlanDetailByUserId(@Param("userId") int userId, @Param("duName") String duName);
	
	@Query(value="SELECT DISTINCT UPD.id, UPD.plan_month, ROUND(UPD.man_day,2) as man_day, UPD.delivery_unit, UPD.user_plan_id, UPD.created_on, UPD.updated_on, UPD.res_delivery_unit "
			+ "FROM user_plan_detail UPD \r\n" + 
			"JOIN user_plan UP ON UP.user_plan_id = UPD.user_plan_id \r\n" + 
			"JOIN project_billable PB ON UP.project_id = PB.project_id\r\n" + 
			"WHERE UPD.plan_month = :planMonth", nativeQuery=true)
	public List<UserPlanDetail> getPlanMonth(@Param("planMonth") String planMonth);
	
	/**
	 * Delete user plan detail
	 * @param userPlanId
	 * @throws SQLException void 
	 * @author: Hoai-Nam
	 */
	@Modifying
	@Transactional
	@Query(value="DELETE FROM dashboard.user_plan_detail WHERE user_plan_id=:userPlanId", nativeQuery=true)
	public void deleteUserPlanDetail(@Param("userPlanId") int userPlanId) throws SQLException;
	
	@Modifying
	@Transactional
	@Query(value="DELETE FROM dashboard.user_plan_detail WHERE user_plan_id IN :userPlanIds", nativeQuery=true)
	public void deleteListUserPlanDetail(@Param("userPlanIds") List<Integer> userPlanIds) throws SQLException;
	
	
	/**
	 * Get list user plan billable detail.
	 * @param deliveryUnit
	 * @param billableMonth
	 * @return
	 * @throws SQLException List<Object> 
	 * @author: Hoai-Nam
	 */
	@Query(value="SELECT TB1.project_name projectName,\r\n" + 
			"       TB1.pm_name pmName,\r\n" + 
			"       TB1.start_date startDate,\r\n" + 
			"       TB1.end_date endDate,\r\n" + 
			"       TB1.billable_value billableValue,\r\n" + 
			"       COALESCE(TB2.man_day, 0) man_day,\r\n" + 
			"       IF(TB2.man_day IS NULL,0,(TB1.billable_value/TB2.man_day)*100) effortEfficiency\r\n" + 
			"FROM\r\n" + 
			"  ( SELECT PB.project_name,\r\n" + 
			"           PB.pm_name,\r\n" + 
			"           PB.start_date,\r\n" + 
			"           PB.end_date,\r\n" + 
			"           PB.billable_month,\r\n" + 
			"           PB.billable_value,\r\n" + 
			"           PB.project_id,\r\n" + 
			"           PB.delivery_unit\r\n" + 
			"   FROM dashboard.project_billable PB\r\n" + 
			"   WHERE PB.delivery_unit = :deliveryUnit\r\n" + 
			"     AND PB.billable_month=:billableMonth ) TB1\r\n" + 
			"LEFT JOIN\r\n" + 
			"  ( SELECT UPD.delivery_unit du,\r\n" + 
			"           UPD.plan_month,\r\n" + 
			"           UP.project_id pid,\r\n" + 
			"           SUM(UPD.man_day) AS man_day\r\n" + 
			"   FROM dashboard.user_plan_detail UPD\r\n" + 
			"   INNER JOIN dashboard.user_plan UP ON UP.user_plan_id=UPD.user_plan_id\r\n" + 
			"   WHERE UPD.delivery_unit=:deliveryUnit \r\n" + 
			"     AND UPD.plan_month=:billableMonth \r\n" + 
			"   GROUP BY UP.project_id ) TB2 ON TB2.pid= TB1.project_id", nativeQuery=true)
	public List<Object> getListUserPlanBillableDetail(@Param("deliveryUnit") String deliveryUnit, @Param("billableMonth") String billableMonth) throws SQLException;
	
	
	@Query(value="SELECT\r\n" + 
			"        TB1.project_name projectName,\r\n" + 
			"        TB1.pm_name pmName,\r\n" + 
			"        TB1.start_date startDate,\r\n" + 
			"        TB1.end_date endDate,\r\n" + 
			"        TB1.billable_value billableValue,\r\n" + 
			"        COALESCE(TB2.man_day,\r\n" + 
			"        0) man_day,\r\n" + 
			"        IF(TB2.man_day IS NULL,\r\n" + 
			"        0,\r\n" + 
			"        (TB1.billable_value/TB2.man_day)*100) effortEfficiency  \r\n" + 
			"    FROM\r\n" + 
			"        ( SELECT\r\n" + 
			"            PB.project_name,\r\n" + 
			"            PB.pm_name,\r\n" + 
			"            PB.start_date,\r\n" + 
			"            PB.end_date,\r\n" + 
			"            PB.billable_month,\r\n" + 
			"            PB.billable_value,\r\n" + 
			"            PB.project_id,\r\n" + 
			"            PB.delivery_unit     \r\n" + 
			"        FROM\r\n" + 
			"            dashboard.project_billable PB     \r\n" + 
			"        WHERE\r\n" + 
			"            PB.billable_month=:billableMonth ) TB1  \r\n" + 
			"    LEFT JOIN\r\n" + 
			"        (\r\n" + 
			"            SELECT\r\n" + 
			"                UPD.delivery_unit du,\r\n" + 
			"                UPD.plan_month,\r\n" + 
			"                UP.project_id pid,\r\n" + 
			"                SUM(UPD.man_day) AS man_day     \r\n" + 
			"            FROM\r\n" + 
			"                dashboard.user_plan_detail UPD     \r\n" + 
			"            INNER JOIN\r\n" + 
			"                dashboard.user_plan UP \r\n" + 
			"                    ON UP.user_plan_id=UPD.user_plan_id     \r\n" + 
			"            WHERE\r\n" + 
			"                UPD.plan_month=:billableMonth     \r\n" + 
			"            GROUP BY\r\n" + 
			"                UP.project_id \r\n" + 
			"        ) TB2 \r\n" + 
			"            ON TB2.pid= TB1.project_id", nativeQuery=true)
	
	public Optional<List<Object>> getListUserPlanBillableDetail(@Param("billableMonth") String billableMonth) throws SQLException;
	
}
