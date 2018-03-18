package com.cmc.dashboard.dto;

import java.util.List;

import com.cmc.dashboard.util.MethodUtil;

public class ListResourceDetailDTO {
	List<ResourcePDetailDTO> ltsUserPlanDetails;
	private float total;

	public List<ResourcePDetailDTO> getResourcePDetailDTOs() {
		return ltsUserPlanDetails;
	}

	public void setResourcePDetailDTOs(List<ResourcePDetailDTO> resourcePDetailDTOs) {
		this.ltsUserPlanDetails = resourcePDetailDTOs;
	}

	public float getTotal() {
		if (!MethodUtil.checkList(ltsUserPlanDetails)) {
			for (ResourcePDetailDTO resourcePDetailDTO : ltsUserPlanDetails) {
				total += resourcePDetailDTO.getManDay();
			}
		}
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public ListResourceDetailDTO(List<ResourcePDetailDTO> resourcePDetailDTOs) {
		super();
		this.ltsUserPlanDetails = resourcePDetailDTOs;
	}

	public ListResourceDetailDTO() {
		super();
	}

}
