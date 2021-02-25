package web;

import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import dao.BuySongs;

@WebServlet("/buyorder/confirm")
public class BuySongOrderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("开始处理OrderServlet的服务");
        String title = "音乐购买";
        String total = request.getParameter("money");
        String message = "购买歌曲";

        //生成订单号
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String orderSn = simpleDateFormat.format(Calendar.getInstance().getTime());
        //向支付宝发送请求
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(
        		BuySongs.gatewayUrl,
        		BuySongs.app_id,
                BuySongs.merchant_private_key, "json",
                BuySongs.charset,
                BuySongs.alipay_public_key,
                BuySongs.sign_type);
        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

        alipayRequest.setNotifyUrl(BuySongs.notify_url);
        alipayRequest.setReturnUrl(BuySongs.return_url);
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderSn;
        //付款金额，必填
        String total_amount = total;
        //订单名称，必填
        String subject = title;
        //商品描述，可空
        String body = message;

       // String 
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        AlipayTradePagePayResponse alipayResponse = null;
        try {
            alipayResponse = alipayClient.pageExecute(alipayRequest);
            System.out.println(alipayResponse.getBody());
            System.out.println(alipayResponse.getMsg());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(alipayResponse.getBody());
    }
}