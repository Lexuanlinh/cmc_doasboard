package com.cmc.dashboard.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.cmc.dashboard.dto.BillableDTO;
import com.cmc.dashboard.dto.ListBillableDTO;
import com.cmc.dashboard.dto.ProjectCssUtilizationDto;
import com.cmc.dashboard.dto.ProjectDTO;
import com.cmc.dashboard.dto.RestResponse;
import com.cmc.dashboard.model.ProjectBillable;
import com.cmc.dashboard.model.ProjectCss;
import com.cmc.dashboard.qms.model.CustomValue;
import com.cmc.dashboard.qms.model.MemberQms;
import com.cmc.dashboard.qms.model.ProjectQms;
import com.cmc.dashboard.qms.model.QmsUser;
import com.cmc.dashboard.qms.repository.CustomValueRepository;
import com.cmc.dashboard.qms.repository.MemberQmsRepository;
import com.cmc.dashboard.qms.repository.ProjectQmsRepository;
import com.cmc.dashboard.qms.repository.UserQmsRepository;
import com.cmc.dashboard.repository.ProjectBillableRepository;
import com.cmc.dashboard.repository.ProjectCssRepository;
import com.cmc.dashboard.repository.UserPlanDetailRepository;
import com.cmc.dashboard.repository.UserPlanRepository;
import com.cmc.dashboard.repository.UserRepository;
import com.cmc.dashboard.util.CustomValueUtil;
import com.cmc.dashboard.util.MessageUtil;
import com.cmc.dashboard.util.MethodUtil;
import com.cmc.dashboard.util.RegularExpressions;
import com.cmc.dashboard.util.WriteCsv;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectBillableRepository projectBillableRepository;

	@Autowired
	ProjectQmsRepository projectQmsRepository;

	@Autowired
	ProjectCssRepository projectCssRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserPlanRepository userPlanRepository;

	@Autowired
	CustomValueRepository customValueRepository;

	@Autowired
	UserQmsRepository userQmsRepository;

	@Autowired
	MemberQmsRepository memberQmsRepository;
	
	@Autowired
	UserPlanDetailRepository userPlanDetailRepository;

