package com.situ.student.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.situ.student.entity.Student;
import com.situ.student.service.IStudentService;
import com.situ.student.service.impl.StudentServiceImpl;
import com.situ.student.util.Constant;
import com.situ.student.util.JDBCUtil;

public class BanjiMainServlet extends BaseServlet {
	private IStudentService studentService = new StudentServiceImpl();


	private void getStudentAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		req.getRequestDispatcher("/WEB-INF/jsp/student_add.jsp").forward(req, resp);
	}

	private void update(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String idStr = req.getParameter("id");
		String name = req.getParameter("name");
		String age = req.getParameter("age");
		String gender = req.getParameter("gender");
		String address = req.getParameter("address");
		Student student = new Student(Integer.parseInt(idStr), name, Integer.parseInt(age), gender, address, new Date());
		if (studentService.update(student)) {
			System.out.println("更新成功");
		} else {
			System.out.println("更新失败");
		}
		resp.sendRedirect(req.getContextPath() + "/findAll.do");
	}

	private void toUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1.获得要修改学生的id
		String idStr = req.getParameter("id");
		int id = Integer.parseInt(idStr);
		//2.把对应id的学生查出来
		Student student = studentService.findById(id);
		//3.把查出来学生放到reques域对象中
		req.setAttribute("student", student);
		//4.转发到edit_student.jsp
		req.getRequestDispatcher("/jsp/student_edit.jsp").forward(req, resp);
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String idStr = req.getParameter("id");
		int id = Integer.parseInt(idStr);
		boolean result = studentService.deleteById(id);
		if (result) {
			System.out.println("删除成功");
		} else {
			System.out.println("删除失败");
		}
		//resp.sendRedirect("/Java1711Web/findAll.do");
		resp.sendRedirect(req.getContextPath() + "/findAll.do");
	}

	private void findByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		List<Student> list = studentService.findByName(name);
		req.setAttribute("list", list);
		// 存储转发是给服务器看的，已经在tomcat下面的/Java1711Web下面所以这个"/"代表/Java1711Web
		//req.getRequestDispatcher("/showInfo.do").forward(req, resp);
		req.getRequestDispatcher("/jsp/student_list.jsp").forward(req, resp);
	}

	private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 1.接收参数
		String name = req.getParameter("name");
		System.out.println("name:" + name);
		byte[] bytes = name.getBytes("iso8859-1");
		String newName = new String(bytes, "utf-8");
		System.out.println("newName: " + newName);
		String age = req.getParameter("age");
		String gender = req.getParameter("gender");
		String address = req.getParameter("address");
		Student student = new Student(name, Integer.parseInt(age), gender, address, new Date(), new Date());
		System.out.println(student);
		// 2.业务处理
		int result = studentService.add(student);
		// 3.输出响应 Magic number
		resp.setContentType("text/html;charset=utf-8");
		/*PrintWriter printWriter = resp.getWriter();
		if (result == Constant.ADD_SUCCESS) {
			printWriter.println("Add Success");
		} else if (result == Constant.ADD_NAME_REPEAT) {
			printWriter.println("Name Repeat");
		} else {
			printWriter.println("Add Fail");
		}
		printWriter.close();*/

		//重定向是给浏览器看的，所以"/"代表的tomacat的目录
		resp.sendRedirect("/Java1711Web/findAll.do");
	}

	private void findAll(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		System.out.println("BanjiMainServlet.findAll()");
		// 1.接收请求参数，封装成对象
		// 2.调业务层处理
		List<Student> list = studentService.findAll();
		// 3.控制界面的跳转
		req.setAttribute("list", list);
		/*RequestDispatcher requestDispatcher = req.getRequestDispatcher("");
		requestDispatcher.forward(req, resp);*/
		//req.getRequestDispatcher("/showInfo.do").forward(req, resp);
		req.getRequestDispatcher("/WEB-INF/jsp/student_list.jsp").forward(req, resp);
	}

	private void showInfo(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		List<Student> list = (List<Student>) req.getAttribute("list");
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter = resp.getWriter();
		printWriter.println("<a href='/Java1711Web/add_student.html'>添加</a>");
		printWriter.println("<table border='1' cellspacing='0'>");
		printWriter.println("<tr>            ");
		printWriter.println("	<th>编号</th>");
		printWriter.println("	<th>姓名</th>");
		printWriter.println("	<th>年龄</th>");
		printWriter.println("	<th>性别</th>");
		printWriter.println("	<th>地址</th>");
		printWriter.println("</tr>           ");

		for (Student student : list) {
			printWriter.println("<tr>            ");
			printWriter.println("	<td>" + student.getId() + "</td>   ");
			printWriter.println("	<td>" + student.getName() + "</td>");
			printWriter.println("	<td>" + student.getAge() + "</td>  ");
			printWriter.println("	<td>" + student.getGender() + "</td>  ");
			printWriter.println("	<td>" + student.getAddress() + "</td>");
			printWriter.println("</tr>           ");
		}

		printWriter.println("</table>");
	}
}
