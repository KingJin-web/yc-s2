package biz;

import java.sql.SQLException;

import biz.BizException;
import util.Utils;
import dao.OrdersongsDao;

public class OrderSongBiz {

    private OrdersongsDao od = new OrdersongsDao();

    public void create(String uname, String singername, String songname, String money) throws BizException {
        Utils.checkNull(uname, "请先登录");
        Utils.checkNull(singername, "选择歌手");
        Utils.checkNull(songname, "歌曲名不为空");
        Utils.checkNull(money, "金额不为空");
        try {
            if (od.listcnt(uname, singername, songname) >= 1) {
                throw new BizException("亲 你已经购买过该歌曲了哦! 快去看看吧");
            } else {
                od.insert(uname, singername, songname, money);
            }
        } catch (SQLException e) {
            throw new BizException("系统繁忙 请稍后再试");
        }

    }
}
