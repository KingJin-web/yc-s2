package web;

import web.BaseServlet;
import dao.IndexXuanRanDao;
import dao.ShareDao;
import dao.SingerTypedao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/xuanran.s")
public class IndexXuanRanServlet extends BaseServlet {

private static final long serialVersionUID = 1L;
	
	private IndexXuanRanDao id=new IndexXuanRanDao();
	private SingerTypedao std=new SingerTypedao();
	private ShareDao sd=new ShareDao();
	public void queryzuixing(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try {
			write(response, id.Listzuixing());
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void queryhuayunan(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try {
			List<Map<String, Object>> list=id.Listhuayunan();
			list.addAll(id.Listhuayunv());
			list.addAll(id.Listhuayuyd());
			list.addAll(id.Listhuazj());
			list.addAll(id.Listrhgs());
			list.addAll(id.Listom());
			write(response, list);
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void querysingertype(HttpServletRequest request, HttpServletResponse response) throws IOException{
		try {
			write(response, id.Listtype());
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从index获取锚点后
	 * 对singer页面渲染
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void querysinger(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String category=request.getParameter("category");
		String page=request.getParameter("page");
		int ipage=0;
		if(page.equals("undefined")||page.equals("NaN")) {
			ipage=1;
		}else {
			ipage=Integer.parseInt(page);
		}
		//int ipage=page .equals("undefined")? 1:Integer.parseInt(page);
		try {
			write(response, std.listsingertype(category,ipage));
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	public void querysingerzuixin(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String category=request.getParameter("category");
		try {
			write(response, std.listsingertypezuixin(category));
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void querycnt(HttpServletRequest request,HttpServletResponse response) {
		String category=request.getParameter("category");
		try {
			write(response, std.queryall(category));
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void queryshare(HttpServletRequest request,HttpServletResponse response) {
		try {
			write(response, id.Listshare());
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * share_item
	 * @param request
	 * @param response
	 */
	public void querysharedetail(HttpServletRequest request,HttpServletResponse response) {
		String name=request.getParameter("name");
		String singers=request.getParameter("singers");
		try {
			write(response, sd.listshare(name, singers));
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * share页
	 * @param request
	 * @param response
	 */
	public void querysharedetails(HttpServletRequest request,HttpServletResponse response) {
		String page=request.getParameter("page");
		int ipage=page .equals("NaN")? 1:Integer.parseInt(page);
		try {
			write(response, sd.listsharedetail(ipage));
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * share排行
	 * @param request
	 * @param response
	 */
	public void queryshareph(HttpServletRequest request,HttpServletResponse response) {
		try {
			write(response, sd.listph());
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 网友分享cnt总条数
	 * @param request
	 * @param response
	 */
	public void queryall(HttpServletRequest request,HttpServletResponse response) {
		try {
			write(response, sd.listall());
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * share_item页热门资源
	 * @param request
	 * @param response
	 */
	
	public void queryrmzy(HttpServletRequest request,HttpServletResponse response) {
		try {
			write(response, sd.listrmzy());
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}
}
