package dao;

import  util.DBHelper;
import  util.DBHelper.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;



public class ShareDao {

	public List<Map<String, Object>> listshare(String name, String singers) throws SQLException {
		String sql = "SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	sq_share a\n" +
				"LEFT JOIN sq_member b ON a.member = b. NAME\n" +
				"LEFT JOIN (\n" +
				"	SELECT\n" +
				"		count(*) cnt,\n" +
				"		member\n" +
				"	FROM\n" +
				"		sq_share\n" +
				"	GROUP BY\n" +
				"		member\n" +
				") c ON b. NAME = c.member\n" +
				"WHERE\n" +
				"	a. NAME = ?\n" +
				"AND singers = ?";
		
		return DBHelper.selectListMap(sql, name, singers);
	}
	
	
	public List<Map<String, Object>> listsharedetail(int page) throws SQLException{
		int begin=(page-1)*15;
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	sq_share a\n" +
				"LEFT JOIN sq_member b ON a.member = b. NAME\n" +
				"LEFT JOIN (\n" +
				"	SELECT\n" +
				"		count(*) cnt,\n" +
				"		member\n" +
				"	FROM\n" +
				"		sq_share\n" +
				"	GROUP BY\n" +
				"		member\n" +
				") c ON b. NAME = c.member\n" +
				"LIMIT ?,\n" +
				" 15";
		return DBHelper.selectListMap(sql,begin);
	}
	/**
	 * 网友分享总条数
 
	 * @return
	 * @throws SQLException
	 */
	public int  listall() throws SQLException{	
		String sql="SELECT\n" +
				"	count(*) cnt\n" +
				"FROM\n" +
				"	sq_share a\n" +
				"LEFT JOIN sq_member b ON a.member = b. NAME\n" +
				"LEFT JOIN (\n" +
				"	SELECT\n" +
				"		count(*) cnt,\n" +
				"		member\n" +
				"	FROM\n" +
				"		sq_share\n" +
				"	GROUP BY\n" +
				"		member\n" +
				") c ON b. NAME = c.member";
		List<Integer> list=DBHelper.selectList(sql, new ResultSetMapper<Integer>() {

			@Override
			public Integer map(ResultSet rs) throws SQLException {
				
				return rs.getInt(1);
			}
		});
		return list.get(0);
	}
	
	/**
	 * share 排行
	 * @throws SQLException 
	 */
	public List<Map<String, Object>> listph() throws SQLException{
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	sq_member b\n" +
				"RIGHT JOIN (\n" +
				"	SELECT\n" +
				"		count(*) cnt,\n" +
				"		member\n" +
				"	FROM\n" +
				"		sq_share\n" +
				"	GROUP BY\n" +
				"		member\n" +
				") a ON b. NAME = a.member\n" +
				"ORDER BY\n" +
				"	cnt DESC";
		
		return DBHelper.selectListMap(sql);
	}

	public List<Map<String, Object>> listrmzy() throws SQLException{
		String sql="select * from sq_share limit 66,12";
		
		return DBHelper.selectListMap(sql);
	}

	
	/**
	 * download页点击分享按钮
	 * 往分享表中添加数据
	 * @throws SQLException 
	 */
	
	public void insertshare(String songname,String singername,String format,int heat,String download,String uname) throws SQLException {
		String sql="insert into sq_share values(null,"
												+ "?,"
												+ "?,"
												+ "null,"
												+ "null,"
												+ "null,"
												+ "?,"
												+ "null,"
												+ "?,"
												+ "?,"
												+ "?,"
												+ "now(),"
												+ "null)";
		DBHelper dbh=new DBHelper();
		dbh.update(sql, songname,singername,format,heat,download,uname);
	}
	/**
	 * 判断某一个用户是否多次分享同一个歌手的同一首歌
	 * @param singername
	 * @param songname
	 * @param uname
	 * @return
	 * @throws SQLException
	 */
	public int selectcnt(String singername,String songname,String uname) throws SQLException {
		String sql="SELECT\n" +
				"	count(*) cnt\n" +
				"FROM\n" +
				"	sq_share\n" +
				"WHERE\n" +
				"	member = ?\n" +
				"AND singers = ?\n" +
				"AND NAME = ?";
		DBHelper dbh=new DBHelper();
		List<Integer> list=dbh.selectList(sql, new  ResultSetMapper<Integer>() {

			@Override
			public Integer map(ResultSet rs) throws SQLException {
				return rs.getInt(1);
			}
		}, uname,singername,songname);
		return list.get(0);
	}
	
}
