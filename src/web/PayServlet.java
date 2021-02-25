package web;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;

import  web.BaseServlet;
import dao.AlipayConfig;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@WebServlet("/pay.do")
public class PayServlet extends BaseServlet {

    public void returnPay(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        /* *
         * 功能：支付宝服务器同步通知页面
         * 日期：2017-03-30
         * 说明：
         * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
         * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。


         *************************页面功能说明*************************
         * 该页面仅做页面展示，业务逻辑处理请勿在该页面执行
         */

        //获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = req.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            valueStr = new String(valueStr.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            params.put(name, valueStr);
        }

//        for (String key : params.keySet()) {
//            System.out.println("key:" + key);
//            String[] strs = requestParams.get(key);
//            for (String value : strs) {
//                System.out.println("\t" + value);
//            }
//        }


//        boolean signVerified = false;
//        try {
//            signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
        //验证
        boolean signVerified = false;
        if (params.get("app_id").equals(AlipayConfig.app_id) || params.get("app_id") == AlipayConfig.app_id) {
            signVerified = true;
        }

        //——请在这里编写您的程序（以下代码仅作参考）——
        if (signVerified) {
            //付款金额
            String total_amount = new String(req.getParameter("total_amount").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            write(resp, total_amount);
        } else {
            write(resp, "验签失败");
            System.out.println("验签失败");
        }
    }
}
