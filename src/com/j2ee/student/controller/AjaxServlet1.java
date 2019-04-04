package com.j2ee.student.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxServlet1 extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	  @Override
	   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	      String name = req.getParameter("name");
	      System.out.println(name);
	      
	      resp.setContentType("text/html;charset=utf-8");
	      resp.getWriter().write("李四");;
	   }
	  
	  @Override
	   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	      String name = req.getParameter("name");
	      String age = req.getParameter("age");
	      System.out.println(name + ", " + age);
	      
	      resp.setContentType("text/html;charset=utf-8");
	      resp.getWriter().write("{\"name\":\"lisi\",\"age\":33}");
	   }
}
