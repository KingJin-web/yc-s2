package web;


import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/notify")
public class NotifyServlet extends HttpServlet{
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("接收到支付宝的异步通知请求——");
        Map<String,String[]> parameterMap=request.getParameterMap();
        System.out.println(parameterMap);
        //成功处理后返回success
        response.getWriter().write("success");
    }
}