package com.j2ee.student.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.j2ee.student.dao.IStudentDao;
import com.j2ee.student.entity.Banji;
import com.j2ee.student.entity.Student;
import com.j2ee.student.util.C3P0Util;
import com.j2ee.student.util.JDBCUtil;
import com.j2ee.student.vo.StudentSearchCondition;

public class StudentDaoImpl implements IStudentDao {
	private QueryRunner queryRunner = new QueryRunner(C3P0Util.getDataSource());

	@Override
	public int add(Student student) {
		int count = 0;
		try {
			String sql = "INSERT INTO student(NAME,age,gender,address,banji_id) VALUES(?,?,?,?,?,?);";
			Object[] params = { student.getName(), student.getAge(), student.getGender(), student.getAddress(),
					 student.getBanji().getId()};
			count = queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}

	@Override
	public int delete(Integer id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Student student) {
		int count = 0;
		try {
			String sql = "UPDATE student SET name=?,age=?,gender=?,address=? WHERE id=?;";
			Object[] params = { student.getName(), student.getAge(), student.getGender(), student.getAddress(),
					student.getId()};
			count = queryRunner.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}

	@Override
	public Student findById(Integer searchId) {
		try {
			String sql = "SELECT id,NAME,age,gender,address,birthday,addTime " + "FROM student where id=?;";
			Object[] params = {searchId};
			return queryRunner.query(sql, new BeanHandler<Student>(Student.class), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Student> findAll() {
		List<Student> list = null;
		try {
			String sql = "SELECT id,NAME,age,gender,address,addTime FROM student;";
			list = queryRunner.query(sql, new BeanListHandler<Student>(Student.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public boolean checkName(String name) {
		Student student = null;
		try {
			String sql = "SELECT NAME FROM student WHERE NAME=?;";
			Object[] params = {name};
			student = queryRunner.query(sql, new BeanHandler<Student>(Student.class), name);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		if (student != null) {
			return true; 
		} else {
			return false;
		}
	}

	@Override
	public List<Student> findByName(String searchName) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT id,NAME,age,gender,address,birthday,addTime FROM student where name=?;";
		List<Student> list = new ArrayList<Student>();
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, searchName);
			resultSet = preparedStatement.executeQuery();
			System.out.println(preparedStatement);
			while (resultSet.next()) {
				Integer id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				Integer age = resultSet.getInt("age");
				String address = resultSet.getString("address");
				String gender = resultSet.getString("gender");
				Date birthday = resultSet.getDate("birthday");// java.sql.Date
				Student student = new Student(id, name, age, gender, address, birthday);
				list.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, preparedStatement, resultSet);
		}
		return list;
	}

	@Override
	public List<Student> showStudentAndBanjiInfo() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT s.id,s.name,s.age,b.id,b.name FROM student AS s INNER JOIN banji AS b ON s.banji_id=b.id;";
		List<Student> list = new ArrayList<Student>();
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			System.out.println(preparedStatement);
			while (resultSet.next()) {
				Integer id = resultSet.getInt("s.id");
				String name = resultSet.getString("s.name");
				Integer age = resultSet.getInt("s.age");
				Integer banjiId = resultSet.getInt("b.id");
				String banjiName = resultSet.getString("b.name");
				Banji banji = new Banji(banjiId, banjiName);
				Student student = new Student(id, name, age, null, null, null, banji);
				list.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, preparedStatement, resultSet);
		}
		return list;
	}

	@Override
	public int deleteById(int id) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int count = 0;
		String sql = "DELETE FROM student WHERE id=?;";
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			count = preparedStatement.executeUpdate();
			System.out.println(preparedStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, preparedStatement);
		}

		return count;
	}

	@Override
	public List<Student> searchByCondition(StudentSearchCondition studentSearchCondition) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Student> list = new ArrayList<Student>();
		String sql = "SELECT id,NAME,age,gender,address,birthday FROM student where 1=1 ";
		List<String> conditionList = new ArrayList<String>();
		try {
			connection = JDBCUtil.getConnection();
			//String sql = "select * from student where name like ? and age=? and gender=?";
			if (studentSearchCondition.getName() != null 
					&& !"".equals(studentSearchCondition.getName())) {
				sql += " and name like ? ";
				conditionList.add("%" + studentSearchCondition.getName() + "%");
			}
			if (studentSearchCondition.getAge() != null 
					&& !"".equals(studentSearchCondition.getAge())) {
				sql += " and age = ? ";
				conditionList.add(studentSearchCondition.getAge());
			}
			if (studentSearchCondition.getGender() != null 
					&& !"".equals(studentSearchCondition.getGender())) {
				sql += " and gender = ? ";
				conditionList.add(studentSearchCondition.getGender());
			}
			//sql语句拼接好
			preparedStatement = connection.prepareStatement(sql);
			//给占位符?赋值
			for (int i = 0; i < conditionList.size(); i++) {
				preparedStatement.setObject(i + 1, conditionList.get(i));
			}

			resultSet = preparedStatement.executeQuery();
			System.out.println(preparedStatement);
			while (resultSet.next()) {
				Integer id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				Integer age = resultSet.getInt("age");
				String address = resultSet.getString("address");
				String gender = resultSet.getString("gender");
				Date birthday = resultSet.getDate("birthday");// java.sql.Date
				Student student = new Student(id, name, age, gender, address, birthday);
				list.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public List<Student> findPageBeanList(int offset, int pageSize) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "SELECT id,NAME,age,gender,address,birthday FROM student limit ?,?;";
		List<Student> list = new ArrayList<Student>();
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, offset);
			preparedStatement.setInt(2, pageSize);
			resultSet = preparedStatement.executeQuery();
			System.out.println(preparedStatement);
			while (resultSet.next()) {
				Integer id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				Integer age = resultSet.getInt("age");
				String address = resultSet.getString("address");
				String gender = resultSet.getString("gender");
				Date birthday = resultSet.getDate("birthday");// java.sql.Date
				Student student = new Student(id, name, age, gender, address, birthday);
				list.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, preparedStatement, resultSet);
		}
		return list;
	}

	@Override
	public int getTotalCount() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select count(*) from student";
		int count = 0;
		try {
			connection = JDBCUtil.getConnection();
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			System.out.println(preparedStatement);
			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, preparedStatement, resultSet);
		}
		return count;
	}

