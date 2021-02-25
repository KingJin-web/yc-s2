package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import biz.BizException;
import util.DBHelper;
import util.DBHelper.ResultSetMapper;
import util.Utils;

public class ScDao {

	/**
	 * 某一个用户收藏歌曲
	 * @param singername
	 * @param song
	 * @param uname
	 * @throws SQLException
	 */
	public void insertcs(String singername,String song,String uname,String bofang_url) throws SQLException {
		String sql="insert into sq_cs values(null,?,?,?,now(),?)";

		DBHelper.update(sql, singername,song,uname,bofang_url);
	}
	
	/**
	 * 判断某一个用户是否多次收藏同一个歌手的同一首歌
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
				"	sq_cs\n" +
				"WHERE\n" +
				"	uname = ?\n" +
				"AND singername = ?\n" +
				"AND songname = ?";
		
		List<Integer> list=DBHelper.selectList(sql, new  ResultSetMapper<Integer>() {

			@Override
			public Integer map(ResultSet rs) throws SQLException {
				return rs.getInt(1);
			}
		}, uname,singername,songname);
		return list.get(0);
	}
	
	/**
	 * 查询某一个用户收藏的歌曲信息
	 * @param uname
	 * @return
	 * @throws SQLException
	 * @throws BizException 
	 */
	public List<Map<String, Object>> selectcsinfo(String uname) throws SQLException, BizException{
		Utils.checkNull(uname, "亲 请先登录");
		String sql="SELECT\n" +
				"	*\n" +
				"FROM\n" +
				"	sq_cs a\n" +
				"LEFT JOIN sq_member b ON a.uname = b. NAME\n" +
				"WHERE\n" +
				"	uname = ?";
		return DBHelper.selectListMap(sql, uname);
	}
	
	public List<Map<String, Object>> selectuser(String uname) throws SQLException, BizException{
		Utils.checkNull(uname, "亲 请先登录");
		String sql="select * from sq_member where name=?";
		return DBHelper.selectListMap(sql, uname);
	}
	
	/**
	 * 删除收藏的歌曲
	 * @param
	 * @return
	 * @throws SQLException
	 * @throws BizException
	 */
	public void detelesongs(String singername,String songname) throws SQLException, BizException{
		String sql="DELETE\n" +
				"FROM\n" +
				"	sq_cs\n" +
				"WHERE\n" +
				"	singername = ?\n" +
				"AND songname =?";
		 DBHelper.update(sql, singername,songname);
	}
}
