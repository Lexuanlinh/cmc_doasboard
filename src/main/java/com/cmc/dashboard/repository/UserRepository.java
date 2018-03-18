package com.cmc.dashboard.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cmc.dashboard.model.Group;
import com.cmc.dashboard.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	@Query(value = "SELECT u.user_id from users u where u.group_id = 1", nativeQuery = true)
	public List<Integer> findUserByGroup();
	
	@Query("FROM User u WHERE u.userName LIKE :userName")
	public User findByUserName(@Param("userName")String userName);

	@Query(value = "SELECT u.group from User u WHERE u.userId = :userId")
	public Optional<Group> getGroupByUser(@Param("userId") int userId);
}
