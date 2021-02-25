package biz;

import java.sql.SQLException;

import biz.BizException;
import  util.Utils;
import dao.ShareDao;

public class ShareBiz {

	private ShareDao sd=new ShareDao();
	public void addshare(String songname,String singername,String format,int heat,String download,String uname) throws BizException {
		Utils.checkNull(songname, "歌曲不为空");
		Utils.checkNull(singername, "歌手不为空");
		Utils.checkNull(format, "形式不为空");
		Utils.checkNull(heat, "热度不为空");
		Utils.checkNull(download, "下载地址不为空");
		Utils.checkNull(uname, "亲先登录一下哦~~！");
		try {
			if(sd.selectcnt(singername, songname, uname)>=1) {
				throw new BizException("亲你已经分享过该歌曲了 快去我的分享看看吧");
			}else {
				sd.insertshare(songname, singername, format, heat, download, uname);
			}
		} catch (SQLException e) {
			throw new BizException("系统繁忙 请稍后再试");
		}
	}
}
