package web;

import java.io.IOException;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import  biz.LyBiz;
import  dao.DetailDao;
import dao.DownLoadDao;
import dao.LyDao;
import dao.SearchDao;
import   biz.BizException;
import  web.BaseServlet;

@WebServlet("/detail.s")
public class DetailServelt  extends BaseServlet{

	private static final long serialVersionUID = 1L;
	private DetailDao dd=new DetailDao();
	private LyBiz lb=new LyBiz();
	private LyDao ld=new LyDao();
	private DownLoadDao dld=new DownLoadDao();
	private SearchDao sd=new SearchDao();
	public void querydetail(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String name=request.getParameter("name");
		try {
			write(response, dd.listdetail(name));
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void querysongs(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String name=request.getParameter("name");
		try {
			write(response, dd.listsong(name));
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	public void queryzjsl(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try {
			write(response, dd.listzjsl());
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}	
	}
	public void queryzjgx(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try {
			write(response, dd.listzjgx());
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	public void addly(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String singername=request.getParameter("singername");
		String songname=request.getParameter("songname");
		String content=request.getParameter("content");
		String uname = (String) request.getSession().getAttribute("name");
		try {
			lb.insertly(singername, songname, uname, content);
			response.getWriter().append("留言成功！！！");
		} catch (BizException e) {
			response.getWriter().append("登录失败！！！ 原因:"+e.getMessage());
		}
	}
	
	public void queryly(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String singername=request.getParameter("singername");
		String songname=request.getParameter("songname");
		try {
			write(response, ld.listly(singername, songname));
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 某一个歌手某一首歌的所有留言数
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void querylycnt(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String singername=request.getParameter("singername");
		String songname=request.getParameter("songname");
		try {
			write(response, ld.listlycnt(singername, songname));
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * download servlet  
	 * 	用于获取download页面要展示的数据
	 */
	public void querydownload(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String name=request.getParameter("name");
		String song=request.getParameter("song");
		try {
			write(response, dld.listdownload(name, song));
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * download servlet  
	 * 	用于获取download页面要展示的数据最新收录
	 */
	public void querydownloadzxsl(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try {
			write(response, dld.listdownloadzjsl());
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 通过search提供的参数 然后获取数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void querySearchlist(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String name=request.getParameter("name");
		try {
			List<Map<String, Object>> list=sd.listname(name);
			list.addAll(sd.listsong(name));
			list.addAll(sd.listnamepy(name));
			list.addAll(sd.listsongpy(name));
			write(response, list);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
}
