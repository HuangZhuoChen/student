package com.j2ee.student.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelConvert {
	
	public static List<Map<String, Object>> converList(ResultSet resultSet) {
	
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			ResultSetMetaData metaData = resultSet.getMetaData();//缁撴灉闆�(ResultSet)缁撴瀯淇℃伅锛屾瘮濡傚瓧娈垫暟锛屽瓧娈靛悕
			int columnCount = metaData.getColumnCount();//杩斿洖瀛楁鐨勪釜鏁�
			while (resultSet.next()) {//杩唬ResultSet
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 1; i <= columnCount; i++) {//寰幆鎵�鏈夋煡璇㈠嚭鏉ュ瓧娈�
					map.put(metaData.getColumnLabel(i), resultSet.getObject(i));
					//metaData.getColumnLabel(i) 寰楀埌鍒悕
					//metaData.getColumnName(i) 鏁版嵁搴撳瓧娈靛悕
				}
				list.add(map);//灏嗗皝瑁呭ソ鐨勪竴琛岃褰曟斁鍒發ist閲岄潰
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
