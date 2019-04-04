package com.j2ee.student.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.j2ee.student.dao.IManagerDao;
import com.j2ee.student.entity.Student;
import com.j2ee.student.util.C3P0Util;
import com.j2ee.student.util.JDBCUtil;
import com.j2ee.student.util.ModelConvert;

public class ManagerDaoImpl implements IManagerDao {
	private QueryRunner queryRunner = new QueryRunner(C3P0Util.getDataSource());
	
	@Override
	public List<Map<String, Object>> findAllByJdbc() {
		System.out.println("ManagerDaoImpl.findAllByJdbc()");
		Connection connection  = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		//List<Student> list = new ArrayList<Student>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			connection = JDBCUtil.getConnection();
			String sql = "SELECT s.name  AS s_name,age,b.name AS b_name,c.name AS c_name,c.credit "
					+ "FROM student AS s INNER JOIN banji AS b ON s.banji_id=b.id "
					+ "INNER JOIN banji_course AS bc ON b.id=bc.banji_id "
					+ "INNER JOIN course AS c ON bc.course_id=c.id;";
			preparedStatement = connection.prepareStatement(sql);
			System.out.println(preparedStatement.toString());
			resultSet = preparedStatement.executeQuery();
			list = ModelConvert.converList(resultSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public List<Map<String, Object>> findAllByDBUtils() {
		System.out.println("ManagerDaoImpl.findAllByDBUtils()");
		String sql = "SELECT s.name  AS s_name,age,b.name AS b_name,c.name AS c_name,c.credit "
				+ "FROM student AS s INNER JOIN banji AS b ON s.banji_id=b.id "
				+ "INNER JOIN banji_course AS bc ON b.id=bc.banji_id "
				+ "INNER JOIN course AS c ON bc.course_id=c.id;";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = queryRunner.query(sql, new MapListHandler());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
