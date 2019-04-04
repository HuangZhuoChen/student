package com.j2ee.student.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.j2ee.student.service.IManagerService;
import com.j2ee.student.service.impl.ManagerServiceImpl;

public class ManagerMainServlet extends BaseServlet{
	private IManagerService managerService = new ManagerServiceImpl();

	public void getManagerPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Map<String, Object>> list = managerService.findAll();
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/jsp/manager_list.jsp").forward(request, response);
	}
}
