package web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * 	自定义基础 BaseServlet
 * 
 * 	规则1： 使用op请求参数区分不同操作类型 
 * 	例如：
 * 		发布留言： 	/msg.s?op=add 
 * 		查询留言： 	/msg.s?op=query 
 * 		回复留言：	/msg.s?op=reply
 * 		....
 *  
 * 	规则2：定义不同业务方法对应处理不同的请求， 用方法名和 op 值对应 方法名，
 * 	例如：
 * 		public void add(...) => op=add 
 * 		public void query(...) ==> op=query
 * 
 * BaseServlet 是一个共同父类， 业务方法写在子类中 java 
 * 	使用的技术：Java的黑科技 —— 反射：动态获取对象的属性或执行对象的方法
 * 
 */

//@WebServlet("/BaseServlet") // 不定义BaseServlet的地址 
// BaseServlet 不希望被创建对象（new）， 只允许继承
public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new Gson();

	// final 禁止子类重写该方法
	protected final void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 在此设置字符集编码
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String op = request.getParameter("op");
		if (op == null) {
			throw new ServletException("必须提供op字段！");
		}
		// 通过反射获取 public 方法对象
		// getClass().getMethod(name, parameterTypes)
		try {
			// 通过反射获取 定义的（当前类中定义的） 方法对象
			Method m = getClass().getDeclaredMethod(op, HttpServletRequest.class, HttpServletResponse.class);
			// 设置强制访问 （非public）
			m.setAccessible(true);
			// 调用method对象， 执行方法
			m.invoke(this, request, response);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new ServletException("获取" + op + "方法失败！", e);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new ServletException("调用" + op + "方法失败！", e);
		}
	}

	protected final void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 	将对象转成 json 通过响应对象输出
	 * @param response
	 * @param obj 要转成 json 的对象
	 * @throws IOException
	 */
	public void write(HttpServletResponse response, Object obj) throws IOException {
		String json = gson.toJson(obj);
		response.getWriter().append(json);
	}

}
