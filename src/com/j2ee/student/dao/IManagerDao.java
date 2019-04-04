package com.situ.student.dao;

import java.util.List;
import java.util.Map;

public interface IManagerDao {

	List<Map<String, Object>> findAllByDBUtils();
	List<Map<String, Object>> findAllByJdbc();

}
