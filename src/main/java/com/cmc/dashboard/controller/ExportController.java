package com.cmc.dashboard.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmc.dashboard.service.ProjectService;
import com.cmc.dashboard.service.UserPlanService;

@CrossOrigin("*")
@RequestMapping(value = "/api")
@Controller
public class ExportController {

	@Autowired
	ProjectService projectService;
	@Autowired
	UserPlanService userPlanService;

	/**
	 * Create URI export project bill able
	 * 
	 * @param response
	 * @param projectId
	 *            void
	 * @author: NVKhoa
	 */
	@RequestMapping(value = "/billable/export", method = RequestMethod.GET)
	public @ResponseBody void exportBillable(HttpServletResponse response, @RequestParam("projectId") int projectId) {
		String fileName;
		try {
			fileName = projectService.exportProjectBillable(projectId);
			this.outputFile(fileName, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create URI export project css
	 * 
	 * @param response
	 * @param projectId
	 *            void
	 * @author: NVKhoa
	 */
	@RequestMapping(value = "/css/export")
	public void exportCss(HttpServletResponse response, @RequestParam("projectId") int projectId) {
		String fileName;
		try {
			fileName = projectService.exportProjectCss(projectId);
			this.outputFile(fileName, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Create file output stream in order to download
	 * 
	 * @param fileName
	 * @param response
	 *            void
	 * @author: NVKhoa
	 */
	private void outputFile(String fileName, HttpServletResponse response) {
		try {
			File file = new File(fileName);
			InputStream in;
			in = new FileInputStream(file);
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
			response.setHeader("Content-Length", String.valueOf(file.length()));
			FileCopyUtils.copy(in, response.getOutputStream());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/resource-allocate/export", method = RequestMethod.GET)
	@ResponseBody
	public void exportResourceAllocate(@RequestParam("projectId") int projectId, HttpServletResponse response)
			throws FileNotFoundException {

		String fileName;
		try {
			fileName = userPlanService.exportResourceAllocate(projectId);
			this.outputFile(fileName, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
