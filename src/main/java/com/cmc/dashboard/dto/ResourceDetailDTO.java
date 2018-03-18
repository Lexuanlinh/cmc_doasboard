package com.cmc.dashboard.dto;

import java.util.List;

import com.cmc.dashboard.util.MethodUtil;

public class ResourceDetailDTO {
	List<UserPlanDetailDTO> ltsUserPlanDetails;
	private float total;


	public float getTotal() {
		for (UserPlanDetailDTO userPlanDetailDTO : ltsUserPlanDetails) {
			total += userPlanDetailDTO.getManDay();
		}
		return MethodUtil.formatFloatNumberType(total);
	}

	public List<UserPlanDetailDTO> getLtsUserPlanDetails() {
		return ltsUserPlanDetails;
	}

	public void setLtsUserPlanDetails(List<UserPlanDetailDTO> ltsUserPlanDetails) {
		this.ltsUserPlanDetails = ltsUserPlanDetails;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public ResourceDetailDTO(List<UserPlanDetailDTO> ltsUserPlanDetails) {
		super();
		this.ltsUserPlanDetails = ltsUserPlanDetails;
	}

}
