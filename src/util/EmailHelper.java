package util;

import com.sun.mail.util.MailSSLSocketFactory;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class EmailHelper {
    private final String STMP = "vhsxfwpexsdmjjeg";
    private final String guilt = "jinpeng.qmail@qq.com";

    /**
     * 漏洞类型：邮箱 SMTP 信息泄露
     *
     * 漏洞等级：高
     *
     * 漏洞地址：https://github.com/KingJin-web/Yc-s1/blob/b08d38b84eef9751e47cefa8454d3da68201bafd/src/Util/EmailHelper.java
     *
     * 漏洞危害：任何人可以通过 SMTP 账号密码收发邮件，进而通过邮箱重置其他平台密码
     *
     * 解决方案：重置 SMTP 密码并检查邮箱是否有敏感信息泄露（请勿只修改代码，历史版本库依旧可见）
     */
    /**
     * @param YanZhengma 验证码
     * @param email      邮箱地
     * @throws MessagingException
     * @throws GeneralSecurityException
     */
    public void email(String email, String YanZhengma) throws MessagingException, GeneralSecurityException {

//        String YanZhengma;
//        int radomInt = new Random().nextInt(999999);
//        YanZhengma = String.valueOf(radomInt);
        //创建一个配置文件并保存
        Properties properties = new Properties();

        properties.setProperty("mail.host", "smtp.qq.com");

        properties.setProperty("mail.transport.protocol", "smtp");

        properties.setProperty("mail.smtp.auth", "true");
        //QQ存在一个特性设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        //创建一个session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("发件人邮箱", "邮箱 SMTP 信息");
            }
        });
        //开启debug模式
        session.setDebug(true);
        //获取连接对象
        Transport transport = session.getTransport();
        //连接服务器 vhsxfwpexsdmjjeg
        //transport.connect("smtp.qq.com", "发件人邮箱", "邮箱 SMTP 信息");
        transport.connect("smtp.qq.com", guilt, STMP);
        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);
        //邮件发送人
        mimeMessage.setFrom(new InternetAddress(guilt));
        //邮件接收人
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        //邮件标题
        mimeMessage.setSubject("Hello Mail");

        //邮件内容
        mimeMessage.setContent(
                "验证码为：\n" + YanZhengma, "text/html;charset=UTF-8");

        //发送邮件
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

        //关闭连接
        transport.close();


    }

    /**
     * 注册账号邮件
     * @param vcode 验证码
     * @param emailNum   邮箱地
     * @param name       用户名
     * @throws MessagingException
     * @throws GeneralSecurityException
     */
    public void email(String emailNum, int vcode, String name) throws MessagingException, GeneralSecurityException {
        String email = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "    <title></title>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"qmbox qm_con_body_content qqmail_webmail_only\" id=\"mailContentContainer\" style=\"\">\n" +
                "        <style type=\"text/css\">\n" +
                "            .qmbox body {\n" +
                "                margin: 0;\n" +
                "                padding: 0;\n" +
                "                background: #fff;\n" +
                "                font-family: \"Verdana, Arial, Helvetica, sans-serif\";\n" +
                "                font-size: 14px;\n" +
                "                line-height: 24px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox div, .qmbox p, .qmbox span, .qmbox img {\n" +
                "                margin: 0;\n" +
                "                padding: 0;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox img {\n" +
                "                border: none;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .contaner {\n" +
                "                margin: 0 auto;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .title {\n" +
                "                margin: 0 auto;\n" +
                "                background: url() #CCC repeat-x;\n" +
                "                height: 30px;\n" +
                "                text-align: center;\n" +
                "                font-weight: bold;\n" +
                "                padding-top: 12px;\n" +
                "                font-size: 16px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .content {\n" +
                "                margin: 4px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .biaoti {\n" +
                "                padding: 6px;\n" +
                "                color: #000;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xtop, .qmbox .xbottom {\n" +
                "                display: block;\n" +
                "                font-size: 1px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xb1, .qmbox .xb2, .qmbox .xb3, .qmbox .xb4 {\n" +
                "                display: block;\n" +
                "                overflow: hidden;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xb1, .qmbox .xb2, .qmbox .xb3 {\n" +
                "                height: 1px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xb2, .qmbox .xb3, .qmbox .xb4 {\n" +
                "                border-left: 1px solid #BCBCBC;\n" +
                "                border-right: 1px solid #BCBCBC;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xb1 {\n" +
                "                margin: 0 5px;\n" +
                "                background: #BCBCBC;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xb2 {\n" +
                "                margin: 0 3px;\n" +
                "                border-width: 0 2px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xb3 {\n" +
                "                margin: 0 2px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xb4 {\n" +
                "                height: 2px;\n" +
                "                margin: 0 1px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xboxcontent {\n" +
                "                display: block;\n" +
                "                border: 0 solid #BCBCBC;\n" +
                "                border-width: 0 1px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .line {\n" +
                "                margin-top: 6px;\n" +
                "                border-top: 1px dashed #B9B9B9;\n" +
                "                padding: 4px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .neirong {\n" +
                "                padding: 6px;\n" +
                "                color: #666666;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .foot {\n" +
                "                padding: 6px;\n" +
                "                color: #777;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .font_darkblue {\n" +
                "                color: #006699;\n" +
                "                font-weight: bold;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .font_lightblue {\n" +
                "                color: #008BD1;\n" +
                "                font-weight: bold;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .font_gray {\n" +
                "                color: #888;\n" +
                "                font-size: 12px;\n" +
                "            }\n" +
                "        </style>\n" +
                "        <div class=\"contaner\">\n" +
                "            <div class=\"title\"> </div>\n" +
                "            <div class=\"content\">\n" +
                "                <p class=\"biaoti\"><b>亲爱的用户，你好！</b></p>\n" +
                "                <b class=\"xtop\"><b class=\"xb1\"></b><b class=\"xb2\"></b><b class=\"xb3\"></b><b class=\"xb4\"></b></b>\n" +
                "                <div class=\"xboxcontent\">\n" +
                "                    <div class=\"neirong\">\n" +
                "                        <p><b>请核对你的用户名：</b><span id=\"userName\" class=\"font_darkblue\">" + name + "</span></p>\n" +
                "                        <p><b>验证码：</b><span class=\"font_lightblue\"><span id=\"yzm\"  onclick=\"return false;\" t=\"7\" style=\"border-bottom: 1px dashed rgb(204, 204, 204); z-index: 1; position: static;\">" + vcode + "</span></span><br><span class=\"font_gray\">(请输入该验证码完成密码，验证码30分钟内有效！)</span></p>\n" +
                "                        <div class=\"line\">如果你未申请注册服务，请忽略该邮件。</div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <b class=\"xbottom\"><b class=\"xb4\"></b><b class=\"xb3\"></b><b class=\"xb2\"></b><b class=\"xb1\"></b></b>\n" +
                "                <p class=\"foot\">如果仍有问题，请联系我们: <span data=\"800-820-5100\" onclick=\"return false;\" t=\"7\" style=\"border-bottom: 1px dashed rgb(204, 204, 204); z-index: 1; position: static;\">021-51875288\n" +
                "</span></p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <style type=\"text/css\">\n" +
                "            .qmbox style, .qmbox script, .qmbox head, .qmbox link, .qmbox meta {\n" +
                "                display: none !important;\n" +
                "            }\n" +
                "        </style>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
        //创建一个配置文件并保存
        Properties properties = new Properties();

        properties.setProperty("mail.host", "smtp.qq.com");

        properties.setProperty("mail.transport.protocol", "smtp");

        properties.setProperty("mail.smtp.auth", "true");
        //QQ存在一个特性设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        //创建一个session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(guilt, STMP);
            }
        });
        //开启debug模式
        session.setDebug(true);
        //获取连接对象
        Transport transport = session.getTransport();
        //连接服务器 vhsxfwpexsdmjjeg
        //transport.connect("smtp.qq.com", "发件人邮箱", "邮箱 SMTP 信息");
        transport.connect("smtp.qq.com", guilt, STMP);
        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);
        //邮件发送人
        mimeMessage.setFrom(new InternetAddress(guilt));
        //邮件接收人
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailNum));
        //邮件标题
        mimeMessage.setSubject("注册账号");

        //邮件内容
        mimeMessage.setContent(email, "text/html;charset=UTF-8");

        //发送邮件
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

        //关闭连接
        transport.close();


    }

    public void email2(String emailNum, String YanZhengma, String name) throws MessagingException, GeneralSecurityException {
        String email = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "    <title></title>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"qmbox qm_con_body_content qqmail_webmail_only\" id=\"mailContentContainer\" style=\"\">\n" +
                "        <style type=\"text/css\">\n" +
                "            .qmbox body {\n" +
                "                margin: 0;\n" +
                "                padding: 0;\n" +
                "                background: #fff;\n" +
                "                font-family: \"Verdana, Arial, Helvetica, sans-serif\";\n" +
                "                font-size: 14px;\n" +
                "                line-height: 24px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox div, .qmbox p, .qmbox span, .qmbox img {\n" +
                "                margin: 0;\n" +
                "                padding: 0;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox img {\n" +
                "                border: none;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .contaner {\n" +
                "                margin: 0 auto;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .title {\n" +
                "                margin: 0 auto;\n" +
                "                background: url() #CCC repeat-x;\n" +
                "                height: 30px;\n" +
                "                text-align: center;\n" +
                "                font-weight: bold;\n" +
                "                padding-top: 12px;\n" +
                "                font-size: 16px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .content {\n" +
                "                margin: 4px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .biaoti {\n" +
                "                padding: 6px;\n" +
                "                color: #000;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xtop, .qmbox .xbottom {\n" +
                "                display: block;\n" +
                "                font-size: 1px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xb1, .qmbox .xb2, .qmbox .xb3, .qmbox .xb4 {\n" +
                "                display: block;\n" +
                "                overflow: hidden;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xb1, .qmbox .xb2, .qmbox .xb3 {\n" +
                "                height: 1px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xb2, .qmbox .xb3, .qmbox .xb4 {\n" +
                "                border-left: 1px solid #BCBCBC;\n" +
                "                border-right: 1px solid #BCBCBC;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xb1 {\n" +
                "                margin: 0 5px;\n" +
                "                background: #BCBCBC;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xb2 {\n" +
                "                margin: 0 3px;\n" +
                "                border-width: 0 2px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xb3 {\n" +
                "                margin: 0 2px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xb4 {\n" +
                "                height: 2px;\n" +
                "                margin: 0 1px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .xboxcontent {\n" +
                "                display: block;\n" +
                "                border: 0 solid #BCBCBC;\n" +
                "                border-width: 0 1px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .line {\n" +
                "                margin-top: 6px;\n" +
                "                border-top: 1px dashed #B9B9B9;\n" +
                "                padding: 4px;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .neirong {\n" +
                "                padding: 6px;\n" +
                "                color: #666666;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .foot {\n" +
                "                padding: 6px;\n" +
                "                color: #777;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .font_darkblue {\n" +
                "                color: #006699;\n" +
                "                font-weight: bold;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .font_lightblue {\n" +
                "                color: #008BD1;\n" +
                "                font-weight: bold;\n" +
                "            }\n" +
                "\n" +
                "            .qmbox .font_gray {\n" +
                "                color: #888;\n" +
                "                font-size: 12px;\n" +
                "            }\n" +
                "        </style>\n" +
                "        <div class=\"contaner\">\n" +
                "            <div class=\"title\"> </div>\n" +
                "            <div class=\"content\">\n" +
                "                <p class=\"biaoti\"><b>亲爱的用户，你好！</b></p>\n" +
                "                <b class=\"xtop\"><b class=\"xb1\"></b><b class=\"xb2\"></b><b class=\"xb3\"></b><b class=\"xb4\"></b></b>\n" +
                "                <div class=\"xboxcontent\">\n" +
                "                    <div class=\"neirong\">\n" +
                "                        <p><b>请核对你的用户名：</b><span id=\"userName\" class=\"font_darkblue\">" + name + "</span></p>\n" +
                "                        <p><b>验证码：</b><span class=\"font_lightblue\"><span id=\"yzm\"  onclick=\"return false;\" t=\"7\" style=\"border-bottom: 1px dashed rgb(204, 204, 204); z-index: 1; position: static;\">" + YanZhengma + "</span></span><br><span class=\"font_gray\">(请输入该验证码完成邮箱修改，验证码30分钟内有效！)</span></p>\n" +
                "                        <div class=\"line\">如果你未申请修改邮箱服务，请忽略该邮件。</div>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "                <b class=\"xbottom\"><b class=\"xb4\"></b><b class=\"xb3\"></b><b class=\"xb2\"></b><b class=\"xb1\"></b></b>\n" +
                "                <p class=\"foot\">如果仍有问题，请联系我们: <span data=\"800-820-5100\" onclick=\"return false;\" t=\"7\" style=\"border-bottom: 1px dashed rgb(204, 204, 204); z-index: 1; position: static;\">021-51875288\n" +
                "</span></p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <style type=\"text/css\">\n" +
                "            .qmbox style, .qmbox script, .qmbox head, .qmbox link, .qmbox meta {\n" +
                "                display: none !important;\n" +
                "            }\n" +
                "        </style>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
        //创建一个配置文件并保存
        Properties properties = new Properties();

        properties.setProperty("mail.host", "smtp.qq.com");

        properties.setProperty("mail.transport.protocol", "smtp");

        properties.setProperty("mail.smtp.auth", "true");
        //QQ存在一个特性设置SSL加密
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        //创建一个session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(guilt, STMP);
            }
        });
        //开启debug模式
        session.setDebug(true);
        //获取连接对象
        Transport transport = session.getTransport();
        //连接服务器 vhsxfwpexsdmjjeg
        //transport.connect("smtp.qq.com", "发件人邮箱", "邮箱 SMTP 信息");
        transport.connect("smtp.qq.com", guilt, STMP);
        //创建邮件对象
        MimeMessage mimeMessage = new MimeMessage(session);
        //邮件发送人
        mimeMessage.setFrom(new InternetAddress(guilt));
        //邮件接收人
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailNum));
        //邮件标题
        mimeMessage.setSubject("重置您的密码");

        //邮件内容
        mimeMessage.setContent(email, "text/html;charset=UTF-8");

        //发送邮件
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

        //关闭连接
        transport.close();


    }
}
