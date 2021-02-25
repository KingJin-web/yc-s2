package dao;

import util.DBHelper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class IndexXuanRanDao {

	
	public List<Map<String, Object>> Listzuixing() throws SQLException{
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	sq_song\n" +
				"ORDER BY\n" +
				"	intime DESC\n" +
				"LIMIT 0,\n" +
				" 27";
		return DBHelper.selectListMap(sql);
	}
	
	public List<Map<String, Object>> Listhuayunan() throws SQLException{
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	sq_song ss\n" +
				"RIGHT OUTER JOIN (\n" +
				"	SELECT\n" +
				"		*\n" +
				"	FROM\n" +
				"		sq_singer\n" +
				"	WHERE\n" +
				"		category = '华语男歌手'\n" +
				"	LIMIT 0,\n" +
				"	12\n" +
				") sg ON sg. NAME = ss.singer\n" +
				"LIMIT 8,\n" +
				" 12";
		return DBHelper.selectListMap(sql);
	}
	public List<Map<String, Object>> Listhuayunv() throws SQLException{
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	sq_song ss\n" +
				"RIGHT OUTER JOIN (\n" +
				"	SELECT\n" +
				"		*\n" +
				"	FROM\n" +
				"		sq_singer\n" +
				"	WHERE\n" +
				"		category = '华语女歌手'\n" +
				"	LIMIT 0,\n" +
				"	12\n" +
				") sg ON sg. NAME = ss.singer\n"+
				"LIMIT 8,\n" +
				" 12"
				;
		return DBHelper.selectListMap(sql);
	}
	public List<Map<String, Object>> Listhuayuyd() throws SQLException{
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	sq_song ss\n" +
				"RIGHT OUTER JOIN (\n" +
				"	SELECT\n" +
				"		*\n" +
				"	FROM\n" +
				"		sq_singer\n" +
				"	WHERE\n" +
				"		category = '华语乐队'\n" +
				"	LIMIT 0,\n" +
				"	12\n" +
				") sg ON sg. NAME = ss.singer\n"+
				"LIMIT 8,\n" +
				" 12";
		return DBHelper.selectListMap(sql);
	}
	public List<Map<String, Object>> Listrhgs() throws SQLException{
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	sq_song ss\n" +
				"RIGHT  OUTER JOIN (\n" +
				"	SELECT\n" +
				"		*\n" +
				"	FROM\n" +
				"		sq_singer\n" +
				"	WHERE\n" +
				"		category = '日韩歌手'\n" +
				"	LIMIT 0,\n" +
				"	12\n" +
				") sg ON sg. NAME = ss.singer\n" +
				"LIMIT 0,\n" +
				" 12";
		return DBHelper.selectListMap(sql);
	}
	public List<Map<String, Object>> Listom() throws SQLException{
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	sq_song ss\n" +
				"RIGHT  OUTER JOIN (\n" +
				"	SELECT\n" +
				"		*\n" +
				"	FROM\n" +
				"		sq_singer\n" +
				"	WHERE\n" +
				"		category = '欧美歌手'\n" +
				"	LIMIT 0,\n" +
				"	12\n" +
				") sg ON sg. NAME = ss.singer\n" +
				"LIMIT 1,\n" +
				" 12";
		return DBHelper.selectListMap(sql);
	}
	public List<Map<String, Object>> Listhuazj() throws SQLException{
		String sql="select * from sq_singer  where category='专辑合集'  limit 0,12";
		return DBHelper.selectListMap(sql);
	}
	
	
	public List<Map<String, Object>> Listshare() throws SQLException{
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	sq_share\n" +
				"LEFT JOIN sq_member ON sq_share.member = sq_member. NAME\n" +
				"LIMIT 0,\n" +
				" 18";
		return DBHelper.selectListMap(sql);
	}
	
	/**
	 * 	歌手类型查询
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> Listtype() throws SQLException{
		String sql="select * from sq_singertype ";
		return DBHelper.selectListMap(sql);
	}
	
}
