package com.cmc.dashboard.qms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.qms.model.CustomValue;
import com.cmc.dashboard.util.CustomValueUtil;

public interface CustomValueRepository extends JpaRepository<CustomValue, Integer>{
	
	/**
	 * Get custom value of project
	 * @return List<CustomValue> 
	 * @author: nvkhoa
	 */
	@Query(value="SELECT * \r\n" +
			"FROM redmine_db.custom_values CV WHERE CV.custom_field_id = " + CustomValueUtil.MAN_DAY_ID+ 
			"										OR CV.custom_field_id = "+CustomValueUtil.START_DATE_ID+"\r\n" + 
			"                                        OR CV.custom_field_id = "+CustomValueUtil.END_DATE_ID+"\r\n" + 
			"                                        OR CV.custom_field_id ="+CustomValueUtil.PROJECT_TYPE_ID+"\r\n" + 
			"                                        OR CV.custom_field_id ="+CustomValueUtil.DELIVERY_UNIT_ID, nativeQuery=true)
	public List<CustomValue> getAllValueProject();
	
	@Query(value="SELECT * \r\n" +
			"FROM redmine_db.custom_values CV \r\n"+
			"WHERE CV.customized_id = :id AND ( CV.custom_field_id = " + CustomValueUtil.MAN_DAY_ID+ "\r\n"+
			"OR CV.custom_field_id = "+CustomValueUtil.START_DATE_ID+"\r\n" + 
			"OR CV.custom_field_id = "+CustomValueUtil.END_DATE_ID+"\r\n" + 
			"OR CV.custom_field_id ="+CustomValueUtil.PROJECT_TYPE_ID+"\r\n" + 
			"OR CV.custom_field_id ="+CustomValueUtil.DELIVERY_UNIT_ID + ")", nativeQuery=true)
	public Optional<List<CustomValue>> findByCustomizeId(@Param("id") int id);
	
	@Query(value="SELECT CF.possible_values FROM redmine_db.custom_fields CF where id = :customFieldId", nativeQuery=true)
	public Optional<String> getPossibleValue(@Param("customFieldId") int customFieldId) ;
}
