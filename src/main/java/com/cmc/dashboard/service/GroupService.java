package com.cmc.dashboard.service;

import java.util.List;

import com.cmc.dashboard.model.Group;

public interface GroupService {
	public List<Group> getAllGroup();

	public Group getById(int id);

	public List<Integer> getGroupIdByQmsUserId(int userId);
	
	public List<Group> findMaxRank(List<Integer> groupIds);
}
