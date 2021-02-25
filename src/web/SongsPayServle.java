package web;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import biz.OrderSongBiz;
import  biz.BizException;
import  web.BaseServlet;

@WebServlet("/songspay.do")
public class SongsPayServle extends BaseServlet {

	/**
	 * 
	 */
	private OrderSongBiz sb=new OrderSongBiz();
	private static final long serialVersionUID = 1L;
	public void buysongs(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String singername = request.getParameter("singername");
		String songname = request.getParameter("songname");
		String uname = (String) request.getSession().getAttribute("name");
		String money=request.getParameter("money");
		
		try {
			sb.create(uname, singername, songname, money);
			write(response, "成功");
		} catch (BizException e) {
			write(response,e.getMessage());
		}
	}

	
}
