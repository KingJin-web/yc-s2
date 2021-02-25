package dao;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.List;


import util.DBHelper;
import  util.DBHelper.ResultSetMapper;

public class OrdersongsDao {

	public void insert(String uname,String singername,String songname,String money) throws SQLException {
		String sql="insert into sq_ordersong values(null,?,?,?,?,now())";
		
		DBHelper.update(sql, uname,singername,songname,money);
	}
	
	
	public int listcnt(String uname,String singername,String songname) throws SQLException {
		String sql="SELECT\n" +
				"	count(*) cnt\n" +
				"FROM\n" +
				"	sq_ordersong\n" +
				"WHERE\n" +
				"	singername = ?\n" +
				"AND songname = ?\n" +
				"AND username = ?";
		List<Integer> list=DBHelper.selectList(sql, new ResultSetMapper<Integer>() {

			@Override
			public Integer map(ResultSet rs) throws SQLException {
				
				return rs.getInt(1);
			}
		}, singername,songname,uname);
		return list.get(0);
	}
	
}
