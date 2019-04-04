package com.situ.student.service;

import java.util.List;

import com.situ.student.entity.Student;
import com.situ.student.vo.PageBean;
import com.situ.student.vo.StudentSearchCondition;

public interface IStudentService {

	List<Student> findAll();

	/**
	 * 添加学生
	 * @param student
	 * @return Constant.ADD_SUCCESS=1 添加成功
	 * 		   Constant.ADD_FAIL=2     添加数据库失败
	 * 		   Constant.ADD_NAME_REPEAT=3     名字重复
	 */
	int add(Student student);

	List<Student> showStudentAndBanjiInfo();

	List<Student> findByName(String name);

	boolean deleteById(int id);

	Student findById(int id);

	boolean update(Student student);

	PageBean searchByCondition(StudentSearchCondition studentSearchCondition);

	PageBean getPageBean(int pageNo, int pageSize);

	boolean checkName(String name);

	boolean deleteAll(String[] ids);

}
