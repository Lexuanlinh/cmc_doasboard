package com.cmc.dashboard.service.qms;

import org.junit.Before;
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
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.qms.repository.UserQmsRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DashboardSystemApplication.class,H2JpaConfig.class})
@ActiveProfiles("test")
public class QmsUserServiceImplTest {

	@Autowired
	QmsUserService qmsUserService;

	@TestConfiguration
	static class QmsUserServiceImplTestContextConfiguration {

		@Bean
		public QmsUserService qmsUserService() {
			return new QmsUserServiceImpl();
		}
	}

	@MockBean
	private UserQmsRepository qmsUserRepository;

	@Before
	public void setUp() {
		QmsUser qmsUser = new QmsUser(1, "admin", "Canh", "Pham Van", true, 1);

		Mockito.when(qmsUserRepository.findUserByLogin(qmsUser.getLogin())).thenReturn(qmsUser);
	}

}