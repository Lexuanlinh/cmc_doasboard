/**
 * 
 */
package com.cmc.dashboard.repository;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.dto.ResourcePDetailDTO;
import com.cmc.dashboard.model.ProjectBillable;
import com.cmc.dashboard.util.CustomValueUtil;

/**
 * @author nahung
 *
 */
public interface ProjectRepository extends JpaRepository<ProjectBillable, Integer> {

	/**
	 *  get list project billable.
	 * @return List<ProjectBillable> 
	 * @author: Hoai-Nam
	 */
	@Query(value = "SELECT DPB.project_billable_id, DPB.project_id,DPB.project_name,DPB.pm_name,DPB.billable_value,DPB.start_date,DPB.end_date FROM dashboard.project_billable DPB", nativeQuery = true)
	public List<ProjectBillable> getAllProjectBillable();
	
	/**
	 *get list pmName, and projectName by projectId
	 * @param ltsProjectId
	 * @return List<UserPlanDetailDTO> 
	 * @author: Hoai-Nam
	 */
	@Query(value="SELECT new com.cmc.dashboard.dto.ResourcePDetailDTO(b.projectId,b.projectName,b.pmName)"
			+ " FROM ProjectBillable AS b "
			+ " WHERE b.projectId IN :listProjectId"
			+ " GROUP BY b.projectName")
	public List<ResourcePDetailDTO>getNameProjectDetails(@Param("listProjectId")List<Integer> ltsProjectId) throws SQLException;
	
	@Query(value="SELECT CV.value FROM redmine_db.custom_values CV WHERE CV.custom_field_id=" 
			+ CustomValueUtil.DELIVERY_UNIT_ID + " AND CV.customized_id = :projectId", nativeQuery=true)
			public String getDuNameByProjectId(@Param("projectId") int projectId); 
}
