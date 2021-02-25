package dao;

import  util.DBHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;



public class SingerTypedao {

	public List<Map<String, Object>> listsingertype(String category,int page) throws SQLException{
		int begin =(page-1)*12;
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
				"WHERE\n" +
				"	category = ?\n" +
				"LIMIT ?,\n" +
				" 12";
		
		return DBHelper.selectListMap(sql,category,begin);
	}

	public int queryall(String category) throws SQLException{
		String sql="SELECT\n" +
				"	count(*) cnt\n" +
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
				"WHERE\n" +
				"	category =?";
		//return DBHelper.selectListMap(sql  );
		List<Integer> list=DBHelper.selectList(sql, new DBHelper.ResultSetMapper<Integer>() {

			@Override
			public Integer map(ResultSet rs) throws SQLException {
				
				return rs.getInt(1);
			}
		},category);
		return list.get(0);
		
	}
	
public List<Map<String, Object>> listsingertypezuixin(String category) throws SQLException{
		
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
				"WHERE\n" +
				"	category = ?\n" +
				"LIMIT 11,\n" +
				" 12";
		
		return DBHelper.selectListMap(sql,category);
	}
}
