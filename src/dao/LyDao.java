package dao;

import util.DBHelper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;


public class LyDao {

    public void addly(String singername, String songname,String uname,String content) throws SQLException {
        String sql = "insert into sq_ly values(null,?,?,?,?,now())";

        DBHelper.update(sql, singername,songname,uname,content);
    }

    public List<Map<String, Object>> listly(String singername,String songname) throws SQLException {
        String sql = "SELECT\n" +
        		"	a.content,\n" +
        		"	ly_time,\n" +
        		"	b.nickname,\n" +
        		"	b.head\n" +
        		"FROM\n" +
        		"	sq_ly a\n" +
        		"LEFT JOIN sq_member b ON a.username = b. NAME\n" +
        		"WHERE\n" +
        		"	singername = ?\n" +
        		"AND songname = ?";

        return DBHelper.selectListMap(sql,singername,songname);

    }
    
    public List<Map<String, Object>> listlycnt(String singername,String songname) throws SQLException {
        String sql = "SELECT\n" +
        		"	COUNT(*) cnt\n" +
        		"FROM\n" +
        		"	sq_ly\n" +
        		"WHERE\n" +
        		"	singername =?\n" +
        		"AND songname = ?";

        return DBHelper.selectListMap(sql,singername,songname);

    }
}
