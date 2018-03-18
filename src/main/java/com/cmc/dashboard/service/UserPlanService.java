package com.cmc.dashboard.service;

import java.sql.SQLException;
import java.util.List;

import com.cmc.dashboard.dto.ListResourceDetailDTO;
import com.cmc.dashboard.dto.RemoveResourcePlanDTO;
import com.cmc.dashboard.dto.ResourceAllocationDTO;
import com.cmc.dashboard.dto.ResourceDTO;
import com.cmc.dashboard.dto.UpdateResourceDTO;
import com.cmc.dashboard.dto.UserPlanMessageDTO;


public interface UserPlanService {

    /**
     * GET LIST RESOURCE ALLOCATION OF PROJECT.
     * @param projectId
     * @return List<UserPlanUtilizationDTO> 
     * @author: Hoai-Nam
     */
    public List<ResourceAllocationDTO> getListResourcesByProjectId(int projectId);
    
    /**
     * ALOCATE FOR MEMBER.
     * @param saveListUPlan
     * @return String 
     * @author: Hoai-Nam
     */
  
    public UserPlanMessageDTO saveNewPlan(String plan);
    
    //public List<ResourceAllocationDTO> updatePlan(String plan);
    public UpdateResourceDTO updatePlan(String plan);
    
    /**
     * delete user plan.
     * @param userId
     * @return String 
     * @author: Hoai-Nam
     */
    public RemoveResourcePlanDTO removePlan(int planId,int userId, int projectId);  
    
    /**
     * get resource detail.
     * @param userId
     * @param month
     * @param year
     * @return ResourceDetailDTO 
     * @author: Hoai-Nam
     */
    public ListResourceDetailDTO getResourceDetail(String userLoginId,String userId,String planMonth);
    
    /**
     * export resource allocate.
     * @param projectId
     * @return
     * @throws SQLException String 
     * @author: Hoai-Nam
     */
    public String exportResourceAllocate(int projectId) throws SQLException;
    
    /**
     * @param userId
     * @param projectId
     * @return
     */
    public List<ResourceDTO> getResourcePlanByUser(int userId, int projectId);
}
