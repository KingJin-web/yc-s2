package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import  util.DBHelper;

public class DownLoadDao {

	public List<Map<String, Object>> listdownload(String name,String song) throws SQLException{
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	sq_song a\n" +
				"LEFT JOIN (\n" +
				"	SELECT\n" +
				"		NAME,\n" +
				"		head\n" +
				"	FROM\n" +
				"		sq_singer\n" +
				") c ON a.singer = c. NAME\n" +
				"WHERE\n" +
				"	a.singer = ?\n" +
				"AND a. NAME = ?";
		
		return DBHelper.selectListMap(sql, name,song);
	}
	
	public List<Map<String, Object>> listdownloadzjsl() throws SQLException{
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	sq_singer a\n" +
				"LEFT JOIN (\n" +
				"	SELECT\n" +
				"		singer,\n" +
				"		count(NAME) total\n" +
				"	FROM\n" +
				"		sq_song\n" +
				"	GROUP BY\n" +
				"		singer\n" +
				") c ON a. NAME = c.singer\n" +
				"limit 66,6";
		
		return DBHelper.selectListMap(sql);
	
	}
}