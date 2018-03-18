package com.cmc.dashboard.repository;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.cmc.dashboard.dto.OverLoadPlanRemovedDTO;
import com.cmc.dashboard.model.OverloadedPlans;

public interface OverloadedPlanRepository extends JpaRepository<OverloadedPlans, Integer>{
	
	@Query(value="SELECT * FROM (SELECT UP.user_id,"
			+ " COALESCE(SUM(UP.effort_per_day), 0) total_effort,"
			+ " DATE_FORMAT((CASE UP.from_date"
			+ " WHEN :fromDate <= UP.from_date THEN UP.from_date"
			+ " ELSE :fromDate"
			+ " END), '%Y-%m-%d') from_date,"
			+ " DATE_FORMAT((CASE UP.to_date"
			+ " WHEN :toDate >= UP.to_date THEN UP.to_date"
			+ " ELSE :toDate"
			+ " END), '%Y-%m-%d') to_date,count(UP.user_id) num_ber,UP.is_overloaded overload"
			+ " FROM dashboard.user_plan UP"
			+ " WHERE :fromDate <= UP.to_date"
			+ " AND :toDate >= UP.from_date"
			+ " AND UP.user_id =:userId) TB1 WHERE TB1.user_id IS NOT NULL", nativeQuery=true)
	public Object getListOfOverloadedPlans(@Param("fromDate") Date startDate, @Param("toDate") Date toDate, @Param("userId") int userId) throws SQLException;
	
	@Query(value="SELECT * FROM (SELECT UP.user_id,"
			+ " COALESCE(SUM(UP.effort_per_day), 0) total_effort,"
			+ " DATE_FORMAT((CASE UP.from_date"
			+ " WHEN :fromDate <= UP.from_date THEN UP.from_date"
			+ " ELSE :fromDate"
			+ " END), '%Y-%m-%d') from_date,"
			+ " DATE_FORMAT((CASE UP.to_date"
			+ " WHEN :toDate >= UP.to_date THEN UP.to_date"
			+ " ELSE :toDate"
			+ " END), '%Y-%m-%d') to_date,count(UP.user_id) num_ber"
			+ " FROM dashboard.user_plan UP"
			+ " WHERE :fromDate <= UP.to_date"
			+ " AND :toDate >= UP.from_date"
			+ " AND UP.user_id =:userId "
			+ " AND UP.user_plan_id NOT IN (:userPlanId)) TB1 WHERE TB1.user_id IS NOT NULL", nativeQuery=true)
	public Object getListOfOverloadedPlansForUpdate(@Param("fromDate") Date startDate, @Param("toDate") Date toDate, @Param("userId") int userId,@Param("userPlanId")int userPlanId) throws SQLException;
	
	
	
	@Modifying
	@Transactional
	@Query(value="DELETE FROM dashboard.overloaded_plans WHERE user_plan_id = :userPlanId", nativeQuery=true)
	public void deleteOverPlan(@Param("userPlanId") int userPlanId) throws SQLException;
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM dashboard.overloaded_plans WHERE user_plan_id IN :ltsOverLoadId",nativeQuery = true)
	public void deleteOverPlanAfterRemovePlan(@Param("ltsOverLoadId")List<Integer> ltsOverLoadId)throws SQLException;
	
	@Query(value = "SELECT new com.cmc.dashboard.dto.OverLoadPlanRemovedDTO(dop.overloadedPlanId,dop.user_plan_id) FROM OverloadedPlans dop WHERE dop.user_id = :userId")
	public List<OverLoadPlanRemovedDTO> getListOverLoadPlanId(@Param("userId")int userId)throws SQLException;
	
	
	@Query(value = "SELECT o.overloaded_plans_id,\r\n" + 
			"       u.user_plan_id\r\n" + 
			"FROM dashboard.overloaded_plans o\r\n" + 
			"INNER JOIN dashboard.user_plan u ON o.user_plan_id = u.user_plan_id\r\n" + 
			"WHERE o.user_id = :userId\r\n" + 
			"  AND u.to_date >= :fromdate\r\n" + 
			"  AND u.from_date <= :todate\r\n" + 
			"GROUP BY u.user_plan_id",nativeQuery = true)
	public List<Object> getListOverLoadPlanIdUpdate(@Param("userId")int userId,@Param("fromdate")Date fromdate,@Param("todate")Date todate)throws SQLException;
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE user_plan SET is_overloaded = 0 WHERE user_plan_id IN (:ltsUserPlanId)",nativeQuery = true)
	public void updateIsOverLoaded(@Param("ltsUserPlanId")List<Integer> ltsUserPlanId )throws SQLException;
}
