/**
 * DashboardSystem - com.cmc.dashboard.util
 */
package com.cmc.dashboard.util;

import java.time.LocalDate;

/**
 * @author: DVNgoc
 * @Date: Dec 19, 2017
 */
public class CustomValueUtil {
	public static final String FILE_NAME_BILLABLE = "src/main/resources/static/exported/Billable_ID";
	public static final String FILE_NAME_CSS = "src/main/resources/static/exported/CSS_ID";
	public static final String FILE_NAME_RESOURCE = "src/main/resources/static/exported/Resource_allocate_";
	public static final String[] BILLABLE_HEADER = { "NO", "BILLABLE MONTH", "MAN MONTH", "ISSUE CODE", "BILLABLE VALUE"};
	public static final String[] CSS_HEADER = { "NO", "TIME", "START DATE", "END DATE", "CSS VALUE"};
	//
	public static final String PROJECT_MANAGER = "PM";
	public static final String DU_LEAD = "DU";
	public static final String DELIVERY_UNIT = "DU";
	public static final String QA = "QA";
	public static final String BOD = "BOD";
	public static final String GROUP_RRC = "RRC";
	public static final String GROUP_TRAINING = "Training";
	//
	public static final String _PROJECT_MANAGER = "DA_PM";
	public static final String _DU_LEAD = "DA_DU_";
	public static final String _QA = "DA_QA";
	public static final String _BOD = "DA_BOD";
	public static final String _RRC = "DA_RRC";

	public static final String COMPANY = "CMC Global";
	public static final String DU_NAME = "du_name";
	public static final String TOTAL = "Total";
	public static final String ALL_TYPE = "All";

	public static final String ROLE_PM = "3";
	public static final String START_DATE = "35";
	public static final String END_DATE = "36";

	// Custom field identify of project
	public static final int DELIVERY_UNIT_USER_ID = 6;
	public static final int DELIVERY_UNIT_ID = 38;
	public static final int MAN_DAY_ID = 9;
	public static final int START_DATE_ID = 35;
	public static final int END_DATE_ID = 36;
	public static final int PROJECT_TYPE_ID = 37;
	public static final int WORKING_DAY = 8;
	public static final int STATUS_CLOSE = 5;

	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String CSS_VALUE_UNKNOWN = "0";

	public static final String START_DATE_KEY = "START_DATE_KEY";
	public static final String END_DATE_KEY = "END_DATE_KEY";
	public static final String SESSION_USER_KEY = "user";
	public static final String TOKEN_KEY = "token";
	public static final int MAN_MONTH = 22;

	public static final int BILLABLE_MIN_VALUE = 0;
	public static final int BILLABLE_MAX_VALUE = 100;

	public static final LocalDate MIN_DATE = LocalDate.of(2017, 04, 01);

	public static final String PROJECT_DASHBOARD = "Project Dashboard";
	public static final String MANAGE_PROJECT = "Quản lý Project";
	public static final String MANAGE_RESOURCE = "Quản lý Resource";

	public static final String DAY = "-01";
	public static final String DU = "DA_DU";
	public static final String OSD_NAME = "OSD";
	public static final String SPLIT_CHAR = "\\-";
	public static final int SUBSTRING_INDEX_GROUP = 3;
	public static final String DA = "DA";
	public static final String DA_PREFIX = "DA_";

	public static final int USER_DELIVERY_UNIT_ID = 6;
}
