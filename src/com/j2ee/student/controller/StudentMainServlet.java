package com.j2ee.student.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyStore.PrivateKeyEntry;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.j2ee.student.entity.Banji;
import com.j2ee.student.entity.Student;
import com.j2ee.student.service.IStudentService;
import com.j2ee.student.service.impl.StudentServiceImpl;
import com.j2ee.student.util.Constant;
import com.j2ee.student.util.JDBCUtil;
import com.j2ee.student.vo.PageBean;
import com.j2ee.student.vo.StudentSearchCondition;

public class StudentMainServlet extends BaseServlet {
	private IStudentService studentService = new StudentServiceImpl();
	//private IBanjiService banjiService = new BanjiServiceImpl();
	
	private void getStudentAdd(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		//List<Banji> list = banjiService.findAll();
		List<Banji> list = new ArrayList<Banji>();
		Banji banji1 = new Banji(1, "java1707");
		list.add(banji1);
		Banji banji2 = new Banji(2, "java1711");
		list.add(banji2);
		req.setAttribute("list", list);
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
		resp.sendRedirect(req.getContextPath() + "/student?method=searchByCondition");
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

	private void deleteById(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String idStr = req.getParameter("id");
		int id = Integer.parseInt(idStr);
		boolean result = studentService.deleteById(id);
		if (result) {
			System.out.println("删除成功");
		} else {
			System.out.println("删除失败");
		}
		//resp.sendRedirect("/Java1711Web/findAll.do");
		resp.sendRedirect(req.getContextPath() + "/student?method=searchByCondition");
	}

	private void findByName(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		List<Student> list = studentService.findByName(name);
		req.setAttribute("list", list);
		// 存储转发是给服务器看的，已经在tomcat下面的/Java1711Web下面所以这个"/"代表/Java1711Web
		//req.getRequestDispatcher("/showInfo.do").forward(req, resp);
		req.getRequestDispatcher("/WEB-INF/jsp/student_list.jsp").forward(req, resp);
	}

	private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		// 1.接收参数
		String name = req.getParameter("name");
		System.out.println("name:" + name);
		String age = req.getParameter("age");
		String gender = req.getParameter("gender");
		String address = req.getParameter("address");
		String banjiId = req.getParameter("banjiId");
		System.out.println(banjiId);
		Banji banji = new Banji();
		banji.setId(Integer.parseInt(banjiId));
		Student student = new Student(name, Integer.parseInt(age), gender, address, new Date(), new Date());
		student.setBanji(banji);
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
		resp.sendRedirect(req.getContextPath() + "/student?method=searchByCondition");
	}

	private void searchByCondition(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("StudentMainServlet.searchByCondition()");
		//1.接收并封装数据
		String pageNoStr = req.getParameter("pageNo");
		String pageSizeStr = req.getParameter("pageSize");
		int pageNo = 1;//默认取第一页的数据
		if (pageNoStr != null && !"".equals(pageNoStr)) {
			pageNo = Integer.parseInt(pageNoStr);
		}
		int pageSize = Constant.DEFAULT_PAGE_SIZE;;//默认每一页条数
		if (pageSizeStr != null && !"".equals(pageSizeStr)) {
			pageSize = Integer.parseInt(pageSizeStr);
		}
		String name = req.getParameter("name");
		String age = req.getParameter("age");
		String gender = req.getParameter("gender");
		StudentSearchCondition studentSearchCondition = new StudentSearchCondition(pageNo, pageSize, name, age, gender);
		//2.调用service层的业务逻辑
		PageBean<Student> pageBean = studentService.searchByCondition(studentSearchCondition);
		//3.将数据封装到域对象中，转发到student_list.jsp页面展示数据
		req.setAttribute("pageBean", pageBean);
		//在界面回显搜索条件
		req.setAttribute("searchCondition", studentSearchCondition);
		req.getRequestDispatcher("/WEB-INF/jsp/student_list.jsp").forward(req, resp);
	}
	
	public void checkName(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String name = request.getParameter("name");
		boolean isExist = studentService.checkName(name);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write("{\"isExist\":"+isExist+"}");
	}
	
	public void deleteAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String[] ids = request.getParameterValues("selectIds");
		for (String string : ids) {
			System.out.println(string);
		}
		studentService.deleteAll(ids);
		
		response.sendRedirect(request.getContextPath() + "/student?method=searchByCondition");
	}
	
	
	
	
	
}
