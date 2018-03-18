package com.cmc.dashboard.service.qms;


import java.sql.SQLException;

import com.cmc.dashboard.dto.UserUtilizationDTO;

public interface QmsUserService {

	UserUtilizationDTO loadInfo(String username) throws SQLException;


}
