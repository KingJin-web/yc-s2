package biz;

import  biz.BizException;
import  util.Utils;
import dao.LyDao;

import java.sql.SQLException;


public class LyBiz {

    private LyDao ld = new LyDao();

    public void insertly(String singername, String songname,String uname,String content) throws BizException {
        Utils.checkNull(uname, "用户名不能为空");
        Utils.checkNull(content, "内容不能为空");
        Utils.checkNull(songname, "歌名不能为空");
        Utils.checkNull(singername, "歌手不能为空");
        try {
            ld.addly(singername, songname, uname, content);
        } catch (SQLException e) {
            throw new BizException("系统繁忙请联系管理员");
        }


    }
}