	@Override
	public int getTotalCount(StudentSearchCondition studentSearchCondition) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String sql = "select count(*) from student where 1=1 ";
		int count = 0;
		List<String> conditionList = new ArrayList<String>();
		try {
			connection = JDBCUtil.getConnection();
			if (studentSearchCondition.getName() != null 
					&& !"".equals(studentSearchCondition.getName())) {
				sql += " and name like ? ";
				conditionList.add("%" + studentSearchCondition.getName() + "%");
			}
			if (studentSearchCondition.getAge() != null 
					&& !"".equals(studentSearchCondition.getAge())) {
				sql += " and age = ? ";
				conditionList.add(studentSearchCondition.getAge());
			}
			if (studentSearchCondition.getGender() != null 
					&& !"".equals(studentSearchCondition.getGender())) {
				sql += " and gender = ? ";
				conditionList.add(studentSearchCondition.getGender());
			}
			//sql语句拼接好
			preparedStatement = connection.prepareStatement(sql);
			//给占位符?赋值
			for (int i = 0; i < conditionList.size(); i++) {
				preparedStatement.setObject(i + 1, conditionList.get(i));
			}
			resultSet = preparedStatement.executeQuery();
			System.out.println(preparedStatement);
			if (resultSet.next()) {
				count = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, preparedStatement, resultSet);
		}
		return count;
	}

	@Override
	public List<Student> findPageBeanList(StudentSearchCondition studentSearchCondition) {
		System.out.println("StudentDaoImpl.findPageBeanList()");
		String sql = "SELECT id,NAME,age,gender,address,birthday FROM student where 1=1 ";
		List conditionList = new ArrayList();
		if (studentSearchCondition.getName() != null && !"".equals(studentSearchCondition.getName())) {
			sql += " and name like ? ";
			conditionList.add("%" + studentSearchCondition.getName() + "%");
		}
		if (studentSearchCondition.getAge() != null && !"".equals(studentSearchCondition.getAge())) {
			sql += " and age = ? ";
			conditionList.add(studentSearchCondition.getAge());
		}
		if (studentSearchCondition.getGender() != null && !"".equals(studentSearchCondition.getGender())) {
			sql += " and gender = ? ";
			conditionList.add(studentSearchCondition.getGender());
		}

		sql += " limit ?,?";
		int offset = (studentSearchCondition.getPageNo() - 1) * studentSearchCondition.getPageSize();
		conditionList.add(offset);
		conditionList.add(studentSearchCondition.getPageSize());
		//2.占位符赋值
		Object[] params = conditionList.toArray();
		try {
			List<Student> list = queryRunner.query(sql, new BeanListHandler<Student>(Student.class), params);
			queryRunner.toString();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean deleteAll(String[] ids) {
		try {
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			connection = JDBCUtil.getConnection();
			String sql = "delete from student where id=?";
			preparedStatement = connection.prepareStatement(sql);
			for (String id : ids) {
				preparedStatement.setInt(1, Integer.parseInt(id));
				preparedStatement.addBatch();
			}
			int[] results = preparedStatement.executeBatch();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
