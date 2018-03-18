package com.cmc.dashboard.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.cmc.dashboard.dto.LoginParameterObject;
import com.cmc.dashboard.dto.OverLoadPlanRemovedDTO;
import com.cmc.dashboard.dto.OverloadedPlanDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MethodUtil {
	private static final String PATTERN_DATE_DMYYYY = "d-M-yyyy";
	private static final String PATTERN_DATE_MMYYYY = "MM-yyyy";
	private static final String PATTERN_DATE_YYYYMMDD = "yyyy-MM-dd";
	private static final String PATTERN_DATE_DDMMYYYY = "dd-MM-YYYY";

	/**
	 * function to paging result list, use on Service
	 * 
	 * @param pageNumber
	 *            Number of current Page.
	 * @param pageSize
	 *            Number record per Page.
	 * @param sortType
	 *            Type sort data,default = ASC.
	 * @param sortField
	 *            field sort.
	 * @return a Page Request.
	 */
	public PageRequest Pagination(int pageNumber, int pageSize, String sortType, String sortField) {
		return (sortType == "DESC") ? new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, sortField)
				: new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, sortField);
	}

	/**
	 * Check object null.
	 * 
	 * @param object
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	public static boolean isNull(Object object) {
		return object == null || "".equals(object);
	}

	/**
	 * check list null.
	 * 
	 * 
	 * @param list
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	public static boolean checkList(List<?> list) {
		return list == null || list.size() == 0;
	}

	/**
	 * Method check paramter null ỏr empty.
	 * 
	 * @param paramter
	 * @return String
	 * @author: Hoai-Nam
	 */
	public static String checkSearchParameter(String paramter) {
		return (paramter.equals("\"\"") || paramter.equals("null") || paramter.startsWith(" ")
				|| paramter.endsWith(" ")) ? "" : paramter;
	}

	/**
	 * method convert password get from client into sha1 code.
	 * 
	 * @param input
	 * @return String
	 * @author: Hoai-Nam
	 */
	public static String sha1(String input) {
		if (input == null || input.equalsIgnoreCase("")) {
			return input;
		}
		try {
			MessageDigest mDigest;
			mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(input.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method check input login;
	 * 
	 * @param data
	 * @param regexter
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	public static boolean validateLoginParams(String data, String regexter) {
		return (data == null || regexter == null || "".equals(data)) ? false : data.matches(regexter);
	}

	/**
	 * method get parameter request from client.
	 * 
	 * @param passedParams
	 * @return LoginParameterObject
	 * @author: Hoai-Nam
	 */
	public static LoginParameterObject getLoginParamsFromString(String passedParams) {
		if (passedParams == null || "".equals(passedParams)) {
			return null;
		}
		try {
			JSONObject object = new JSONObject(passedParams);
			String username = object.getString("username");
			String password = object.getString("password");
			return new LoginParameterObject(username, password);

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * method compare beetwen two date.
	 * 
	 * @param oldDate
	 * @param newDate
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	public static boolean checkDateValid(Date startDate, Date endDate, Date dateCheck) {
		if (dateCheck.after(startDate) && dateCheck.before(endDate) || dateCheck.equals(startDate)
				|| dateCheck.equals(endDate)) {
			return true;
		}
		return false;
	}

	/**
	 * method calculate Effort efficiency of DU.
	 * 
	 * @param sumbillable
	 * @param manDay
	 * @return Float
	 * @author: Hoai-Nam
	 */
	public static Float calculateEE(float sumbillable, float manDay) {
		DecimalFormat df = new DecimalFormat("#.00");
		return (manDay != 0) ? Float.parseFloat(df.format(sumbillable / manDay)) : 0;
	}

	/**
	 * method format float to 1 decimal places.
	 * 
	 * @param number
	 * @return Float
	 * @author: Hoai-Nam
	 */
	public static Float formatFloatNumberType(float number) {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		return Float.valueOf(formatter.format(number));
	}
	
	public static Double formatDoubleNumberType(double number) {
		DecimalFormat formatter = new DecimalFormat("#.##");
		return Double.valueOf(formatter.format(number));
	}

	/**
	 * check input is number ?
	 * 
	 * @param projectId
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	public static boolean validateInterger(String projectId) {
		try {
			Integer.parseInt(projectId);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * method get string in json parameter.
	 * 
	 * @param jObj
	 * @param keyName
	 * @return String
	 * @author: Hoai-Nam
	 */
	public static String getStringValue(JsonObject jObj, String keyName)throws NullPointerException {
		return jObj.get(keyName).isJsonNull() ? "" : jObj.get(keyName).getAsString();
	}

	public static OverloadedPlanDTO getOverloadedPlanDTO(Object obj) throws ParseException {
		if (obj == null) {
			return null;
		}
		Object[] listProperties = (Object[]) obj;
		int userId = Integer.parseInt(listProperties[0].toString());
		float totalEffort = Float.parseFloat(listProperties[1].toString());
		String returnedFromDate = (String) listProperties[2];
		String returnedToDate = (String) listProperties[3];
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = formatter.parse(returnedFromDate);
		Date toDate = formatter.parse(returnedToDate);
		int count = Integer.parseInt(listProperties[4].toString());
		boolean isOverLoad = listProperties[5].toString().equals("true")?true:false;
		return new OverloadedPlanDTO(userId, totalEffort, fromDate, toDate,count,isOverLoad);
	}
	
	public static OverloadedPlanDTO getOverloadedPlanDTOUpdate(Object obj) throws ParseException {
		if (obj == null) {
			return null;
		}
		Object[] listProperties = (Object[]) obj;
		int userId = Integer.parseInt(listProperties[0].toString());
		float totalEffort = Float.parseFloat(listProperties[1].toString());
		String returnedFromDate = (String) listProperties[2];
		String returnedToDate = (String) listProperties[3];
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = formatter.parse(returnedFromDate);
		Date toDate = formatter.parse(returnedToDate);
		int count = Integer.parseInt(listProperties[4].toString());
		return new OverloadedPlanDTO(userId, totalEffort, fromDate, toDate,count);
	}
	
	public static List<OverLoadPlanRemovedDTO> getOverLoadPlanRemovedDTO(List<Object> obj) {
		if (checkList(obj)) {
			return null;
		}
		List<OverLoadPlanRemovedDTO> ltsOverLoad = new ArrayList<>();
		int overLoadPlan =0;
		int userPlanId =0;
		for (Object object : obj) {
			Object[] objOverload =(Object[])object;
			overLoadPlan=Integer.parseInt(objOverload[0].toString());
			userPlanId=Integer.parseInt(objOverload[1].toString());
			ltsOverLoad.add(new OverLoadPlanRemovedDTO(overLoadPlan, userPlanId));
		}
		return ltsOverLoad;
	}

	/**
	 * Method return format of message error of function create allocate for user.
	 * 
	 * @param startDate
	 * @param endDate
	 * @return String
	 * @author: Hoai-Nam
	 */
	public static String formatMessageError(Date startDate, Date endDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MMM/dd");
		return sdf.format(startDate) + " -> " + sdf.format(endDate);
	}

	/**
	 * method convert String to Date.
	 * 
	 * @param localDate
	 * @return Date
	 * @author: Hoai-Nam
	 */
	public static Date convertStringToDate(String localDate) throws DateTimeException, DateTimeParseException {
		return Date.from(LocalDate.parse(localDate).atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	
	
	public static LocalDate converStringToLocalDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateConverted = LocalDate.parse(date, formatter);
		return dateConverted;
	}

	/**
	 * convert json to json array.
	 * 
	 * @param json
	 * @return JsonObject
	 * @author: Hoai-Nam
	 */
	public static JsonObject getJsonObjectByString(String json) throws ParseException {
		JsonParser parser = new JsonParser();
		JsonElement tradeElement = parser.parse(json);
		return tradeElement.getAsJsonObject();
	}

	public static JsonArray getJsonArrayByString(String json) throws ParseException {
		JsonParser parser = new JsonParser();
		JsonElement tradeElement = parser.parse(json);
		return tradeElement.getAsJsonArray();
	}

	/**
	 * check input json.
	 * 
	 * @param json
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	public static boolean checkJson(String json) {
		try {
			new JSONObject(json);
		} catch (JSONException ex) {
			try {
				new JSONArray(json);
			} catch (JSONException ex1) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Check date type.
	 * 
	 * @param inputDate
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	public static boolean isValid(String input) {
		DateTimeFormatter[] formatters = {
				new DateTimeFormatterBuilder().appendPattern("yyyy-MM").parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
						.toFormatter(),
				new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd").parseStrict().toFormatter() };
		for (DateTimeFormatter formatter : formatters) {
			try {
				LocalDate.parse(input, formatter);
				return true;
			} catch (DateTimeParseException e) {
			}
		}
		return false;
		
	}

	/**
	 * The function to get a map containing start_date and end_date
	 * 
	 * @param month
	 * @param year
	 * @return Map<String,String>
	 * @author: DVNgoc
	 */
	public static Map<String, String> getDayOfDate(int month, int year) {
		Map<String, String> map = new HashMap<String, String>();
		String startDate = null, endDate = null;
		LocalDate initial = LocalDate.of(year, month, 1);
		startDate = initial.withDayOfMonth(1).toString();
		endDate = initial.withDayOfMonth(initial.lengthOfMonth()).toString();
		map.put(CustomValueUtil.START_DATE_KEY, startDate);
		map.put(CustomValueUtil.END_DATE_KEY, endDate);
		return map;
	}

	/**
	 * Validate format String date
	 * 
	 * @param date
	 * @return boolean
	 * @author: CMC-GLOBAL
	 */
	public static boolean isValidDate(int month, int year) {
		try {
			LocalDate date = LocalDate.now();
			LocalDate _date = LocalDate.of(year, month, 1);
			if (_date.isBefore(date) || _date.isEqual(date)) {
				if (_date.isAfter(CustomValueUtil.MIN_DATE) || _date.isEqual(CustomValueUtil.MIN_DATE)) {
					return true;
				}
			}
			return false;
		} catch (DateTimeException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Compare between two parame.
	 * 
	 * @param paramter1
	 * @param paramter2
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	public static boolean compare(String paramter1, String paramter2) {
		return (paramter1.equals(paramter2));
	}

	/**
	 * check user active?.
	 * 
	 * @param status
	 * @return boolean
	 * @author: Hoai-Nam
	 */
	public static boolean checkStatusUser(int status) {
		return (status != 1);
	}

	/**
	 * method get plan detail from start-date and end-date
	 * 
	 * @param startDate
	 * @param endDate
	 * @param toltalManDay
	 * @return Map<String,Float>
	 * @author: Hoai-Nam
	 */
	public Map<String, Float> getPlanMonthByStartDateEnDate(Date startDate, Date endDate, float toltalManDay) {
		Map<String, Float> mapsTestDate = null;
		try {
			mapsTestDate = getEffortForEachMonth(startDate, endDate, toltalManDay);
		} catch (ParseException e) {
			return mapsTestDate;
		}
		return mapsTestDate;
	}

	public static Map<String, Float> getEffortForEachMonth(Date startDate, Date endDate, float totalManday)
			throws ParseException {

		// Check date is valid: startDate must be before endDate
		// if (startDate.equals(endDate) || startDate.after(endDate)) {
		// return null;
		// }
		//
		if (startDate.after(endDate)) {
			return null;
		}

		Map<String, Float> mapMonthAndEffort = new HashMap<>();
		DateFormat formater = new SimpleDateFormat(PATTERN_DATE_MMYYYY);

		float workingDays = getTotalWorkingDaysBetweenDate(startDate, endDate);

		float effortPerDay = totalManday / workingDays;

		// List<String> lstMonth = getMonthsBetween2Date(startDate, endDate);
		String _startDate = dateToString(startDate);
		String _endDate = dateToString(endDate);
		List<String> lstMonth = getMonth2Date(_startDate, _endDate);
		float effortPerMonth;
		long totalWorkingDays = 0;

		Date startD = null, endD;
		Map<String, String> getStartDate = getDayOfMonth(formater.format(startDate));
		Map<String, String> getEndDate = getDayOfMonth(formater.format(endDate));
		Map<String, String> getCurrentDate;

		LocalDate startDateLocal = dateToLocalDate(startDate);
		LocalDate endDateLocal = dateToLocalDate(endDate);
		Date date = null;
		LocalDate localCurrentDate;
		for (String month : lstMonth) {
			date = getDateNow(month);
			localCurrentDate = dateToLocalDate(date);
			if (startDateLocal.equals(endDateLocal)) {
				totalWorkingDays = getTotalWorkingDaysBetweenDate(startDate, endDate);
			}else if (startDateLocal.getMonthValue() == endDateLocal.getMonthValue()
					&& startDateLocal.getYear() == endDateLocal.getYear()) {
				totalWorkingDays = getTotalWorkingDaysBetweenDate(startDate, endDate);
			} else if (startDateLocal.getMonthValue() == localCurrentDate.getMonthValue()
					&& startDateLocal.getYear() == localCurrentDate.getYear()) {
				totalWorkingDays = getTotalWorkingDaysBetweenDate(startDate,
						getDateNow(getStartDate.get(CustomValueUtil.END_DATE_KEY)));
			} else if (endDateLocal.getMonthValue() == localCurrentDate.getMonthValue()
					&& endDateLocal.getYear() == localCurrentDate.getYear()) {
				startD = getDateNow(getEndDate.get(CustomValueUtil.START_DATE_KEY));
				totalWorkingDays = getTotalWorkingDaysBetweenDate(startD, endDate);
			} else {
				getCurrentDate = getStartEndDayOfMonth(month);
				startD = getDateNow(getCurrentDate.get(CustomValueUtil.START_DATE_KEY));
				endD = getDateNow(getCurrentDate.get(CustomValueUtil.END_DATE_KEY));
				totalWorkingDays = getTotalWorkingDaysBetweenDate(startD, endD);
			}
			effortPerMonth = (effortPerDay * totalWorkingDays);
			mapMonthAndEffort.put(formater.format(date), effortPerMonth);
		}
		return mapMonthAndEffort;
	}

	public static Date getLastDayOfMonth(Date endDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
		return calendar.getTime();
	}

	private static List<String> getMonthsBetween2Date(Date startDate, Date endDate) {
		List<String> lstMonth = new ArrayList<>();
		DateFormat formater = new SimpleDateFormat(PATTERN_DATE_DMYYYY);

		Calendar cStartDate = Calendar.getInstance();
		cStartDate.setTime(startDate);

		Calendar cEndDate = Calendar.getInstance();
		cEndDate.setTime(endDate);

		int position = 0;

		while (cStartDate.before(cEndDate)) {

			if (position == 0) {
				lstMonth.add(formater.format(cStartDate.getTime()));

			} else {
				cStartDate.set(Calendar.DAY_OF_MONTH, 1);
				lstMonth.add(formater.format(cStartDate.getTime()));
			}
			position++;
			// add one month to date per loop
			cStartDate.add(Calendar.MONTH, 1);
		}

		if (lstMonth.size() > 1) {
			lstMonth.set(lstMonth.size() - 1, formater.format(endDate));
		}
		return lstMonth;
	}

	public static List<DayOfWeek> getIgnoreDays() {
		List<DayOfWeek> ignoreList = new ArrayList<DayOfWeek>();
		ignoreList.add(DayOfWeek.SATURDAY);
		ignoreList.add(DayOfWeek.SUNDAY);
		return ignoreList;
	}

	public static Date getDate(String strDate) throws ParseException {
		//return new SimpleDateFormat(PATTERN_DATE_DMYYYY).parse(strDate);
		return new SimpleDateFormat(PATTERN_DATE_YYYYMMDD).parse(strDate);
	}

	public static long getTotalWorkingDaysBetweenDate(Date start, Date end) {
		LocalDate calStart = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate originalEnd = end.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		List<DayOfWeek> ignore = getIgnoreDays();
		// LocalDate calStart = start;
		LocalDate calEnd = originalEnd.plusDays(1L);

		return Stream.iterate(calStart, d -> d.plusDays(1)).limit(calStart.until(calEnd, ChronoUnit.DAYS))
				.filter(d -> !ignore.contains(d.getDayOfWeek())).count();
	}

	public static List<String> getMonthBetween2Date(String _startDate, String _endDate) {
		List<String> lstMonth = new ArrayList<>();

		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		DateTimeFormatter _formatter = DateTimeFormatter.ofPattern("MM-yyyy");
		LocalDate startDate = LocalDate.parse(_startDate, formatter);
		LocalDate endDate = LocalDate.parse(_endDate, formatter);

		while (startDate.isBefore(endDate) || startDate.equals(endDate)) {
			startDate = LocalDate.of(startDate.getYear(), startDate.getMonthValue(), 1);
			String month = startDate.format(_formatter).toString();
			lstMonth.add(month);
			startDate = startDate.plusMonths(1);
		}
		return lstMonth;
	}

	/**
	 * Get working days of month
	 * 
	 * @param month
	 * @return int
	 * @author: DVNgoc
	 */
	public static int getWokingDaysOfMonth(String month) {
		try {
			LocalDate date = LocalDate.parse(("01-" + month), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			LocalDate startOfMonth = date.withDayOfMonth(1);
			LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());
			int workingDays = 0;
			while (!startOfMonth.isAfter(endOfMonth)) {
				DayOfWeek day = startOfMonth.getDayOfWeek();
				if ((day != DayOfWeek.SATURDAY) && (day != DayOfWeek.SUNDAY)) {
					workingDays++;
				}
				startOfMonth = startOfMonth.plusDays(1);
			}
			if (workingDays <= 0) {
				return -1;
			} else {
				return workingDays;
			}
		} catch (DateTimeParseException e) {
			return -1;
		}
	}

	public static String getCurrentDate() {
		return LocalDate.now().toString();
	}

	public static void isMonthValid(String month) throws ParseException {
		DateFormat formater = new SimpleDateFormat(PATTERN_DATE_MMYYYY);
		formater.parse(month);
	}

	/**
	 * get startdate and enddate of month
	 * 
	 * @param monthYear
	 * @return Map<String,String>
	 * @author: Hoai-Nam
	 */
	public static Map<String, String> getDayOfMonth(String monthYear) {
		Map<String, String> map = new HashMap<String, String>();
		String[] splitDate = monthYear.split("-");
		String startDate = null, endDate = null;
		LocalDate initial = LocalDate.of(Integer.parseInt(splitDate[1]), Integer.parseInt(splitDate[0]), 1);
		startDate = initial.withDayOfMonth(1).toString();
		endDate = initial.withDayOfMonth(initial.lengthOfMonth()).toString();
		map.put("START_DATE_KEY", startDate);
		map.put("END_DATE_KEY", endDate);
		return map;
	}

	/**
	 * Format string MM-YYYY to date YYYY-MM-DD
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 *             String
	 * @author: Hoai-Nam
	 */
	public static String formatDateString(String date) {
		Date dateFomart = null;
		try {
			dateFomart = stringToDate(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateToString(dateFomart);
	}

	public static Date stringToDate(String string) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("MM-yyyy");
		return dateFormat.parse(string);
	}

	public static String dateToString(Date date) {
		DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
		return outputFormatter.format(date);
	}

	/**
	 * Check user role has access api request
	 * 
	 * @param role
	 * @return true if has role else false
	 */
	public static boolean hasRole(Role role) {
		Collection<? extends GrantedAuthority> auths = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		return auths == null ? false
				: auths.stream().anyMatch(ga -> ga.getAuthority().equals(role.getName())
						|| ga.getAuthority().equals(Role.ADMIN.getName()));

	}

	/**
	 * get Total WorkingDay.
	 * 
	 * @param planMonth
	 * @return int
	 * @author: Hoai-Nam
	 */
	public static int getTotalWorkingDay(String planMonth) {
		Map<String, String> getDay = getDayOfMonth(planMonth);
		Date startDate = null;
		Date endDate = null;
		int total = 0;
		try {
			startDate = new SimpleDateFormat("yyyy-MM-dd").parse(getDay.get(CustomValueUtil.START_DATE_KEY));
			endDate = new SimpleDateFormat("yyyy-MM-dd").parse(getDay.get(CustomValueUtil.END_DATE_KEY));
			total = (int) getTotalWorkingDaysBetweenDate(startDate, endDate);
		} catch (ParseException e) {
			return 0;
		}
		return total;
	}

	/**
	 *
	 * @return String current date with format {@link #PATTERN_DATE_YYYYMMDD}
	 */
	public static String getDateNow() {
		return new SimpleDateFormat(PATTERN_DATE_YYYYMMDD).format(new Date());
	}

	public static String converToJson(int userId, String fromDate, String toDate, float manDay, String role,
			int projectId) {
		String jsonPlan = "{\n" + "\"userPlanId\"" + " :" + "\"" + "" + "\"" + "," + "\n\"fromDate\"" + " :" + "\""
				+ fromDate + "\"" + "," + "\n\"manDay\"" + " :" + "\"" + manDay + "\"" + "," + "\n\"projectId\"" + " :"
				+ "\"" + projectId + "\"" + "," + "\n\"toDate\"" + " :" + "\"" + toDate + "\"" + "," + "\n\"userId\""
				+ " :" + "\"" + userId + "\"" + "," + "\n\"role\"" + " :" + "\"" + role + "\"" + "}";
		return jsonPlan;
	}
	public static List<String> getMonth2Date(String _startDate, String _endDate) {
		List<String> lstMonth = new ArrayList<>();

		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		DateTimeFormatter _formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startDate = LocalDate.parse(_startDate, formatter);
		LocalDate endDate = LocalDate.parse(_endDate, formatter);

		while (startDate.isBefore(endDate) || startDate.equals(endDate)) {
			startDate = LocalDate.of(startDate.getYear(), startDate.getMonthValue(), 1);
			String month = startDate.format(_formatter).toString();
			lstMonth.add(month);
			startDate = startDate.plusMonths(1);
		}
		return lstMonth;
	}
	
	public static Map<String, String> getStartEndDayOfMonth(String monthYear) {
		Map<String, String> map = new HashMap<String, String>();
		String[] splitDate = monthYear.split("-");
		String startDate = null, endDate = null;
		LocalDate initial = LocalDate.of(Integer.parseInt(splitDate[0]), Integer.parseInt(splitDate[1]), 1);
		startDate = initial.withDayOfMonth(1).toString();
		endDate = initial.withDayOfMonth(initial.lengthOfMonth()).toString();
		map.put("START_DATE_KEY", startDate);
		map.put("END_DATE_KEY", endDate);
		return map;
	}

	public static LocalDate dateToLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	public static Date getDateNow(String date) throws ParseException{
		return new SimpleDateFormat(PATTERN_DATE_YYYYMMDD).parse(date);
	}
	
	/**
	 * Get delivery unit from DUL group
	 * @param group
	 * @return String 
	 * @author: NVKhoa
	 */
	public static String getUnitFromDUL(String group) {
		return group.substring(3, group.length());
	}
}
