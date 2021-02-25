package web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import biz.ScBiz;
import biz.ShareBiz;
import  biz.BizException;
import  web.BaseServlet;
import dao.ScDao;

@WebServlet("/downloadbutton.s")
public class DownloadButtonServlet extends BaseServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ScBiz cb = new ScBiz();
	private ScDao sd = new ScDao();
	private ShareBiz sb=new ShareBiz();
	private ScBiz sbb=new ScBiz();
	public void addcsinfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String singername = request.getParameter("singername");
		String songname = request.getParameter("songname");
		String uname = (String) request.getSession().getAttribute("name");
		String bofang_url= request.getParameter("bofang_url");
		try {
			cb.addcs(singername, songname, uname,bofang_url);
			write(response, "收藏成功");
		} catch (BizException e) {
			write(response,  e.getMessage());
		}
	}

	/**
	 * 判断是否已经收藏
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void querycnt(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String singername = request.getParameter("singername");
		String songname = request.getParameter("songname");
		String uname = (String) request.getSession().getAttribute("name");
		try {
			write(response,sd.selectcnt(singername, songname, uname));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryscinfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uname = (String) request.getSession().getAttribute("name");
		try {
			try {
				write(response, sd.selectcsinfo(uname));
			} catch (BizException e) {
				write(response, e.getMessage());
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询某一个用户的信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void queryuserinfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uname = (String) request.getSession().getAttribute("name");
		try {
			try {
				write(response, sd.selectuser(uname));
			} catch (BizException e) {
				write(response, e.getMessage());
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 	分享按钮  点击分享
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void addshare(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String singername = request.getParameter("singername");
		String songname = request.getParameter("songname");
		String format = request.getParameter("format");
		String heat = request.getParameter("heat");
		int heats=heat==null?0 :Integer.parseInt(heat);
		String download=request.getParameter("download");
		String uname = (String) request.getSession().getAttribute("name");
		try {
			sb.addshare(songname, singername, format, heats, download, uname);
			write(response, "分享成功");
		} catch (BizException e) {
			write(response,  e.getMessage());
		}
	}
	
	/**
	 * 删除收藏歌曲
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deletesongs(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String singername = request.getParameter("singername");
		String songname = request.getParameter("songname");
		try {
			sbb.deletesongs(singername, songname);
			write(response, "删除成功");
		} catch (BizException e) {
			write(response,  e.getMessage());
		}
	}
	
}
