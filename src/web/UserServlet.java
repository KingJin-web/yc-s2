package web;

import  util.DBHelper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/user.s")
public class UserServlet  extends BaseServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 不用重写doget dopost
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public void query(HttpServletRequest request,HttpServletResponse response) throws SQLException, IOException {
		String sql="select * from tbl_user";
		List<?> ret= DBHelper.selectListMap(sql);
		write(response, ret);
	}
	
}
