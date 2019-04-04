package com.j2ee.student.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.omg.CORBA.TRANSACTION_REQUIRED;

public class BaseServlet extends HttpServlet{
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		//得到Session里面数据
		String userName = (String) session.getAttribute("userName");
		/*if (userName == null) {
			//2.重定向到登录界面
			resp.sendRedirect(req.getContextPath() + "/jsp/login.jsp");
			return;
		}*/
		
		System.out.println(req.getRequestURI());
		System.out.println(req.getContextPath());
		String servletPath = req.getServletPath();
		System.out.println(servletPath);
		//处理post请求乱码
		//req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=utf-8");
	
		// /Java1711WebStudent/student?method=findAll
		// 1.得到get请求中method对应的值即要调用的方法
		String methodName = req.getParameter("method");
		// 2.获得当前被访问对象的字节码对象
		// this.getClass() ：StudentMainServlet.class  BanjiMainServlet.class
		Class clazz = this.getClass();
		// 3.获取当前字节码对象中指定的方法
		//clazz.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
		try {
			Method method = clazz.getDeclaredMethod(methodName, new Class[]{HttpServletRequest.class, HttpServletResponse.class});
			method.setAccessible(true);
			// 4.调用要执行的方法
			//method.invoke(this, req, resp);
			method.invoke(this, new Object[]{req, resp});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
