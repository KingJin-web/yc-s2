package biz;

import bean.SqMember;
import bean.SqShare;

import util.EmailHelper;
import util.Utils;
import dao.UserDao;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.List;

public class UserBiz {
    private final UserDao userDao = new UserDao();

    public void InsertUser(SqMember sqMember) throws BizException {
        Utils.nameIsUse(sqMember.getName());
        Utils.checkNull(sqMember.getPwd(), "请输入密码");
        Utils.isEmail(sqMember.getEmail(), "请输入合法邮箱");
        try {
            userDao.register(sqMember);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BizException("系统故障");
        }

    }

    /**
     * 发送验证码
     *
     * @param email 收件人
     * @param name  用户名
     * @param vcode 验证码
     * @throws BizException
     */
    public void SendMail(String email, String name, int vcode) throws BizException {

        Utils.nameIsUse(name);
        Utils.isEmail(email, "请输入合法邮箱");
        EmailHelper emailHelper = new EmailHelper();
        try {
            emailHelper.email(email, vcode, name);
        } catch (MessagingException | GeneralSecurityException e) {
            e.printStackTrace();
        }

    }

    public void loginBiz(SqMember sqMember) throws BizException {
        Utils.checkNull(sqMember.getName(), "请输入用户名");
        Utils.checkNull(sqMember.getPwd(), "请输入密码");

        try {
            int i = userDao.login(sqMember);
            if (i != 1) {
                throw new BizException("用户或密码错误");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws BizException {
        UserBiz userBiz = new UserBiz();
        SqMember sqMember = new SqMember();
        sqMember.setName("King");
        sqMember.setPwd("aaa");
        sqMember.setEmail("aaa");
        userBiz.InsertUser(sqMember);
    }

    /**
     * 通过用户名查询用户信息
     *
     * @param name
     * @return
     */
    public SqMember queryByName(String name) {
        SqMember sqMember = null;
        try {
            sqMember = userDao.selectByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert sqMember != null;
        sqMember.setPwd("");
        return sqMember;
    }


    /**
     * 修改用户信息
     *
     * @param sqMember
     */
    public void change(SqMember sqMember) throws BizException {

        Utils.checkNull(sqMember.getNickname(), "请输入昵称");
        Utils.isPhone(sqMember.getPhone());
        Utils.isEmail(sqMember.getEmail(), "请输入合法有效");
        Utils.isQq(sqMember.getQq());
        try {
            userDao.updateById(sqMember);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addShare(SqShare sqShare) throws BizException {

        Utils.checkNull(sqShare.getName(), "请填写歌曲名");
        Utils.checkNull(sqShare.getSingers(), "请填写歌手名");
        Utils.checkNull(sqShare.getDownUrl(), "请填写下载地址");

        try {
            userDao.insertShare(sqShare);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<SqShare> queryShareByName(String name) {
        try {
            return userDao.selectShareByUserName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 分页查询
     *
     * @param name
     * @param page
     * @param pageNums
     * @return
     */
    public List<SqShare> queryShareByName(String name, int page, int pageNums) {
        try {
            return userDao.selectShareByUserName(name, page, pageNums);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int querySharePages(String name) {
        try {
            return userDao.selectSharePages(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteShearById(String id) throws BizException {
        try {
            userDao.deleteShearById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BizException("删除失败");
        }
        throw new BizException("删除成功");
    }

    public void changeUserHead(String head, String name) throws BizException {
        // System.out.println(head);
        try {
            userDao.updateUserHead(head, name);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BizException("系统错误 ！");
        }
        throw new BizException("修改成功 ！");
    }

    /**
     * 会员充值
     *
     * @param money
     * @param name
     */
    public void changeUserVipDate(String money, String name) throws BizException {
        try {
            if (money.equals("15") || money == "15") {
                userDao.vipOneMonths(name);
            } else if (money.equals("30") || money == "30") {
                userDao.vipThreeMonths(name);
            } else if (money.equals("99") || money == "99") {
                userDao.vipYears(name);
            } else {
                throw new BizException("系统故障 请联系管理员");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BizException("系统故障 请联系管理员");
        }

    }

    public void isVipBiz(String name) throws BizException {
        try {
            if (userDao.isVip(name)) {
                throw new BizException("0");
            } else {
                throw new BizException("1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new BizException("系统错误");
        }
    }

    public void changeUserHeadVip(String head, String name) {

    }
}
