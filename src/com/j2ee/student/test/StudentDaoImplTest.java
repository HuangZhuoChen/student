package com.j2ee.student.test;

import java.util.Date;
import java.util.List;

import javax.swing.text.html.FormSubmitEvent;

import org.junit.Before;
import org.junit.Test;

import com.j2ee.student.dao.IStudentDao;
import com.j2ee.student.dao.impl.StudentDaoImpl;
import com.j2ee.student.entity.Student;

public class StudentDaoImplTest {
	private IStudentDao studentDao;
	
	@Before
	public void before() {
		studentDao = new StudentDaoImpl();
	}

	@Test
	public void testAdd() {
		IStudentDao studentDao = new StudentDaoImpl();
		Student student = new Student("xxx", 20, "男", "青岛", new Date(), new Date());
		int result = studentDao.add(student);
		if (result > 0) {
			System.out.println("添加成功");
		} else {
			System.out.println("添加失败");
		}
	}
	
	@Test
	public void testFindAll() {
		List<Student> list = studentDao.findAll();
		for (Student student : list) {
			System.out.println(student);
		}
	}
	
}