//	@Autowired
//	HttpSession session;

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ProjectService#getListProjectCssByProjectId(int)
	 */
	@Override
	public List<ProjectCssUtilizationDto> getListProjectCssByProjectId(int projectId) throws SQLException {
		return projectCssRepository.getListCssbyProjectId(projectId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ProjectService#saveProjectCss(com.cmc.dashboard.
	 * model.ProjectCss)
	 */
	@Override
	public RestResponse createProjectCss(String json) throws SQLException {

		// Get info from Json
		Gson gson = new Gson();
		TypeToken<ProjectCss> token = new TypeToken<ProjectCss>() {
		};
		ProjectCss projectCss = new ProjectCss();
		List<ProjectCssUtilizationDto> listProjectCss = new ArrayList<ProjectCssUtilizationDto>();

		try {
			projectCss = gson.fromJson(json, token.getType());
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_UNFORMAT, MessageUtil.DATA_UNFORMAT);
		} catch (JsonParseException e) {
			e.printStackTrace();
			return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_UNFORMAT, MessageUtil.DATA_UNFORMAT);
		}

		// Set data for object
		ProjectQms projectQms = projectQmsRepository.findOne(projectCss.getProjectId());
		listProjectCss = projectCssRepository.getListCssbyProjectId(projectCss.getProjectId());

		String projectCssDeliveryUnit = projectQmsRepository.getProjectDeliveryUnitById(projectCss.getProjectId());

		String startDateOfProjectStr = projectQmsRepository.getProjectStartDateById(projectCss.getProjectId());
		String endDateOfProjectStr = projectQmsRepository.getProjectEndDateById(projectCss.getProjectId());

		SimpleDateFormat formatter = new SimpleDateFormat(CustomValueUtil.DATE_FORMAT);
		Date startDate, endDate;
		try {

			startDate = formatter.parse(startDateOfProjectStr);
			endDate = formatter.parse(endDateOfProjectStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_INVALID, listProjectCss);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_INVALID, listProjectCss);
		}
		try {
			projectCss.setProjectName(projectQms.getProjectName());
			projectCss.setDeliveryUnit(projectCssDeliveryUnit);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_INVALID, listProjectCss);
		}
		// set attribute 'time' for object
		int maxTime = 0;
		for (ProjectCssUtilizationDto dto : listProjectCss) {
			if (dto.getTime() > maxTime) {
				maxTime = dto.getTime();
			}
		}
		projectCss.setTime(maxTime + 1);

		// validate start_date and end_date and add ProjectCss to DB
		if (maxTime == 0) {
			if ((!projectCss.getStartDate().before(startDate)) && (!projectCss.getEndDate().after(endDate))
					&& (projectCss.getTime() <= 100) && (projectCss.getTime() > 0)) {
				projectCssRepository.save(projectCss);
				return RestResponse.success(projectCssRepository.getListCssbyProjectId(projectCss.getProjectId()));
			} else {
				return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_INVALID, listProjectCss);
			}
		} else

		{
			ProjectCss lastProjectCss = projectCssRepository.getLastestProjectCssByProjectId(maxTime,
					projectQms.getId());
			if (projectCss.getStartDate().before(lastProjectCss.getEndDate())
					|| (projectCss.getEndDate().after(endDate))) {
				return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_INVALID, listProjectCss);
			}

			projectCssRepository.save(projectCss);
			return RestResponse.success(projectCssRepository.getListCssbyProjectId(projectCss.getProjectId()));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ProjectService#updateProjectCss(java.util.List)
	 */
	@Override
	public RestResponse updateProjectCss(List<ProjectCss> listProjectCss) throws SQLException {
		if (listProjectCss == null || listProjectCss.isEmpty()) {
			return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_EMPTY, MessageUtil.DATA_EMPTY);
		}
		List<ProjectCssUtilizationDto> listCSS = projectCssRepository
				.getListCssbyProjectId(listProjectCss.get(0).getProjectId());

		// get StartDate and EndDate of Project
		int projectId = 0;
		projectId = listProjectCss.get(0).getProjectId();
		String startDateStr = projectQmsRepository.getProjectStartDateById(projectId);
		String endDateStr = projectQmsRepository.getProjectEndDateById(projectId);

		SimpleDateFormat formatter = new SimpleDateFormat(CustomValueUtil.DATE_FORMAT);
		Date startDate, endDate;
		try {
			startDate = formatter.parse(startDateStr);
			endDate = formatter.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_INVALID, listCSS);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_INVALID, listCSS);
		}

		try {
			// validate first and last ProjectCss
			if (listProjectCss.get(0).getStartDate().before(startDate)
					|| listProjectCss.get(listProjectCss.size() - 1).getEndDate().after(endDate)) {
				return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_INVALID, listCSS);
			}
			// validate all ProjectCss
			int i = 0;
			ProjectCss currentCss, nextCss;
			while (i < listProjectCss.size() - 1) {
				currentCss = listProjectCss.get(i);
				nextCss = listProjectCss.get(i + 1);
				if (currentCss.getEndDate().after(nextCss.getStartDate())
						|| currentCss.getStartDate().after(currentCss.getEndDate())) {
					return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_INVALID, listCSS);
				}
				i++;
			}
			// validate time
			for (ProjectCss projectCss : listProjectCss) {
				if (projectCss.getTime() > 100 || projectCss.getTime() <= 0 || projectCss.getScoreValue() > 100
						|| projectCss.getScoreValue() < 0) {
					return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_INVALID, listCSS);
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_INVALID, listCSS);
		}
		try {
			projectCssRepository.save(listProjectCss);
			return RestResponse.success(projectCssRepository.getListCssbyProjectId(projectId));
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_INVALID, listCSS);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return RestResponse.errorWithData(HttpStatus.OK, MessageUtil.DATA_INVALID, listCSS);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectService#deleteProjectCss(int)
	 */
	@Override
	public String deleteProjectCss(int projectCssId) throws SQLException {
		try {
			ProjectCss projectCss = projectCssRepository.getOne(projectCssId);
			if (projectCss.getProjectCssId() == projectCssId) {
				projectCssRepository.delete(projectCss);
			} else {
				return MessageUtil.ERROR;
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return (MessageUtil.ERROR);
		} catch (EntityNotFoundException e1) {
			return (MessageUtil.ERROR);
		}

		return MessageUtil.SUCCESS;
	}

	@Override
	public Page<ProjectDTO> getProjectListByUser(int userId, int page, int size) throws SQLException {

		Page<ProjectDTO> pageDto = new PageImpl<>(new ArrayList<>(), null, 0);
		List<ProjectDTO> lstProjectDto = new ArrayList<ProjectDTO>();
		try {
			/*
			 * Get group info of user from dashboard db
			 * String groupName = userRepository.getGroupByUser(userId).get().getGroupName();
			 */ 
			
			// Get group info from qms db
			String groupName = userQmsRepository.findGroup(userId).get().getLastname();
			Pageable pageable = new PageRequest(page - 1, size);
			if (!groupName.isEmpty() && groupName != null) {
				Page<ProjectQms> projects = this.getProjectByDu(groupName, userId, pageable);
				lstProjectDto = this.createProjectList(projects.getContent());
				pageDto = new PageImpl<>(lstProjectDto, pageable, projects.getTotalElements());
			}

			return pageDto;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return pageDto;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return pageDto;
		}
	}

	/**
	 * Get list qms project by duName or user identify
	 * 
	 * @param groupName
	 * @param userId
	 * @return List<ProjectQms>
	 * @author: nvkhoa
	 */
	private Page<ProjectQms> getProjectByDu(String groupName, int userId, Pageable pageRequest) throws SQLException {

		Page<ProjectQms> projects = new PageImpl<>(new ArrayList<>());
		LocalDate date = LocalDate.now();
		Map<String, String> getDayOfDate = MethodUtil.getDayOfDate(date.getMonthValue(), date.getYear());
		String startDate = getDayOfDate.get(CustomValueUtil.START_DATE_KEY);
		String endDate = getDayOfDate.get(CustomValueUtil.END_DATE_KEY);
		try {
			if (groupName.contains(CustomValueUtil.PROJECT_MANAGER)) {
				
				projects = projectQmsRepository.getProjectListByUser(userId, startDate, endDate, pageRequest);
				
			} else if (groupName.contains(CustomValueUtil.QA) || groupName.contains(CustomValueUtil.BOD)) {
				
				projects = projectQmsRepository.getAllProject(startDate, endDate, pageRequest);
				
			} else if (groupName.contains(CustomValueUtil.DU_LEAD) || groupName.contains(CustomValueUtil.GROUP_RRC)) {
				
				String duName = MethodUtil.getUnitFromDUL(groupName);
				projects = projectQmsRepository.getProjectByDeliveryUnit(duName, startDate, endDate, pageRequest);
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		return projects;
	}

	/**
	 * Create list Project DTO object from list QMS project
	 * 
	 * @param projects
	 * @return List<ProjectDTO>
	 * @author: nvkhoa
	 */
	private List<ProjectDTO> createProjectList(List<ProjectQms> projects) throws SQLException, NullPointerException {

		List<ProjectDTO> projectsDto = new ArrayList<ProjectDTO>();

		List<CustomValue> customValues = customValueRepository.getAllValueProject();
		List<QmsUser> users = userQmsRepository.getUserIsPm();
		List<MemberQms> members = memberQmsRepository.getAllMemberRolePM();

		ProjectDTO projectDto;
		for (ProjectQms project : projects) {
			projectDto = new ProjectDTO();

			projectDto.setProjectId(project.getId());

			projectDto.setProjectName(project.getProjectName());
			
			projectDto.setStatus(project.getStatus());

			projectDto.setProjectManager(users, members);

			projectDto.setCustomValue(customValues);

			projectsDto.add(projectDto);
		}

		return projectsDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectService#getBillableByProject(int)
	 */
	@Override
	public List<BillableDTO> getBillableByProject(int projectId) throws SQLException {
		List<BillableDTO> lstBillable = new ArrayList<>();
		try {
			List<CustomValue> customValues = customValueRepository.findByCustomizeId(projectId).get();
			ProjectQms project = Optional.ofNullable(projectQmsRepository.findOne(projectId)).get();
			
			Set<String> projectManager = new HashSet<>(Optional.ofNullable(projectQmsRepository.getProjectManagerById(projectId)).get());

			ProjectDTO projectDto = new ProjectDTO(projectId, project.getProjectName(), projectManager);

			projectDto.setCustomValue(customValues);

			this.addListOfBillable(projectDto);
			List<Object> objects = projectBillableRepository.getListBillable(projectId).get();
			BillableDTO billableDto;
			
			for (Object object : objects) {
				Object[] _object = (Object[]) object;
				billableDto = new BillableDTO();
				billableDto.setProjectBillableId(Integer.parseInt(_object[0].toString()));
				billableDto.setBillableMonth(_object[1].toString());
				billableDto.setBillableValue(_object[2] == null? 0 : Float.parseFloat(_object[2].toString()));
				billableDto.setIssueCode(_object[3] == null ? null : _object[3].toString());
				billableDto.setManMonth(_object[4] == null ? 0 : Double.parseDouble(_object[4].toString()));
				lstBillable.add(billableDto);
			}
			return lstBillable;
		} catch (NullPointerException e) {
			e.printStackTrace();
			return lstBillable;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			return lstBillable;
		}

	}

	/**
	 * Create list of project billable
	 * 
	 * @param projectDto
	 * @return
	 * @throws SQLException
	 *             List<ProjectBillable>
	 * @author: NVKhoa
	 */
	private void addListOfBillable(ProjectDTO projectDto) throws SQLException {
		List<ProjectBillable> lstBillable = new ArrayList<ProjectBillable>();

		List<String> lstMonth = MethodUtil.getMonthBetween2Date(projectDto.getStartDate(), projectDto.getEndDate());
		ProjectBillable projectBillable;
		for (String month : lstMonth) {
			projectBillable = new ProjectBillable();
			projectBillable.setBillableMonth(month);
			projectBillable.setProjectId(projectDto.getProjectId());
			projectBillable.setProjectName(projectDto.getProjectName());
			projectBillable.setPmName(projectDto.toStringManager());
			if (projectDto.getDeliveryUnit() == null) {
				projectBillable.setDeliveryUnit("");
			} else {
				projectBillable.setDeliveryUnit(projectDto.getDeliveryUnit());
			}
			projectBillable.setStartDate(MethodUtil.convertStringToDate(projectDto.getStartDate()));
			projectBillable.setEndDate(MethodUtil.convertStringToDate(projectDto.getEndDate()));
			lstBillable.add(projectBillable);
		}
		this.getValueOfBillable(lstBillable, projectDto.getProjectId());
	}

	/**
	 * Synchronize project billable data between two database
	 * 
	 * @param lstBillable
	 * @param projectId
	 * @return
	 * @throws SQLException
	 *             List<ProjectBillable>
	 * @author: NVKhoa
	 */
	private void getValueOfBillable(List<ProjectBillable> lstBillable, int projectId) throws SQLException, NullPointerException {
		List<ProjectBillable> billables = projectBillableRepository.getBillableByProject(projectId);

		List<ProjectBillable> list = new ArrayList<>();
		for (ProjectBillable element : lstBillable) {
			boolean isExist = false;
			for (ProjectBillable item : billables) {
				if (element.getBillableMonth().equals(item.getBillableMonth())) {
					item.setStartDate(element.getStartDate());
					item.setEndDate(element.getEndDate());
					item.setPmName(element.getPmName());
					item.setProjectName(element.getProjectName());
					isExist = true;
				}
			}
			if (!isExist) {
				list.add(element);
			}
		}
		billables.addAll(list);
		projectBillableRepository.save(billables);

		for (ProjectBillable element : billables) {
			boolean isExist = false;
			for (ProjectBillable item : lstBillable) {
				if (item.getBillableMonth().equals(element.getBillableMonth())) {
					isExist = true;
				}
			}
			if (!isExist) {
				projectBillableRepository.delete(element);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmc.dashboard.service.ProjectService#updateProjectBillable(java.lang.
	 * String)
	 */
	@Override
	public ListBillableDTO updateProjectBillable(String json) {
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(json);
		JsonArray trade = element.getAsJsonArray();
		List<ProjectBillable> projectBillables = new ArrayList<ProjectBillable>();
		List<BillableDTO> listBillable = new ArrayList<>();
		try {
			ProjectBillable projectBillable;
			for (JsonElement item : trade) {
				JsonObject object = item.getAsJsonObject();

				int pBillableId = Integer.parseInt(MethodUtil.getStringValue(object, "projectBillableId"));

				projectBillable = projectBillableRepository.findOne(pBillableId);
				int billableValue = Integer.parseInt(MethodUtil.getStringValue(object, "billableValue"));
				if (CustomValueUtil.BILLABLE_MAX_VALUE < billableValue
						|| billableValue < CustomValueUtil.BILLABLE_MIN_VALUE) {
					return new ListBillableDTO(MessageUtil.DATA_INVALID, listBillable);
				}
				projectBillable.setBillableValue(billableValue);
				if (MethodUtil.getStringValue(object, "issueCode").length() == 0) {
					return new ListBillableDTO(MessageUtil.DATA_EMPTY, listBillable);
				}
				if (!MethodUtil.validateLoginParams(MethodUtil.getStringValue(object, "issueCode"),
						RegularExpressions.STRING_PATTERN)) {
					return new ListBillableDTO(MessageUtil.DATA_UNFORMAT, listBillable);
				}
				projectBillable.setIssueCode(MethodUtil.getStringValue(object, "issueCode"));
				projectBillable.setUpdatedOn(new Date());

				projectBillables.add(projectBillable);

			}
			projectBillableRepository.save(projectBillables);
			listBillable = this.getBillableByProject(projectBillables.get(0).getProjectId());
			return new ListBillableDTO(MessageUtil.UPDATE_SUCCESS, listBillable);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return new ListBillableDTO(MessageUtil.DATA_NOT_EXIST, listBillable);

		} catch (NumberFormatException e) {

			e.printStackTrace();
			return new ListBillableDTO(MessageUtil.DATA_INVALID, listBillable);

		} catch (DataAccessException e) {

			return new ListBillableDTO(MessageUtil.UPDATE_ERROR, listBillable);
		} catch (SQLException e) {
			return new ListBillableDTO(MessageUtil.UPDATE_ERROR, listBillable);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectService#exportProjectBillable(int)
	 */
	@Override
	public String exportProjectBillable(int projectId) throws SQLException {
		LocalDate today = LocalDate.now();
		String fileName = CustomValueUtil.FILE_NAME_BILLABLE+ Integer.toString(projectId)+ "_" + today + ".csv";
		String[] header = CustomValueUtil.BILLABLE_HEADER;
		List<String> _header = Arrays.asList(header);
		try {
			List<Object> objects = projectBillableRepository.getListBillable(projectId).get();
			List<ArrayList<String>> projects = new ArrayList<ArrayList<String>>();
			ArrayList<String> billable;
			int i = 1;
			for (Object object : objects) {
				Object[] _object = (Object[]) object;
				billable = new ArrayList<>();
				billable.add(Integer.toString(i));
				billable.add(_object[1].toString());
				billable.add(_object[4] == null ? "0" :_object[4].toString());		
				billable.add(_object[3] == null ? null : _object[3].toString());
				billable.add(_object[2] == null? Integer.toString(0) : _object[2].toString());
				projects.add(billable);
				i++;
			}
			WriteCsv.writeCsv(fileName, _header, projects);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cmc.dashboard.service.ProjectService#exportProjectCss(int)
	 */
	@Override
	public String exportProjectCss(int projectId) throws SQLException {
		LocalDate today = LocalDate.now();
		String fileName = CustomValueUtil.FILE_NAME_CSS+ Integer.toString(projectId) + "_" + today + ".csv";
		String[] header = CustomValueUtil.CSS_HEADER;
		List<String> _header = Arrays.asList(header);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			List<ProjectCssUtilizationDto> list = this.getListProjectCssByProjectId(projectId);
			List<ArrayList<String>> projects = new ArrayList<ArrayList<String>>();
			ArrayList<String> projectCss;
			for (int i = 0; i < list.size(); i++) {
				projectCss = new ArrayList<String>();
				projectCss.add(Integer.toString(i+1));
				projectCss.add(Integer.toString(list.get(i).getTime()));
				projectCss.add(formatter.format(list.get(i).getStartDate()));
				projectCss.add(formatter.format(list.get(i).getEndDate()));
				projectCss.add(Float.toString(list.get(i).getScoreValue()));
				projects.add(projectCss);
			}

			WriteCsv.writeCsv(fileName, _header, projects);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

}
