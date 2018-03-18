package com.cmc.dashboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.GroupRepository;

@Service
public class GroupServiceImpl implements GroupService {
	@Autowired
	GroupRepository groupRepository;
	
	@Autowired
	UserQmsRepository userQmsRepository;

	@Override
	public List<Group> getAllGroup() {
		return groupRepository.findAll();
	}

	@Override
	public Group getById(int id) {
		return groupRepository.findById(id);
	}

	@Override
	public List<Integer> getGroupIdByQmsUserId(int userId) {
		return userQmsRepository.getGroupIdByQmsUserId(userId);
	}

	@Override
	public List<Group> findMaxRank(List<Integer> groupIds) {
		return groupRepository.findMaxRank(groupIds);
	}

}
