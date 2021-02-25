package dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import  util.DBHelper;
import  util.DBHelper.ResultSetMapper;

public class ZanDao {

	public void zanadd(String uname,String singername) throws SQLException {
		String sql="insert into sq_zan values(null,?,?,now())";
		
		DBHelper.update(sql,uname,singername);
	}
	
	public List<Map<String , Object>> getzans(String singername) throws SQLException {
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	(\n" +
				"		SELECT\n" +
				"			count(*) cnt,\n" +
				"			singername\n" +
				"		FROM\n" +
				"			sq_zan\n" +
				"		GROUP BY\n" +
				"			singername\n" +
				"	) c\n" +
				"WHERE\n" +
				"	c.singername = ?";
		
		return DBHelper.selectListMap(sql, singername);
	}
	
	/**
	 * 	检测用户对歌手是否点赞
	 * @param singername
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public int getcnt(String singername,String name) throws SQLException {
		String sql="select count(*) cnt from sq_zan where singername=? and uname=?";
		
		List<Integer> list= DBHelper.selectList(sql, new  ResultSetMapper<Integer>() {

			@Override
			public Integer map(ResultSet rs) throws SQLException {
				
				return rs.getInt(1);
			}
		}, singername,name);
		return list.get(0);
	}

	public void qxzan(String uname, String singername) throws SQLException {
		String sql="DELETE \n" +
				"FROM\n" +
				"	sq_zan\n" +
				"WHERE\n" +
				"	singername = ?\n" +
				"AND uname = ?";
		
		DBHelper.update(sql,singername,uname);
	}
	
}
