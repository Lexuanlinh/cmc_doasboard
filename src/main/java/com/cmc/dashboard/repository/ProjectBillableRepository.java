/**
 * 
 */
package com.cmc.dashboard.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.dto.ProjectTimeDTO;
import com.cmc.dashboard.model.ProjectBillable;

/**
 * @author nahung
 *
 */
public interface ProjectBillableRepository extends JpaRepository<ProjectBillable, Integer> {

	/**
	 * get start date and end date of project
	 * @param projectId
	 * @return ProjectTimeDTO 
	 * @author: Hoai-Nam
	 */
	@Query(value="SELECT new com.cmc.dashboard.dto.ProjectTimeDTO(P.startDate,P.endDate)"
			+ " FROM ProjectBillable P"
			+ " WHERE P.projectId = :projectId Group by P.projectId")
	public ProjectTimeDTO getDateProject(@Param("projectId")int projectId) throws SQLException,NullPointerException;
	

	/**
	 * TODO description
	 * @param deliveryUnit
	 * @param billableMonth
	 * @return
	 * @throws SQLException List<BillableDTO> 
	 * @author: NVKhoa
	 */
	@Query(value="SELECT PB. project_billable_id, PB.billable_month, PB.billable_value, PB.issue_code, \r\n" + 
			"(SELECT ROUND(SUM(UP.man_month),2) FROM user_plan UP WHERE UP.project_id = PB.project_id) as manMonth\r\n" + 
			"FROM dashboard.project_billable PB \r\n" + 
			"WHERE PB.project_id = :projectId ", nativeQuery = true)
	public Optional<List<Object>> getListBillable(@Param("projectId") int projectId)throws SQLException;
	
	
	/**
	 * Get project billable by project
	 * @param projectId
	 * @return List<ProjectBillable> 
	 * @author: NVKhoa
	 */
	@Query(value = "SELECT DP FROM ProjectBillable DP WHERE DP.projectId = :projectId")
	public List<ProjectBillable> getBillableByProject(@Param("projectId") int projectId);
	
	/**
	 * Get project billable by month
	 * @param billableMonth
	 * @return List<ProjectBillable> 
	 * @author: NVKhoa
	 */
	
	@Query(value = "SELECT DP FROM ProjectBillable DP WHERE DP.billableMonth = :billableMonth")
	public List<ProjectBillable> getBillableByMonth(@Param("billableMonth") String billableMonth);
	
}
