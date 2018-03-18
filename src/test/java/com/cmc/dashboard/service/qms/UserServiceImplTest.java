package com.cmc.dashboard.service.qms;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cmc.dashboard.DashboardSystemApplication;
import com.cmc.dashboard.db.H2JpaConfig;
import com.cmc.dashboard.model.User;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.service.UserService;
import com.cmc.dashboard.service.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DashboardSystemApplication.class,H2JpaConfig.class})
@ActiveProfiles("test")
public class UserServiceImplTest {

	@Autowired
	UserService userService;

	@TestConfiguration
	static class UserServiceImplTestContextConfiguration {

		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}
	}

	@MockBean
	private UserRepository userRepository;

	@Before
	public void setUp() {
		User user = new User();
		user.setUserId(1);
		user.setUserName("admin");
		user.setFullName("Pham Van Canh");		

		Mockito.when(userRepository.findByUserName(user.getUserName())).thenReturn(user);
	}

	@Test
	public void loadInfo() {

		String username = "admin";
		User user = null;
		user = userService.findByUserName(username);

		assertNotNull(user);
		assertThat(user.getUserName()).isEqualTo(username);

	}

}